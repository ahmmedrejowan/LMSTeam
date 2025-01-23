package com.rejowan.lmsteamprofile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.rejowan.lmsteamprofile.ui.modules.home.Home
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainUiTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Home::class.java)

    @Before
    fun setup() {
        System.setProperty("is_test", "true")
    }

    @Test
    fun testBottomNavigationWithViewPager2() {

        onView(withText("Matches")).check(matches(isDisplayed()))

        onView(withId(R.id.navProfile)).perform(click())
        onView(withText("Profile Fragment")).check(matches(isDisplayed()))

        onView(withId(R.id.navLms)).perform(click())
        onView(withText("My LMS Fragment")).check(matches(isDisplayed()))

        onView(withId(R.id.navShop)).perform(click())
        onView(withText("Shop Fragment")).check(matches(isDisplayed()))

        onView(withId(R.id.navMore)).perform(click())
        onView(withText("More Fragment")).check(matches(isDisplayed()))
    }
}