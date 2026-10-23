# Agent Note: 普通阴影的网格、覆盖率与缓存

Status: implemented

## Problem

通用 Compose 投影不能表达原厂光源、两路网格、离散衰减和 inverse clip。只抄颜色也会遗漏完整 alpha 链路。减少承载层时若合并两路裁剪，会改变 SrcOver 的交叉项。

## Decision

CouiShadowRenderer 使用公开 Mesh 承载原 Skia 工厂与网格计算。fast 保留 A8 查表与线性采样，generic 保留原计算与凹形/失败分支；只有真实三角化失败才进入 blur，成功的空网格不是失败。普通阴影与 caustic 分开，Outline 不被材质 SDF 替换。

ambient、override spot 与节点 alpha 的运算顺序和整数截断位置保留。网格包含主体缩放，外层只平移，不重复乘节点透明度。每主体只缓存当前两路资源，不累计动画历史。ambient/fast spot 同时规范化缓存键与几何输入的局部原点，generic spot 保留窗口坐标与量化。

Android 15 起，正常 fast 胶囊使用公开 Canvas.clipShader 表达独立覆盖率裁剪，同时保留 Paint 中的原 BitmapShader。每次在投影平移后设置裁剪，save/restore 限制作用域，ambient/spot 独立绘制。Android 14 保留每路 RenderNode/RenderEffect。不得先合成两路再裁一次，也不得用硬 Path Difference 替换导数边缘。

内部节点同色复用前检查 hasDisplayList；系统丢弃后重录命令，但复用网格和效果。不要为此添加持续刷新或新的生命周期 owner。便捷 couiShadow 处理本地坐标与 dp，窗口变换、节点透明度、多主体继续使用低层入口。普通 RoundRect 明确拒绝 inverse clip。

## Evidence

从 Metric 的同名 Note、2026-10-22-coui-shadow-single-pass Note 及 SPEC 的普通投影契约合并整理。原始基准为 PJZ110 组件 OutlineProvider、ShadowUtils、COUIFABShadowUtils 和 libhwui 的 drawFastShadow/ShadowRRectOp，结合对应 Skia 源码核对。公开接口依据为 Canvas.clipShader 与 RenderNode.hasDisplayList 契约。

当前实现见 [CouiShadowRenderer](../../../../coui/src/main/java/com/itos/coui/effect/CouiShadowRenderer.kt)。菜单使用独立 LV4、普通 RoundRect 和关闭的 clipping；FAB 使用 Oval，底栏使用 fullsmooth Outline。用户已降低的透明附件强度继续保留，不作为原厂默认。

## Alternatives considered

- **继续使用经验 Gaussian 或只保留常见 fast/凸形。** 代码更少，但丢失离散表和真实缩放下的 generic、凹形及失败路径。
- **所有系统保留两层滤镜。** 外观已有依据，但新公开 Shader 裁剪可以表达相同的独立覆盖率，无须为正常 fast 路径继续保留这两层中间图。
- **先合成再裁剪，或 Mesh 内二分查表。** 前者层数少但改变混合；后者能省滤镜但增加片段分支和 uniform 表，公开裁剪已允许保留原纹理。
- **调用 OPlus 私有 HWUI 接口。** 接近原承载，但不能满足跨品牌使用。

## Consequences

新版正常 fast 胶囊省去两层滤镜，旧平台仍有其成本；中间量化和导数变化需要设备对照。公开 Mesh 没有完整厂商精度能力探测，不能承诺所有低精度 GPU 与原 half varying 等价。任意透视、三维光源与全局历史缓存不在当前接口范围。

原工程曾执行原库几何/alpha 对照、缓存与版本路由的 JVM 替身检查，并采集过有限设备样本。样本范围与操作速度有限，不是固定性能收益。本仓库迁入的是决定与边界，本轮未重跑原库或设备对照，不把历史检查算作当前构建验证。
