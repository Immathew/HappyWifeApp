package com.example.happywifeapp.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.pressBack
import androidx.test.filters.MediumTest
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

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddNewEventFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

//    @Test
//    fun pressBackButton_popBackStack() {
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<AddNewEventFragment> {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        pressBack()
//
//        verify(navController).popBackStack()
//    }
}