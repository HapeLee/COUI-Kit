# Agent Note: 独立 COUI 工程与 Demo

Status: implemented

## Problem

Metric 的公共 COUI 源码已与项目装配分层，但仍依赖 Metric 的约定插件。需要一个可独立打开的库工程，并通过 Demo 展示外部宿主如何使用公开接口。

## Decision

在独立工程中设立 `coui` 与 `demo` 两个模块。库源码、资源和既有许可从 Metric 的公共模块原样迁入，保留包名和接口；使用本工程的 Android Library 与 Compose 配置，不引用原工程路径或约定插件。Demo 通过本地 project 依赖直接调用公开 API。

Demo 负责主题、形状、受控状态和页面导航，展示全部现有组件族、便捷阴影及融合算法。融合示例通过滑条驱动距离并显示背景与 DstIn 内容遮罩，不接入侧滑手势或记录业务。每个绘制节点独立持有 renderer，浮动组件在不透明内容采样源之外。

沿用目标工程现有 AGP、Gradle Wrapper 和 Gradle JVM 配置，使用 AGP 内置 Kotlin 并单独配置 Compose 编译插件。当前依赖与平台要求以构建文件和源码为准，不在笔记维护版本快照。

`demo` 作为测试载体，debug 与 release 均使用插件默认 debug 签名配置，构建时自动使用或生成本机默认测试密钥。签名材料不进入 Git。仓库记录源码、资源、构建配置、Wrapper 和开发文档，忽略各模块生成物及本地 IDE、Gradle、Kotlin 状态。首次提交不包含远端或发布接线。

用户明确暂缓 Maven 发布、Metric 依赖切换和公开发行。Metric 原模块保持原状，现阶段两份源码并存；后续消费切换再处理旧模块，不能提前删除。

## Alternatives considered

- **引用 Metric 的源码目录或约定插件。** 可以少复制配置，但新工程仍依赖外部工作区，无法独立维护。
- **同时配置发布和消费切换。** 可以消除暂时的源码副本，但超出本次授权范围。
- **把记录侧滑迁入库作为演示。** 会引入业务容器，违反仅迁出融合算法的职责边界。

## Consequences

库和 Demo 可在同一工程内开发。后续修改需要明确选择源码归属；Metric 尚未切换依赖，独立工程的改动不会自动进入 Metric。迁移后的当前职责和装配约束见根 README 与库 README，不复制原工程特有的规范链接。

本轮静态检查通过：迁出的源码、资源和许可共 65 个文件与原文件逐字节一致；71 个 Kotlin/KTS 文件通过 PSI 语法解析；Demo 的 41 个 COUI 导入均可公开访问，62 处调用符合参数声明；14 个 XML 文件、54 组双语资源及格式参数、TOML、启动入口、模块依赖、文档链接和格式检查通过。公开调用检查不解析 Android 类型。未执行 Android 构建、Gradle Sync 或设备验证，不能据此确认插件兼容、GPU 行为或视觉效果。
