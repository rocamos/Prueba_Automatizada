Feature: Recruitment Process
  This feature allows adding and validating a new candidate in the recruitment process.

  Scenario: Add a new candidate and validate
    Given I navigate to the login page
    When I enter valid credentials
    And I navigate to the recruitment page
    When I add a new candidate
    Then I should validate the candidate is hired
    And I close the browser




