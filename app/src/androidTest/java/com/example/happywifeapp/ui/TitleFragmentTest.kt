package com.example.happywifeapp.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.happywifeapp.R
import com.example.happywifeapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class TitleFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddNewEvent_navigateToAddNewEventFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<TitleFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ll_add_new_event)).perform(click())

        verify(navController).navigate(
            TitleFragmentDirections.actionTitleFragmentToAddNewEventFragment()
        )
    }
}