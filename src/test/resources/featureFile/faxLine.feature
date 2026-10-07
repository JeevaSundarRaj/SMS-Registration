Feature: Fax Send and Receive Validation
  Background:
    Given User is in Login page
    When User is logging in via username password
    Then User redirected to Homepage

  Scenario: Verify fax from User to User is visible to receiver
    Given User is using the Dialpad app
    When User sends a fax from "User" to "User"
    Then Fax should be received successfully
    And Receiver should see the fax as "Just now" and download opens in a new tab

  Scenario Outline: Verify fax is visible and downloadable on receiving CC or department
    Given User is using the Dialpad app
    When User sends a fax from "<Sender>" to "<Receiver>"
    Then Fax should be received successfully
    And "<Receiver>" should see the fax as "Just now" and download opens in a new tab

    Examples:
      | Sender | Receiver |
      | User   | CC       |
      | Dept   | CC       |
      | User   | Dept     |
      | CC     | Dept     |
      | CC     | CC2      |
      | Dept   | Dept2    |

  Scenario: Verify concurrent fax limit popup when sending multiple faxes rapidly
    Given User is using the Dialpad app
    When User sends multiple faxes continuously from "User" to "User"
    Then Concurrent fax limit popup should be displayed

  Scenario: Verify large volume fax is sent and delivered successfully
    Given User is using the Dialpad app
    When User sends a large volume fax from "User" to "User"
    Then Large fax should be received successfully
