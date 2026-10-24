# Agent Note: Demo 用记录侧滑展示融合

Status: implemented

## Problem

融合调参面板展示了算法，但距离滑条不能说明它在列表侧滑中的实际表现。用户要求用少量类似 Metric 的记录项展示动画，并进一步减少说明文案、长菜单数量和多余点击反馈。

## Decision

融合分组只保留三条示例记录。DemoRecordSwipeRow 适配 Metric 的记录侧滑链路，保留手势取消、释放速度、展开回弹、按钮跟随、长拉伸提交、点击确认和离场动画。固定宽度测量内容，避免卡片收缩时文本重新排版。侧滑与删除状态只属于 Demo，示例不读取、写入或删除真实记录。

DemoRecordMetaball 的背景与内容遮罩使用同一几何、各自独立的 renderer，保留离屏 DstIn 与 RTL 坐标转换。使用 Demo 表面颜色让记录与外层分组可区分。COUI 的算法、公开接口与依赖不变，库仍不提供侧滑容器。平台门槛沿用原融合示例，旧系统显示要求；创建失败明确显示错误。

记录使用稳定 key，单次只展开一项，离场完成后才移除示例，全部删除后提供恢复入口。卡片与操作按钮保留语义及键盘点击入口。两种采样面板移除前景文字，各数值滑条占满父级可用宽度；复选框行保留 toggleable 语义并关闭 indication。长菜单保留四组三项，通过高度上限触发滚动。普通菜单和子菜单继续可用。

## Alternatives considered

- **继续用滑条驱动融合。** 装配少，容易单独观察算法，但不能满足直接观察记录侧滑的要求；旧画布、开关、说明及专用资源一并移除。
- **把侧滑容器加入 COUI。** 宿主更容易接入，但会把删除确认与列表状态带入算法库；示例在 Demo 适配 Metric 链路，只消费公开算法。
- **重新写一个拖动位移示例。** 代码更少，但需要重新处理取消、速度、内容遮罩和 RTL，并可能丢失 Metric 已有的动画行为；保留原链路，只去除业务依赖和本示例不用的旧平台绘制分支。

## Consequences

Demo 可以观察真实触摸驱动的融合，示例删除仅影响页面状态。接口使用方法见 DemoRecordSamples、DemoRecordSwipeRow 和 DemoRecordMetaball；这些文件不构成库的公开 API。旧系统不创建 RuntimeShader。

静态检查通过：79 个 Kotlin/KTS 文件通过 PSI 语法检查，80 处可定位的 COUI/Demo 函数调用具名参数与声明一致；65 组双语资源及引用匹配。手势、回弹、离场、固定宽度与绘制链路已对照 Metric 源码，96 组坐标转换及菜单、滑条、稳定标识断言通过；Note 门禁和文档链接有效。临时检查器不加入仓库。

Android 构建、Preview、设备触摸、TalkBack 与 GPU 视觉结果由用户验证；PSI 不执行 Android 类型编译，静态检查不能证明原厂效果验收。

## Existing-note audit

- [Demo 精简](2026-10-24-demo-focused-showcase.md)：继续保留三页与精简展示范围，本篇补充融合交互方式，互链。
- [独立工程](../architecture/2026-10-23-standalone-coui-demo.md)：部分取代其滑条驱动融合的演示决定，保留原有理由和库边界，互链。
- [模块边界](../architecture/2026-10-21-coui-module-boundary.md)：算法与宿主职责不变，无需改写。
- [M3 装配](2026-10-23-demo-material-and-gallery.md)：语义、采样分层与状态 owner 仍有效，当前范围沿用其已链接的 Demo 精简决定。
- 其余活跃笔记与本次展示方式无关，没有被完全取代的决定或待处置提案。
