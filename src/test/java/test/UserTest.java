package test;

import com.aventstack.extentreports.Status;
import endpoints.UserEndpoints;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.User;
import utilities.ExtentReportManager;


public class UserTest extends ExtentReportManager {

    Faker faker;
    User userpayload;
    public Logger logger;

    @BeforeClass
    public void setup(){

        faker = new Faker();
        userpayload = new User();

        userpayload.setId(faker.idNumber().hashCode());
        userpayload.setUsername(faker.name().fullName());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());
        userpayload.setEmail(faker.internet().emailAddress());
        userpayload.setPassword(faker.internet().password());
        userpayload.setPhone(faker.phoneNumber().phoneNumber());

        logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser()
    {
        logger.info("************* creating user ***********");
        ExtentReportManager.test = ExtentReportManager.extent.createTest("testPostUser");
        Response response=UserEndpoints.createUser(userpayload);
        response.then().log().all();

        test.log(Status.INFO, "POST request sent to create user");
        test.log(Status.INFO, "Response: " + response.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("User created successfully");
        logger.info("************ User is created ***********");
    }

    @Test(priority = 2)
    public void testGetUserByName()
    {
        logger.info("************ Reading User ***********");
        ExtentReportManager.test = ExtentReportManager.extent.createTest("testGetUserByName");
        Response response=UserEndpoints.readUser(this.userpayload.getUsername());
        response.then().log().all();
        test.log(Status.INFO, "GET request sent to fetch user");
        test.log(Status.INFO, "Response: " + response.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("User fetched successfully");
    }

    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        logger.info("************ Updating user ***********");
        ExtentReportManager.test = ExtentReportManager.extent.createTest("testUpdateUserByName");
        userpayload.setUsername(faker.name().fullName());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());

        Response response=UserEndpoints.updateUser(this.userpayload.getUsername(),userpayload);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200);

        //checking response after update
        Response responseAfterUpdate=UserEndpoints.updateUser(this.userpayload.getUsername(), this.userpayload);
        test.log(Status.INFO, "Update request sent to update the user");
        test.log(Status.INFO, "Response: " + response.asString());
        Assert.assertEquals(responseAfterUpdate.statusCode(), 200);
        responseAfterUpdate.then().log().all();
        test.pass("User updated successfully");
    }

    @Test(priority = 4)
    public void testDeleteUserByName()
    {
        logger.info("************ Deleting User ***********");
        ExtentReportManager.test = ExtentReportManager.extent.createTest("testDeleteUserByName");
        Response response=UserEndpoints.deleteUser(this.userpayload.getUsername());
        Assert.assertEquals(response.statusCode(), 200);
        response.then().log().all();
        test.log(Status.INFO, "Delete request sent to Delete user");
        test.log(Status.INFO, "Response: " + response.asString());
    }
}