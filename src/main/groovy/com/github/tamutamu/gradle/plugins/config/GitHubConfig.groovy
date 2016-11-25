package com.github.tamutamu.gradle.plugins.config

import org.gradle.api.InvalidUserDataException

class GitHubConfig {

	final API_BASE_URL = "https://api.github.com/"

	/** directory of git clone output */
	def String dir

	/** Github Organization Name */
	def String user

	/** Github Organization Name */
	def String org

	def apiBasePath() {
		if(user) {
			"https://api.github.com/users/${user}"
		}else if(org){
			"https://api.github.com/orgs/${org}"
		}else{
			throw new InvalidUserDataException("Missing user or org!")
		}
	}
}
