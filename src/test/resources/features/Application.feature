Feature: the Application can be managed by user

  @ignore
  Scenario: user makes call to create application
    When the user calls post for application
    Then the user receives status code of 201 for Application
    And the user receives Application

  @ignore
  Scenario: user makes call to update application
    When system has an application
    When the user calls put for application
    Then the user receives status code of 200 for Application
    And the user receives updated Application

  Scenario: user makes call to Get application
    When system has an application
    When the user makes GET call for application
    Then the user receives status code of 200 for Application
    And the user receives Application

  @ignore
  Scenario: user makes call to Delete application
    When system has an application
    When the user makes DELETE call for application
    Then the user receives status code of 204 for Application
    And the application gets deleted