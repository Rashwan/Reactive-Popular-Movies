# Reactive Popular Movies
Rewrite for Final project of Udacity's [Developing Android Apps](https://www.udacity.com/course/developing-android-apps--ud853) course using moderan Android stack.
You can find the old project [here](https://github.com/Rashwan/PopularMovies).

### Features:
* Discover the most popular or top rated movies.
* Watch all the movie's trailers and see how people reviewd the movie.
* Save your favorite movies to view them even when you are offline.

### Screenshots
#### Phone : 
<p align="center">
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/BrowseMovies(phone).png" width="40%" />
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/MovieDetails(phone).png" width="40%" />
</p>

#### Tablet :
<p align="center">
 <img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/Tablet.png" width="75%" />
</p>

### Inspiration 
The app is built using  [MVP (Model View Presenter)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern, it draws inspiration from many open-sourced apps especially:
* [bourbon](https://github.com/hitherejoe/Bourbon) (Architicture).
* [plaid](https://github.com/nickbutcher/plaid) (Animation & Transitions).
* [Ribot's android-boilerplate](https://github.com/ribot/android-boilerplate) (Architicure & coding style).

### Requirements 
* JDK 1.8
* [Android SDK](http://developer.android.com/sdk/index.html).
* [Android Nougat (API 24)](https://developer.android.com/studio/releases/platforms.html) .
* Latest Android SDK Tools and build tools.

### How to use : 
* Get an api key from [here](https://www.themoviedb.org/documentation/api) and add it to `gradle.properties` file.
* To build install and run a debug version, run this from the root of the project:

    `./gradlew app:assembleDebug`


### Libraries : 
* [Picasso](http://square.github.io/picasso)
* [Retrofit](http://square.github.io/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Okhttp](http://square.github.io/okhttp)
* [SqlBrite](https://github.com/square/sqlbrite)
* [SqlDelight](https://github.com/square/sqldelight)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [RetroLambda](https://github.com/orfjackal/retrolambda)
* [AutoValue](https://github.com/google/auto/tree/master/value)
* [AutoValue Moshi](https://github.com/rharter/auto-value-moshi)
* [AutoValue parcel](https://github.com/rharter/auto-value-parcel)
* [Dagger 2](http://google.github.io/dagger)
* [ButterKnife](http://jakewharton.github.io/butterknife)
* [Timber](https://github.com/JakeWharton/timber)
