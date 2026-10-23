# Agent Note: 官方对齐、Developer 指南与 PR 笔记流程

Status: implemented

## Problem

独立库缺少可随仓库交付的开发规范和关键取舍。只保留聊天或 Metric 外部路径会使后续开发者重复采用已否定算法、误改已批准特化，或在没有效果证据时声称原厂一致。

## Decision

[Developer 指南](../../../../DEVELOPER.md) 持有开发与评审契约，AGENTS 持有执行权限，库 README 持有装配契约，代码/构建配置持有参数。新增 COUI 能力要求以指定官方版本为基准，效果至少 1:1，尽可能采用相同算法、执行顺序和运动语义；无法等价的公开承载差异必须明确。

从 Metric SPEC 与核心 Note 整理分层、渐进卷积、普通阴影、菜单会话与层序、顶栏搜索、滑条六个领域。阴影合并原网格和后续 clipShader 决定；搜索合并槽位捕获、兄弟透明前景和焦点边界。保留首次决定日期、来源、重要备选和验证限制，不复制全部业务规范、本机绝对路径、设备数据或历史参数表。Metric 原文保留，不自动切换其依赖。

[实现分析与复刻指南](../../../../docs/coui-replication-guide.md) 从 Metric 的 `project-doc/coui-replication-guide.md` 迁入。保留分析流程、典型链路和排错经验；宿主组件、Design System、记录业务和本机资料目录改为本库的模块边界、Demo 与独立材料交接方式。专题链接只指向本仓库已有代码和 Note，不依赖未迁入的研究档案。README、Developer 与 AGENTS 提供对应阅读入口。

项目级安装 write-notes-like-deepseek Skill、参考文档、模板和脚本，团队不依赖个人安装。仓库规则及用户授权优先，通用确认流程不产生重复审批。非平凡变更检索历史后更新或新增 Note，与实现同一个 PR；纯机械修改不立 Note。

PR 模板要求官方依据、API 影响、Note、实际检查和待验收项；变更通过功能分支与 PR 进入 main。CI 使用 Node 类型擦除执行已有静态校验器，无需 npm 安装，不执行 Android 构建或发布。远端保护由维护者配置，本地文档不能代替服务端强制设置。

## Alternatives considered

- **只链接个人 Skill 或 Metric 路径。** 文件少，但其他开发者克隆后无法独立执行，也难以判断库适用边界。
- **原样复制整个 SPEC 与全部 Note。** 历史完整，却混入 Binder、服务和业务约束；只迁入仍约束当前实现的决定。
- **只有“必须写 Note”的文字要求。** 接线最少，但格式、状态和归档封印容易失效；复用脚本检查结构，语义和效果留给评审。

## Consequences

团队可在仓库内阅读与验证同一规则，代价是契约改变时同步对应文档。迁入稿独立维护，不与 Metric 自动同步，也不能按旧快照改回当前参数。新规范不撤销已有批准特化。

当前用户暂缓 Android 构建、外部消费、许可证和发行准备，本次不增加这些工作。未验收项目明确记录，不将开发规范当作效果保证。仓库没有远端，本次仅在本地功能分支准备差异，不创建远端 PR、不 push 或 merge；本地脚本通过也不表示 GitHub CI 已运行。

本地 Node.js 校验通过全部十篇 Note 的目录、状态、格式、相对链接和归档检查；当前没有归档条目。新增文件格式与 BOM、项目文档相对链接、workflow YAML 和差异检查通过。CI 命令与本地使用同一脚本；未触发远端工作流。

## Existing-note audit

- [独立工程](../architecture/2026-10-23-standalone-coui-demo.md)、[Demo 展示](../feature/2026-10-23-demo-material-and-gallery.md)：部分重叠，保留原迁出/展示决定，互链；本篇建立开发协作方式。
- [公开边界收口](../architecture/2026-10-23-public-api-boundary.md)：部分重叠，运行时边界单独持有，互链。
- 没有同范围旧流程 Note、待执行提案或待归档记录，不建立索引或空目录。
