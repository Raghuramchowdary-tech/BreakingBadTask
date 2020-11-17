# BreakingBadTask Android application
Android app that lets people search for a breed and find out more about a specific dog.
The application uses the api provided by https://breakingbadapi.com/

## Description

The sample app is a two fragment and one single activity application
* First screen shows a list of characters and ability to filter by searching and also by selecting their appearance in seasons
* Second screen will show the details of the character

### Application techstack

The app is fully written in kotlin

The app uses some of the latest frameworks on Android
1. Navigation controller (No more fragment transactions)
2. List adapter for resyclerview to handle the data changes (So diff calculation happens on background thread)
3. Espresso tests are written for the UI (However, there are lot more tests can be written. The code is written in a way to enable that)
4. Unit tests are written for code (However, there are lot more tests can be written. The code is written in a way to enable that)
5. Data binding and view binding both are used :) 
6. Uses retrofit for network calls

## Getting Started

1.  Pull down the code locally.
2.  Open Android Studio and select 'Open an existing Android Studio Project'
3.  Navigate to checked out repository.
4.  Run the application
