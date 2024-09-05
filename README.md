<h1 align="center">
CryptoX 
</h1>


<p align="center">
    <img src="https://img.shields.io/badge/-Kotlin-7c6fe1?style=flat&logo=kotlin&logoColor=white">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/recepbrk"><img alt="Profile" src="https://img.shields.io/badge/github-recepbrk-blue"/></a> 

</p>
<div align="center">
  <table style="border-collapse: collapse; width: auto; text-align: center;">
    <tr>
      <td style="border: 1px solid #ccc; padding: 10px; border-radius: 8px; background-color: #f9f9f9; vertical-align: middle;">
        <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/res/drawable/logo.png" alt="CryptoX" width="30" style="vertical-align: middle;">
      </td>
      <td style="border: 1px solid #ccc; padding: 10px; border-radius: 8px; background-color: #f9f9f9; vertical-align: middle;">
        <span style="font-size: 1.2em; font-weight: bold;">CryptoX</span>
      </td>
    </tr>
  </table>
</div>

CryptoX is a comprehensive mobile application designed to provide users with real-time cryptocurrency data, including live price updates, a detailed glossary of cryptocurrency terms, and the latest news in the crypto world.

## Features

- **Real-Time Price Tracking:** Get up-to-the-minute price updates for popular cryptocurrencies like Bitcoin, Ethereum, and various altcoins. CryptoX ensures that you have access to accurate and current market data.

- **Cryptocurrency Terminology Glossary:** Explore an extensive glossary that explains essential cryptocurrency terms. Whether you're a beginner or an experienced investor, CryptoX helps you understand the complex jargon and concepts of the crypto world.

- **News Aggregation:** Stay informed with the latest news from reputable sources. CryptoX aggregates relevant news, providing insights into market trends, technological advancements, regulatory changes, and significant events in blockchain and digital assets.

## Screenshots
| SignIn Screen        | Sign Up  Screen                    | Splash  Screen                  | Coinlist Screen          | Coin Detail Screen       |
|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|
| <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/1.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/2.jpg" width="180"/> |<img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/3.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/4.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/5.jpg" width="180"/> 


| Favorite Screen         | Crypto Terms Screen                      | News Screen                     | News Detail Screen          | Settings Screen       |
|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|-----------------------------------|
|<img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/6.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/7.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/8.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/9.jpg" width="180"/> | <img src="https://github.com/recepbrk/CryptoXCleanArchitecture/blob/master/app/src/main/java/com/recepguzel/cryptoxcleanarchitecture/screenshots/10.jpg" width="180"/> 

## Tech stack & Open-source libraries

- [Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
-  [Clean Architecture](https://developer.android.com/topic/architecture) - The application was written with Clean Architecture in mind.
- [Navigation Component](https://developer.android.com/guide/navigation) - Single activity multiple fragments approach
- [JetPack](https://developer.android.com/jetpack)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable lists.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Dispose of observing data when lifecycle state changes.
    - [Fragment-ktx](https://developer.android.com/kotlin/ktx#fragment) - A set of Kotlin extensions that helps with fragment lifecycle.
    - [View Binding](https://developer.android.com/topic/libraries/view-binding) - Allows you to more easily write code that interacts with views
    - [Data Binding](https://developer.android.com/topic/libraries/data-binding?hl=en) - Data Binding is the process of binding data to a layout xml file.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - UI related data holder, lifecycle aware.
    - [Swiperefreshlayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) - SwipeRefreshLayout is an Android component that allows users to refresh content by pulling down on the screen.
- [Firebase](https://firebase.google.com/)
    - [Authentication](https://firebase.google.com/docs/auth?hl=tr) 
    - [Authentication -Email](https://firebase.google.com/docs/auth/android/email-link-auth?hl=tr)
    - [Authentication -Google](https://firebase.google.com/docs/auth/android/google-signin?hl=tr)
    - [Authentication -Reset Password](https://firebase.google.com/docs/auth?hl=tr)
    - [Firebase Firestore](https://firebase.google.com/docs/firestore?hl=tr)
    - [Firebase Admob](https://firebase.google.com/docs/admob?hl=tr)
    - [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging?hl=tr)

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) is a dependency injection library for Android
- [Room](https://developer.android.com/training/data-storage/room) persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite. 
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android
-  [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) Store
  private primitive data in key-value pairs. Standard Android library with no specific version (comes with the Android SDK).

  
## Project Architecture
## MVVM (Model-View-ViewModel)

In this project, MVVM architecture is employed to separate concerns and improve maintainability. The architecture consists of:

- Model: Handles data operations and business logic, interacting with data sources such as APIs or databases.
- View: Displays the data and handles user interactions. It observes the ViewModel for updates and reflects changes in the UI.
- ViewModel: Acts as an intermediary between the Model and View. It retrieves data from the Model, processes it, and provides it to the View in a format suitable for display.
## Clean Architecture

Clean Architecture is applied to ensure a well-structured and scalable codebase. It separates the project into distinct layers:

- Presentation(UI) Layer: Contains the UI and ViewModels, responsible for presenting data to the user and handling user input.
- Domain Layer: Encapsulates business logic and use cases. It defines the application's core functionality and communicates with the data layer.
 - Data Layer: Manages data retrieval and storage. It handles API calls, database interactions, and data mapping.

   ![image](https://github.com/user-attachments/assets/eb3bf886-2376-4cb6-9234-ece71d036a68)

## API Key 🔑

- You will need to provide a private key to retrieve data from the NEWS API.
- Generate a new key from [here](https://newsapi.org/). 

``` API_KEY = YOUR_API_KEY ```

## Find this repository useful?

- Don't forget give a star. ⭐
- You can follow me on Github 💻
-   <a href="https://github.com/recepbrk"><img alt="Profile" src="https://img.shields.io/badge/github-recepbrk-blue"/></a>

  ## Licence

```
    Designed and developed by 2024 recepbrk (Recep Güzel)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
