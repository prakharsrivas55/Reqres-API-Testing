package api_test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api_payloads.User;
import api_utilities.ApiValidations;
import api_endpoints.Routes;
import api_endpoints.UserEndPoints;
import io.restassured.response.Response;  
import java.util.Map; 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;

// import org.apache.log4j.Logger;
// import io.qameta.allure.Step;
// import io.qameta.allure.testng.AllureTestNGListener;
// import org.testng.annotations.Listeners;
// @Listeners(AllureTestNGListener.class)

public class UserTests {

    // public static Logger log = Logger.getLogger(UserTests.class);
    private static final Logger log = LogManager.getLogger(UserTests.class);
    ApiValidations mailers= new ApiValidations();

    Faker faker = new Faker();
    User userPayload = new User();

    @BeforeClass
    public void setupData() {

        log.info("************************* REQRES API TEST STARTED ************************");

        // Generate random user data using Faker
        userPayload.setName(faker.name().fullName());
        userPayload.setJob(faker.job().title());
        
        log.info("Generated User Name: " + userPayload.getName());
        log.info("Generated User Job: " + userPayload.getJob());
    }

    @Test
    @Feature("User API")
    @Story("Create a user")
    public void Test_Post_A_User() {

        log.info("************************** TestCase 1 Started (CREATE USER) **************************");

        String apiName= "Create user API";
        // Call API to create a user
        Response response = UserEndPoints.createUser(userPayload);
        String responseBody = response.getBody().asString();

        // Log user details from the response
        log.info("User Details: " + responseBody);
        log.info("Created User Name: " + response.jsonPath().getString("name"));
        log.info("Created User Job: " + response.jsonPath().getString("job"));
        log.info("Created User ID: " + response.jsonPath().getString("id"));
        log.info("User Created At: " + response.jsonPath().getString("createdAt"));

        // Assert that user creation was successful
        Assert.assertEquals(response.getStatusCode(), 201);
        if (response.getStatusCode() == 201) {
            mailers.sendSuccessMail(apiName, Routes.post_url, response.getStatusCode());
            
        }
        else{
            String errorMessage = "Unexpected status code received: " + response.getStatusCode();
            log.error("API call failed: " + errorMessage);
            mailers.sendFailedMail(apiName, Routes.post_url, response.getStatusCode(), errorMessage);
        }

        log.info("************************* TestCase 1 Completed (CREATE USER) *************************");
    }

    @Test(priority = 2)
    @Feature("User API")
    @Story("Get a user by ID")
    public void Test_Get_All_Users () {


        log.info("************************** TestCase 2 Started (GET USER) **************************");

        String apiName= "Get Users details ";
        int page= 2;
        int statusCode;
        String errorMessage = "";

        // Call API to get user by ID
        Response response = UserEndPoints.readUser(page);
        response.then().log().all();
        statusCode=response.getStatusCode();

        if (statusCode == 200)
        {
            // Assert that the response status is OK
            Assert.assertEquals(response.getStatusCode(), 200); 
            mailers.sendSuccessMail(apiName, Routes.get_url, statusCode); 
        }
        else
        {
            errorMessage = "API returned an unexpected status code: " + statusCode;
            log.error("API call failed: " + errorMessage);
            mailers.sendFailedMail(apiName, Routes.put_url(page), statusCode, errorMessage);
        }
        
        log.info("************************* TestCase 2 Completed (GET USER) *************************");
    }

    @Test(priority = 3)
    @Feature("User API")
    @Story("Update user details")
    public void Test_Update_A_UserDetails() {

        log.info("************************** TestCase 3 Started (UPDATE USER) **************************");

        String apiName= "Update User ";
        int userId= 7;
        int statusCode;
        String errorMessage = "";

        // Fetch details of user with ID 7
        Response response = UserEndPoints.readUser(2);
        response.then().log().all();

        // Check if ID 7 exists in the list of users
        Map<String, Object> userDetails = response.jsonPath().getMap("data.find { it.id ==" + userId + " }");
        if (userDetails != null) 
        {
            log.info("User ID: " + userDetails.get("id"));
            log.info("First Name: " + userDetails.get("first_name"));
            log.info("Last Name: " + userDetails.get("last_name"));
            log.info("Email: " + userDetails.get("email"));

            // Update the user with ID 7
            response = UserEndPoints.updateUser(7, userPayload);
            response.then().log().all();
            statusCode= response.getStatusCode();

            if(statusCode == 200)
            {
                // Assert the update
                Assert.assertEquals(response.getStatusCode(), 200);
                Assert.assertEquals(response.jsonPath().getString("name"), userPayload.getName(), "User name should match");
                Assert.assertEquals(response.jsonPath().getString("job"), userPayload.getJob(), "User job should match");

                mailers.sendSuccessMail(apiName, Routes.put_url(userId), statusCode);
            }
            
            else 
            {
                errorMessage = "API returned an unexpected status code: " + statusCode;
                log.error("API call failed: " + errorMessage);
                mailers.sendFailedMail(apiName, Routes.put_url(userId), statusCode, errorMessage);
            }
        }

        else{
            log.warn("User with ID " + userId + "not found.");
            errorMessage = "User with ID " + userId + " not found in the database.";
            mailers.sendFailedMail(apiName, Routes.put_url(userId),  response.getStatusCode(), errorMessage);
        }

        log.info("************************* TestCase 3 Completed (UPDATE USER) *************************");
    }

    @Test(priority = 4)
    @Feature("User API")
    @Story("Delete a user")
    public void Test_Deleting_A_UserDetails() {

        log.info("************************** TestCase 4 Started (DELETE USER) **************************");

        String apiName= "Deleted User ";
        int userId= 2;
        int statusCode;
        String errorMessage = "";

        // Call API to delete user with ID 2
        Response response = UserEndPoints.deleteUser(userId);
        response.then().log().all();
        statusCode= response.getStatusCode();
        // Assert that user deletion was successful (204 No Content)
        if(statusCode== 204)
        {
            Assert.assertEquals(statusCode, 204);
            mailers.sendSuccessMail(apiName, Routes.delete_url(userId), statusCode);
        }
        else
        {
            errorMessage = "API call failed. Status Code: " + statusCode;
            log.error("User deletion failed with status code " + statusCode + ". Error message: " + errorMessage);
            mailers.sendFailedMail(apiName, Routes.delete_url(userId), statusCode, errorMessage);
        }

        log.info("************************* TestCase 4 Completed (DELETE USER) *************************");
    }


}

