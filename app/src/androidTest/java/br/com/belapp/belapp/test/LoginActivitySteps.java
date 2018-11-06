package br.com.belapp.belapp.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

import br.com.belapp.belapp.R;
import br.com.belapp.belapp.activities.LoginActivity;
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
    public void iamOnLoginScreen() {
        assertNotNull(activity);
    }

    @When("^I input an email, test@test.com")
    public void iInputAnEmail() {
        onView(withId(R.id.edtEmail)).perform(typeText("test@test.com"));
    }

    @And("^I input a password, correctPassword")
    public void iInputPassword() {
        onView(withId(R.id.edtSenha)).perform(typeText("correctPassword"), closeSoftKeyboard());
    }
    @And("^I press submit button$")
    public void iPressSubmitButton() {
        onView(withId(R.id.btnLogar)).perform(click());
    }

    @Then("^I should not see auth error")
    public void iShouldNotSeeAuthError() {
        onView(withText("sucesso")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
