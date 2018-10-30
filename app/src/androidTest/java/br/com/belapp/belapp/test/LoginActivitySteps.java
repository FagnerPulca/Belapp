package br.com.belapp.belapp.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.LoginActivity;
import br.com.belapp.belapp.R;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class LoginActivitySteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Activity activity;

    @Before("@login-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@login-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on login screen")
    public void I_am_on_login_screen() {
        assertNotNull(activity);
    }

    @When("^I input an email, test@test.com")
    public void I_input_an_email() {
        onView(withId(R.id.email)).perform(typeText("test@test.com"));
    }

    @And("^I input a password, correctPassword")
    public void I_input_password() {
        onView(withId(R.id.password)).perform(typeText("correctPassword"), closeSoftKeyboard());
    }
    @And("^I press submit button$")
    public void I_press_submit_button() {
        onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @Then("^I should not see auth error")
    public void I_should_not_see_auth_error() {
        onView(withText("sucesso")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
