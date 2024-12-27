// package api.test;

// import org.testng.Assert;
// import org.testng.annotations.BeforeClass;
// import org.testng.annotations.Test;
// import org.apache.log4j.Logger;

// import com.github.javafaker.Faker;
// import api.payloads.User;
// import api.endpoints.UserEndPoints;
// import io.restassured.response.Response;  
// import java.util.Map; 

// import io.qameta.allure.Feature;
// import io.qameta.allure.Story;
// import io.qameta.allure.Step;

// public class UserTests {

//     public static Logger log= Logger.getLogger(UserTests.class);

//     Faker faker= new Faker();
//     User userPayload= new User();

//     @BeforeClass
//     public void setupData(){

//         log.info("************************* REQRES API TEST STARTED ************************");

//         // faker= new Faker();
//         // userPayload= new User();

//         userPayload.setName(faker.name().fullName());
//         userPayload.setJob(faker.job().title());
        
//         log.info("Generated User Name: " + userPayload.getName());
//         log.info("Generated User Job: " + userPayload.getJob());

//     }

//     @Test
//     @Feature("User API")
//     @Story("Get user by ID")
//     // @Test(priority = 1)
//     // @Test(enabled = false)
//     public void testpostuser(){

//         log.info("************************** TestCase 1 Started (CREATE USER) **************************");

//         Response response= UserEndPoints.createUser(userPayload);
//         String responseBody= response.getBody().asString();

//         log.info("User Details :"+ responseBody);
        
//         log.info("User Name "+ response.jsonPath().getString("name"));
//         log.info("User Name "+ response.jsonPath().getString("job"));
//         log.info("User Name "+ response.jsonPath().getString("id"));
//         log.info("User Name "+ response.jsonPath().getString("createdAt"));

//         Assert.assertEquals(response.getStatusCode(),201);

//         log.info("************************* TestCase 1 Completed (CREATE USER) *************************");

//     }

//     // @Test(enabled = false)
//     @Test(priority = 2)
//     public void testreadUser(){

//         log.info("************************** TestCase 2 Started (GET USER) **************************");

//         Response response= UserEndPoints.readUser(2);
//         response.then().log().all();

//         Assert.assertEquals(response.getStatusCode(),200);

//         log.info("************************* TestCase 2 Completed (GET USER) *************************");

//     }

//     // @Test(enabled = false)
//     @Test(priority = 3)
//     public void testupdateuser(){

//         log.info("************************** TestCase 3 Started (UPDATE USER) **************************");


//         // userPayload.setName(faker.name().fullName());
//         // userPayload.setJob(faker.job().title());

//         Response response= UserEndPoints.readUser(2);
//         response.then();
//         // int userId = response.jsonPath().getInt("data.find { it.id == 7 }.id");


//         Assert.assertEquals(response.getStatusCode(),200);
//         Map<String, Object> userDetails = response.jsonPath().getMap("data.find { it.id == 7 }");

//         // log.info("userId = " + response.jsonPath().getInt("data.find { it.id == 7 }.id"));
//         log.info("User ID: " + userDetails.get("id"));
//         log.info("First Name: " + userDetails.get("first_name"));
//         log.info("Last Name: " + userDetails.get("last_name"));
//         log.info("Email: " + userDetails.get("email"));


//         response= UserEndPoints.updateUser(7,userPayload);
//         response.then().log().all();

//         Assert.assertEquals(response.getStatusCode(),200);
//         Assert.assertEquals(response.jsonPath().getString("name"), userPayload.getName(), "User name should match");  
//         Assert.assertEquals(response.jsonPath().getString("job"), userPayload.getJob(), "User job should match");  

//         log.info("************************* TestCase 3 Completed (UPDATE USER) *************************");

//     }

//     // @Test(enabled = false)
//     @Test(priority = 4)
//     public void deletinguser(){

//         log.info("************************** TestCase 4 Started (DELETE USER) **************************");

//         Response response= UserEndPoints.deleteUser(2);
//         response.then().log().all();
//         Assert.assertEquals(response.getStatusCode(),204);

//         log.info("************************* TestCase 4 Completed (DELETE USER) *************************");

//     }
// }


package api_test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
// import org.apache.log4j.Logger;

import com.github.javafaker.Faker;
import api_payloads.User;
import api_endpoints.UserEndPoints;
import io.restassured.response.Response;  
import java.util.Map; 

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
// import io.qameta.allure.testng.AllureTestNGListener;
import org.testng.annotations.Listeners;

// @Listeners(AllureTestNGListener.class)
public class UserTests {

    // public static Logger log = Logger.getLogger(UserTests.class);
    private static final Logger log = LogManager.getLogger(UserTests.class);

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

        log.info("************************* TestCase 1 Completed (CREATE USER) *************************");
    }

    @Test(priority = 2)
    @Feature("User API")
    @Story("Get a user by ID")
    public void Test_Get_All_Users () {

        log.info("************************** TestCase 2 Started (GET USER) **************************");

        // Call API to get user by ID
        Response response = UserEndPoints.readUser(2);
        response.then().log().all();

        // Assert that the response status is OK
        Assert.assertEquals(response.getStatusCode(), 200);

        log.info("************************* TestCase 2 Completed (GET USER) *************************");
    }

    @Test(priority = 3)
    @Feature("User API")
    @Story("Update user details")
    public void Test_Update_A_UserDetails() {

        log.info("************************** TestCase 3 Started (UPDATE USER) **************************");

        // Fetch details of user with ID 7
        Response response = UserEndPoints.readUser(2);
        response.then().log().all();

        // Check if ID 7 exists in the list of users
        Map<String, Object> userDetails = response.jsonPath().getMap("data.find { it.id == 7 }");
        if (userDetails != null) {
            log.info("User ID: " + userDetails.get("id"));
            log.info("First Name: " + userDetails.get("first_name"));
            log.info("Last Name: " + userDetails.get("last_name"));
            log.info("Email: " + userDetails.get("email"));

            // Update the user with ID 7
            response = UserEndPoints.updateUser(7, userPayload);
            response.then().log().all();

            // Assert the update
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.jsonPath().getString("name"), userPayload.getName(), "User name should match");
            Assert.assertEquals(response.jsonPath().getString("job"), userPayload.getJob(), "User job should match");
        } else {
            log.warn("User with ID 7 not found.");
        }

        log.info("************************* TestCase 3 Completed (UPDATE USER) *************************");
    }

    @Test(priority = 4)
    @Feature("User API")
    @Story("Delete a user")
    public void Test_Deleting_A_UserDetails() {

        log.info("************************** TestCase 4 Started (DELETE USER) **************************");

        // Call API to delete user with ID 2
        Response response = UserEndPoints.deleteUser(2);
        response.then().log().all();

        // Assert that user deletion was successful (204 No Content)
        Assert.assertEquals(response.getStatusCode(), 204);

        log.info("************************* TestCase 4 Completed (DELETE USER) *************************");
    }


}

