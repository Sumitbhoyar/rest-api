package com.hr.recruitment.cucumber.stepdef;

import com.hr.recruitment.cucumber.SpringIntegrationTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpMethod;

import static com.hr.recruitment.cucumber.CucumberLiterals.APPLICATION_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class VersionTest extends SpringIntegrationTest {

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable {
        execute(APPLICATION_URL + "/version", null, String.class, HttpMethod.GET);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        assertThat("status code is incorrect : " + latestStatusCode, statusCode, is(latestStatusCode.value()));
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        assertThat(latestResponse, is(version));
    }
}