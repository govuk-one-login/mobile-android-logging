# Signing commits

Please see the [Glossary] for the following terms used within this page:

- GPG
- NIST

## Generating a GPG key

In order to generate a GPG key, a Developer needs to download the required
tools. The [gnupg download page] works, as does `homebrew`:

```shell
brew install gpg

# The following command now works.
gpg --version
```

Developers should create a GPG key that's:

- RSA and RSA based
- Developer preference on the length of the key. NIST recommends 2048 at
  minimum. The longer, the better.
- A Developer's preference in relation to expiry
- A Developer's real name
- The email address defined in the git configuration's `user.email` property.
  If the email address you use is different then the repository won't verify
  the commit.
- A comment to help identify the created GPG key at a glance, such as
  "govuk-one-login GitHub key."

Use the following command to create a GPG key with the preceding properties:

```shell
gpg --full-generate-key
```

Please see the GitHub article '[generating a new GPG key]' for more information.

## Making GitHub aware of the GPG key

GitHub becomes aware of the created GPG key via
the [GitHub SSH and GPG keys settings]. Any title works for the GPG key, so long
as the entry is identifiable. It may be useful for the title to match the
comment stored within the created GPG key.

The 'Key' input field takes an exported GPG key. In order to obtain an exported
GPG key, list the created GPG secret keys with the proceeding command:

```shell
gpg --list-secret-keys --keyid-format=long
```

The command outputs entries that follow the pattern of defining the type of key,
a forward slash, then it's private identifier and the date of creation. This
looks like so:

```
/Users/developer/.gnupg/pubring.kbx
-----------------------------------
sec   rsa4096/0123456789ABCDEF 2022-07-27 [SC]
      0123456789ABCDEF0123456789ABCDEF01234567
uid                 [ultimate] The Developer (govuk-one-login) <1234+Account@users.noreply.github.com>
ssb   rsa4096/123456789ABCDEF0 2022-07-27 [E]
```

For the preceding example, the identifier is '0123456789ABCDEF', prefixed by
'rsa4096/'.

Use the private identifier with the proceeding command,
replacing `${identifier}` with your identifier:

```shell
gpg --armor --export ${identifier}
```

The expected output begins with `-----BEGIN PGP PUBLIC KEY BLOCK-----` and ends
with `-----END PGP PUBLIC KEY BLOCK-----`. Copy, paste the entire contents into
the 'Key' input field within GitHub, then save.

Please see the GitHub article '[adding a GPG key to your GitHub account]' for
more information.

## Making Git aware of the GPG key

Set the `user.signingkey` git configuration property to your private identifier
with the following command in a terminal window at the cloned repository's root
directory:

```shell
git config user.signingkey ${identifier}
```

Automatically sign commits by setting the git configuration property
`commit.gpgsign` to true:

```shell
git config commit.gpgsign true
```

In order for GPG to handle password protected keys, set the `GPG_TTY`
environment variable within the `~/.bash_profile`, `~/.profile` for BaSH users,
or the `~/.zprofile` or `~/.zshrc` files for Zsh users:

BaSH Users:

```shell
$ if [ -r ~/.bash_profile ]; then echo 'export GPG_TTY=$(tty)' >> ~/.bash_profile; \
  else echo 'export GPG_TTY=$(tty)' >> ~/.profile; fi
```

Zsh Users:

```shell
$ if [ -r ~/.zshrc ]; then echo 'export GPG_TTY=$(tty)' >> ~/.zshrc; \
  else echo 'export GPG_TTY=$(tty)' >> ~/.zprofile; fi
```

Please see the GitHub article '[telling Git about your GPG Key]' for more
information.

## Verifying that the GPG key is successfully linked

Create a new git branch:

```shell
git checkout -b $(id -un)/gpgTest # currentUsername/gpgTest
```

Add an empty file to the branch. Commit and push the commit. 

```shell
git checkout $(id -un)/gpgTest # currentUsername/gpgTest

touch $(git rev-parse --show-toplevel)/emptyFile.txt # rootGitDirectory/emptyFile.txt
git add $(git rev-parse --show-toplevel)/emptyFile.txt # git add /path/to/root/git/directory
git commit --message="$(id -un): Test GPG signing (Add empty file)" --no-verify

git push origin HEAD --no-verify

git show --show-signature | grep "Good signature from" # Outputs a line if the commit is verified

open https://github.com/govuk-one-login/mobile-id-check-android/tree/$(id -un)/gpgTest
```

If successful, the latest commit hash on the page has a green tick next to it.

If unsuccessful, perform any necessary changes and try again. The proceeding
script is an alternative to the preceding shell block, if the empty file
already exists:

```shell
git checkout $(id -un)/gpgTest

rm $(git rev-parse --show-toplevel)/emptyFile.txt # rootGitDirectory/emptyFile.txt
git commit --message="$(id -un): Test GPG signing (Remove empty file)" --no-verify

git push origin HEAD --no-verify

git show --show-signature | grep "Good signature from" # Outputs a line if the commit is verified

open https://github.com/govuk-one-login/mobile-id-check-android/tree/$(id -un)/gpgTest
```

Alternate between the two preceding shell script blocks until the configuration
is correct.

[adding a GPG key to your GitHub account]: https://docs.github.com/en/authentication/managing-commit-signature-verification/adding-a-gpg-key-to-your-github-account

[generating a new GPG key]: https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key

[GitHub SSH and GPG keys settings]: https://github.com/settings/keys

[Glossary]: /docs/glossary.md

[gnupg download page]: https://www.gnupg.org/download/

[telling Git about your GPG Key]: https://docs.github.com/en/authentication/managing-commit-signature-verification/telling-git-about-your-signing-key
