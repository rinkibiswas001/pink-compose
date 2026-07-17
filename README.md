# Pink Compose

CI Test
Pink Compose is a modern Android application built with Jetpack Compose, designed to explore and manage recipes.

## Features

- **Authentication** : Login screen with session persistence, so users stay logged in across app restarts.
- **Home** : A landing screen with an auto-sliding image banner, a horizontal list of recipe categories, and a grid of all recipes.
- **Recipe Categories** : Browse recipes grouped by category (Breakfast, Lunch, Dinner, Dessert, Drinks).
- **Recipe List** : View all recipes within a selected category.
- **Recipe Details** : See ingredients, duration, and a step-by-step cooking guide with images and navigation between steps.
- **Profile** : View account details and log out.

## Tech Stack

- **Jetpack Compose**: For building a reactive and modern UI.
- **Kotlin**: The primary programming language.
- **Clean Architecture**: Organized into data, domain, and presentation layers for better maintainability and testability.
- **Coil**: Efficient image loading and caching.
- **Hilt** : Dependency injection across ViewModels, use cases, and repositories.
- **Retrofit/OkHttp**: For networking and API interactions.
- **DataStore Preferences** — Lightweight local storage for login session state.
- **my-json-server (GitHub-backed)** — mock REST API for users, categories, and recipes.
- **Gradle Kotlin DSL**: For project configuration.

## Architecture

The project follows the Clean Architecture pattern:

- **Data**: Contains remote DTOs, repository implementations, and API services.
- **Domain**: Contains business logic, including models and use cases.
- **Presentation**: Contains ViewModels and Compose UI components.

```
com.pinkcompose/
├── data/
│   ├── remote/        # ApiService, DTOs
│   ├── local/          # DataStore (session persistence)
│   └── repository/    # Repository implementations
├── domain/
│   ├── model/          # Domain models
│   ├── repository/    # Repository interfaces
│   └── use_case/      # Business logic
└── presentation/
    ├── login/
    ├── home/
    ├── recipe_category/
    ├── recipe_list/
    ├── recipe_details/
    ├── profile/
    └── screens/        # Navigation graph, shared nav bars
```

## App Flow
```
Login Screen
│
▼
Home Screen (banner + categories + recipe list)
│
▼
Recipe Category Screen
│
▼
Recipe List Screen
│
▼
Recipe Details Screen (ingredients + cooking steps)

Profile Screen (accessible via bottom nav / drawer)
```

## Getting Started

To get started with this project, clone the repository and open it in Android Studio.

```
git clone <repository-url>
```

Build the project and run it on an emulator or a physical device.

## Demo Login

This app uses a mock backend (my-json-server), which has a single seeded user for testing:

```
Email: demo@gmail.com
Password: 123456
```

## API
Recipe and category data is served from a my-json-server instance backed by a db.json file in a GitHub repository, providing endpoints for users, categories, and recipes.


