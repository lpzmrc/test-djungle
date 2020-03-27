A small repo containing the test code
# Login
Login displays a login view. It can be tested with the following credentials, provided as `Username:Password`
- `mario.rossi:12345678` -> causes successful login to be returned and provide access to the other views
- `exception:*` -> causes exception to be thrown by http client
- `*,*` -> causes api error to be return and managed consequently

Username can be a text string or a valid email address, meanwhile Password must be at least 6 characters long

# Gallery
Gallery displays an image ist in a grid view: to force data refresh swipe down and release.

# ToDos
ToDos displays a todo list in a list view: to force data refresh swipe down and release.

# Setting
Settings displays logged user related info and an button to log out from the application.

# Known Issues
1. The code uses the [Fragment](https://developer.android.com/reference/androidx/fragment/app/Fragment), [Navigation Component](https://developer.android.com/guide/navigation/), [BottomNavigationView](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView) component to navigate between __Gallery__, __ToDos__, __Settings__ views: it removes the active __Fragment__ from the backstack, when replacing it with the next one. As a conseguence every time the and user tap on the bottm bar the fragment is reloaded.
