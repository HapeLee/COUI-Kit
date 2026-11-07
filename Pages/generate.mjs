import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const ROOT_DIR = path.resolve(__dirname, '..');

const COUI_COLOR_PATH = path.join(ROOT_DIR, 'coui/src/main/java/com/itos/coui/theme/CouiColor.kt');
const COUI_ICONS_PATH = path.join(ROOT_DIR, 'coui-icons/src/main/java/com/itos/coui/icons/CouiIcons.kt');
const COUI_VECTORS_PATH = path.join(ROOT_DIR, 'coui-icons/src/main/java/com/itos/coui/icons/CouiIconVectors.kt');
const OUTPUT_DATA_PATH = path.join(__dirname, 'data.js');

function formatNum(n) {
  return Number(Number(n).toFixed(4)).toString();
}

function parseKotlinFloat(raw) {
  const s = raw.trim();
  const m = s.match(/^(-?\d+(?:\.\d+)?)f?$/);
  if (!m) {
    throw new Error(`Unsupported float literal: ${raw}`);
  }
  return parseFloat(m[1]);
}

function parseKotlinDp(raw) {
  const s = raw.trim();
  const m = s.match(/^(-?\d+(?:\.\d+)?)f?\.dp$/);
  if (!m) {
    throw new Error(`Unsupported Dp literal: ${raw}`);
  }
  return parseFloat(m[1]);
}

function parseHexColorLiteral(raw) {
  const s = raw.trim();
  const m = s.match(/^Color\(0x([0-9A-Fa-f]{8})\)$/);
  if (!m) {
    throw new Error(`Unsupported Color literal: ${raw}`);
  }
  const hex = m[1].toUpperCase();
  const a = parseInt(hex.slice(0, 2), 16);
  const rgb = `#${hex.slice(2)}`;
  return { hex: rgb, alpha: a / 255, rawAlpha: a };
}

function parseCouiColors(source) {
  const colors = { Day: {}, Night: {} };
  const objectRegex = /object\s+(Day|Night)\s*\{([\s\S]*?)\n\s*\}/g;
  let match;
  const matchedThemes = new Set();
  while ((match = objectRegex.exec(source)) !== null) {
    const theme = match[1];
    matchedThemes.add(theme);
    const body = match[2];
    const lines = body.split('\n');
    for (const rawLine of lines) {
      const line = rawLine.replace(/\/\*[\s\S]*?\*\//g, '').replace(/\/\/.*$/, '').trim();
      if (!line) continue;
      const valMatch = line.match(/^val\s+([A-Z0-9_]+)\s*=\s*(.+)$/);
      if (!valMatch) {
        throw new Error(`Unsupported syntax in CouiColor.${theme}: ${line}`);
      }
      const [, name, expr] = valMatch;
      const rhs = expr.trim();
      if (rhs.startsWith('Color(')) {
        const parsed = parseHexColorLiteral(rhs);
        if (parsed.rawAlpha !== 0xFF) {
          throw new Error(`Unexpected non-opaque theme color ${theme}.${name}: ${rhs}`);
        }
        colors[theme][name] = parsed.hex;
      } else if (/^[A-Z0-9_]+$/.test(rhs)) {
        if (!(rhs in colors[theme])) {
          throw new Error(`Unresolved color reference ${rhs} in CouiColor.${theme}.${name}`);
        }
        colors[theme][name] = colors[theme][rhs];
      } else if (/^(Day|Night)\.[A-Z0-9_]+$/.test(rhs)) {
        const [refTheme, refName] = rhs.split('.');
        if (!colors[refTheme] || !(refName in colors[refTheme])) {
          throw new Error(`Unresolved cross-theme color reference ${rhs} in CouiColor.${theme}.${name}`);
        }
        colors[theme][name] = colors[refTheme][refName];
      } else {
        throw new Error(`Unsupported color expression in CouiColor.${theme}.${name}: ${rhs}`);
      }
    }
  }
  if (!matchedThemes.has('Day') || !matchedThemes.has('Night')) {
    throw new Error('Failed to parse both Day and Night objects from CouiColor.kt');
  }
  return colors;
}

function splitTopLevelArgs(str) {
  const args = [];
  let current = '';
  let depthParen = 0;
  let inString = false;
  for (let i = 0; i < str.length; i++) {
    const ch = str[i];
    if (ch === '"' && str[i - 1] !== '\\') {
      inString = !inString;
      current += ch;
    } else if (!inString) {
      if (ch === '(') {
        depthParen++;
        current += ch;
      } else if (ch === ')') {
        depthParen--;
        if (depthParen < 0) {
          throw new Error(`Unbalanced parentheses in argument list: ${str}`);
        }
        current += ch;
      } else if (ch === ',' && depthParen === 0) {
        if (current.trim()) {
          args.push(current.trim());
        }
        current = '';
      } else {
        current += ch;
      }
    } else {
      current += ch;
    }
  }
  if (inString || depthParen !== 0) {
    throw new Error(`Unterminated string or parentheses in argument list: ${str}`);
  }
  if (current.trim()) {
    args.push(current.trim());
  }
  return args;
}

function parseNamedOrPositionalArgs(argListStr, positionalNames = []) {
  const rawArgs = splitTopLevelArgs(argListStr);
  const result = {};
  rawArgs.forEach((arg, idx) => {
    const eqIdx = (() => {
      let depth = 0;
      let inStr = false;
      for (let i = 0; i < arg.length; i++) {
        const c = arg[i];
        if (c === '"' && arg[i - 1] !== '\\') inStr = !inStr;
        else if (!inStr) {
          if (c === '(') depth++;
          else if (c === ')') depth--;
          else if (c === '=' && depth === 0 && arg[i + 1] !== '=' && arg[i - 1] !== '!' && arg[i - 1] !== '<' && arg[i - 1] !== '>') {
            return i;
          }
        }
      }
      return -1;
    })();
    if (eqIdx !== -1) {
      const key = arg.slice(0, eqIdx).trim();
      const val = arg.slice(eqIdx + 1).trim();
      result[key] = val;
    } else {
      if (idx >= positionalNames.length) {
        throw new Error(`Unexpected positional argument at index ${idx}: ${arg}`);
      }
      result[positionalNames[idx]] = arg;
    }
  });
  return result;
}

function resolveColorExpr(expr, colors, env = {}) {
  const s = expr.trim();
  if (s in env) {
    return env[s];
  }
  if (s === 'Color.Unspecified') {
    return { type: 'unspecified' };
  }
  if (s === 'Color.Black') {
    return { type: 'color', hex: '#000000', alpha: 1, rawAlpha: 255 };
  }
  if (s === 'Color.White') {
    return { type: 'color', hex: '#FFFFFF', alpha: 1, rawAlpha: 255 };
  }
  if (s.startsWith('Color(0x')) {
    const c = parseHexColorLiteral(s);
    return { type: 'color', ...c };
  }
  const couiMatch = s.match(/^CouiColor\.(Day|Night)\.([A-Z0-9_]+)$/);
  if (couiMatch) {
    const [, theme, name] = couiMatch;
    const hex = colors[theme]?.[name];
    if (!hex) {
      throw new Error(`Unknown CouiColor reference: ${s}`);
    }
    return { type: 'color', hex, alpha: 1, rawAlpha: 255, role: `${theme}.${name}` };
  }
  throw new Error(`Unsupported color expression: ${s}`);
}

function parseOffsetExpr(expr) {
  const m = expr.trim().match(/^Offset\(\s*([^,]+)\s*,\s*([^)]+)\s*\)$/);
  if (!m) {
    throw new Error(`Unsupported Offset expression: ${expr}`);
  }
  return {
    x: parseKotlinFloat(m[1]),
    y: parseKotlinFloat(m[2]),
  };
}

function parseColorStops(expr, colors, env) {
  const m = expr.trim().match(/^arrayOf\(([\s\S]*)\)$/);
  if (!m) {
    throw new Error(`Unsupported colorStops expression: ${expr}`);
  }
  const items = splitTopLevelArgs(m[1]);
  return items.map((item) => {
    const parts = item.split(/\s+to\s+/);
    if (parts.length !== 2) {
      throw new Error(`Unsupported colorStop item: ${item}`);
    }
    const offset = parseKotlinFloat(parts[0]);
    const color = resolveColorExpr(parts[1], colors, env);
    if (color.type !== 'color') {
      throw new Error(`Invalid stop color in gradient: ${item}`);
    }
    return { offset, hex: color.hex, alpha: color.alpha };
  });
}

function parseTileMode(expr) {
  if (!expr) return 'pad';
  const s = expr.trim();
  if (s === 'TileMode.Clamp') return 'pad';
  if (s === 'TileMode.Mirror') return 'reflect';
  if (s === 'TileMode.Repeated') return 'repeat';
  throw new Error(`Unsupported TileMode: ${s}`);
}

function parseBrushExpr(expr, colors, env) {
  if (!expr || expr.trim() === 'null') {
    return null;
  }
  const s = expr.trim();
  const solidMatch = s.match(/^SolidColor\((.+)\)$/);
  if (solidMatch) {
    const innerExpr = solidMatch[1].trim();
    const isBackgroundParam = innerExpr in env && env[innerExpr]?.isBackgroundParam === true;
    const color = resolveColorExpr(innerExpr, colors, env);
    if (color.type !== 'color') {
      throw new Error(`SolidColor must wrap a valid color: ${s}`);
    }
    return {
      type: 'solid',
      hex: color.hex,
      alpha: color.alpha,
      rawAlpha: color.rawAlpha,
      isBackgroundParam,
    };
  }
  const linearMatch = s.match(/^Brush\.linearGradient\(([\s\S]*)\)$/);
  if (linearMatch) {
    const args = parseNamedOrPositionalArgs(linearMatch[1]);
    const allowed = new Set(['colorStops', 'start', 'end', 'tileMode']);
    for (const k of Object.keys(args)) {
      if (!allowed.has(k)) throw new Error(`Unsupported linearGradient arg: ${k}`);
    }
    return {
      type: 'linearGradient',
      stops: parseColorStops(args.colorStops, colors, env),
      start: parseOffsetExpr(args.start),
      end: parseOffsetExpr(args.end),
      spreadMethod: parseTileMode(args.tileMode),
    };
  }
  const radialMatch = s.match(/^Brush\.radialGradient\(([\s\S]*)\)$/);
  if (radialMatch) {
    const args = parseNamedOrPositionalArgs(radialMatch[1]);
    const allowed = new Set(['colorStops', 'center', 'radius', 'tileMode']);
    for (const k of Object.keys(args)) {
      if (!allowed.has(k)) throw new Error(`Unsupported radialGradient arg: ${k}`);
    }
    return {
      type: 'radialGradient',
      stops: parseColorStops(args.colorStops, colors, env),
      center: parseOffsetExpr(args.center),
      radius: parseKotlinFloat(args.radius),
      spreadMethod: parseTileMode(args.tileMode),
    };
  }
  throw new Error(`Unsupported Brush expression: ${s}`);
}

function parseStringLiteral(expr) {
  const s = expr.trim();
  if (!s.startsWith('"') || !s.endsWith('"')) {
    throw new Error(`Expected string literal, got: ${expr}`);
  }
  return s.slice(1, -1);
}

function parsePathDataExpr(expr) {
  const s = expr.trim();
  if (s.startsWith('"')) {
    return parseStringLiteral(s);
  }
  const m = s.match(/^PathParser\(\)\.parsePathString\(("[^"]*")\)\.toNodes\(\)$/);
  if (m) {
    return parseStringLiteral(m[1]);
  }
  throw new Error(`Unsupported pathData expression: ${expr}`);
}

function parseStrokeCap(expr) {
  if (!expr) return 'butt';
  const s = expr.trim();
  if (s === 'StrokeCap.Butt') return 'butt';
  if (s === 'StrokeCap.Round') return 'round';
  if (s === 'StrokeCap.Square') return 'square';
  throw new Error(`Unsupported StrokeCap: ${s}`);
}

function parseStrokeJoin(expr) {
  if (!expr) return 'miter';
  const s = expr.trim();
  if (s === 'StrokeJoin.Miter') return 'miter';
  if (s === 'StrokeJoin.Round') return 'round';
  if (s === 'StrokeJoin.Bevel') return 'bevel';
  throw new Error(`Unsupported StrokeJoin: ${s}`);
}

function parsePathFillType(expr) {
  if (!expr) return 'nonzero';
  const s = expr.trim();
  if (s === 'PathFillType.NonZero') return 'nonzero';
  if (s === 'PathFillType.EvenOdd') return 'evenodd';
  throw new Error(`Unsupported PathFillType: ${s}`);
}

function extractBalancedBlock(source, openBraceIdx) {
  if (source[openBraceIdx] !== '{') {
    throw new Error(`Expected '{' at index ${openBraceIdx}`);
  }
  let depth = 0;
  let inString = false;
  for (let i = openBraceIdx; i < source.length; i++) {
    const ch = source[i];
    if (ch === '"' && source[i - 1] !== '\\') {
      inString = !inString;
    } else if (!inString) {
      if (ch === '{') depth++;
      else if (ch === '}') {
        depth--;
        if (depth === 0) {
          return {
            body: source.slice(openBraceIdx + 1, i),
            endIdx: i,
          };
        }
      }
    }
  }
  throw new Error('Unbalanced braces in source');
}

function extractBalancedParen(source, openParenIdx) {
  if (source[openParenIdx] !== '(') {
    throw new Error(`Expected '(' at index ${openParenIdx}`);
  }
  let depth = 0;
  let inString = false;
  for (let i = openParenIdx; i < source.length; i++) {
    const ch = source[i];
    if (ch === '"' && source[i - 1] !== '\\') {
      inString = !inString;
    } else if (!inString) {
      if (ch === '(') depth++;
      else if (ch === ')') {
        depth--;
        if (depth === 0) {
          return {
            argsStr: source.slice(openParenIdx + 1, i),
            endIdx: i,
          };
        }
      }
    }
  }
  throw new Error('Unbalanced parentheses in source');
}

function parseVectorCommands(bodyStr, colors, env) {
  const s = bodyStr.replace(/\/\*[\s\S]*?\*\//g, '').replace(/\/\/.*$/gm, '');
  let idx = 0;
  const commands = [];
  let openGroups = 0;

  while (idx < s.length) {
    while (idx < s.length && /\s/.test(s[idx])) idx++;
    if (idx >= s.length) break;

    const identMatch = s.slice(idx).match(/^([a-zA-Z_][a-zA-Z0-9_]*)\s*\(/);
    if (!identMatch) {
      throw new Error(`Unsupported statement in vector builder body near: ${s.slice(idx, idx + 80)}`);
    }
    const fnName = identMatch[1];
    const openParenIdx = idx + identMatch[0].length - 1;
    const { argsStr, endIdx } = extractBalancedParen(s, openParenIdx);
    idx = endIdx + 1;

    if (fnName === 'addPath' || fnName === 'iconPath') {
      const positional = fnName === 'addPath'
        ? ['pathData', 'pathFillType', 'name', 'fill', 'fillAlpha', 'stroke', 'strokeAlpha', 'strokeLineWidth', 'strokeLineCap', 'strokeLineJoin', 'strokeLineMiter', 'trimPathStart', 'trimPathEnd', 'trimPathOffset']
        : ['pathData', 'fill', 'fillAlpha', 'stroke', 'strokeAlpha', 'strokeLineWidth', 'strokeLineCap', 'strokeLineJoin', 'strokeLineMiter', 'pathFillType', 'trimPathStart', 'trimPathEnd', 'trimPathOffset', 'name'];
      const args = parseNamedOrPositionalArgs(argsStr, positional);
      const allowed = new Set(positional);
      for (const k of Object.keys(args)) {
        if (!allowed.has(k)) {
          throw new Error(`Unsupported ${fnName} parameter: ${k}`);
        }
      }

      const trimStart = args.trimPathStart !== undefined ? parseKotlinFloat(args.trimPathStart) : 0;
      const trimEnd = args.trimPathEnd !== undefined ? parseKotlinFloat(args.trimPathEnd) : 1;
      const trimOffset = args.trimPathOffset !== undefined ? parseKotlinFloat(args.trimPathOffset) : 0;
      if (trimStart !== 0 || trimEnd !== 1 || trimOffset !== 0) {
        throw new Error(`Unsupported non-default trimPath in ${fnName}: start=${trimStart}, end=${trimEnd}, offset=${trimOffset}`);
      }

      commands.push({
        type: 'path',
        d: parsePathDataExpr(args.pathData),
        fill: parseBrushExpr(args.fill, colors, env),
        fillAlpha: args.fillAlpha !== undefined ? parseKotlinFloat(args.fillAlpha) : 1,
        stroke: parseBrushExpr(args.stroke, colors, env),
        strokeAlpha: args.strokeAlpha !== undefined ? parseKotlinFloat(args.strokeAlpha) : 1,
        strokeLineWidth: args.strokeLineWidth !== undefined ? parseKotlinFloat(args.strokeLineWidth) : 0,
        strokeLineCap: parseStrokeCap(args.strokeLineCap),
        strokeLineJoin: parseStrokeJoin(args.strokeLineJoin),
        strokeLineMiter: args.strokeLineMiter !== undefined ? parseKotlinFloat(args.strokeLineMiter) : 4,
        fillRule: parsePathFillType(args.pathFillType),
      });
    } else if (fnName === 'addGroup') {
      const positional = ['name', 'rotate', 'pivotX', 'pivotY', 'scaleX', 'scaleY', 'translationX', 'translationY', 'clipPathData'];
      const args = parseNamedOrPositionalArgs(argsStr, positional);
      const allowed = new Set(positional);
      for (const k of Object.keys(args)) {
        if (!allowed.has(k)) {
          throw new Error(`Unsupported addGroup parameter: ${k}`);
        }
      }
      const rotate = args.rotate !== undefined ? parseKotlinFloat(args.rotate) : 0;
      const pivotX = args.pivotX !== undefined ? parseKotlinFloat(args.pivotX) : 0;
      const pivotY = args.pivotY !== undefined ? parseKotlinFloat(args.pivotY) : 0;
      const scaleX = args.scaleX !== undefined ? parseKotlinFloat(args.scaleX) : 1;
      const scaleY = args.scaleY !== undefined ? parseKotlinFloat(args.scaleY) : 1;
      const translationX = args.translationX !== undefined ? parseKotlinFloat(args.translationX) : 0;
      const translationY = args.translationY !== undefined ? parseKotlinFloat(args.translationY) : 0;
      const clipPathD = args.clipPathData !== undefined ? parsePathDataExpr(args.clipPathData) : null;

      openGroups++;
      commands.push({
        type: 'addGroup',
        rotate,
        pivotX,
        pivotY,
        scaleX,
        scaleY,
        translationX,
        translationY,
        clipPathD,
      });
    } else if (fnName === 'clearGroup') {
      if (argsStr.trim() !== '') {
        throw new Error(`clearGroup() takes no arguments, got: ${argsStr}`);
      }
      openGroups--;
      if (openGroups < 0) {
        throw new Error('clearGroup() called without matching addGroup()');
      }
      commands.push({ type: 'clearGroup' });
    } else {
      throw new Error(`Unsupported vector builder call: ${fnName}`);
    }
  }

  if (openGroups !== 0) {
    throw new Error(`Unclosed addGroup() calls remaining: ${openGroups}`);
  }
  return commands;
}

function buildGroupTransform(cmd) {
  const parts = [];
  if (cmd.translationX !== 0 || cmd.translationY !== 0) {
    parts.push(`translate(${formatNum(cmd.translationX)} ${formatNum(cmd.translationY)})`);
  }
  if (cmd.rotate !== 0) {
    if (cmd.pivotX !== 0 || cmd.pivotY !== 0) {
      parts.push(`rotate(${formatNum(cmd.rotate)} ${formatNum(cmd.pivotX)} ${formatNum(cmd.pivotY)})`);
    } else {
      parts.push(`rotate(${formatNum(cmd.rotate)})`);
    }
  }
  if (cmd.scaleX !== 1 || cmd.scaleY !== 1) {
    if (cmd.pivotX !== 0 || cmd.pivotY !== 0) {
      parts.push(`translate(${formatNum(cmd.pivotX)} ${formatNum(cmd.pivotY)})`);
      parts.push(`scale(${formatNum(cmd.scaleX)} ${formatNum(cmd.scaleY)})`);
      parts.push(`translate(${formatNum(-cmd.pivotX)} ${formatNum(-cmd.pivotY)})`);
    } else {
      parts.push(`scale(${formatNum(cmd.scaleX)} ${formatNum(cmd.scaleY)})`);
    }
  }
  return parts.join(' ');
}

function renderSvg(vectorSpec, iconName, themeKey, mode) {
  const defs = [];
  const bodyElements = [];
  let gradCounter = 0;
  let clipCounter = 0;
  const idPrefix = `coui-${iconName}-${themeKey.toLowerCase()}`;

  const hasTintColor = vectorSpec.tintColor && vectorSpec.tintColor.type === 'color';
  const tintHex = hasTintColor ? vectorSpec.tintColor.hex : null;

  function registerBrushDef(brush) {
    gradCounter++;
    const gradId = `${idPrefix}-grad-${gradCounter}`;
    const stopsXml = brush.stops.map((st) => {
      const stopAttrs = [`offset="${formatNum(st.offset)}"`, `stop-color="${st.hex}"`];
      if (st.alpha < 1) {
        stopAttrs.push(`stop-opacity="${formatNum(st.alpha)}"`);
      }
      return `<stop ${stopAttrs.join(' ')}/>`;
    }).join('');

    if (brush.type === 'linearGradient') {
      const attrs = [
        `id="${gradId}"`,
        `x1="${formatNum(brush.start.x)}"`,
        `y1="${formatNum(brush.start.y)}"`,
        `x2="${formatNum(brush.end.x)}"`,
        `y2="${formatNum(brush.end.y)}"`,
        `gradientUnits="userSpaceOnUse"`,
      ];
      if (brush.spreadMethod !== 'pad') {
        attrs.push(`spreadMethod="${brush.spreadMethod}"`);
      }
      defs.push(`<linearGradient ${attrs.join(' ')}>${stopsXml}</linearGradient>`);
      return `url(#${gradId})`;
    }
    if (brush.type === 'radialGradient') {
      const attrs = [
        `id="${gradId}"`,
        `cx="${formatNum(brush.center.x)}"`,
        `cy="${formatNum(brush.center.y)}"`,
        `r="${formatNum(brush.radius)}"`,
        `gradientUnits="userSpaceOnUse"`,
      ];
      if (brush.spreadMethod !== 'pad') {
        attrs.push(`spreadMethod="${brush.spreadMethod}"`);
      }
      defs.push(`<radialGradient ${attrs.join(' ')}>${stopsXml}</radialGradient>`);
      return `url(#${gradId})`;
    }
    throw new Error(`Unknown gradient brush type: ${brush.type}`);
  }

  for (const cmd of vectorSpec.commands) {
    if (cmd.type === 'addGroup') {
      const gAttrs = [];
      const transform = buildGroupTransform(cmd);
      if (transform) {
        gAttrs.push(`transform="${transform}"`);
      }
      if (cmd.clipPathD) {
        clipCounter++;
        const clipId = `${idPrefix}-clip-${clipCounter}`;
        defs.push(`<clipPath id="${clipId}"><path d="${cmd.clipPathD}"/></clipPath>`);
        gAttrs.push(`clip-path="url(#${clipId})"`);
      }
      bodyElements.push(gAttrs.length > 0 ? `<g ${gAttrs.join(' ')}>` : '<g>');
    } else if (cmd.type === 'clearGroup') {
      bodyElements.push('</g>');
    } else if (cmd.type === 'path') {
      const pAttrs = [`d="${cmd.d}"`];

      // Fill handling
      if (!cmd.fill || (cmd.fill.type === 'solid' && cmd.fill.rawAlpha === 0)) {
        pAttrs.push('fill="none"');
      } else if (cmd.fill.type === 'solid') {
        const effectiveColor = (mode === 'tint' && tintHex) ? tintHex : cmd.fill.hex;
        pAttrs.push(`fill="${effectiveColor}"`);
        const shouldMarkFill =
          mode === 'tint' ||
          (mode === 'background' && cmd.fill.isBackgroundParam === true);
        if (shouldMarkFill) {
          pAttrs.push('data-coui-fill="true"');
        }
        const effectiveAlpha = cmd.fillAlpha * cmd.fill.alpha;
        if (effectiveAlpha < 1) {
          pAttrs.push(`fill-opacity="${formatNum(effectiveAlpha)}"`);
        }
      } else {
        if (mode === 'tint') {
          throw new Error(`Unexpected gradient fill in tint-mode icon ${iconName}`);
        }
        const paintRef = registerBrushDef(cmd.fill);
        pAttrs.push(`fill="${paintRef}"`);
        if (cmd.fillAlpha < 1) {
          pAttrs.push(`fill-opacity="${formatNum(cmd.fillAlpha)}"`);
        }
      }

      // Stroke handling
      if (cmd.stroke && cmd.strokeLineWidth > 0 && !(cmd.stroke.type === 'solid' && cmd.stroke.rawAlpha === 0)) {
        if (cmd.stroke.type === 'solid') {
          const effectiveStrokeColor = (mode === 'tint' && tintHex) ? tintHex : cmd.stroke.hex;
          pAttrs.push(`stroke="${effectiveStrokeColor}"`);
          const shouldMarkStroke =
            mode === 'tint' ||
            (mode === 'background' && cmd.stroke.isBackgroundParam === true);
          if (shouldMarkStroke) {
            pAttrs.push('data-coui-stroke="true"');
          }
          const effectiveStrokeAlpha = cmd.strokeAlpha * cmd.stroke.alpha;
          if (effectiveStrokeAlpha < 1) {
            pAttrs.push(`stroke-opacity="${formatNum(effectiveStrokeAlpha)}"`);
          }
        } else {
          const paintRef = registerBrushDef(cmd.stroke);
          pAttrs.push(`stroke="${paintRef}"`);
          if (cmd.strokeAlpha < 1) {
            pAttrs.push(`stroke-opacity="${formatNum(cmd.strokeAlpha)}"`);
          }
        }
        pAttrs.push(`stroke-width="${formatNum(cmd.strokeLineWidth)}"`);
        if (cmd.strokeLineCap !== 'butt') {
          pAttrs.push(`stroke-linecap="${cmd.strokeLineCap}"`);
        } else {
          pAttrs.push('stroke-linecap="butt"');
        }
        if (cmd.strokeLineJoin !== 'miter') {
          pAttrs.push(`stroke-linejoin="${cmd.strokeLineJoin}"`);
        } else {
          pAttrs.push('stroke-linejoin="miter"');
          if (cmd.strokeLineMiter !== 4) {
            pAttrs.push(`stroke-miterlimit="${formatNum(cmd.strokeLineMiter)}"`);
          }
        }
      }

      if (cmd.fillRule === 'evenodd') {
        pAttrs.push('fill-rule="evenodd"');
        pAttrs.push('clip-rule="evenodd"');
      }

      bodyElements.push(`<path ${pAttrs.join(' ')}/>`);
    }
  }

  const svgAttrs = [
    'xmlns="http://www.w3.org/2000/svg"',
    `width="${formatNum(vectorSpec.width)}"`,
    `height="${formatNum(vectorSpec.height)}"`,
    `viewBox="0 0 ${formatNum(vectorSpec.viewportWidth)} ${formatNum(vectorSpec.viewportHeight)}"`,
    'fill="none"',
  ];
  const defsXml = defs.length > 0 ? `<defs>${defs.join('')}</defs>` : '';
  return `<svg ${svgAttrs.join(' ')}>${defsXml}${bodyElements.join('')}</svg>`;
}

function parseCouiIconVectorsFile(source, colors) {
  const clean = source.replace(/\/\*[\s\S]*?\*\//g, '').replace(/\/\/.*$/gm, '');
  const objIdx = clean.indexOf('internal object CouiIconVectors');
  if (objIdx === -1) {
    throw new Error('Could not find internal object CouiIconVectors');
  }
  const braceIdx = clean.indexOf('{', objIdx);
  const { body } = extractBalancedBlock(clean, braceIdx);

  const helperFunctions = new Map();
  const vectorProperties = new Map();
  const aliasProperties = new Map();

  let idx = 0;
  while (idx < body.length) {
    while (idx < body.length && /\s/.test(body[idx])) idx++;
    if (idx >= body.length) break;

    const rest = body.slice(idx);

    // 1. private fun <name>(name: String, tintColor: Color): ImageVector = iconVector(...) { ... }
    const privFunMatch = rest.match(/^private\s+fun\s+([a-zA-Z0-9_]+)\s*\(([^)]*)\)\s*:\s*ImageVector\s*=\s*iconVector\s*\(/);
    if (privFunMatch) {
      const fnName = privFunMatch[1];
      const paramStr = privFunMatch[2];
      const openParenPos = idx + privFunMatch[0].length - 1;
      const { argsStr: iconVecArgsStr, endIdx: parenEnd } = extractBalancedParen(body, openParenPos);
      let afterParen = parenEnd + 1;
      while (afterParen < body.length && /\s/.test(body[afterParen])) afterParen++;
      const { body: builderBody, endIdx: blockEnd } = extractBalancedBlock(body, afterParen);

      helperFunctions.set(fnName, {
        kind: 'tintedHelper',
        paramStr,
        iconVecArgsStr,
        builderBody,
      });
      idx = blockEnd + 1;
      continue;
    }

    // 2. internal fun appUpdate(backgroundColor: Color): ImageVector { return iconVector(...) { ... } }
    const appUpdateFunMatch = rest.match(/^internal\s+fun\s+([a-zA-Z0-9_]+)\s*\(\s*([a-zA-Z0-9_]+)\s*:\s*Color\s*\)\s*:\s*ImageVector\s*\{/);
    if (appUpdateFunMatch) {
      const fnName = appUpdateFunMatch[1];
      const colorParamName = appUpdateFunMatch[2];
      const openBracePos = idx + appUpdateFunMatch[0].length - 1;
      const { body: fnBody, endIdx: fnEnd } = extractBalancedBlock(body, openBracePos);
      const retMatch = fnBody.match(/^\s*return\s+iconVector\s*\(/);
      if (!retMatch) {
        throw new Error(`Unsupported body in internal fun ${fnName}`);
      }
      const openParenPos = retMatch[0].length - 1;
      const { argsStr: iconVecArgsStr, endIdx: parenEnd } = extractBalancedParen(fnBody, openParenPos);
      let afterParen = parenEnd + 1;
      while (afterParen < fnBody.length && /\s/.test(fnBody[afterParen])) afterParen++;
      const { body: builderBody } = extractBalancedBlock(fnBody, afterParen);

      helperFunctions.set(fnName, {
        kind: 'paramColorHelper',
        colorParamName,
        iconVecArgsStr,
        builderBody,
      });
      idx = fnEnd + 1;
      continue;
    }

    // 3. val <Name>: ImageVector by lazy { ... }
    const lazyValMatch = rest.match(/^val\s+([a-zA-Z0-9_]+)\s*:\s*ImageVector\s+by\s+lazy\s*\{/);
    if (lazyValMatch) {
      const propName = lazyValMatch[1];
      const openBracePos = idx + lazyValMatch[0].length - 1;
      const { body: lazyBody, endIdx: lazyEnd } = extractBalancedBlock(body, openBracePos);
      const trimmedLazy = lazyBody.trim();

      const directIconVec = trimmedLazy.match(/^iconVector\s*\(/);
      if (directIconVec) {
        const openParenPos = directIconVec[0].length - 1;
        const { argsStr: iconVecArgsStr, endIdx: parenEnd } = extractBalancedParen(trimmedLazy, openParenPos);
        let afterParen = parenEnd + 1;
        while (afterParen < trimmedLazy.length && /\s/.test(trimmedLazy[afterParen])) afterParen++;
        const { body: builderBody } = extractBalancedBlock(trimmedLazy, afterParen);

        const vecArgs = parseNamedOrPositionalArgs(iconVecArgsStr, [
          'name', 'width', 'height', 'viewportWidth', 'viewportHeight', 'tintColor',
        ]);
        const tintColor = resolveColorExpr(vecArgs.tintColor, colors);
        const commands = parseVectorCommands(builderBody, colors, {});
        vectorProperties.set(propName, {
          name: parseStringLiteral(vecArgs.name),
          width: parseKotlinDp(vecArgs.width),
          height: parseKotlinDp(vecArgs.height),
          viewportWidth: parseKotlinFloat(vecArgs.viewportWidth),
          viewportHeight: parseKotlinFloat(vecArgs.viewportHeight),
          tintColor,
          commands,
        });
      } else {
        const callMatch = trimmedLazy.match(/^([a-zA-Z0-9_]+)\s*\(([\s\S]*)\)$/);
        if (!callMatch) {
          throw new Error(`Unsupported lazy property body for ${propName}: ${trimmedLazy}`);
        }
        const [, helperName, callArgsStr] = callMatch;
        const helper = helperFunctions.get(helperName);
        if (!helper) {
          throw new Error(`Unknown helper function ${helperName} called in ${propName}`);
        }
        const callArgs = splitTopLevelArgs(callArgsStr);
        if (helper.kind === 'tintedHelper') {
          if (callArgs.length !== 2) {
            throw new Error(`Expected 2 args for ${helperName} in ${propName}`);
          }
          const env = {
            name: callArgs[0],
            tintColor: resolveColorExpr(callArgs[1], colors),
          };
          const vecArgs = parseNamedOrPositionalArgs(helper.iconVecArgsStr, [
            'name', 'width', 'height', 'viewportWidth', 'viewportHeight', 'tintColor',
          ]);
          const tintColor = resolveColorExpr(vecArgs.tintColor, colors, env);
          const commands = parseVectorCommands(helper.builderBody, colors, env);
          vectorProperties.set(propName, {
            name: parseStringLiteral(env.name),
            width: parseKotlinDp(vecArgs.width),
            height: parseKotlinDp(vecArgs.height),
            viewportWidth: parseKotlinFloat(vecArgs.viewportWidth),
            viewportHeight: parseKotlinFloat(vecArgs.viewportHeight),
            tintColor,
            commands,
          });
        } else if (helper.kind === 'paramColorHelper') {
          if (callArgs.length !== 1) {
            throw new Error(`Expected 1 arg for ${helperName} in ${propName}`);
          }
          const resolvedBg = resolveColorExpr(callArgs[0], colors);
          const env = {
            [helper.colorParamName]: { ...resolvedBg, isBackgroundParam: true },
          };
          const vecArgs = parseNamedOrPositionalArgs(helper.iconVecArgsStr, [
            'name', 'width', 'height', 'viewportWidth', 'viewportHeight', 'tintColor',
          ]);
          const tintColor = resolveColorExpr(vecArgs.tintColor, colors, env);
          const commands = parseVectorCommands(helper.builderBody, colors, env);
          vectorProperties.set(propName, {
            name: parseStringLiteral(vecArgs.name),
            width: parseKotlinDp(vecArgs.width),
            height: parseKotlinDp(vecArgs.height),
            viewportWidth: parseKotlinFloat(vecArgs.viewportWidth),
            viewportHeight: parseKotlinFloat(vecArgs.viewportHeight),
            tintColor,
            commands,
            isBackgroundParameterized: true,
          });
        } else {
          throw new Error(`Unsupported helper kind: ${helper.kind}`);
        }
      }

      idx = lazyEnd + 1;
      continue;
    }

    // 4. val <Name>: ImageVector get() = <TargetName>
    const getterValMatch = rest.match(/^val\s+([a-zA-Z0-9_]+)\s*:\s*ImageVector\s+get\(\)\s*=\s*([a-zA-Z0-9_]+)/);
    if (getterValMatch) {
      const [, propName, targetName] = getterValMatch;
      aliasProperties.set(propName, targetName);
      idx += getterValMatch[0].length;
      continue;
    }

    throw new Error(`Unsupported declaration in CouiIconVectors.kt near: ${rest.slice(0, 100)}`);
  }

  for (const [aliasName, targetName] of aliasProperties.entries()) {
    if (!vectorProperties.has(targetName)) {
      throw new Error(`Unresolved alias target ${targetName} for ${aliasName}`);
    }
    vectorProperties.set(aliasName, vectorProperties.get(targetName));
  }

  return vectorProperties;
}

function parseCouiIconsFile(source, colors, vectorProperties) {
  const clean = source.replace(/\/\*[\s\S]*?\*\//g, '').replace(/\/\/.*$/gm, '');
  const objIdx = clean.indexOf('object CouiIcons');
  if (objIdx === -1) {
    throw new Error('Could not find object CouiIcons');
  }
  const braceIdx = clean.indexOf('{', objIdx);
  const { body } = extractBalancedBlock(clean, braceIdx);

  const localVariants = new Map();
  const publicIcons = [];

  let idx = 0;
  while (idx < body.length) {
    while (idx < body.length && /\s/.test(body[idx])) idx++;
    if (idx >= body.length) break;

    const rest = body.slice(idx);

    // 1. private val <xxxVariants>: Pair<ImageVector, ImageVector> by lazy { themedVectors(...) { ... } }
    const privPairMatch = rest.match(/^private\s+val\s+([a-zA-Z0-9_]+)\s*:\s*Pair<ImageVector,\s*ImageVector>\s+by\s+lazy\s*\{/);
    if (privPairMatch) {
      const varName = privPairMatch[1];
      const openBracePos = idx + privPairMatch[0].length - 1;
      const { body: lazyBody, endIdx: lazyEnd } = extractBalancedBlock(body, openBracePos);
      const trimmed = lazyBody.trim();
      const tvMatch = trimmed.match(/^themedVectors\s*\(/);
      if (!tvMatch) {
        throw new Error(`Expected themedVectors(...) in ${varName}`);
      }
      const openParenPos = tvMatch[0].length - 1;
      const { argsStr: tvArgsStr, endIdx: parenEnd } = extractBalancedParen(trimmed, openParenPos);
      let afterParen = parenEnd + 1;
      while (afterParen < trimmed.length && /\s/.test(trimmed[afterParen])) afterParen++;
      const { body: builderBody } = extractBalancedBlock(trimmed, afterParen);

      const tvArgs = parseNamedOrPositionalArgs(tvArgsStr, [
        'name', 'width', 'height', 'viewportWidth', 'viewportHeight', 'autoMirror',
      ]);
      const commands = parseVectorCommands(builderBody, colors, {});
      const baseSpec = {
        name: parseStringLiteral(tvArgs.name),
        width: parseKotlinDp(tvArgs.width),
        height: parseKotlinDp(tvArgs.height),
        viewportWidth: parseKotlinFloat(tvArgs.viewportWidth),
        viewportHeight: parseKotlinFloat(tvArgs.viewportHeight),
        autoMirror: tvArgs.autoMirror === 'true',
        commands,
      };
      localVariants.set(varName, {
        day: { ...baseSpec, tintColor: { type: 'color', hex: '#000000', alpha: 1, rawAlpha: 255 } },
        night: { ...baseSpec, tintColor: { type: 'color', hex: '#FFFFFF', alpha: 1, rawAlpha: 255 } },
      });
      idx = lazyEnd + 1;
      continue;
    }

    // 2. Public val <Name>: ImageVector
    const pubValMatch = rest.match(/^val\s+([a-zA-Z0-9_]+)\s*:\s*ImageVector\s+(?:@Composable\s+)?get\(\)\s*=\s*([^\n]+)/);
    if (pubValMatch) {
      const [, iconName, getterExprRaw] = pubValMatch;
      const getterExpr = getterExprRaw.trim();

      const darkBranchMatch = getterExpr.match(/^if\s*\(\s*CouiTheme\.isDark\s*\)\s*([^\s]+)\s+else\s+([^\s]+)$/);
      if (darkBranchMatch) {
        const [, nightRef, dayRef] = darkBranchMatch;
        let daySpec;
        let nightSpec;
        if (dayRef.endsWith('.first') && nightRef.endsWith('.second')) {
          const baseVar = dayRef.slice(0, -'.first'.length);
          const pair = localVariants.get(baseVar);
          if (!pair) {
            throw new Error(`Unknown local variant pair ${baseVar} for ${iconName}`);
          }
          daySpec = pair.day;
          nightSpec = pair.night;
        } else if (dayRef.startsWith('CouiIconVectors.') && nightRef.startsWith('CouiIconVectors.')) {
          const dayKey = dayRef.slice('CouiIconVectors.'.length);
          const nightKey = nightRef.slice('CouiIconVectors.'.length);
          daySpec = vectorProperties.get(dayKey);
          nightSpec = vectorProperties.get(nightKey);
          if (!daySpec || !nightSpec) {
            throw new Error(`Unresolved CouiIconVectors reference for ${iconName}: ${dayRef} / ${nightRef}`);
          }
        } else {
          throw new Error(`Unsupported conditional getter for ${iconName}: ${getterExpr}`);
        }
        publicIcons.push({ name: iconName, daySpec, nightSpec });
      } else if (getterExpr.startsWith('CouiIconVectors.')) {
        const key = getterExpr.slice('CouiIconVectors.'.length);
        const spec = vectorProperties.get(key);
        if (!spec) {
          throw new Error(`Unresolved CouiIconVectors reference for ${iconName}: ${getterExpr}`);
        }
        publicIcons.push({ name: iconName, daySpec: spec, nightSpec: spec });
      } else {
        throw new Error(`Unsupported public ImageVector getter for ${iconName}: ${getterExpr}`);
      }

      idx += pubValMatch[0].length;
      continue;
    }

    // 3. @Composable fun AppUpdate(color: Color): ImageVector = ...
    const appUpdateFunMatch = rest.match(/^@Composable\s+fun\s+AppUpdate\s*\(\s*color\s*:\s*Color\s*\)\s*:\s*ImageVector\s*=\s*if\s*\(\s*color\s*==\s*Color\.Unspecified\s*\)\s*\{/);
    if (appUpdateFunMatch) {
      const ifBracePos = idx + appUpdateFunMatch[0].length - 1;
      const { endIdx: ifEnd } = extractBalancedBlock(body, ifBracePos);
      const afterIf = body.slice(ifEnd + 1);
      const elseMatch = afterIf.match(/^\s*else\s*\{/);
      if (!elseMatch) {
        throw new Error('Expected else block in fun AppUpdate(color: Color)');
      }
      const elseBracePos = ifEnd + 1 + elseMatch[0].length - 1;
      const { endIdx: elseEnd } = extractBalancedBlock(body, elseBracePos);
      idx = elseEnd + 1;
      continue;
    }

    // 4. private fun themedVectors(...)
    const themedVecFunMatch = rest.match(/^private\s+fun\s+themedVectors\s*\(/);
    if (themedVecFunMatch) {
      const openParenPos = idx + themedVecFunMatch[0].length - 1;
      const { endIdx: parenEnd } = extractBalancedParen(body, openParenPos);
      const bracePos = body.indexOf('{', parenEnd);
      const { endIdx: fnEnd } = extractBalancedBlock(body, bracePos);
      idx = fnEnd + 1;
      continue;
    }

    throw new Error(`Unsupported declaration in CouiIcons.kt near: ${rest.slice(0, 100)}`);
  }

  return publicIcons;
}

function determineIconMode(iconEntry) {
  const ORIGINAL_ICONS = new Set(['AI', 'Google', 'Setting', 'Phone']);
  if (iconEntry.name === 'AppUpdate' || iconEntry.daySpec.isBackgroundParameterized) {
    return 'background';
  }
  if (ORIGINAL_ICONS.has(iconEntry.name) || iconEntry.daySpec.tintColor?.type === 'unspecified') {
    return 'original';
  }
  return 'tint';
}

function validateGeneratedPayload({ icons, colors }) {
  if (!Array.isArray(icons) || icons.length === 0) {
    throw new Error('Self-check failed: generated icon list is empty');
  }
  if (
    !colors ||
    !colors.Day ||
    Object.keys(colors.Day).length === 0 ||
    !colors.Night ||
    Object.keys(colors.Night).length === 0
  ) {
    throw new Error('Self-check failed: Day or Night color palette is empty');
  }

  const seenNames = new Set();
  for (const icon of icons) {
    if (!icon.name || typeof icon.name !== 'string') {
      throw new Error('Self-check failed: icon is missing a valid name');
    }
    if (seenNames.has(icon.name)) {
      throw new Error(`Self-check failed: duplicate icon name "${icon.name}"`);
    }
    seenNames.add(icon.name);

    if (
      typeof icon.day !== 'string' ||
      !icon.day.startsWith('<svg') ||
      !icon.day.includes('<path') ||
      typeof icon.night !== 'string' ||
      !icon.night.startsWith('<svg') ||
      !icon.night.includes('<path')
    ) {
      throw new Error(`Self-check failed: missing or invalid day/night SVG for "${icon.name}"`);
    }
  }

  const appUpdate = icons.find((item) => item.name === 'AppUpdate');
  if (appUpdate) {
    if (appUpdate.mode !== 'background') {
      throw new Error(`Self-check failed: AppUpdate mode must be "background", got "${appUpdate.mode}"`);
    }
    for (const [themeKey, svg] of [['day', appUpdate.day], ['night', appUpdate.night]]) {
      const pathTags = svg.match(/<path\b[^>]*\/>/g) || [];
      const markedFillPaths = pathTags.filter((t) => t.includes('data-coui-fill="true"'));
      const markedStrokePaths = pathTags.filter((t) => t.includes('data-coui-stroke="true"'));
      const whitePaths = pathTags.filter(
        (t) => /fill="#FFFFFF"/i.test(t) || /stroke="#FFFFFF"/i.test(t)
      );
      if (markedFillPaths.length !== 1 || markedStrokePaths.length !== 0) {
        throw new Error(
          `Self-check failed: AppUpdate (${themeKey}) must mark only 1 background fill path (got fill=${markedFillPaths.length}, stroke=${markedStrokePaths.length})`
        );
      }
      if (whitePaths.length === 0) {
        throw new Error(`Self-check failed: AppUpdate (${themeKey}) is missing white (#FFFFFF) foreground paths`);
      }
      for (const wp of whitePaths) {
        if (wp.includes('data-coui-fill') || wp.includes('data-coui-stroke')) {
          throw new Error(`Self-check failed: AppUpdate (${themeKey}) white foreground path must not be marked for tinting`);
        }
      }
    }
  }
}

function main() {
  const colorSource = fs.readFileSync(COUI_COLOR_PATH, 'utf8');
  const iconsSource = fs.readFileSync(COUI_ICONS_PATH, 'utf8');
  const vectorsSource = fs.readFileSync(COUI_VECTORS_PATH, 'utf8');

  const colors = parseCouiColors(colorSource);
  const vectorProperties = parseCouiIconVectorsFile(vectorsSource, colors);
  const publicIcons = parseCouiIconsFile(iconsSource, colors, vectorProperties);

  const icons = publicIcons.map((entry) => {
    const mode = determineIconMode(entry);
    const daySvg = renderSvg(entry.daySpec, entry.name, 'Day', mode);
    const nightSvg = renderSvg(entry.nightSpec, entry.name, 'Night', mode);
    return {
      name: entry.name,
      day: daySvg,
      night: nightSvg,
      mode,
    };
  });

  const payload = {
    icons,
    colors,
  };

  validateGeneratedPayload(payload);

  const jsContent = `window.COUI_ICON_DATA = ${JSON.stringify(payload, null, 2)};\n`;
  fs.writeFileSync(OUTPUT_DATA_PATH, jsContent, 'utf8');

  console.log(`Generated and verified ${icons.length} icons to ${OUTPUT_DATA_PATH}`);
}

main();
