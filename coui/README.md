# COUI 公共接口

本模块只依赖 Android、Compose 和 AndroidX。宿主提供主题、Shape、受控状态、坐标及回调；库不读取 App 主题、偏好或日志，不持有业务导航。

| 能力 | 公开入口 |
| --- | --- |
| 背景采样 | [rememberCouiBackdrop / couiBackdrop](src/main/java/com/itos/coui/backdrop/CouiBackdrop.kt) |
| 普通阴影 | [Modifier.couiShadow / CouiShadowDefaults](src/main/java/com/itos/coui/effect/CouiShadow.kt)、[CouiShadowRenderer / CouiShadowOutline](src/main/java/com/itos/coui/effect/CouiShadowRenderer.kt) |
| 顶栏渐进模糊 | [CouiTopAppBar / CouiTopAppBarDefaults](src/main/java/com/itos/coui/topappbar/CouiTopAppBar.kt) |
| 顶栏按钮 | [CouiTopAppBarButton](src/main/java/com/itos/coui/topappbar/CouiTopAppBarButton.kt)、[CouiToolbarStyle](src/main/java/com/itos/coui/topappbar/CouiToolbarStyle.kt)、[CouiTopAppBarButtonDefaults](src/main/java/com/itos/coui/topappbar/CouiTopAppBarButtonDefaults.kt) |
| 搜索 | [CouiTopAppBarSearch / CouiTopAppBarSearchButton](src/main/java/com/itos/coui/topappbar/CouiTopAppBarSearch.kt)、[rememberCouiTopAppBarSearchState](src/main/java/com/itos/coui/topappbar/CouiTopAppBarSearchState.kt) |
| 底栏 | [CouiBottomBar / CouiNavigationItem](src/main/java/com/itos/coui/navigation/CouiBottomBar.kt) |
| FAB | [CouiFab](src/main/java/com/itos/coui/floatingactionbutton/CouiFab.kt)、[CouiFabDefaults](src/main/java/com/itos/coui/floatingactionbutton/CouiFabDefaults.kt)、[CouiFabColor](src/main/java/com/itos/coui/floatingactionbutton/CouiFabColor.kt) |
| 菜单 | [CouiMenu / CouiMenuItem](src/main/java/com/itos/coui/menu/CouiMenu.kt)、[CouiMenuStyle / CouiMenuDefaults](src/main/java/com/itos/coui/menu/CouiMenuStyle.kt)、[ProvideCouiMenuBackdrop](src/main/java/com/itos/coui/menu/CouiMenuBackdrop.kt) |
| 滑动选择条 | [CouiSlider](src/main/java/com/itos/coui/slider/CouiSlider.kt)、[CouiSliderDefaults](src/main/java/com/itos/coui/slider/CouiSliderDefaults.kt) |
| 复选框 | [CouiCheckbox](src/main/java/com/itos/coui/checkbox/CouiCheckbox.kt)、[CouiCheckboxDefaults](src/main/java/com/itos/coui/checkbox/CouiCheckboxDefaults.kt) |
| 横向双主体融合与遮罩 | [CouiMetaballBody / CouiMetaballRenderer](src/main/java/com/itos/coui/effect/CouiMetaballRenderer.kt) |
| 跟随回弹及预设 | [rememberCouiMetaballFollowingTranslation](src/main/java/com/itos/coui/motion/CouiMetaballFollowing.kt)、[CouiSwipeRevealDefaults](src/main/java/com/itos/coui/swipereveal/CouiSwipeRevealDefaults.kt) |
| 共用形状 | [CouiShape](src/main/java/com/itos/coui/shape/CouiShape.kt)、[CouiCapsuleShape](src/main/java/com/itos/coui/shape/CouiCapsuleShape.kt) |

## 装配契约

- 一个 `CouiBackdrop` 只挂到一个内容节点。源子树不得采样自身。浮动材质源须包含不透明页面；顶栏、底栏和 FAB 放在采样子树外。菜单通过 `ProvideCouiMenuBackdrop` 提供所属窗口的源，Popup 本身不进入源子树。
- 顶栏按钮、按钮组和搜索必须位于 `CouiTopAppBar` 的内容中。搜索按钮与搜索框共用同一个搜索状态；查询、展开目标、焦点请求与返回处理归宿主。
- 每个独立绘制节点持有自己的可变 renderer。单主体按钮及 FAB 使用 `CouiFloatingRenderer.standalone()`，底栏使用三主体 `CouiFloatingRenderer()`；不要跨节点复用实例。
- 底栏自己处理底部系统安全区。宿主用测得的底栏高度给内容留白，不再重复叠加底部系统 inset。
- `CouiCheckbox` 只绘制状态。宿主负责点击和无障碍语义；整行可点击时不再添加第二个点击节点。
- `LocalCouiContourGlowEnabled` 控制公共轮廓与光晕。深浅色、主色和 Shape 由宿主显式传入；固定参数直接引用各组件族的 Defaults，不在宿主复制数值。

阴影保留便捷 Modifier 和高自定义程度的 Canvas 接口。便捷入口处理本地坐标和 dp 换算，不改变布局；普通 RoundRect 不支持 inverse clipping。窗口坐标、缩放、透明度和多主体由低层接口装配。

融合入口只支持同色、横向双主体 CONIC 融合。主体中心、尺寸、圆角与融合范围使用绘制节点本地像素；RTL、布局、裁剪和输入归宿主。内容遮罩使用同一组几何、`Color.White` 和 `BlendMode.DstIn`，先在独立离屏层画内容，再画遮罩。背景和遮罩分别持有 renderer。库不提供侧滑容器、删除确认或业务提交。

平台门槛见各入口的 `RequiresApi` 与内部版本分支。宿主应在创建渲染器前检查版本，并明确处理创建失败；不得吞掉错误或将失败视为成功。Demo 的完整装配见 [DemoApp](../demo/src/main/java/com/itos/coui/demo/DemoApp.kt) 与 [DemoSamples](../demo/src/main/java/com/itos/coui/demo/DemoSamples.kt)。

## 实现来源与限制

迁移保留原源码中的厂商行为依据及实现限制。顶栏预设包含原项目已采用的强度调整，不将其称为未经适配的原厂值；公开 GPU 承载也不证明与厂商渲染逐像素等价。阴影相关的既有 Skia 许可保留在 [coui-skia-LICENSE](src/main/resources/META-INF/coui-skia-LICENSE)。
