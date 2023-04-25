# LifeStyleApp
Required Functionality (Modules):
1. I want the app to take in my name, age, location (city and country), height, weight, sex, and “activity level”, and store a full profile picture for me. I’d like the app to show a little thumbnail version of that same profile picture of me at the top right.
2. I want the app to have a module that estimates my basal metabolic rate (BMR), the number of calories I need to survive. This app should then tell me my daily target calorie intake.
3. I want to be able to use the app to find nice hikes near me with the tap of a button and show them to me on Google Maps.
4. I want to be able to see today’s weather for my current city at the tap of a button.
5. I want to be able to see how my caloric intake changes as I change my activity level; this means showing my caloric intake somewhere visibly on the app at all times. I also want to be able to change my activity level easily at all times.
Design Features:
1. One reason I’m coming to you is that I’ve heard you’re good at designing stuff for both phones and tablets. I want the same app to look good and function well on both a phone and a tablet.
2. I want to be able to see all the module names on-screen at the same time. When I tap one, I want to be able to access that particular module. On a tablet, I want the list of modules to always be visible on the left, even when I’m currently using a module. On a phone, there’s not enough room, so I’d like to be able to only see the module I’m currently on.
3. I’d like to have nice looking buttons for each module. I hate tapping on those grey buttons or on text.
4. I want to be able to go in and change my user data at any time.
5. I may add other modules later, so I want you to program things to be efficient right from the outset.
6. Just a warning: I may want to add other users to my app in the future. You don’t have to add any login/authentication functionality right now, but I think it’s important for you to know that more than one person may eventually use this app. Of course, as a non-technical person, I’m not sure what that means in terms of how you code/design things.
7. I want the app as a whole to look appealing in terms of colors and text. I’d like it if you could follow the Material Design guidelines for button sizes, text sizes, and text colors.
8. This is pretty obvious, but I want the app to work even if I rotate the device, hit the back button inside the app, switch out of the app and back to it.
9. I want the app to be well-tested! I don’t want it to crash or be too annoying!
10. When I enter my data, I don’t want to be forced to type anything other than my name.
11. I want to be able to view a summary of my entered data at any time.


You will re-organize your code to use the Repository design pattern. In other words, your user data and any internet data must be fed into a repository class. The repository class must in turn be managed by a view model class extending ViewModel or AndroidViewModel, which creates, writes to, and reads from the repository. You are welcome to experiment with a design that includes multiple ViewModels and repositories to enforce separation of concerns.
2. You have, in general, 2 sources of data for your projects. The first is user data, and the second is data from the internet. Modify your repository (or repositories) to do the job of fetching data from the internet using whatever utility classes you have; the repository (or whatever class feeds the data to the repository) must use coroutines in Kotlin to achieve this. Remember, the ViewModel holds all repository instances, giving automatic protection against configuration changes, but onSaveInstanceState will still be required to handle the application stopping. Internet data fetches must now be done with coroutines. Using Java threads will be penalized!
3. You will reorganize your code so that all Activities and Fragments fetch data from the ViewModel, and not from each other. This means no more passing Parcelable data from Fragments to Activities and Activities to Fragments. All bulky data lives in the ViewModel, and all data is fetched from the ViewModel. See here for a simple example: https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
4. Add a Room database to backup user data from the repository. Decide if Room is needed for the internet access part of your app; if you are fetching some form of continually refreshed data, implement a Room database for this also. If you already have an SQLite database implemented, update it to use Room, as this is now the idiomatic way of doing things on the Android platform.
