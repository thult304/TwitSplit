# This app's code is wrote whole in Kotlin.

# Architecture of the app: I use MVVM to build the app

# Main Libraries: DataBinding, RxJava, Architecture Components (LiveData, ViewModel), RecylerView,

# Main Unit test file: (note: I only write unit for the main function splitMessage)
/app/src/test/java/com/hoasung/twitsplit/SplitMessageUnitTest.kt

#Main Logic UI to post Tweeter messages in the class:
    com.hoasung.twitsplit.fragment.tweeter.PostMessageFragment

#Main Logic MVVM to post Tweeter message in the class:
    com.hoasung.twitsplit.mvvm.tweeter.TweeterPostViewModel

#Main API to post tweeter message is customized in the class
    com.hoasung.twitsplit.repository.TweeterPostRepository