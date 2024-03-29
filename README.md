# TechnicalTest
An app to display list of products<br/>
This App is using [get Products API](https://dummyjson.com/products) as a remote data

Architecture
---------------
When start developpment an Android app it's very important to plan the architecture of the project becuse it will allow us to create complex, robust, good quality, easy to maintain applications so for this project i choose implemented MVVM + Clean Architecture becuase it's very recomanded.

This diagram will show you the modules and their interactions that we will be using:
![Architecture](screenshots/architecture.png "Architecture")

Structure
---------------
data: (Model). Where we perform data operations.

di: Dependency Injection with the help of Hilt.

ui: Our Fragments and ViewModels helping to display data to the user.

adapters: contain all adapters

models: Our Models on the App

applications : contain the application Class


Screenshots
-----------
![MainFragment](screenshots/products_screen.png "list of products")
![ProductDetailsFragment](screenshots/product_details_screen.png "product details")

Application features
---------------
* add product
* get products
* get product details

Libraries Used
---------------
* [Kotlin](https://kotlinlang.org/)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
* [LiveData](https://developer.android.com/jetpack/arch/livedata) - notify the view when data changes .
* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
* [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite database with in-app objects and compile-time checks
* [Material](https://material.io/develop/android/docs/getting-started/) - Material Components.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
* [Retrofit2](https://square.github.io/retrofit/)- networking
* [Glide](https://github.com/bumptech/glide)- display images
* [Dagger Hilt](https://dagger.dev/hilt/) - dependency injector
* [Gson](https://github.com/google/gson) - convert Java Objects into their JSON and vice versa
* [Mockito](https://site.mockito.org/) - mockito for mock data
* [Junit](https://junit.org/junit5/) - Junit for unit test

The improvement period of this project
---------------
7 hours