Feature: Application Login
  Background:
    Given User is in Login page
    When User is logging in via Microsoft with valid credentials
    Then User redirected to Homepage

 Scenario:
   Given User is in campaign registration page
   When User registers my brand and campaign
   And Brand and Campaign registered successfully
   Then User Deletes the registration

