package api.endpoints;
/* Swagger URL - https://petstore.swagger.io
Create User(Post): https://petstore.swagger.io/v2/user
Get User(Get): https://petstore.swagger.io/v2/user/{username}
Update User(put): https://petstore.swagger.io/v2/user/{username}
Delete User(delete)): https://petstore.swagger.io/v2/user/{username}
Routes contains only URLs of the modules in the project
 */

public class Routes {

    public static String base_url = "https://petstore.swagger.io/v2";

    //post Url
    public static String post_url=base_url+"/user";
    public static String get_url=base_url+"/user/{username}"; //Get Url
    public static String update_url=base_url+"/user/{username}"; //Update Url
    public static String delete_url=base_url+"/user/{username}"; //Delete url
}
