package com.github.tamutamu.gradle.plugins.util

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import com.github.tamutamu.gradle.plugins.config.GitHubConfig

import groovy.json.*
import groovyx.net.http.HTTPBuilder

class GithubApi {

	GitHubConfig config

	def http

	def slurper = new JsonSlurper()

	GithubApi(config) {
		this.config = config
		http = createHttp()
	}

	def createHttp() {
		def buildHttp = new HTTPBuilder(this.config.API_BASE_URL)

		if(System.hasProperty("http.proxyHost") && System.hasProperty("http.proxyPort")) {
			def httpProxyHost = System.getProperty("http.proxyHost")
			def httpProxyPort = System.getProperty("http.proxyPort").toInteger()
			buildHttp.setProxy(httpProxyHost, httpProxyPort, "http")
		}

		buildHttp
	}

	def getCloneUrlAll() {
		getRepoObj().collect { it.clone_url }
	}

	def getRepoObj(page=1) {

		def resJsonText = http.request( GET, TEXT ) {
			uri.path = this.config.apiBasePath() + "/repos"
			uri.query = [ page: page, per_page : 30 ]

			headers.Accept = 'application/json'
			headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"

			response.success = { resp, text ->
				text.text
			}
			response.failure = {res -> println "error ${res.statusLine.statusCode}. ${uri.path}" }
		}

		def json = slurper.parseText(resJsonText)

		if(json.empty){
			[]
		} else {
			json += getRepoObj(++page)
			json
		}
	}
}
