Feature: Application Login
  Background:
    Given User is in Login page
    When User is logging in via username password
    Then User redirected to Homepage

 Scenario: Verify brand and campaign registration
   Given User is in campaign registration page
   When User registers my brand and campaign
   And Brand and Campaign registered successfully
   Then User Deletes the registration

