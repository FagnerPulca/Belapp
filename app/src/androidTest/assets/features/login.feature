Feature: Login

  @login-feature
  Scenario: Login
    Given I am on login screen
    When I input an email, test@test.com
    And I input a password, correctPassword
    And I press submit button
    Then I should not see auth error