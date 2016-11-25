package com.github.tamutamu.gradle.plugins.config

import org.gradle.listener.ActionBroadcast
import org.gradle.util.ConfigureUtil

class GitHubConfig {

	/** directory of git clone output */
	def String dir
	
	/** Github Organization Name */
	def String org
	
	/** including repository name */
	def String include
	
	def apiBaseUrl() {
		"https://api.github.com/orgs/${org}"
	}
	
	def Closure paramClosure

	def ActionBroadcast<String> paramClosureList = new ActionBroadcast<String>()

	def closureList(Closure closure) {
		paramClosureList.add(ConfigureUtil.configureUsing(closure))
	}
}
