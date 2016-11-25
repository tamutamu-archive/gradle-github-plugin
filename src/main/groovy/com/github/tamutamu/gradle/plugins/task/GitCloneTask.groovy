package com.github.tamutamu.gradle.plugins.task

import org.ajoberstar.grgit.Grgit
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import com.github.tamutamu.gradle.plugins.config.GitHubConfig
import com.github.tamutamu.gradle.plugins.util.GithubApi

class GitCloneTask extends DefaultTask {

	GitHubConfig config

	@TaskAction
	void runTask() {
		try{
			def cloneUrls = new GithubApi(config).getCloneUrlAll()
			def repoDir
			cloneUrls.each {
				repoDir = it.find(/[^\/]+(?=\.git)/)
				Grgit.clone(dir: new File(config.dir, repoDir), uri: it)
			}
		}catch(Exception e){
			e.printStackTrace()
		}
	}
}