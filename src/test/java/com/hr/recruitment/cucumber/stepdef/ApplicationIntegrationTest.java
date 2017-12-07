package com.hr.recruitment.cucumber.stepdef;

import com.hr.recruitment.cucumber.SpringIntegrationTest;
import com.hr.recruitment.domain.Application;
import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.domain.enumeration.ApplicationStatus;
import com.hr.recruitment.repository.ApplicationRepository;
import com.hr.recruitment.repository.OfferRepository;
import com.hr.recruitment.web.rest.dto.ApplicationDTO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;

import static com.hr.recruitment.cucumber.CucumberLiterals.APPLICATION_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ApplicationIntegrationTest extends SpringIntegrationTest {

    public static final String URL = APPLICATION_URL + "/offers/{offerId}/applications";
    public static final LocalDate START_DATE = LocalDate.now();
    private static Long applicationId;
    private static Long offerId;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private OfferRepository offerRepository;

    public Offer create_offer() throws Throwable {
        Offer entity = new Offer();
        entity.setJobTitle("Software Engg");
        entity.setStartDate(START_DATE);

        offerRepository.save(entity);
        offerId = entity.getId();
        return entity;
    }

    @When("^the user calls post for application$")
    public void the_user_issues_post_application() throws Throwable {
        create_offer();
        ApplicationDTO entity = new ApplicationDTO();
        entity.setApplicationStatus(ApplicationStatus.APPLIED);
        entity.setCandidateEmail("user@email.com");
        entity.setResumeText("ResumeText");
        execute(URL.replace("{offerId}", offerId.toString()), entity, ApplicationDTO.class, HttpMethod.POST);
    }

    @When("the user calls put for application$")
    public void the_user_issues_put_application() throws Throwable {
        ApplicationDTO entity = new ApplicationDTO();
        entity.setApplicationStatus(ApplicationStatus.INVITED);
        entity.setCandidateEmail("user@email.com");
        entity.setResumeText("ResumeText");
        entity.setId(applicationId);

        execute(URL.replace("{offerId}", offerId.toString()) + "/" + applicationId, entity, ApplicationDTO.class, HttpMethod.PUT);
    }

    @When("^system has an application$")
    public void create_application() throws Throwable {
        Application entity = new Application();
        entity.setApplicationStatus(ApplicationStatus.APPLIED);
        entity.setCandidateEmail("user@email.com");
        entity.setResumeText("ResumeText");
        entity.setOffer(create_offer());
        applicationRepository.save(entity);
        applicationId = entity.getId();
    }

    @Then("^the user receives status code of (\\d+) for Application$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        assertThat("status code is incorrect : " + latestStatusCode, latestStatusCode.value(), is(statusCode));
    }

    @And("^the user receives Application$")
    public void the_client_receives_application() throws Throwable {
        ApplicationDTO response = (ApplicationDTO) SpringIntegrationTest.latestResponse;
        assertThat(response.getApplicationStatus(), is(ApplicationStatus.APPLIED));
        assertThat(response.getCandidateEmail(), is("user@email.com"));
        assertThat(response.getResumeText(), is("ResumeText"));
    }

    @And("^the user receives updated Application$")
    public void the_client_receives_updated_application() throws Throwable {
        ApplicationDTO response = (ApplicationDTO) SpringIntegrationTest.latestResponse;
        assertThat(response.getApplicationStatus(), is(ApplicationStatus.INVITED));
    }

    @Then("^the application gets deleted")
    public void the_application_gets_deleted() throws Throwable {
        Application application = applicationRepository.findOne(applicationId);
        assertThat(application, is(nullValue()));
    }

    @When("^the user makes GET call for application$")
    public void the_user_makes_GET_call() throws Throwable {
        the_user_makes_HTTP_call(HttpMethod.GET);
    }

    @When("^the user makes DELETE call for application$")
    public void the_user_makes_DELETE_call() throws Throwable {
        the_user_makes_HTTP_call(HttpMethod.DELETE);
    }

    private void the_user_makes_HTTP_call(HttpMethod httpMethod) throws Throwable {
        execute(URL.replace("{offerId}", offerId.toString()) + "/" + applicationId, null, ApplicationDTO.class, httpMethod);
    }

}
