job('test') {
	scm {
		git {
			remote {
				url('https://github.com/alandoni/xml-job-to-dsl')
				credentials('jenkins')
			}
			branch('*/${GIT_BRANCH}')
			extensions {
				wipeOutWorkspace()
			}
		}
	}
}