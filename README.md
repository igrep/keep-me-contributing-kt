# keep-me-contributing-kt

Check if you have contributed to the configured repository today.  
Runs as a native Android app or a browser app.  
Written pulely in Kotlin (thanks to Kotlin/JS!).

## Why?

**I never want to miss to fill [the contribution map in GitHub](https://github.com/igrep).**  
To accomplish that, I decided to update [this repository](https://github.com/igrep/daily-commits) every day.  
It's just a diary to record what computer-related-things I've done everyday.  
Updating the repository is not directly connected to contributions to OSSs, but it does keep me motivated.

## Usage of the Browser App

1. Go to [keep-me-contributing.igreque.info](https://keep-me-contributing.igreque.info/).
1. Enter your GitHub user name, repository name to check the latest commit date, and your [pesonal access token for GitHub](https://help.github.com/en/articles/creating-a-personal-access-token-for-the-command-line), then click `Start Checking`.
1. The app checks the `commttedDate` of the latest commit on the master branch of `https://github.com/<your-github-user-name>/<repository-name>` with [GitHub API](https://developer.github.com/v4/).
    - Then if the latest commit date is not later than the beginning of today, the app warns you ⚠️.
1. That's it!


## Known Issues

Currently this app is **only for me**. So there're several issues I intentionally skipped to solve.  
Pull requests are welcome!

1. The Android app is not published on Google Play yet (due to the following issues).
1. The timezone is **hardcoded as Asia/Tokyo**. So if you don't live in Japan, it doesn't work as expected.
1. Poor error handling: e.g. even if you enter a wrong personal access token, the app doesn't show you any detailed error messages.
1. The app **doesn't check who** is the committer. So if you check commits of a repository committed by several people, the app can show wrong check result.
1. Only GitHub is supported. Sorry for GitLab and Bitbucket fans.
1. No iOS version. But I have no iOS machine and no Mac, so I'd publish the core library on a Maven repository to let someone to create if necessary.
