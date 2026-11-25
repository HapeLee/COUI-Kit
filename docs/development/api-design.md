# 公开 API 设计

本规范面向库维护者，用于约束新增或评审公开接口时的调用设计、参数契约与失败语义。

## 可见性与命名

- 默认声明为 `internal` 或 `private`，仅为真实外部需求公开接口。
- 公开 UI 组件不带 `Coui` 前缀（如 `Text`、`Switch`、`Menu`、`Scaffold`），通过包名区分来源。配置类型、Defaults、Style、状态、`CouiTheme`、`rememberCoui…` 工厂与 `coui…` 效果保留前缀；同文件混用同名组件时用 `import as`。
- 不为测试或 Demo 暴露内部装配（菜单滚动条坐标、Popup 定位、动效帧、搜索运动、底栏附件控制器）。
- 变更公开声明前先检索库、Demo 与已知消费者；改名、改参或改生命周期时，同步更新使用文档、调用方及迁移说明。

## 零配置优先与默认外观

- 省略 `dark`、`colors`、`style`、`material`、`backdrop` 等外观参数时，组件必须直接呈现当前主题（原生或 Material 3）下的推荐默认外观。
- 禁止要求调用方自行拼装系统版本判断、窗口背景源读取或 Defaults 工厂才能获得默认样式。
  > [!NOTE]
  > 现状特例：`Dialog` 系列当前 `material` 默认为 `null`（开启材质需显式传入），属于历史遗留；新增浮层组件须默认读取可用窗口源与系统能力，不要复制该特例。
- 主操作、强调色等主题角色必须默认读取当前主题，不得因调用方省略配色而抛出异常；显式传入的外观覆盖始终优先。
- 固定预设集中归属对应组件族 Defaults（`CouiXxxDefaults`），宿主不复制内部算法参数。

## 受控状态、显隐与交互归属

- 业务状态统一采用「只读值 `T` + 回调 `(T) -> Unit`」或显式受控状态对象（`CouiXxxState`）。**禁止新增接收 `MutableState<T>` 由组件内部直接改写的公开参数**（`BottomSheet` 为历史特例，新组件不得效仿）。
- 需要退场动画或采样同步的浮层与展开组件，统一常驻组合，通过 `visible: Boolean` / `expanded: Boolean` + `onDismissRequest: () -> Unit` 控制；不设“传 `null` 切换另一套窗口动画”的双模分支。
- 禁用态、加载态的交互拦截应完整收敛在组件内部；组合型行组件须清楚界定“外层承担交互时子控件仅负责绘制”的契约，避免把交互源与防连点样板推给每个调用方。

## 标准参数排序与参数归属

公开 Composable 遵循统一参数顺序，保留既有顺序不变：

1. `modifier: Modifier = Modifier`（首位且默认 `Modifier`）
2. 必填业务状态或数据（如 `value`、`checked`、`items`）
3. 必填事件回调（如 `onValueChange`、`onDismissRequest`）
4. 行为开关（`enabled`、`loading` 等）
5. 环境依赖（`backdrop`、`renderer` 等；需要时支持自动解析或显式降级）
6. 外观覆盖（`dark`、`colors`、`style`、`shape`、`material` 等；超过 3 个同类参数收敛为 `CouiXxxColors` 或 `CouiXxxStyle`）
7. 尾随内容槽位（`content`、`title` 等）

需要后置参数参与自动解析时，仅对相关配置使用 `Unspecified` 或可空默认值，不批量改成 `null`。

| 职责 | 承载方式 | 示例 |
| --- | --- | --- |
| 当前节点的外部布局与独立装饰 | 标准 Modifier 或 COUI 效果入口 | 外部留白、固定尺寸、整组选择线 |
| 内部多区域共享的状态与样式 | 组件参数或 Defaults | `enabled`、数值范围、字号、状态配色、面板形状 |
| 多子节点排列与业务联动 | 组件组合或内容槽位 | 小时与分钟联动、确认草稿、自定义行内容 |

- `enabled` 和 `onClick` 属于交互组件核心参数，不改为 Modifier；影响内部测量或运动的字号、范围、`wrap` 等保持参数形式。不新增 `couiWidth` 等标准 API 包装层，也不新增仅转发参数的包装组件。
- 调用方 `Modifier` 仅在根布局应用一次，内部默认尺寸随后追加并服从父级约束；背景、阴影、裁剪与内边距顺序按实际绘制层级设计。
- 同一几何应同时提供裁剪与效果所需参数：`CouiShape` 自动提供圆角半径与权重，任意 `Shape` 无法反推时保留明确的高级配置参数。
- Compose Modifier 顺序与自定义节点原则见 [Compose Modifier 规范](https://developer.android.com/develop/ui/compose/modifiers)。

## 平台门槛、资源所有权与失败语义

- 区分两类错误并在 KDoc 写明边界：
  - **开发期契约错误**（空列表、越界下标、必填无障碍描述缺失、非法数值范围、未实现组合如 `RoundRect` 开启 `clipping = true`）：在入口处通过 `require` / `check` 立即失败，并附简体中文上下文。
  - **运行时环境能力**（系统版本、采样源、GPU/Shader 编译）：高版本入口标 `@RequiresApi`；同族组件统一“无采样源回退纯色 vs 必须有源”策略；可变 `renderer` 构造失败必须向宿主暴露，禁止吞错或静默换算法。
- 常规入口与底层自定义（如 `couiShadow` 与 `CouiShadowRenderer`、`CouiMetaballRenderer`、单主体 `CouiFloatingRenderer.standalone()` 与三主体 `CouiFloatingRenderer()`）共用同一后端，不拆简单版与高级版两套组件。
- 可变 `renderer`、图层与采样坐标归具体节点独立持有，禁止跨节点复用或通过复制返回的 Modifier 隐式共享。开发诊断入口 `CouiRenderDiagnostics` / `renderDiagnosticProbe` 仅在主线程按需启停，不作为业务状态。

## API 评审自查清单

1. 未被真实外部场景需要的符号是否均收窄为 `internal` / `private`？
2. 省略所有外观可选参数时，是否直接呈现出推荐的主题外观（原生或 M3）？
3. 状态是否遵循「值 + 回调」或受控 State，且未新增 `MutableState<T>` 传参？
4. 参数顺序是否对齐标准模板，且未出现重复参数、包装层或错误的 Modifier 归属？
5. `require` / `check`、API 门槛注解、无采样源策略与 `renderer` 失败处理是否清晰且与同族一致？
6. 公开变更是否同步了库内调用方、Demo、使用文档与迁移说明？

历史收口决策见[API 收口 Note](../../.agents/notes/implemented/architecture/2026-10-23-public-api-boundary.md)。
