package StefDefs;

import common.shared.Test.BaseTest_Digital;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BuildToolFlowsStepDefinition extends BaseTest_Digital
{
    @Given("User visits mazda home page")
    public void user_visits_mazda_home_page()
    {
        System.out.println("Steps1");
    }

    @Then("Validate All Broken links")
    public void validate_all_broken_links()
    {
        System.out.println("Steps2");
    }
}
