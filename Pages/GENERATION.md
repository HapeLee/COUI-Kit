# 图标网页数据生成指南

本文说明如何从 Kotlin 源码生成 `Pages/data.js` 并预览或发布图标网页。无需手动编辑生成物 `Pages/data.js`。

## 日常生成与本地预览

在仓库根目录执行生成器（GitHub Actions 工作流使用 Node 24，本地建议使用兼容的现代 Node 版本）：

```sh
node Pages/generate.mjs
```

`Pages/generate.mjs` 读取以下源码并自动从现有公开图标导出 `Pages/data.js`：

- `coui-icons/src/main/java/com/itos/coui/icons/CouiIcons.kt`
- `coui-icons/src/main/java/com/itos/coui/icons/CouiIconVectors.kt`
- `coui/src/main/java/com/itos/coui/theme/CouiColor.kt`

在仓库根目录启动本地静态服务预览：

```sh
python3 -m http.server 8080 --directory Pages
```

浏览器访问 `http://localhost:8080`。每次重新执行 `node Pages/generate.mjs` 后刷新页面即可看到最新结果。

## 新增或修改图标

1. 按仓库现有已支持的 Kotlin `ImageVector` 写法在 `CouiIconVectors.kt`（或 `CouiIcons.kt` 内部）提供日间与夜间定义，并在 `CouiIcons.kt` 对应样式下注册公开图标属性。
2. 在仓库根目录重新运行 `node Pages/generate.mjs`。
3. 生成器仅解析当前仓库已支持的 Kotlin DSL 子集，不是所有 Kotlin 写法都支持。若运行报 `Unsupported ...` 等解析或自检错误，先根据报错检查图标写法，或在 `Pages/generate.mjs` 中扩展对应解析规则后重试。

## 修改或新增颜色预设

- **修改现有 `Day` / `Night` 色值**：更新 `CouiColor.kt` 后直接重新运行 `node Pages/generate.mjs`。
- **新增颜色名称**：除在 `CouiColor.kt` 定义并重新运行 `node Pages/generate.mjs` 外，还需将新色名加入 `Pages/app.js` 的 `PALETTE_KEYS` 数组。网页色板仅显示 `PALETTE_KEYS` 中列出的选定色，`HOME_*` 重复别名当前不在界面显示。

## 多色与特殊背景策略

图标染色模式与特殊规则由 `Pages/generate.mjs` 处理：

- **原色图标（禁止改色）**：`ORIGINAL_ICONS` 名单中的 `AI`、`Google`、`Setting`、`Phone`，以及 `ImageVector.Builder` 中 `tintColor = Color.Unspecified` 的常规多色图标，会由生成器自动识别并保持原色，网页不对其进行单色染色。
- **背景改色图标**：`AppUpdate` 保留白色前景（`#FFFFFF`），仅对圆形背景路径标记可改色。
- 一般原色与单色图标可由生成器自动识别；若新增未支持的特殊染色或背景效果，需扩展 `Pages/generate.mjs` 中的分类与标记逻辑，并在本地预览中核对实际渲染与生成代码。

## GitHub Pages 发布

仓库工作流 `.github/workflows/coui-icons-pages.yml` 采用 `workflow_dispatch` 手动触发，不会随代码推送自动发布：

1. 首次使用前，在 GitHub 仓库 `Settings -> Pages -> Build and deployment` 中将 `Source` 设置为 **GitHub Actions**。
2. 将最新的图标源码、`Pages/` 目录及 `.github/workflows/coui-icons-pages.yml` 提交并推送到远端仓库。
3. 在 GitHub `Actions` 页面选择 **部署 COUI Icons 页面**，点击 **Run workflow** 手动触发；工作流会在远端执行 `node Pages/generate.mjs` 重新生成 `Pages/data.js` 并部署站点。
