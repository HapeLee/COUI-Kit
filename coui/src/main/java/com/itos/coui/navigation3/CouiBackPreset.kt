package com.itos.coui.navigation3

/** Nav3 内容转场预设。[backReleaseDurationMillis] 是宿主协调栏位显隐时使用的提交时长上限。 */
enum class CouiBackPreset(val backReleaseDurationMillis: Int) {
    /** 双页面跟手缩放，松手后从最后绘制帧继续提交。 */
    AOSP(450),

    /** 水平共享轴位移与淡入淡出，手势进度由 Nav3 驱动。 */
    CHATGPT(310),
}
