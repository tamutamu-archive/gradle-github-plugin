package com.github.tamutamu.gradle.plugins.task

import com.github.tamutamu.gradle.plugins.config.GitHubConfig
import com.github.tamutamu.gradle.plugins.util.GithubApi;

import org.ajoberstar.gradle.git.base.GrgitPlugin
import org.ajoberstar.grgit.Grgit;
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GitCloneTask extends DefaultTask {
	
	GitHubConfig config

	@TaskAction
	void runTask() {
		try{
			
			def repos = new GithubApi(config.org).getRepoList()

			repos.each { println it }
			
			//Grgit.clone(dir: config.dir, uri: 'https://github.com/tamutamu/gradle-test.git')
			
		}catch(Exception e){
			e.printStackTrace()
		}
	}
}