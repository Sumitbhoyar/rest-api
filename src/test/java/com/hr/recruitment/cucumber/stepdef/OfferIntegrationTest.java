package com.hr.recruitment.cucumber.stepdef;

import com.hr.recruitment.cucumber.SpringIntegrationTest;
import com.hr.recruitment.domain.Offer;
import com.hr.recruitment.repository.OfferRepository;
import com.hr.recruitment.web.rest.dto.OfferDTO;
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

public class OfferIntegrationTest extends SpringIntegrationTest {

    public static final String URL = APPLICATION_URL + "/offers";
    public static final LocalDate START_DATE = LocalDate.now();
    private static Long offerId;
    @Autowired
    private OfferRepository offerRepository;

    @When("^the user calls post for offer$")
    public void the_user_issues_POST_offer() throws Throwable {
        OfferDTO entity = new OfferDTO();
        entity.setJobTitle("Software Engg");
        entity.setStartDate(START_DATE);
        execute(URL, entity, OfferDTO.class, HttpMethod.POST);
    }

    @When("the user calls put for offer$")
    public void the_user_issues_put_offer() throws Throwable {
        OfferDTO entity = new OfferDTO();
        entity.setJobTitle("New Title");
        entity.setStartDate(START_DATE);
        entity.setId(offerId);

        execute(URL + "/" + offerId, entity, OfferDTO.class, HttpMethod.PUT);
    }

    @When("^system has an offer$")
    public void create_offer() throws Throwable {
        Offer entity = new Offer();
        entity.setJobTitle("Software Engg");
        entity.setStartDate(START_DATE);

        offerRepository.save(entity);
        offerId = entity.getId();
    }

    @Then("^the user receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        assertThat("status code is incorrect : " + latestStatusCode, latestStatusCode.value(), is(statusCode));
    }

    @And("^the user receives Offer$")
    public void the_client_receives_offer() throws Throwable {
        OfferDTO response = (OfferDTO) SpringIntegrationTest.latestResponse;
        assertThat(response.getJobTitle(), is("Software Engg"));
        assertThat(response.getStartDate(), is(START_DATE));
    }

    @And("^the user receives updated Offer$")
    public void the_client_receives_updated_offer() throws Throwable {
        OfferDTO response = (OfferDTO) SpringIntegrationTest.latestResponse;
        assertThat(response.getJobTitle(), is("New Title"));
        assertThat(response.getStartDate(), is(START_DATE));
    }

    @Then("^the offer gets deleted")
    public void the_offer_gets_deleted() throws Throwable {
        Offer offer = offerRepository.findOne(offerId);
        assertThat(offer, is(nullValue()));
    }

    @When("^the user makes call (.+)$")
    public void the_user_makes_call_HTTP_offers_id(String httpMethod) throws Throwable {
        execute(URL + "/" + offerId, null, OfferDTO.class, HttpMethod.valueOf(httpMethod));
    }

}
