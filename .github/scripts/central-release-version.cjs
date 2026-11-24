const assert = require('node:assert/strict');
const fs = require('node:fs');

function parseReleaseTag(refType, tag) {
  assert.equal(refType, 'tag', '发行必须由 Tag 触发');
  assert.match(tag, /^v(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-alpha(0|[1-9]\d*))?$/, 'Tag 格式应为 v主版本.次版本.补丁版本, 可带 -alphaN');
  const version = tag.slice(1);
  return { version, stable: !version.includes('-') };
}

if (process.argv[2] === '--self-check') {
  assert.deepEqual(parseReleaseTag('tag', 'v0.0.1-alpha18'), { version: '0.0.1-alpha18', stable: false });
  assert.deepEqual(parseReleaseTag('tag', 'v0.1.0'), { version: '0.1.0', stable: true });
  for (const tag of ['0.1.0', 'v1.2', 'v01.2.3', 'v1.2.3-SNAPSHOT', 'v1.2.3-alpha01', 'v1.2.3-beta1', 'v1.2.3-rc2', 'v1.2.3+build', 'v1.2.3\n', 'v1.2.3/extra', 'v1.2.3;echo bad']) {
    assert.throws(() => parseReleaseTag('tag', tag));
  }
  assert.throws(() => parseReleaseTag('branch', 'v1.2.3'));
  console.log('发行 Tag 版本与渠道自检通过');
} else {
  const { version, stable } = parseReleaseTag(process.env.GITHUB_REF_TYPE, process.env.GITHUB_REF_NAME);
  fs.appendFileSync(process.env.GITHUB_OUTPUT, `version=${version}\nstable=${stable}\n`);
}
