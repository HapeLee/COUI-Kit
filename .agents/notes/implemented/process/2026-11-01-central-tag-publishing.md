# Agent Note: Maven Central 按版本 Tag 自动发行

Status: implemented

## Problem

两模块已有可验证的 Maven 产物，Central 发行仍需维护者在本地签名、组装 ZIP、上传并手动发布。源码版本、上传状态和消费者实际可下载状态由人工串联，重复操作容易遗漏适配模块或误将验证完成当作发行完成。

## Decision

[Central 工作流](../../../../.github/workflows/publish-central.yml)只响应上游仓库发行 Tag。Tag 指向已合入 main 的提交，版本由 Tag 去掉首字母 v 得到并同时传给两个模块。稳定版和 alpha/beta/rc 的接受范围由[版本解析器](../../../../.github/scripts/central-release-version.cjs)维护；开发 SNAPSHOT 不进入此路径，根开发版本不随发行修改。

先在没有发布凭据的步骤执行库 Lint、本地发行构建与现有产物检查。随后只在签名上传步骤注入 Portal User Token 和 ASCII-armored GPG 私钥，通过 Gradle 属性启用已有 Vanniktech 插件的 Central 与签名功能。普通 PR、Demo 和 Packages 仍使用原配置，不依赖这些凭据。签名步骤关闭 configuration cache，整个发布任务关闭 Gradle 缓存。

从根目录调用 `publishAndReleaseToMavenCentral`，使两个模块进入插件共享服务的同一部署。插件负责签名、打包、上传和状态轮询，不自建 API 客户端。等待 PUBLISHED 后，独立匿名下载两模块的发行文件和签名，全部成功才输出发行摘要。网络重试有界，失败保持失败；不自动重复提交部署或移动 Tag。

Tag 推送本身就是该版本的发行操作，不再增加 Portal 人工点击步骤。工作流文件须先合入 main；配置完成不等于已发行。使用说明、Secrets 格式和恢复方法归[发布指南](../../../../docs/development/publishing.md)。

## Evidence

- [Vanniktech 0.37.0 Central 文档](https://github.com/vanniktech/gradle-maven-publish-plugin/blob/0.37.0/docs/central.md)定义属性、内存签名和 PUBLISHED 等待方式。
- [共享发布服务](https://github.com/vanniktech/gradle-maven-publish-plugin/blob/0.37.0/plugin/src/main/kotlin/com/vanniktech/maven/publish/central/MavenCentralBuildService.kt)收集各项目产物并创建同一部署。
- [Central Publisher API](https://central.sonatype.org/publish/publish-portal-api/)区分验证、发布中和已发布；读取成功不是上传请求成功的同义词。

## Existing-note audit

- [GitHub Packages 预览发布](2026-10-25-github-packages-preview.md)：部分重叠，复用双模块产物检查；保留原快照渠道并互链。
- [Demo 签名与 CI](2026-10-25-demo-signing-ci.md)：部分重叠，沿用工具链和无凭据 PR 边界；不改变 Demo 触发或签名。
- [可选 M3 模块](../architecture/2026-10-31-optional-material3-style.md)：部分重叠，沿用两模块同版本及核心不依赖 M3 的约束。
- 开发指南、许可和组件记录不涉及 Central 自动发行决定；未发现须拒绝或归档的同范围提案。

## Alternatives considered

- **提交标题使用 publish 前缀。** 接近既有 Demo 操作，但需要额外解析提交消息并写 Tag；直接推送版本 Tag 能绑定源码且只需仓库读取权限。
- **自行打包 ZIP 并调用 Portal API。** 能控制每次请求，但会重复插件已有的聚合、签名和轮询职责；继续使用项目已安装的插件。
- **普通 main 提交自动发版。** 无需单独打 Tag，但每次合并都会产生不可覆盖的公开版本；发行意图由版本 Tag 明确表达。

## Consequences

维护者无需手动上传 ZIP，代价是首次使用仍要验证 Secret 内容、签名公钥分发和 Central 权限。Central 已发布版本不可覆盖；超时后须先检查原 deployment，不能把等待超时当作上传未发生。

版本解析器内置有效版本、错误格式、SNAPSHOT 和非 Tag 输入断言，PR 执行同一自检入口。静态检查不能证明签名及上传成功；本轮不创建发行 Tag，不执行 Android 构建或实际发布，完整链路以首次授权发行的 Actions 结果为准。
