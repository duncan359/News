# News
News was using MVP Structure and implements by Eventbus and Retrofit.

## Environment
* Android Studio 3.0.1
* Support SDK 11 to 25

### Structure 
* Activity load context from **fragment** which are available in **com.duncan.read.News.view** folder 
* Fragment call presenter which are available in **com.duncan.read.News.presenter** for process **HACKER NEWS** api calling. 
* presenter do process for request and call to Retrofit by UseCaseImpl whih are available in **com.duncan.read.News.domain** 
* ReposityitoryImpl will be called in **UseCaseImpl** by **Retrofit** and **Logansquare** library and return callback. 
* UseCaseImpl will pass the result to presenter by eventbus event. 
* presenter will collect the data from server and do the process and pass to **fragment** by eventbus. 
* fragment receive response and process to generate listview and display in screen.
