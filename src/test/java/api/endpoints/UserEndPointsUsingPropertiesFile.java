package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

//Method created for getting URL's from Properties file
public class UserEndPointsUsingPropertiesFile {

    static ResourceBundle getURL(){
        ResourceBundle routes = ResourceBundle.getBundle("routes"); //Loaf Properties File
        return routes;
    }

    public static Response createUser(User payload){

        String post_url = getURL().getString("post_url");
        Response res = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(post_url);
        return res;
    }

    public static Response readUser(String userName){
        String get_url = getURL().getString("get_url");
        Response res = given()
                .pathParam("username",userName)

                .when()
                .post(get_url);
        return res;
    }
    public static Response updateUser(User payload, String userName){

        String update_url = getURL().getString("update_url");
        Response res = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .pathParam("username", userName)
                .when()
                .put(update_url);
        return res;
    }
    public static Response deleteUser(String userName){
        String delete_url = getURL().getString("delete_url");
        Response res = given()
                .pathParam("username",userName)
                .when()
                .delete(delete_url);
        return res;
    }

}
