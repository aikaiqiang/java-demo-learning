> [CHANGELOG](https://keepachangelog.com/en/1.0.0/) 更新日志通用格式

注意：按照最新的版本号在前的顺序排列

- `title`：标题部分使用固定的文案：「更新日志」
- `version`：版本号 `version` 即项目的每一个发布版所使用的版本号。版本号需遵循 [SemVer](https://www.jianshu.com/p/622b5f57b965) 版本号命名规范，注意：版本号前不要加 v
- `date`：发布时间 `date` 即版本发布时的所在日期，日期采用 `yyyy-MM-dd` 的格式
- `type`：更新类型 `type` 用以说明更新的方面。这里的 `type` 和 `svn` 提交日志中的 `type` 有所联系，然而并不一一对应，同前面提到的「标题」部分，默认使用中文版本的词汇，`type` 的可选值如下：
    - （Features）：新增功能。
    - 修复（Fixed）：修复 bug。
    - 变更（Changed）：对于某些已存在功能所发生的逻辑变化。
    - 优化（Refactored）：性能或结构上的优化，并未带来功能的逻辑变化。
    - 即将删除（Deprecated）：不建议使用 / 在以后的版本中即将删除的功能。
    - 删除（Removed）：已删除的功能
- `desc`： 描述内容，比如：注意事项，修复说明，升级说明
```
# title [<「更新日志」>]

## [<version>] - <date>

### <type>
* <desc>
* <desc>

### <type>
* <desc>
* <desc>
```
```
# 「更新日志」

## [6.2.4] - 2018-01-10
### 变更
* `Node.fn.map()` 之前返回 NodeList 自身，现在将正确地返回被 map 后的数组。

### 修复
* 修复在非 ks-debug 模式下仍然输出 `KISSY.log()` 控制台信息的问题。

### 修复
* 修复 `KISSY.getScript` 在传入了 `timeout` 参数后报错的问题。[#12]

### 新增
* node 模块增加 API `Node.fn`，以兼容传统 KIMI 的 node 对象扩展机制。 
* ua 模块现在可以识别 Microsoft Edge 浏览器。

### 优化
* `KISSY.getScript()` 从 loader 模块中独立出来，io 模块不再依赖 loader 模块。

### 已删除
* io 模块默认去掉了对 XML 的 converter 支持。
```


