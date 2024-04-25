# Git large file storage (LFS)

[Git LFS] replaces large files such as audio samples, videos, datasets, and
graphics with text pointers inside Git, while storing the file contents on a
remote server.

The code base uses LFS for the contents of the [Root directory's lib folder].
Because LFS initially provides references to files instead of the actual files,
the initial set up of the git repository requires the developer to fetch and
pull the files stored within the LFS servers.

Refer to the [Dependencies] tutorial for installing the `git-lfs` homebrew recipe.

## Obtaining the remotely stored files

Fetching the LFS files obtains the latest state of the files:

```shell
git lfs fetch --all
```

The preceding command doesn't download the files. It makes sure that the
references are up to date.

To download the files themselves:

```shell
git lfs pull
```

## Troubleshooting

### LFS isn't initialised

Refer to and complete the [Git Hooks tutorial], as the project git hooks contains the necessary LFS
commands.

[Dependencies]: ./dependencies.md
[Git Hooks tutorial]: ./gitHooks.md
[Git LFS]: https://git-lfs.com/
[Root directory's lib folder]: ../../libs