node {
	try{
		stage('检出代码'){
			// 加载配置
			loadProjectConfig();
			echo "项目: ${APP_CHINESE_NAME}，代码检出..."
	    	// 从仓库获取代码
	    	checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: ' ', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: '${C_SVN_PATH}']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
		}
		stage('静态代码检测'){
			echo "项目: ${APP_CHINESE_NAME}，静态代码检测..."
	   		if (Boolean.valueOf("${IS_USE_CODE_CHECK}")) {
	   			echo "需要静态代码检查"
	   		} else {
	   			echo "不需要静态代码检查"
	   		}
		}
		stage('单元测试'){
			echo "项目: ${APP_CHINESE_NAME}，单元测试..."
	   		if (Boolean.valueOf("${IS_NEED_UNIT_TEST}")) {
	   			echo "需要进行单元测试"
	   		} else {
	   			echo "不需要进行单元测试"
	   		}
		}
	   	stage('编译打包镜像推送'){
	   		//这里是构建，你可以调用job入参或者项目配置的参数，比如：
	   		echo "项目: ${APP_CHINESE_NAME}，编译打包..."
	   		withMaven(jdk: 'jdk1.8', maven: 'maven3.5.3') {
	            sh "chmod 775 -R ./**"
				sh "sh ./src/bin/scripts/docker_images_clear.sh"
	            sh "mvn clean package -Dmaven.test.skip=true"
	        }
	   	}
	   	stage('归档成品'){
	   		echo "项目: ${APP_CHINESE_NAME}，文件存档..."
	       	def jar = getShEchoResult ("find ./ -name '*.jar'")
	       	def artifactsDir="${ARTIFACTS_BASE_PATH}/artifacts/${JOB_NAME}"//存放产物的文件夹
	       	sh "pwd"
	       	sh "ls"
	       	archiveArtifacts artifacts: "**/target/*.jar", fingerprint: true
	       	echo "文件存档路径：${artifactsDir}"
	       	sh "rm -rf ${artifactsDir}/*"
	        sh "mkdir -p ${artifactsDir}"
	       	sh "mv ${jar} ${artifactsDir}"
	   	}
	   	stage('通知负责人'){
	   		emailext body: "构建项目:${BUILD_URL}\r\n构建完成", subject: '构建结果通知【成功】', to: "${EMAIL_MASTER}"
	   	}
	   	stage('演示环境部署'){
	   		//当前构建是否成功
	   		if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
				
			}
	   	}
	   	stage('通知相关人员'){
	   		emailext body: "最新项目演示环境:${BUILD_URL}\r\n构建完成", subject: '构建结果通知【成功】', to: "${EMAIL_ALL}"
	   	}
	} catch (e) {
		emailext body: "构建项目:${BUILD_URL}\r\n构建失败，\r\n错误消息：${e.toString()}", subject: '构建结果通知【失败】', to: "${EMAIL_MASTER}"
	} finally{
		// 清空工作空间
        cleanWs notFailBuild: true
	}
}
 
// 获取 shell 命令输出内容
def getShEchoResult(cmd) {
    def getShEchoResultCmd = "ECHO_RESULT=`${cmd}`\necho \${ECHO_RESULT}"
    return sh (
        script: getShEchoResultCmd,
        returnStdout: true
    ).trim()
}

//加载项目里面的配置文件
def loadProjectConfig(){
    def jenkinsConfigFile="../${JOB_NAME}@script/src/bin/jenkins/scripts/jenkins.groovy"
    if (fileExists("${jenkinsConfigFile}")) {
        load "${jenkinsConfigFile}"
        echo "找到打包参数文件${jenkinsConfigFile}，加载成功"
    } else {
        echo "${jenkinsConfigFile}不存在,请在项目${jenkinsConfigFile}里面配置打包参数"
        sh "exit 1"
    }
}
