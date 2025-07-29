package endpoints;

/* url - https://petstore.swagger.io/v2

under the above url we have different module in which "USER" is one of the module.. similarly we will see for other modules like store, pet etc..
create user (post) - https://petstore.swagger.io/v2/user
get user (get) - https://petstore.swagger.io/v2/user/{username}
update user (Put) - https://petstore.swagger.io/v2/user/{username}
delete user (delete) - https://petstore.swagger.io//v2/user/{username}
 */

public class Routes {

    // we created this variable as Static - So, that i can use it directly by giving class name and variable
    public static String base_url = "https://petstore.swagger.io/v2";

    // just concatenated with base url variable
    public static String post_url = base_url + "/user";
    public static String get_url = base_url + "/user/{username}";
    public static String put_url = base_url + "/user/{username}";
    public static String delete_url = base_url + "/user/{username}";

    // similarly we will create for other modules like pet, store etc..

}
