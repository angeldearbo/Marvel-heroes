# Marvel-heroes

A sample project using the Marvel API to show a list of superheroes and some details about them.

| Superheroes List | Superhero Details | Error & Retry |
-------------------|-------------------|----------------
|![Superheroes List](/images/list.png) | ![Superhero Details](/images/detail.png) | ![Error Loading](/images/retry.png) |

## Marvel API KEY

The project need `marvel_public_api_key` and `marvel_private_api_key` to build. You can add them to your home level `gradle.properties` file (located in `~/.gradle` on Unix based systems):

```
marvel_public_api_key=<PUBLIC API KEY HERE>
marvel_private_api_key=<PRIVATE API KEY HERE>
```

or using `-Pmarvel_public_api_key=<PUBLIC API KEY HERE> -Pmarvel_private_api_key=<PRIVATE API KEY HERE>` to each gradle command e.g.:

```
./gradlew assembleDebug -Pmarvel_public_api_key=<PUBLIC API KEY HERE> -Pmarvel_private_api_key=<PRIVATE API KEY HERE>
```

Check out the [Marvel Developer portal][mdp] for more info.

## App Architecture

The app uses a clean architecture with kotlin coroutines. The app follows a layered architecture with data, domain,  framework and usecases layer. The package structure is an attempt to package by layer. The app uses une activity to list superheroes and other activity to show superheores details.

### Data Layer

The API call is modeled using Retrofit, GSON Serialization as the converter. The data layer converts the DTO objects to Domain objects. Any errors that happen up to this point are mapped to a sealed class `HeroesError` (categorised as Error or Fatal). A custom exception `HeroesError` that contains a property of the error.

### Domain Layer

The main class here is `Hero`. Contains all data classes for this app.

### Framework

Contains all framework integration, datasources implementation and ui.

Each activity has a Jetpack ViewModel that:

- exposes a single `LiveData` backed by a `UiModel` (caching the last item) describing the state of the view at a given time
- exposes all usecases functions for their view

The Activity observes the `LiveData<UiModel>` in 'onCreate' and updates the `Sceen`.

### Dependency Injection

The sample uses Dagger Hilt.

### Testing

[Under contrsuction]

## Acknowledgments

This sample are heavily inspired by open source code I have read:

- [Architect Coders][architect-coders]
- [Superheroes LordRaydenMK][superheores-lordraydenmk]
- [Karumi KataSuperheroes][karumi-kata]

[mdp]: https://developer.marvel.com/
[architect-coders]: https://github.com/antoniolg/architect-coders
[superheroes-lordraydenmk]: https://github.com/LordRaydenMK/SuperheroesAndroid
[karumi-kata]: https://github.com/Karumi/KataSuperHeroesJetpack
