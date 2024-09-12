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

### Usage
The commit prefix is inserted when pressing the 'm' icon in the commit tool window.

Alternatively, automatic commit prefix can be toggled in Monorepo Tools > Auto Commit Message.

### Getting started

1. Open File > Settings > Monorepo Tools
2. Configure 'Folder Levels'
   1. This is how many folders that should be removed from a path. E.g. for a file `modules/submodules/submodule1`, a folder level for `modules` and level `2`, will remove `modules/submodules` and insert `[submodule1]`, whereas a level of `1` will only remove `modules` and insert `[submodules]`.
   2. This is useful as the whole folder structure needn't be written into a config.
3. Configure 'Expand folders'
   1. This is what folders to keep in the path to keep, e.g. if the parent folder should still be part of the project name. For a file `modules/module1`, and an expansion for `modules` is set, the prefix will be `[modules/module1]`.
4. Configure root folder name. If any changes are made in the root folder, a custom name can be specified to insert in the commit prefix. E.g. a file `./file.txt`, the default prefix will be `[root]`.

<!-- Plugin description end -->
