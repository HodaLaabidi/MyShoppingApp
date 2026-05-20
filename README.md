# MyShoppingApp 🛒

An Android application for managing shopping lists with location-based features.

## 📱 Demo
<!-- Add your demo video or GIF here -->
![App Demo](demo/demo.gif)

## ✨ Features
- **Add/Edit/Delete** shopping items.
- **Quantity Management**: Keep track of how many items you need.
- **Location Integration**: Search for and attach addresses to your shopping items using Google Maps API.
- **Material 3 Design**: Modern and responsive user interface.

## 🛠️ Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit & Gson
- **Maps & Location**: Google Maps Compose & Play Services Location
- **Dependency Management**: Gradle Version Catalog

## 🔑 Setup
To run this project, you need to add your Google Maps API Key:
1. Create a `local.properties` file in the root directory.
2. Add the following line:
   ```properties
   MAPS_API_KEY=YOUR_API_KEY_HERE
   ```
