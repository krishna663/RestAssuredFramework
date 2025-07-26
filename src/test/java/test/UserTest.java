package test;

import endpoints.UserEndpoints;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.User;

public class UserTest {

    Faker faker;
    User userpayload;

    @BeforeClass
    public void setupData(){

        faker = new Faker();
        userpayload = new User();

        userpayload.setId(faker.idNumber().hashCode());
        userpayload.setUsername(faker.name().fullName());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());
        userpayload.setEmail(faker.internet().emailAddress());
        userpayload.setPassword(faker.internet().password());
        userpayload.setPhone(faker.phoneNumber().phoneNumber());
    }

    @Test(priority = 1)
    public void testPostUser()
    {
        Response response=UserEndpoints.createUser(userpayload);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    public void testGetUserByName()
    {
        Response response=UserEndpoints.readUser(this.userpayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        userpayload.setUsername(faker.name().fullName());
        userpayload.setFirstName(faker.name().firstName());
        userpayload.setLastName(faker.name().lastName());

        Response response=UserEndpoints.updateUser(this.userpayload.getUsername(),userpayload);
        response.then().log().all();

        Assert.assertEquals(response.statusCode(), 200);

        //checking response after update
        Response responseAfterUpdate=UserEndpoints.readUser(this.userpayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.statusCode(), 200);
        responseAfterUpdate.then().log().all();
    }

    @Test(priority = 4)
    public void testDeleteUserByName()
    {
        Response response=UserEndpoints.deleteUser(this.userpayload.getUsername());
        Assert.assertEquals(response.statusCode(), 200);
        response.then().log().all();
    }
}