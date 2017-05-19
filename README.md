# Reactive Popular Movies
Rewrite for Final project of Udacity's [Developing Android Apps](https://www.udacity.com/course/developing-android-apps--ud853) course using moderan Android stack.
You can find the old project [here](https://github.com/Rashwan/PopularMovies).

### Features:
* Discover popular, top rated or upcoming movies.
* Discover Movies that your nearby friends have liked. (Using Nearby Messages API)
* Watch all the movie's trailers and see how people reviewd the movie.
* See the movie's review score from (IMDB, TMDB, Rotten Tomatoes and Metacritic).
* See similar movies for your chosen movie.
* Get to know the cast of the movie along with their bio, images and their movies credits.
* Save your favorite movies to view them even when you are offline. (Work in progress)
* Add Movies to your Watchlist and view them even when you are offline. (Work in progress)
### Screenshots
<p align="center">
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-1-fs8.png" width="30%" />
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-2-fs8.png" width="30%" />
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-3-fs8.png" width="30%" />
</p>
<p align="center">
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-4-fs8.png" width="30%" />
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-5-fs8.png" width="30%" />
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-6-fs8.png" width="30%" />
</p>
<p align="center">
<img src="https://github.com/Rashwan/Reactive-Popular-Movies/blob/master/readme-art/readme-art-7-fs8.png" width="30%" />
</p>

### Inspiration 
The app is built using  [MVP (Model View Presenter)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern, it draws inspiration from many open-sourced apps especially:
* [bourbon](https://github.com/hitherejoe/Bourbon) (Architicture).asd
* [plaid](https://github.com/nickbutcher/plaid) (Animation & Transitions).
* [Ribot's android-boilerplate](https://github.com/ribot/android-boilerplate) (Architicure & coding style).

### Requirements 
* JDK 1.8
* [Android SDK](http://developer.android.com/sdk/index.html).
* [Android Nougat (API 25)](https://developer.android.com/studio/releases/platforms.html) .
* Latest Android SDK Tools(Android studio 3.0 canary) and build tools.

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
* [AutoValue](https://github.com/google/auto/tree/master/value)
* [AutoValue Moshi](https://github.com/rharter/auto-value-moshi)
* [AutoValue parcel](https://github.com/rharter/auto-value-parcel)
* [Dagger 2](http://google.github.io/dagger)
* [ButterKnife](http://jakewharton.github.io/butterknife)
* [Timber](https://github.com/JakeWharton/timber)

<B>The app is a work in progress many refinements will be made to existing features along with new features like search :) .</B>
