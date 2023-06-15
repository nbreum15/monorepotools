# CLI startup performance

## JAR
```shell
cd to/a/git/repo
time git diff --name-only --cached | xargs ./../monorepo-tools/cli/build/distributions/cli/bin/cli -config=file:.idea/MonorepoCommitMessage.xml -branch-name=(git rev-parse --abbrev-ref HEAD)
```

### Timings
- 1.15 s
- 1.20 s
- 1.14 s
- 1.19 s
- 1.16 s

Avg: 1.168 s

## NATIVE (GraalVM)
```shell
cd to/a/git/repo
time git diff --name-only --cached | xargs ./../monorepo-tools/cli/build/native/nativeCompile/cli -config=file:.idea/MonorepoCommitMessage.xml -branch-name=(git rev-parse --abbrev-ref HEAD)
```

### Timings
- 556.11 ms
- 553.94 ms
- 563.95 ms
- 605.12 ms
- 600.73 ms

Avg: 575.97 ms = 0.57597 s

Time saved: 1.168 s - 0.576 s = 0.592 s