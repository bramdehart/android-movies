# android-movies
## About
This app let's you search for movies. You can view movie details and save your favorites.

## Project requirements
* Uses Activities
* Uses an external RESTful API
* Implements Localization
* Good Architecture
* Uses Local Storage
* Responsive layout
* Optional: Animations

### Activities
The following activities are used:
* TrendingActivity (Home screen of the app, contains an overview today's trending movies)
* SearchActivity (Activity with an editbox implementend. Fires a new intent when submitting a search request)
* SearchResultActivity (Contains an overview of movies that are the result of a JSON search request by movie name)
* MovieDetailActivity (Shows detailed information about a selected movie)
* FavoritesActivity (Favorite movies of the user)

### RESTful API
The app makes use of the TMDB API. The Movie Database (TMDb) is a community built movie and TV database. You can find more information on [their website](https://www.themoviedb.org/).

JSON request URL's are build within the NetworkUtils class.

### Recyclerview and Adapters
Recyclerviews are used within the TrendingActivity (Movies), SearchResultActivity (Movies), FavoritesActivity (Movies) and MovieDetailActivity (Cast / Crew). Their adapters can be found within the adapter package.

### Localization
The current languages are supported: English, German, Dutch.
Localization is implementend by supporting different language xml string files. On top of that, the system's language is added to each JSON request so that these results are also localized.

### Architecture
Classes are devided into the following packages: activities, adapters, data, helpers, models.

### Local Storage
The app uses SQLite to enable Local Storage functionality. Favorite movies are stored within an local database on the user's device. Local Storage functionality are realised within the FavoritesContract, FavoritesDbHelper and FavoritesActivity classes. 

### Responsive layout
Response layout is implemented. For example, more movies are shown when the app is displayed in landscape mode.  

### Animations
There are some animations implemented within the MovieRecyclerView and MovieDetailActivity.

## Screenshots
<img src="https://www.bramdehart.nl/screenshots/android/android-1.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-2.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-3.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-4.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-6.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-7.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-12.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-11.png" width="250" /><img src="https://www.bramdehart.nl/screenshots/android/android-9.png" width="250" />
<img src="https://www.bramdehart.nl/screenshots/android/android-8.png" width="500" />
