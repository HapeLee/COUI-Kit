(function () {
  "use strict";

  const rawData = window.COUI_ICON_DATA || {};
  const icons = Array.isArray(rawData.icons) ? rawData.icons : [];
  const colors = rawData.colors && typeof rawData.colors === "object"
    ? rawData.colors
    : { Day: {}, Night: {} };

  const state = {
    theme: "day", // "day" | "night"
    query: "",
    selectedIconName: icons.length > 0 ? icons[0].name : null,
    // colorSelection: null | { type: "preset", palette: "Day" | "Night", key: string, hex: string } | { type: "custom", hex: string }
    colorSelection: null,
    snippetType: "expr" // "expr" | "composable"
  };

  let copyFeedbackTimer = null;

  const dom = {
    html: document.documentElement,
    themeDayBtn: document.getElementById("theme-day-btn"),
    themeNightBtn: document.getElementById("theme-night-btn"),
    searchInput: document.getElementById("search-input"),
    searchClearBtn: document.getElementById("search-clear-btn"),
    iconCount: document.getElementById("icon-count"),
    iconGrid: document.getElementById("icon-grid"),
    emptyState: document.getElementById("empty-state"),
    emptyStateText: document.getElementById("empty-state-text"),
    detailPanel: document.querySelector(".detail-panel"),
    detailSvgContainer: document.getElementById("detail-svg-container"),
    detailName: document.getElementById("detail-name"),
    detailSubtitle: document.getElementById("detail-subtitle"),
    resetColorBtn: document.getElementById("reset-color-btn"),
    colorModeNotice: document.getElementById("color-mode-notice"),
    colorControls: document.getElementById("color-controls"),
    paletteDay: document.getElementById("palette-day"),
    paletteNight: document.getElementById("palette-night"),
    customColorInput: document.getElementById("custom-color-input"),
    customColorHex: document.getElementById("custom-color-hex"),
    customColorWrap: document.querySelector(".custom-color-picker-wrap"),
    snippetExprBtn: document.getElementById("snippet-expr-btn"),
    snippetCompBtn: document.getElementById("snippet-comp-btn"),
    codeOutput: document.getElementById("code-output"),
    copyCodeBtn: document.getElementById("copy-code-btn"),
    copyFeedback: document.getElementById("copy-feedback")
  };

  function getSelectedIcon() {
    if (!state.selectedIconName) {
      return null;
    }
    return icons.find((item) => item.name === state.selectedIconName) || null;
  }

  function getFilteredIcons() {
    const q = state.query.trim().toLowerCase();
    if (!q) {
      return icons;
    }
    return icons.filter((item) => String(item.name || "").toLowerCase().includes(q));
  }

  function getIconRawSvg(icon) {
    if (!icon) {
      return "";
    }
    if (state.theme === "night") {
      return icon.night || icon.day || "";
    }
    return icon.day || icon.night || "";
  }

  function canRecolorIcon(icon) {
    if (!icon) {
      return false;
    }
    return icon.mode === "tint" || icon.mode === "background";
  }

  /**
   * 仅对带 data-coui-fill="true" 或 data-coui-stroke="true" 的 SVG 元素应用颜色，
   * 其余路径、渐变和透明度原样保留；original 模式禁止改色。
   */
  function buildSvgElement(icon, applySelectedColor) {
    const rawSvg = getIconRawSvg(icon);
    const wrapper = document.createElement("div");
    wrapper.innerHTML = rawSvg.trim();
    const svg = wrapper.querySelector("svg");
    if (!svg) {
      return null;
    }

    svg.setAttribute("focusable", "false");
    svg.setAttribute("aria-hidden", "true");

    const activeHex = applySelectedColor && state.colorSelection && canRecolorIcon(icon)
      ? state.colorSelection.hex
      : null;

    if (activeHex) {
      const fillTargets = svg.querySelectorAll('[data-coui-fill="true"]');
      fillTargets.forEach((node) => {
        node.setAttribute("fill", activeHex);
      });

      const strokeTargets = svg.querySelectorAll('[data-coui-stroke="true"]');
      strokeTargets.forEach((node) => {
        node.setAttribute("stroke", activeHex);
      });
    }

    return svg;
  }

  function formatColorKotlinExpr(selection) {
    if (!selection) {
      return null;
    }
    if (selection.type === "preset") {
      return `CouiColor.${selection.palette}.${selection.key}`;
    }
    const cleanHex = String(selection.hex || "#000000").replace(/^#/, "").toUpperCase();
    return `Color(0xFF${cleanHex})`;
  }

  function buildComposeCode(icon) {
    if (!icon) {
      return "// 请选择一个图标";
    }

    const name = icon.name;
    const mode = icon.mode || "tint";
    const hasCustomColor = Boolean(state.colorSelection) && canRecolorIcon(icon);
    const colorExpr = hasCustomColor ? formatColorKotlinExpr(state.colorSelection) : null;

    if (state.snippetType === "expr") {
      if (mode === "background") {
        if (colorExpr) {
          return `CouiIcons.${name}(color = ${colorExpr})`;
        }
        return `CouiIcons.${name}`;
      }
      if (mode === "tint" && colorExpr) {
        return [
          `Icon(`,
          `    imageVector = CouiIcons.${name},`,
          `    contentDescription = null,`,
          `    tint = ${colorExpr},`,
          `)`
        ].join("\n");
      }
      return `CouiIcons.${name}`;
    }

    // Icon / Image 完整调用代码
    if (mode === "original") {
      return [
        `Icon(`,
        `    imageVector = CouiIcons.${name},`,
        `    contentDescription = null,`,
        `    tint = Color.Unspecified,`,
        `)`
      ].join("\n");
    }

    if (mode === "background") {
      const vectorExpr = colorExpr
        ? `CouiIcons.${name}(color = ${colorExpr})`
        : `CouiIcons.${name}`;
      return [
        `Icon(`,
        `    imageVector = ${vectorExpr},`,
        `    contentDescription = null,`,
        `    tint = Color.Unspecified,`,
        `)`
      ].join("\n");
    }

    // mode === "tint"
    const tintArg = colorExpr ? colorExpr : "Color.Unspecified";
    return [
      `Icon(`,
      `    imageVector = CouiIcons.${name},`,
      `    contentDescription = null,`,
      `    tint = ${tintArg},`,
      `)`
    ].join("\n");
  }

  const PALETTE_KEYS = ["BLUE", "GREEN", "YELLOW", "RED", "ORANGE", "CYAN_BLUE"];

  /**
   * 在驼峰单词边界插入零宽空格，使长名称仅在词边界自然换行，避免在单词中间断行。
   */
  function formatWrappableName(name) {
    return String(name || "")
      .replace(/([a-z0-9])([A-Z])/g, "$1\u200B$2")
      .replace(/([A-Z]+)([A-Z][a-z])/g, "$1\u200B$2");
  }

  function renderPalettes() {
    renderPaletteGroup(dom.paletteDay, "Day", colors.Day || {});
    renderPaletteGroup(dom.paletteNight, "Night", colors.Night || {});
  }

  function renderPaletteGroup(container, paletteName, paletteObj) {
    container.innerHTML = "";
    const entries = PALETTE_KEYS
      .filter((key) => Object.prototype.hasOwnProperty.call(paletteObj, key))
      .map((key) => [key, paletteObj[key]]);

    if (entries.length === 0) {
      const empty = document.createElement("span");
      empty.className = "palette-title";
      empty.textContent = "暂无色板数据";
      container.appendChild(empty);
      return;
    }

    entries.forEach(([key, hexValue]) => {
      const btn = document.createElement("button");
      btn.type = "button";
      btn.className = "swatch-btn";
      btn.style.backgroundColor = hexValue;
      btn.dataset.palette = paletteName;
      btn.dataset.key = key;
      btn.dataset.hex = hexValue;
      const label = `CouiColor.${paletteName}.${key} (${hexValue})`;
      btn.setAttribute("aria-label", label);
      btn.setAttribute("title", label);
      btn.setAttribute("aria-pressed", "false");

      btn.addEventListener("click", () => {
        const icon = getSelectedIcon();
        if (!canRecolorIcon(icon)) {
          return;
        }
        state.colorSelection = {
          type: "preset",
          palette: paletteName,
          key: key,
          hex: hexValue
        };
        updateDetailPanel();
      });

      container.appendChild(btn);
    });
  }

  function renderGrid() {
    const filtered = getFilteredIcons();
    dom.iconCount.textContent = `${filtered.length} 个图标`;
    dom.iconGrid.innerHTML = "";

    if (icons.length === 0) {
      dom.emptyState.hidden = false;
      dom.emptyStateText.textContent = "未加载到图标数据（请先生成 Pages/data.js）";
      return;
    }

    if (filtered.length === 0) {
      dom.emptyState.hidden = false;
      dom.emptyStateText.textContent = `未找到包含 “${state.query.trim()}” 的图标`;
      return;
    }

    dom.emptyState.hidden = true;

    // 若当前选中的图标不在过滤结果中，保持选中或回退到首个可见项
    const selectedExistsInFiltered = filtered.some((item) => item.name === state.selectedIconName);
    if (!selectedExistsInFiltered && filtered.length > 0) {
      state.selectedIconName = filtered[0].name;
    }

    const fragment = document.createDocumentFragment();
    filtered.forEach((icon) => {
      const isSelected = icon.name === state.selectedIconName;
      const btn = document.createElement("button");
      btn.type = "button";
      btn.className = `icon-card${isSelected ? " is-selected" : ""}`;
      btn.setAttribute("role", "option");
      btn.setAttribute("aria-selected", isSelected ? "true" : "false");
      btn.setAttribute("aria-label", `${icon.name} 图标`);
      btn.dataset.iconName = icon.name;

      const svgBox = document.createElement("div");
      svgBox.className = "icon-card-svg";
      const svgEl = buildSvgElement(icon, false);
      if (svgEl) {
        svgBox.appendChild(svgEl);
      }

      const nameSpan = document.createElement("span");
      nameSpan.className = "icon-card-name";
      nameSpan.textContent = formatWrappableName(icon.name);

      btn.appendChild(svgBox);
      btn.appendChild(nameSpan);

      btn.addEventListener("click", () => {
        if (state.selectedIconName !== icon.name) {
          state.selectedIconName = icon.name;
          syncGridSelection();
          updateDetailPanel();
        }
        scrollToDetailOnMobile();
      });

      fragment.appendChild(btn);
    });

    dom.iconGrid.appendChild(fragment);
  }

  function syncGridSelection() {
    const cards = dom.iconGrid.querySelectorAll(".icon-card");
    cards.forEach((card) => {
      const matched = card.dataset.iconName === state.selectedIconName;
      card.classList.toggle("is-selected", matched);
      card.setAttribute("aria-selected", matched ? "true" : "false");
    });
  }

  function scrollToDetailOnMobile() {
    if (!dom.detailPanel || !window.matchMedia("(max-width: 960px)").matches) {
      return;
    }
    dom.detailPanel.scrollIntoView({ behavior: "smooth", block: "start" });
  }

  function updateColorControlsUI(icon) {
    const recolorable = canRecolorIcon(icon);
    const mode = icon ? icon.mode : "original";

    if (!icon) {
      dom.colorModeNotice.textContent = "请选择一个图标以查看配色与代码。";
    } else if (mode === "original") {
      dom.colorModeNotice.textContent =
        "多色图标固定使用原厂配色，不支持改色。";
    } else if (mode === "background") {
      dom.colorModeNotice.textContent =
        "可更改圆形背景色，白色箭头保持不变。";
    } else {
      dom.colorModeNotice.textContent =
        "可自由选择预设或自定义颜色；点击「默认原图」恢复当前主题默认配色。";
    }

    dom.colorControls.classList.toggle("is-disabled", !recolorable);

    const isDefaultColor = !state.colorSelection || !recolorable;
    dom.resetColorBtn.classList.toggle("is-active", isDefaultColor);
    dom.resetColorBtn.setAttribute("aria-pressed", isDefaultColor ? "true" : "false");

    const allSwatches = dom.colorControls.querySelectorAll(".swatch-btn");
    allSwatches.forEach((btn) => {
      btn.disabled = !recolorable;
      const isSelected = Boolean(
        recolorable &&
        state.colorSelection &&
        state.colorSelection.type === "preset" &&
        state.colorSelection.palette === btn.dataset.palette &&
        state.colorSelection.key === btn.dataset.key
      );
      btn.classList.toggle("is-selected", isSelected);
      btn.setAttribute("aria-pressed", isSelected ? "true" : "false");
    });

    dom.customColorInput.disabled = !recolorable;
    const isCustomSelected = Boolean(
      recolorable && state.colorSelection && state.colorSelection.type === "custom"
    );
    dom.customColorWrap.classList.toggle("is-selected", isCustomSelected);
  }

  function updateDetailPanel() {
    const icon = getSelectedIcon();
    dom.detailSvgContainer.innerHTML = "";

    if (!icon) {
      dom.detailName.textContent = "未选择图标";
      dom.detailSubtitle.textContent = "COUI 图标";
      updateColorControlsUI(null);
      dom.codeOutput.textContent = "// 暂无可用图标";
      return;
    }

    const svgEl = buildSvgElement(icon, true);
    if (svgEl) {
      dom.detailSvgContainer.appendChild(svgEl);
    }

    dom.detailName.textContent = formatWrappableName(icon.name);
    const modeLabelMap = {
      tint: "单色图标",
      background: "背景可调色图标",
      original: "多色原色图标"
    };
    dom.detailSubtitle.textContent = modeLabelMap[icon.mode] || "COUI 图标";

    updateColorControlsUI(icon);
    dom.codeOutput.textContent = buildComposeCode(icon);
  }

  function setTheme(nextTheme) {
    if (nextTheme !== "day" && nextTheme !== "night") {
      return;
    }
    state.theme = nextTheme;
    dom.html.setAttribute("data-theme", nextTheme);

    const isDay = nextTheme === "day";
    dom.themeDayBtn.classList.toggle("is-active", isDay);
    dom.themeDayBtn.setAttribute("aria-pressed", isDay ? "true" : "false");
    dom.themeNightBtn.classList.toggle("is-active", !isDay);
    dom.themeNightBtn.setAttribute("aria-pressed", !isDay ? "true" : "false");

    // 主题切换更新所有网格图标的默认原图以及右侧详情预览
    renderGrid();
    updateDetailPanel();
  }

  function showCopyFeedback(message, isSuccess) {
    if (copyFeedbackTimer) {
      clearTimeout(copyFeedbackTimer);
    }
    dom.copyFeedback.hidden = false;
    dom.copyFeedback.textContent = message;
    dom.copyFeedback.classList.toggle("is-success", Boolean(isSuccess));
    dom.copyFeedback.classList.toggle("is-error", !isSuccess);

    copyFeedbackTimer = setTimeout(() => {
      dom.copyFeedback.hidden = true;
    }, 3500);
  }

  async function copyCurrentCode() {
    const code = dom.codeOutput.textContent || "";
    if (!code.trim()) {
      showCopyFeedback("复制失败：当前没有可复制的代码内容。", false);
      return;
    }

    if (navigator.clipboard && typeof navigator.clipboard.writeText === "function" && window.isSecureContext) {
      try {
        await navigator.clipboard.writeText(code);
        showCopyFeedback("已复制 Compose 代码到剪贴板。", true);
        return;
      } catch (err) {
        // 继续尝试兼容方案或如实报错
      }
    }

    try {
      const textarea = document.createElement("textarea");
      textarea.value = code;
      textarea.setAttribute("readonly", "");
      textarea.style.position = "fixed";
      textarea.style.top = "-9999px";
      textarea.style.opacity = "0";
      document.body.appendChild(textarea);
      textarea.select();
      const succeeded = document.execCommand("copy");
      document.body.removeChild(textarea);

      if (succeeded) {
        showCopyFeedback("已复制 Compose 代码到剪贴板。", true);
      } else {
        showCopyFeedback("复制失败：浏览器拒绝访问剪贴板，请手动选中上方代码复制。", false);
      }
    } catch (error) {
      const reason = error && error.message ? `（${error.message}）` : "";
      showCopyFeedback(`复制失败：无法写入剪贴板${reason}，请手动选中代码复制。`, false);
    }
  }

  function bindEvents() {
    dom.themeDayBtn.addEventListener("click", () => setTheme("day"));
    dom.themeNightBtn.addEventListener("click", () => setTheme("night"));

    dom.searchInput.addEventListener("input", (event) => {
      state.query = event.target.value || "";
      dom.searchClearBtn.hidden = state.query.length === 0;
      renderGrid();
      updateDetailPanel();
    });

    dom.searchClearBtn.addEventListener("click", () => {
      state.query = "";
      dom.searchInput.value = "";
      dom.searchClearBtn.hidden = true;
      dom.searchInput.focus();
      renderGrid();
      updateDetailPanel();
    });

    dom.resetColorBtn.addEventListener("click", () => {
      state.colorSelection = null;
      updateDetailPanel();
    });

    dom.customColorInput.addEventListener("input", (event) => {
      const icon = getSelectedIcon();
      const hex = String(event.target.value || "#007aff").toUpperCase();
      dom.customColorHex.textContent = hex;
      if (!canRecolorIcon(icon)) {
        return;
      }
      state.colorSelection = {
        type: "custom",
        hex: hex
      };
      updateDetailPanel();
    });

    dom.snippetExprBtn.addEventListener("click", () => {
      state.snippetType = "expr";
      dom.snippetExprBtn.classList.add("is-active");
      dom.snippetExprBtn.setAttribute("aria-pressed", "true");
      dom.snippetCompBtn.classList.remove("is-active");
      dom.snippetCompBtn.setAttribute("aria-pressed", "false");
      updateDetailPanel();
    });

    dom.snippetCompBtn.addEventListener("click", () => {
      state.snippetType = "composable";
      dom.snippetCompBtn.classList.add("is-active");
      dom.snippetCompBtn.setAttribute("aria-pressed", "true");
      dom.snippetExprBtn.classList.remove("is-active");
      dom.snippetExprBtn.setAttribute("aria-pressed", "false");
      updateDetailPanel();
    });

    dom.copyCodeBtn.addEventListener("click", () => {
      copyCurrentCode();
    });
  }

  function init() {
    renderPalettes();
    bindEvents();
    renderGrid();
    updateDetailPanel();
  }

  init();
})();
