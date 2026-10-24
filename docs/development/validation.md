# 验证与评审

验证要回答本次修改可能改变的具体行为。先选能暴露差异的输入，再核对中间状态、结束和再次进入；检查范围随改动确定。

## 原厂对照与设备验收

[开发流程](workflow.md)规定完整实现后的双向评审。[复刻指南](replication-guide.md#6-验证原理不伪造验收)区分源码核对、原库执行、数学断言、语法检查和设备结果的证据范围。

视觉与输入验收对齐目标版本、尺寸、density、深浅色、字体和操作序列，检查动画中段。受影响时覆盖反向、未拖动的 UP、CANCEL、父容器接管、隐藏恢复、RTL、键盘和无障碍。性能结论附可复现的设备条件与数据。

权限由 [AGENTS.md](../../AGENTS.md)持有。未执行构建或设备检查时，在 PR 中记录未执行项、原因和需要用户验收的行为。

## 文档与静态检查

文档变更检查相对路径、章节锚点、目录可达、公开 API 示例、失效入口及编码。移动页面后，检查普通文档、PR 模板、AGENTS 和活跃 Note 的入站链接。

在仓库根目录用 Node.js 24 执行现有 Note 校验，无需安装 npm 依赖：

~~~sh
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-agent-note-tree.ts
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-agent-note-format.ts
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-archived-agent-notes.ts
git diff --check
~~~

Note 校验只检查结构、状态、相对链接和归档封印；代码与效果由评审验证。CI 当前执行 Note 检查，不执行 Android 构建或发布。根据风险选择必要的针对性检查，不建立镜像实现的测试或无关测试框架。

## 开发诊断

[CouiRenderDiagnostics](../../coui/src/main/java/com/itos/coui/diagnostic/CouiRenderDiagnostics.kt) 与 renderDiagnosticProbe 在主线程按需启停。诊断结果用于定位录制、缓存或绘制成本，不成为业务状态，不默认启用截图或逐帧日志。

提交前清理本任务产生的临时检查器、日志和实验入口，保留复现所需的证据及当前有效结果。实际执行的命令、输入、原库对照和待验收项写入 PR。
