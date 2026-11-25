# Agent Note: 单选弹窗与 USB 用途参考

Status: implemented

## Problem

互斥设置既可能点选即提交，也可能选完再确认。弹窗需要统一选项行的视觉与单选语义，操作区的内容由场景决定。

## Decision

[SingleChoiceDialog](../../../../coui/src/main/java/com/itos/coui/dialog/SingleChoiceDialog.kt) 是单选预设的公开组件，复用 Dialog 窗口、标题和操作区。内部列表组合已有选项行、单选框和分隔线，提供滚动空间，整行承担点击、键盘和无障碍语义。宿主通过 `actionRows` 声明按钮并提供回调，通过 `onDismissRequest` 处理返回和外点。选中值与显隐由宿主持有。

参考设备为 PJZ110，系统构建为 `PJZ110_17.0.0.103(CN01)`。系统包 `com.android.systemui` 位于 `/system_ext/priv-app/SystemUI/SystemUI.apk`，版本为 `17.99.02`，versionCode 为 `179902`，SHA-256 为 `a06bf8e0046a5e6a19347e09064e6ce8f79e0e963c78c8ce65f0fbf56d770e62`。

`UsbService.showUsbDialog` 使用 `COUIAlertDialog.BottomAssignment`、`ChoiceListAdapter` 和 `oplus_usb_select_dialog_layout`，以当前 USB 用途定位已选行。行首装配用途图标，行末单选框随行状态绘制。构造器开启背景模糊，并通过负向按钮绑定「取消」。常规点选分支调用 `onUsbSelect`，随后通过 `delayDismiss` 延迟 200 ms 关闭。启用认证时，业务分支可在认证完成后提交。

库的默认外观来自当前 COUI 主题，材质跟随公开窗口源和平台能力。Demo 使用库内图标，并在点选回调里更新本地示例值和关闭状态。认证、USB 模式写入和设备策略由系统入口负责。

## Alternatives considered

- 通过普通 Dialog 的正文槽组合列表，可以自由安排正文。单选预设额外提供独立滚动的选项区，适合需要固定标题和操作区的场景。
- 内置取消按钮能缩短 USB 示例的调用。让宿主声明操作区可覆盖取消、确认和无需按钮的场景，按钮仍使用 Dialog 的排布规则。

## Consequences

选项状态从一个宿主值推导，禁用项保持原选中显示与禁用语义。业务可在同一选项样式下安排即时提交或草稿确认，滚动区域由预设负责。原厂窗体材质由系统提供，库侧使用现有 Dialog 采样管线，实际外观和退场仍需设备检查。接入方式见[单选弹窗](../../../../docs/components/dialog.md#单选弹窗)。
