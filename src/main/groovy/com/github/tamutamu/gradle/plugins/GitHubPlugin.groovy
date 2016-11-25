package com.github.tamutamu.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

import com.github.tamutamu.gradle.plugins.config.GitHubConfig
import com.github.tamutamu.gradle.plugins.task.GitCloneTask

class GitHubPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.task("gitClone", type: GitCloneTask) {
			config = project.extensions.create('github', GitHubConfig)
		}
	}
}
