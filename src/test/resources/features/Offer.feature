Feature: the Offer can be managed by user

  Scenario: user makes call to create offer
    When the user calls post for offer
    Then the user receives status code of 201
    And the user receives Offer

  Scenario: user makes call to update offer
    When system has an offer
    When the user calls put for offer
    Then the user receives status code of 200
    And the user receives updated Offer

  Scenario: user makes call to Get offer
    When system has an offer
    When the user makes call GET
    Then the user receives status code of 200
    And the user receives Offer

  Scenario: user makes call to Delete offer
    When system has an offer
    When the user makes call DELETE
    Then the user receives status code of 204
    And the offer gets deleted