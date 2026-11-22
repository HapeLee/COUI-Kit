# Agent Note: 顶栏渐进 Gaussian 与采样边界

Status: implemented

## Problem

顶栏曾使用多级 Kawase atlas 放大混合，出现粗网格。菜单使用 Fast Kawase 不能证明顶栏也使用该算法；没有采样器证据时，也不能把问题归因于 nearest filtering。

## Decision

完整顶栏的模糊参数见[材质分支](2026-11-21-coui-bar-sampling-and-material.md)。低层入口仍允许宿主配置渐隐方式。

TopAppBar 在相应平台通过 CouiGradientBlur 移植位置可变 Gaussian。横、纵两遍分别记录到原尺寸 GraphicsLayer，前一遍栅格化后再进入后一遍。保留整数核、对称权重、中心剩余权重和末端返回，不改成均匀模糊与原图混合。不进行 Bitmap 回读，也不在低分辨率图层重绘矢量源。

独立 Density 用于采样录制，不能让 DrawScope 成为自己的密度代理。背景源、坐标和裁剪继续复用 CouiBackdrop；渐进输出排除标题与按钮，按钮采样该输出并通过 parent 补足源区域外底图。

低层入口可使用固定逻辑渐隐长度、下沿锚定和 smoothstep。完整顶栏使用固定像素参考半径和全高幂曲线。折叠时完整背景只平移，不随当前栏高压缩。透明边界恢复有效样本颜色并保留中心 alpha。旧平台使用分层 Gaussian 和遮罩。

TopAppBarSurface 按背景模糊、颜色遮罩的顺序合成。背景淡入透明度分别用于两者，按钮背景源复用同一组绘制指令。原厂优先使用不透明的顶栏底色，再取页面底色。COUI 使用显式颜色、Scaffold 不透明底色或当前主题底色，并采用 GradientBackgroundDrawable 的线性节点。

API 33+ 在纵向卷积输出中完成遮罩混色，并在写入颜色目标前增加半个 8 位色阶的空间抖动。这样可将暗色渐变中落在同一色阶的整行分散到相邻色阶，覆盖模糊直接返回原像素的尾部。该抖动属于公开渲染路径的适配。透明遮罩继续使用原模糊输出，抖动保持输出覆盖率。

## Evidence

来源为原宿主的同名 Note 与顶部栏渐进模糊契约。原厂基准是 PJZ110 Contacts、OppoNote2、Calendar 的 AppBarBlurHelper.applyGradientBlurRenderEffect，经 framework 的 GradientBlur 进入 libhwui 的 OplusGradientGaussianBlurEffect。对应起止强度不同分支先横后纵，两遍共用竖向半径场，并绕过普通 Gaussian 的降采样分支。原厂独立背景 Drawable 的 positions/alphas 不是卷积核。

原厂 `OplusGradientGaussianBlurEffect::blur` 每次执行生成一个百分位噪声种子，横纵共用，默认除数为 200。公开 `RenderEffect` 保存 uniform 快照，因此 COUI 在重录背景时更新两遍的快照，复用 Shader 编译结果和第二遍的录制指令。系统对已录制图层的内部重绘时机由 HWUI 决定。

此证据仅限定该材料与分支，不证明任意画布矩阵或厂商缓存始终为屏幕原分辨率。原材料不作为本仓库运行依赖。当前算法与参数位置见 [CouiGradientBlur](../../../../coui/src/main/java/com/itos/coui/effect/CouiGradientBlur.kt)。

## Alternatives considered

- **提高 atlas 分辨率或补滤波。** 改动少，但保留没有原厂顶栏依据的多级近似。
- **平台均匀 Gaussian 后渐变混合。** 实现简单，原厂也有备用分支，但不是已确认的默认位置可变核路径。
- **反射厂商 RenderEffect。** 可以复用其执行器，但不满足跨品牌要求。
- **密集节点与平滑遮罩。** 连续斜率能弱化控制点处的接痕，但深色纯底在零斜率尾部会形成更宽的同色区域。少量线性节点也避开了部分 Skia GPU 后端的多节点纹理路径。
- **给遮罩画笔开启抖动。** 它影响遮罩输出，无法处理遮罩与背景混色后的所有量化边界。

## Consequences

透明中心的工作省略与固定第二遍指令复用见[绘制工作省略](../simplification/2026-11-01-render-work-elision.md)；本篇的核、完整采样范围和既有视觉适配保持。

移除粗网格放大来源，保留原尺寸卷积的采样成本。高 density 的强度适配会扩大卷积核和外沿，不能以图层减少宣称更快。

原工程的离线 Skia 对照覆盖核、边界、顺序、锚点、曲线和方差。公开接口通过重放正文取得背景，原厂直接对目标 Canvas 取样。中间目标格式、噪声更新时机与色带观感需要设备对照。
