package test;

import endpoints.UserEndpoints;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.User;
import utilities.DataProviders;

public class DataDrivenTest {

    User payload = new User();
    Response response;
    JSONObject object;
    Faker faker = new Faker();

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userid, String userName, String firstName, String lastName, String email, String pwd, String ph)
    {
        this.payload.setId(Integer.parseInt(userid));
        this.payload.setUsername(userName);
        this.payload.setFirstName(firstName);
        this.payload.setLastName(lastName);
        this.payload.setEmail(email);
        this.payload.setPassword(pwd);
        this.payload.setPhone(ph);

        response=UserEndpoints.createUser(this.payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testUserDelete(String uname)
    {
        response=UserEndpoints.deleteUser(uname);
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().log().all();
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testGetUser(String uname) throws InterruptedException {
        Thread.sleep(2000);
        response = UserEndpoints.readUser(uname);
        object = new JSONObject(response.asString());

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(object.getString("username"),uname);
        response.then().log().all();
    }

    @Test(priority = 3, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testUpdateUser(String userid, String userName, String firstName, String lastName, String email, String pwd, String ph)
    {
        // Generate new username using Faker
        String newUsername = faker.name().username();

        // Build payload with updated username
        this.payload.setId(Integer.parseInt(userid));
        this.payload.setUsername(newUsername); // ✅ this is the new username
        this.payload.setFirstName(firstName);
        this.payload.setLastName(lastName);
        this.payload.setEmail(email);
        this.payload.setPassword(pwd);
        this.payload.setPhone(ph);

        // Send update using the original username (userName)
        response = UserEndpoints.updateUser(userName, this.payload);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        // ✅ Verify updated username in GET call
        response = UserEndpoints.readUser(newUsername);
        object = new JSONObject(response.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(object.getString("username"), newUsername);
    }

}
