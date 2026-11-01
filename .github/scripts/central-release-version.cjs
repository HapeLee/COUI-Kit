const assert = require('node:assert/strict');
const fs = require('node:fs');

function releaseVersion(refType, tag) {
  assert.equal(refType, 'tag', 'Central 发行必须由 Tag 触发');
  assert.match(tag, /^v(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-(?:alpha|beta|rc)(0|[1-9]\d*))?$/, 'Tag 格式应为 v主版本.次版本.补丁版本, 可带 -alphaN、-betaN 或 -rcN');
  return tag.slice(1);
}

if (process.argv[2] === '--self-check') {
  for (const tag of ['v0.0.1-alpha4', 'v0.1.0', 'v1.2.3-beta1', 'v1.2.3-rc2']) {
    assert.equal(releaseVersion('tag', tag), tag.slice(1));
  }
  for (const tag of ['0.1.0', 'v1.2', 'v01.2.3', 'v1.2.3-SNAPSHOT', 'v1.2.3-alpha01', 'v1.2.3+build', 'v1.2.3\n', 'v1.2.3/extra', 'v1.2.3;echo bad']) {
    assert.throws(() => releaseVersion('tag', tag));
  }
  assert.throws(() => releaseVersion('branch', 'v1.2.3'));
  console.log('Central Tag 版本自检通过');
} else {
  const version = releaseVersion(process.env.GITHUB_REF_TYPE, process.env.GITHUB_REF_NAME);
  fs.appendFileSync(process.env.GITHUB_OUTPUT, `version=${version}\n`);
}
