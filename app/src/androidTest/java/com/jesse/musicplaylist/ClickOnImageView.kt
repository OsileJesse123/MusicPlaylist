package com.jesse.musicplaylist

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import org.hamcrest.Matcher

class ClickOnImageView(val viewId: Int): ViewAction {

    private val click: ViewAction = click()

    override fun getConstraints(): Matcher<View> {
        return click.constraints
    }

    override fun getDescription(): String {
        return "click on custom image view"
    }

    override fun perform(uiController: UiController?, view: View?) {
        click.perform(uiController, view?.findViewById(viewId))
    }
}