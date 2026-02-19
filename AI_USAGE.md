# AI Usage Disclosure

## AI Tools Used
- **Google AI (Android Studio Sidekick)**: Used for boilerplate generation, architecture brainstorming, and dependency management.

## Where AI was used
- **Gradle Configuration**: Assisted in setting up the `libs.versions.toml` and `build.gradle.kts` files with modern versioning and Hilt/KSP integration.
- **Data Layer**: Generated the initial Room Entity, DAO, and Retrofit interface based on the FakeStoreAPI structure.
- **UI Components**: Drafted the initial Compose layouts for the product list and detail screens.
- **Architecture**: Helped in structuring the Clean Architecture layers (Data, Domain, UI).

## What AI output was accepted vs modified
- **Accepted**: Standard Room and Retrofit interface patterns, basic Compose UI layouts (LazyColumn, Card, etc.).
- **Modified**: 
    - Repository implementation was significantly modified to follow the "Single Source of Truth" pattern (Offline First).
    - UI state management (`UiState`) was customized to handle Loading, Success, Error, and Empty states consistently.
    - Gradle accessor names were manually corrected (e.g., changing hyphenated names to dot-notated accessors) when sync errors occurred.

## What AI suggestions were rejected and why
- **Rejected**: A suggestion to use a simple List in the ViewModel instead of a formal `UiState` wrapper. I rejected this to ensure a better UX with proper loading and error handling, as required by the assignment.

## Example of Improvement
- The AI initially suggested fetching data directly from the API in the ViewModel. I improved this by introducing a Repository layer and a Room database for offline caching, ensuring the app remains functional without an internet connection (Offline Support requirement).
