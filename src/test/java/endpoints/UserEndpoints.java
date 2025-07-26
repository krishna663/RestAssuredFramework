package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.User;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    //create to perform CRUD operations - create, retrieve, update, delete  ,,,, validations (then()) we will do in TC

    public static Response createUser(User payload)
    {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)

                .when()
                .post(Routes.post_url);

        return  response;
    }

    public static Response readUser(String userName)
    {
        Response response = given()
                .pathParam("username",userName)

                .when()
                .get(Routes.get_url);

        return  response;
    }

    public static Response updateUser(String userName, User payload)
    {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .pathParam("username",userName)

                .when()
                .put(Routes.put_url);

        return  response;
    }

    public static Response deleteUser(String userName)
    {

        Response response = given()
                .pathParam("username",userName)

                .when()
                .delete(Routes.delete_url);
        return  response;
    }
}
