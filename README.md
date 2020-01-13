# rate-converter-android

#### Features
- Fetches rates every second from API
- Calculates currency conversion based on user input
- Switches selected rate to the top

#### Known issues
- Clicking on text input doesn't change row order
- Input only from the first row is considered
- Missing tests for view model

#### FAQ
- _Koin_?
    - Using a service locator for a small scale project like this is suitable and easy to set up
- Warning _StaticLayout: maxLineHeight should not be -1.  maxLines:1 lineCount:1_ is shown in console
    - Apparently a bug that will be fixed https://issuetracker.google.com/issues/121092510
