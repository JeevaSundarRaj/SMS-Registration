Feature: Application Login
  Background:
    Given User is in Login page
    When User is logging in via username password
    Then User redirected to Homepage

 Scenario: Verify brand and campaign registration
   Given User is in campaign registration page
   When User deletes the existing campaign
   And User deletes the existing brand
   And User registers my brand and campaign
   Then Brand and Campaign registered successfully


# Scenario: Create campaign only
#   Given User is in campaign registration page
#   And User registers my brand and campaign
#   Then Brand and Campaign registered successfully
