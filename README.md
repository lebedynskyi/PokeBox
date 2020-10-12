
  
# Android test application    
 Develop one app showing a list of Pokémon.      
When user selects a Pokémon from the list, app must show details of the Pokémon such as name, picture(s), stats and type (fire, poison, etc.) API are available here: https://pokeapi.co    
      
### Guidelines: 
1. Min. SDK 21  - ***Done***  
2. Use Kotlin language  - ***Done***  
3. Use Clean Architecture (Repository pattern) and MVI (e.g. Uniflow lib) - ***Done***  
4. Use JetPack: (ViewModel, Room, Navigation) - ***Done***  
5. Use Koin (DI) - ***Done***  
6. Use Retrofit2 and OkHttp3  - ***Done***  
7. Use Coroutines + Flow  ***Done***  
8. Use Moshi and Glide  - ***Done***  
9. Create pagination with Jetpack’s Paging library  ***Done***  
10. Publish code in a github public repository  - ***Done***  
  ### Bonus tasks: 
11. Make app work offline too - ***Done***  
12. Write Unit Tests  ***Not enough time***  
13. Customize the project with something you believe could be useful for the app


 ###  Some explanation:
1. There is no API to get images in the list. According official answer from **Slack** Group I used workaround - Used hardcoded API Link with ID placeholder. You can find it [here](https://github.com/lebedynskyi/PokeBox/blob/7916658bf3ac90c838ae6c4f09aec6c2760c86bf/app/build.gradle#L31)  
  
2. Images are downloaded via [Glide](https://bumptech.github.io/glide/) lazy loaded. Not a bunch of images. This is Highly customizable tools. That allow to show different images on different states. Loading or Error. There no need to use spinner. You can find it here. [PlaceHolder](https://github.com/lebedynskyi/PokeBox/blob/43d8a367164ca6d2c84b36740a9724f1f4605f1f/app/src/main/java/app/box/pokemon/ui/ViewExtensions.kt#L18)

  
3. According Pagination. I’ve used Pagination from Android [Pagination 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)  
UI has connection via **Flow** stream that is going from data base. Than UI Adapter triggers [Mediator](https://github.com/lebedynskyi/PokeBox/blob/43d8a367164ca6d2c84b36740a9724f1f4605f1f/app/src/main/java/app/box/pokemon/data/source/PagingPokemonSourceMediator.kt) and it fetch more data and update database, that triggers UI to update

Also there is a branch called **Pagination 2** that used previous deprecated **[Pagination 2](https://developer.android.com/topic/libraries/architecture/paging)**