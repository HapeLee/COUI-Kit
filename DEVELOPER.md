# COUI 开发指南

本指南约束组件开发和评审。执行权限见 [AGENTS.md](AGENTS.md)，组件契约见 [库说明](coui/README.md)，宿主接入见 [接入指南](docs/INTEGRATION.md)。参数以代码和构建配置为准，不在文档维护另一份参数表。

分析新组件或定位复刻差异时，按 [实现分析与复刻指南](docs/coui-replication-guide.md) 确认实际消费者、算法与绘制链，再进入对应代码和 Note。

## 模块归属

`coui` 持有通用组件、效果、Shader、几何、输入、动画和预设，只依赖 Android、Compose、AndroidX。主题、HCT、应用圆角、偏好、导航、错误展示和业务状态由宿主装配。新增组件不得反向读取 Demo 或 Metric。

`demo` 必须只使用公开 API，展示范围以 README 和当前任务为准。所选示例覆盖深浅色、启停和主要交互状态，全部数值滑条使用 CouiSlider；不因库仍提供底层能力就恢复已取消的独立效果分组。不得为演示公开内部状态。Demo 的 M3 部分沿用已迁入的 Metric 规范：页面与卡片分层、G2 形状、正确的开关/单选/复选语义、整行交互单一 owner。Material 横屏使用侧栏，COUI 保持底栏。

## 官方 1:1 对齐

**新增 COUI 能力必须以官方实现为基准，效果至少达到 1:1，尽可能采用相同的算法、参数语义、执行顺序和状态转换。** “看起来相近”不能作为验收标准。这里的官方指选定版本的 COUI/Oplus 原厂实现；Demo 中的 M3 控件按相应 AndroidX 官方接口与规范装配。

开发前先确定对应 APK、framework 或 native 库的版本、构建标识或哈希。记录真实调用入口、资源覆盖和实际启用的分支。优先读取官方源码；没有源码时，通过原始 APK、JAR、SO 与实际行为定位。证据可以记录类名、方法、资源名、Shader 片段和二进制偏移；偏移只对指定材料有效。原始材料不是运行依赖，不提交设备数据、整包反编译产物或本机绝对路径。

先还原可见数据链，再选择 Compose 承载。核对单位与坐标、采样网格、两遍/多遍顺序、混合模式、预乘 alpha、裁剪覆盖率、量化位置、弹簧速度连续性、取消和释放。不能因为名称同为 blur 或 spring 就用另一种算法替换；不能用均匀模糊加渐变代替已确认的位置可变卷积，或用连续公式替换已确认的离散查表。

优先使用 Android/Compose 公开 GPU 接口承载相同计算，不依赖 OEM 私有 SDK、私有 HWUI 符号或厂商 SO。遇到公开 API 无法表达的路径，先记录具体差异及对效果的影响，再提出方案；未获当前任务授权，不自行换算法、削减交互或声称完成 1:1。优化也受此约束，不能以层数减少代替效果或性能证据。

验收使用同一原厂基准，并对齐尺寸、density、深浅色、字体和输入序列。检查静止、按压、拖动、快速反向、松手、CANCEL、隐藏/恢复、RTL、键盘和无障碍。视觉对照覆盖轮廓、材质、模糊、阴影及动画中段；性能结论需要可复现的设备数据。静态检查、桌面 Shader 编译或参数一致不等于 GPU 像素一致。未执行的项目在 PR 中标为待验收，不能写成已通过。

现有实现包含用户已批准的特化，例如菜单组合描边、顶栏强度/收起速度和宿主 G2 形状。它们是已有契约，不在本次文档迁移中改回。当前差异见 [核心实现笔记](#核心实现笔记)；后续新增差异必须单独说明和评审，不得将既有特化表述为官方默认。

## 公开 API

默认使用 `internal` 或 `private`。公开入口应能解释使用场景、状态 owner、平台门槛、单位、失败行为和资源生命周期。组件通过受控值与回调接入，不接管宿主业务。`Modifier` 为第一个参数并默认 `Modifier`。

保留高自定义程度的底层算法接口；常用入口复用同一后端，不再造效果。普通阴影保留 `couiShadow` 与 `CouiShadowRenderer`，双主体融合只公开算法和跟随回弹，不加入侧滑容器。预设集中在所属组件族 Defaults，宿主不复制固定参数。

菜单滚动条坐标、拖动状态、Popup 定位、菜单动效帧、搜索运动实现和底栏附件控制器属于内部装配。不要为了测试或 Demo 暴露它们。公开预设弹簧仍可直接引用。菜单模糊从 `rememberCouiMenuBlur` 获取，不让宿主组装固定数量的 GraphicsLayer。

修改公开声明前检索库、Demo 和已知消费者。改变可见性、参数、单位或生命周期时，同步调用与接入文档，并在 PR 写明迁移方式。当前尚未发行，不承诺已稳定的二进制 ABI；这不是随意破坏已知调用的理由。

## 实现习惯

- 保持单一状态 owner；背景、效果和业务状态分开。每个绘制节点独立持有可变 renderer。动画、监听和图层随对应节点释放，不建立全局历史缓存。
- 输入观察器不能抢占点击或滚动；普通触摸提交只有一个 owner。CANCEL 不提交业务动作。保留键盘、焦点、TalkBack 和禁用态。
- 不吞错误、不模拟成功、不新增静默降级。已有平台兼容路径保留明确语义；Shader 创建失败交给宿主记录并决定替代 UI。协程捕获 `CancellationException` 后重抛。
- 不新增 `!!`。无状态预设使用 `object`；注释解释依据和约束，不复述代码。源码使用 UTF-8 无 BOM。KDoc、开发文档、提交与 PR 使用简体中文。
- 用户文案进入所属模块的字符串资源，默认英文并同步已有简体中文。库不依赖应用日志框架；需要诊断时用明确回调，避免逐帧日志。
- 不为单个组件升级无关依赖、引入另一套主题或样式框架。Styles API 仅在任务需要时使用，不能把实验性 opt-in 当作全库迁移授权。

## Note 工作流

使用仓库内的 [write-notes-like-deepseek Skill](.agents/skills/write-notes-like-deepseek/SKILL.md)。不依赖某位开发者的全局安装。行为、架构、跨文件契约、开发流程、验证策略或数据格式改变时必须写或更新 Note；纯格式、无歧义重命名和不改行为的局部机械修改不新建 Note。

动手前搜索活跃决定：

```sh
rg --hidden --glob '!.agents/notes/archived/**' '组件名|机制名' .agents/notes/
```

同一决定的路径、符号等事实就地更新。理由翻转则新建并互链，不覆盖旧理由。新建时将命中项分类为无关、部分重叠、完全吸收或过时提案，并记录处理结果。只建立实际使用的生命周期/分类目录，不建立 INDEX。

Note 与实现同一个 PR。正文说明问题、当前决定、真实备选、代价和验证限制；引用当前源码位置，避免复制可变参数。尚未实施用 proposed，落地后移入 implemented 并同步状态，不能保留已完成的计划正文。用户当前授权和仓库执行规则优先于 Skill 的通用确认流程，不额外增加确认门。

本地使用 Node.js 24 执行静态校验，无需 npm 安装：

```sh
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-agent-note-tree.ts
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-agent-note-format.ts
node --experimental-strip-types .agents/skills/write-notes-like-deepseek/scripts/verify-archived-agent-notes.ts
git diff --check
```

归档校验默认对照 HEAD；检查整个分支时，将 `AGENT_NOTE_ARCHIVE_BASE_REF` 设为目标分支的共同基点。PR CI 自动传入 PR 的 base SHA。校验检查结构、相对链接、状态和归档封印；代码与效果仍需评审。

## 分支与 PR

开发在功能分支进行，自动代理默认使用 `codex/` 前缀。变更通过 PR 进入主分支；禁止直接 push 到 `main`，禁止代理自行合并或推送。用户授权本地开发不等于授权远端提交。创建分支、commit、push 和 PR 时仍遵守当前任务的操作授权。

PR 使用 [统一模板](.github/pull_request_template.md)，说明问题和最终行为、官方依据、公开 API 影响、Note、检查结果及待验收项。维护者应在远端启用主分支保护、PR 评审和 Note 检查；本地文档与 workflow 不能替代远端保护设置。

默认只做差异、引用和相关静态检查；不默认新增测试。Android 构建、Sync、Preview、安装、签名、截图、真机视觉和性能验证由用户执行。CI 当前只检查 Note，不执行 Android 构建或发布。新增非平凡分支或算法时，根据风险选择一项有针对性的检查，不增加镜像实现的测试。

## 核心实现笔记

下列笔记从 Metric 的 SPEC 和实现 Note 整理迁入，保留仍有效的决定、真实取舍和未验证边界。Metric 原文不删除，本仓库中的相对路径独立有效。

- [模块边界与算法归属](.agents/notes/implemented/architecture/2026-10-21-coui-module-boundary.md)
- [渐进 Gaussian 与采样契约](.agents/notes/implemented/bug-fix/2026-10-18-coui-topbar-gradient-blur.md)
- [普通阴影的网格、覆盖率与缓存](.agents/notes/implemented/bug-fix/2026-10-18-coui-native-shadow.md)
- [菜单的输入会话与合成层序](.agents/notes/implemented/bug-fix/2026-10-15-coui-submenu-input-dispatch.md)
- [顶栏 overlay、搜索与焦点边界](.agents/notes/implemented/bug-fix/2026-10-20-topappbar-search-transition-boundaries.md)
- [字体设置式滑条与已知物理边界](.agents/notes/implemented/feature/2026-10-21-coui-slider.md)

新增能力先从相关笔记下钻到代码，不把历史取样结果当作新版本的验收。
