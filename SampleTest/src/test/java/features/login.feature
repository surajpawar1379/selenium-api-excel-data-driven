Feature: Login on the Practice Test Automation site

  Scenario: Successful login with valid credentials
    Given the user is on the practice test login page
    When the user logs in with username "student" and password "Password123"
    Then the user should see the success message "Logged In Successfully"

  Scenario Outline: Unsuccessful login with invalid credentials
    Given the user is on the practice test login page
    When the user logs in with username "<username>" and password "<password>"
    Then the user should see the error message "<error>"

    Examples:
      | username       | password       | error                       |
      | incorrectUser  | Password123    | Your username is invalid!   |
      | student        | incorrectPass  | Your password is invalid!   |
