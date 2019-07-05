// 完整正式名称【别修改，新项目修改成新名字】
env.APP_CHINESE_NAME = "项目名称"
// 英文名字，用于产物命名【别修改，新项目修改成新名字】
env.APP_ENGLISH_NAME = "Project Name"
// 是否需要执行单元测试
env.IS_NEED_UNIT_TEST = false
// QA 自动化测试 Job 名称，不为空就会触发
env.AUTOMATION_TEST_CI_NAME = isOnline()
// 是否启动静态代码检查
env.IS_USE_CODE_CHECK = isOnline()
// 文件存档基础路径
env.ARTIFACTS_BASE_PATH = "${params.ARTIFACTS_BASE_PATH}"



// 可以写逻辑判断函数，可以调用job界面传入的参数
def isOnline() {
	if("${params.BRANCH}" == "master"){
		return true;
	} else {
		return false;
	}
}
