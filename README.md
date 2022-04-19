# :pencil: MyNotes
![Build](https://github.com/avidraghav/MyNotes/actions/workflows/Android-CI.yml/badge.svg)
[![GitHub license](https://img.shields.io/badge/License-Apache-blue.svg)](LICENSE)
![GitHub stars](https://img.shields.io/github/stars/avidraghav/MyNotes?style=social)
![GitHub forks](https://img.shields.io/github/forks/avidraghav/MyNotes?style=social)
![Twitter Follow](https://img.shields.io/twitter/follow/avidRaghav?label=Follow&style=social)

[![Contributions Highly Welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/avidraghav/MyNotes/issues)
[![Availble issues to contribute](https://img.shields.io/github/issues/avidRaghav/MyNotes?style=social)](https://github.com/avidraghav/MyNotes/issues)




**MyNotes** is a Demo app which demonstrates the use of **MVVM architecture, Dependency Injection, Navigation Component Library, Room Database, LiveData, Coroutines, Datastore** and other **Modern Android Development**. 

# :movie_camera: Working Demo
<img src="https://user-images.githubusercontent.com/49483235/158948517-970c6250-51b6-43a1-85ac-563090295f1b.gif" width="400" height="600">

# 🗡️ Dependency Injection
The project uses [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection :syringe: 

I highly recommend to start with Manual Dependency injection method and then move to Hilt, see branch [***`di-ManualInjection`***](https://github.com/avidraghav/MyNotes/tree/di-ManualInjection) for implementation.

- To Implement DI with Hilt see  [***`di-hilt`***](https://github.com/avidraghav/MyNotes/tree/di-hilt)
# :wrench: Built With
- [Kotlin](https://kotlinlang.org/) - Programming Language Used in the app.
- [Navigation Component Library](https://developer.android.com/guide/navigation) - For setting up app's navigation.
- [Room Database](https://developer.android.com/training/data-storage/room) - For persisting data/tasks in device's local storage.
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - To execute code asynchronously.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - In the simplest of ways consider this as a pipe through which the data flows in the app, by using flow
   a developer doesn't need to refresh variables which contain data.  
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Used to observe the data on the UI layer
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - To Store UI-related data that isnt destroyed on UI changes.
- [RecylerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) - To Display data in a list format.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - For Dependency Injection
- [Datastore](https://developer.android.com/topic/libraries/architecture/datastore) - For Storing Data

 see [***`feature/Prefernces-Datastore`***](https://github.com/avidraghav/MyNotes/tree/feature/Prefernces-Datastore) to Implement Preferences Datastore by following Abstraction


# :white_check_mark: Tests
- For an example of Room Database Schema Migration see branch [***`room-migration-testing`***](https://github.com/avidraghav/MyNotes/tree/room-migration-testing)
- For Implementing Instrumented Test Cases for Room Database see branch [***`testing`***](https://github.com/avidraghav/MyNotes/tree/testing)

# 🏗️ Architecture Design


<img width="658" alt="Screenshot 2022-01-18 at 1 31 48 PM" src="https://user-images.githubusercontent.com/49483235/149895306-79dd64bb-7629-42f3-97dd-1796f4a65b40.png">

# This project is made for educational purposes so don't be shy of asking any questions about any concept from the app or about android developement. 
Do open <a href ="https://github.com/avidraghav/MVVM-TodoApp/issues" target="_blank">issues</a>
  and raise PRs to I'll try my best to assist you. If this project helps you then do leave a 🌟 :smiley:

## 📩 Contact

Don't let yourself get caught in the decision paralysis, just start learning and building. 😉

DM me at 👇

* Twitter: <a href="https://twitter.com/avidRaghav" target="_blank">@avidRaghav</a>
* LinkedIn: <a href="https://www.linkedin.com/in/avidraghav/">Raghav Aggarwal</a>

## License

```
 Copyright 2022 Raghav Aggarwal

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
