# 🛒 Lumos Practical - E-Commerce Product Catalog

A production-ready Android application demonstrating a product catalog fetched from a REST API, featuring **True Pagination**, **Offline-First Caching**, and a robust **Clean Architecture**.

---

## 🚀 Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/oyehanif/LumosPractical.git
   ```
2. **Open in Android Studio**:
   - Use **Android Studio Ladybug (2024.2.1)** or newer.
   - Recommended: Use **JDK 17** (standard for modern Android projects).
3. **Gradle Sync**:
   - Once the project opens, wait for the automatic Gradle sync to complete.
4. **Run the App**:
   - Connect a physical device or start an emulator (**API 26+ required** for adaptive icon support).
   - Click the **Run** button (Green Play Icon).

---

## 🏛 Architecture Overview

The project is built using **MVVM (Model-View-ViewModel)** with a strictly modularized **Clean Architecture** approach:

### 1. Data Layer (`data/`)
- **Remote**: Retrofit service for [DummyJSON API](https://dummyjson.com/).
- **Local**: Room Database for persistent caching.
- **Paging**: `RemoteMediator` implementation to synchronize remote API data with the local database.
- **Repository Implementation**: Manages data flow between remote and local sources.

### 2. Domain Layer (`domain/`)
- **Models**: Plain Kotlin data classes representing the business entities (e.g., `Product`).
- **Repository Interface**: Defines the contract for data operations, abstracting the implementation details from the UI.

### 3. UI Layer (`ui/`)
- **Jetpack Compose**: 100% declarative UI components.
- **ViewModels**: State-aware components that interact with the repository and expose data via Kotlin `Flow`.
- **Navigation**: Type-safe navigation using `Compose Navigation`.

---

## 💡 Key Decisions & Trade-offs

### 1. Paging 3 + RemoteMediator (Offline-First)
- **Decision**: Used Paging 3 with `RemoteMediator` for the product list.
- **Trade-off**: While Paging 3 has a steeper learning curve than simple lists, it provides a superior "Production-Ready" experience by handling edge cases like scroll-to-load, database-as-source-of-truth, and state management (Loading/Error) out of the box.

### 2. Single Source of Truth
- **Decision**: The UI only observes the local database.
- **Trade-off**: This adds a layer of complexity (Mappers, RemoteKeys), but ensures the app works perfectly offline and prevents UI "flickering" during network refreshes.

### 4. Forced Refresh Policy
- **Decision**: Clears the local database upon a successful `REFRESH` call.
- **Trade-off**: This ensures the user always sees fresh data when they "pull to refresh" or launch the app, at the expense of extra network usage.

---

## 🚧 Known Limitations

- **Image Caching Policy**: Coil handles basic memory/disk caching, but a more aggressive custom disk caching policy could be implemented for very low-connectivity environments.
- **Unit Testing**: Due to the time constraints of the practical task (4-6 hours), the focus was placed on architecture and core functionality. Full coverage of Unit and UI tests is a prioritized future enhancement.
- **Adaptive Icon**: Requires API 26+. Devices on API 24-25 will fall back to legacy icons.

---

## 🛠 Tech Stack
- **Kotlin Coroutines & Flow**
- **Dagger Hilt** (Dependency Injection)
- **Room** (Persistence)
- **Retrofit & OkHttp** (Networking)
- **Jetpack Compose** (UI)
- **Paging 3** (Pagination)
- **Coil** (Image Loading)
