package com.thananonp.composeexperiment.debug

import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.thananonp.composeexperiment.OnboardingView
import org.junit.Rule
import org.junit.Test

class SnapshotTests : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_1() {
        composeTestRule.setContent {
            OnboardingView {}
        }
        compareScreenshot(composeTestRule, name = "TEST")
    }
}