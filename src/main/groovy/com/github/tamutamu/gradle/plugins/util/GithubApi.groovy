package com.github.tamutamu.gradle.plugins.util

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.*
import groovyx.net.http.HTTPBuilder

class GithubApi {

	final API_BASE_URL = "https://api.github.com/"

	def org

	def http
	
	def slurper = new JsonSlurper()

	GithubApi(org) {
		this.org = org
		http = createHttp()
	}
	
	def createHttp() {
		def buildHttp = new HTTPBuilder(API_BASE_URL)
		
		// setting proxy.
		def httpProxyHost = System.getProperty("http.proxyHost")
        def httpProxyPort = System.getProperty("http.proxyPort").toInteger()
		
		if(httpProxyHost && httpProxyPort) {
		  buildHttp.setProxy(httpProxyHost, httpProxyPort, "http")
		}

		buildHttp
	}
	
	def getRepoList(page=1) {
		
		def resJsonText = http.request( GET, TEXT ) {
			uri.path = "/orgs/${org}/repos"
			uri.query = [ page: page, per_page : 10 ]
			
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
		  println page 
		  json += getRepoList(++page)
		  json
		}
		
	}
}
