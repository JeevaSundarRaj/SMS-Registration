Feature: Mainline Auto-Reply Validation

  Background:
    Given User is in Login page
    When User is logging in via username password
    Then User redirected to Homepage

  Scenario Outline: Verify auto-reply off then on for "<MainlineType>"
    Given User opens settings and app windows for "<MainlineType>"
    When User disables auto-reply for "<MainlineType>"
    And User sends a test message to "<MainlineType>"
    Then No auto-reply should be received
    When User enables auto-reply for "<MainlineType>"
    And User sends a test message to "<MainlineType>"
    Then Auto-reply message should be received

    Examples:
      | MainlineType |
      | Mainline     |
#      | CallCenter   |
#      | Department   |
