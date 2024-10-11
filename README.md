Android fitness app
[Project proposal.pdf](https://github.com/user-attachments/files/17342831/Project.proposal.pdf)


# Health Tracker App

## Introduction
The Health Tracker App is designed to help users monitor their health and promote a healthier lifestyle. The app allows users to track their calorie intake and expenditure while providing insights to manage their fitness goals effectively.

### User Group
The app is suitable for users of all ages, including fitness enthusiasts, health-conscious individuals, those participating in fitness challenges, and individuals with specific health conditions.

### Scope and Limitations
The app's scope includes the following functionalities:
- **User Profile Management**: Users can create and customize accounts with personal metrics and goals.
- **Fitness and Activity Tracking**: Users can log their diet, workouts, and calorie consumption. The app does not directly read data from wearable devices.
- **Nutrition Tracking**: Users can enter their food intake to track calories, though the app does not automatically detect or scan meals.
- **Insights and Analytics**: Users receive insights and analytics based on their activity and nutrition, including predictive analytics to recalculate goals.
- **Recommendations**: Users receive workout and nutrition suggestions based on their age, gender, and weight.
- **Social Sharing**: Users can share their progress with others but cannot view personal data from other users.

### Limitations
- Tracking accuracy depends on manual input.
- Data privacy concerns if not managed properly.
- Advanced features may require complex technical implementation.
- Lack of professional feedback from nutritionists or coaches.

## Proposed Functionalities and Screens
### Key Functionalities
- **User Profile Management**: Edit personal information and input metrics.
- **Food Tracker**: Log daily food intake to calculate calorie consumption.
- **Exercise Tracker**: Log daily exercises to track calories burned.
- **Goals and Data Management**: View trends and estimated weight through graphs and reports.

### Key Screens
| Key Screens                | Key Components              |
|---------------------------|-----------------------------|
| Welcome Screen            | Navigation Bottom Bar       |
| Login Screen              | -                           |
| Register Screen           | Date Picker                 |
| Profile Screen            | Expanded Dropdown Menu      |
| Home Screen               | -                           |
| Date Selection Screen      | Date Picker                 |
| Calorie Input Screen      | Room, LazyColumn            |
| View Activity Screen       | Graphs, Reports             |
| Goal Screen               | Graphs, Reports             |
| Food Recommendation Screen | LazyColumn, Retrofit        |
| Workout Recommendation Screen| LazyColumn, Retrofit      |
| Competition Screen        | LazyColumn, Retrofit        |

## System Architecture
The app consists of a server-side and client-side architecture. The UI layer features a navigation bottom bar for easy access to different screens. 

- **UI Layer**: Comprises various UI elements like LazyColumn, DatePicker, and Dropdown Menu, with ViewModels handling data from the data layer.
- **Data Layer**: Manages application data and business logic using repositories. A local database is implemented using Room for SQLite access, while Retrofit connects to a remote Firebase Database for storing user credentials, weight history, food items, and exercises.

## UI Design and Prototypes
### Key Screens
- **Welcome Screen**: Initial screen for login and registration.
- **Login Screen**: For returning users.
- **Register Screen**: To create new user accounts.
- **Profile Creation Screen**: For setting up a user profile.
- **Home Screen**: Central hub for app navigation and controls.
- **View Activity Screen**: Summary of user activity.
- **Workout Recommendations Screen**: Suggested workouts based on user data.
- **Competition Screen**: Score comparison with friends.
- **Calorie Input Screen**: For entering daily food and exercise data.

## Advanced Features
### Google Authentication
Implementing Google Authentication allows for:
- Convenient user login.
- Enhanced security with multifactor authentication.
- Access to user profile information.

For implementation details, follow the [Google Android Developers guide](https://developer.android.com/training/sign-in/passkeys).

## References
- [Google Safety Centre: Secure Sign-In & Authentication Tools](https://safety.google/authentication/)
- [Google Android Developers: Sign in Your User with Credential Manager](https://developer.android.com/training/sign-in/passkeys)
- [Android Developers: UI Layer](https://developer.android.com/topic/architecture/ui-layer)

## Photo References
- Activity Interface: ![Activity Interface](https://img.freepik.com/free-vector/workout-tracker-app-interface_52683-46943.jpg)
- Google Sign-In UI: ![Google Sign-In UI](https://storage.googleapis.com/support-kms-prod/vTnPzkyVEiL4gKfJF3WyMdzDqyAKoPFF2EZQ)

