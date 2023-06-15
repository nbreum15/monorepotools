# Monorepo Tools

<!-- Plugin description -->
# Features

## Commit message prefix

Inserts a commit prefix based on:

- The branch name
- The changed project files

For example, for a checked out a branch called `1234-a-new-feature`, and the following changes:
```
modules/module1/file.txt
```

The tool can be configured to write a commit message prefix: `#1234 [module1]`. If no issue ID is present in the branch, none is inserted.

### Getting started

1. Open File > Settings > Monorepo Tools
2. Configure 'Folder Levels'
   1. This is how many folders that should be removed from a path. E.g. for a file `modules/submodules/submodule1`, a folder level for `modules` and level `2`, will remove `modules/submodules` and insert `[submodule1]`, whereas a level of `1` will only remove `modules` and insert `[submodules]`.
   2. This is useful as the whole folder structure needn't be written into a config.
3. Configure 'Expand folders'
   1. This is what folders to keep in the path to keep, e.g. if the parent folder should still be part of the project name. For a file `modules/module1`, and an expansion for `modules` is set, the prefix will be `[modules/module1]`.
4. Configure root folder name. If any changes are made in the root folder, a custom name can be specified to insert in the commit prefix. E.g. a file `./file.txt`, the default prefix will be `[root]`.

## Exclude folders (experimental feature)
*Note this feature uses `git ls-files`, and therefore only works in a Git repository*.

Exclude folders automatically based on glob patterns.

For example, in a monorepo you may only want to have Java APIs shown, so you can configure a pattern:

`**/package.json` to exclude all folders with such a file. These exclusions will be written to the module IML file.

<!-- Plugin description end -->
