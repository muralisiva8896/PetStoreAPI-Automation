package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenTests {

    User payload;

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String userName, String fname, String lname, String email, String pwd, String ph){
        payload = new User();

        payload.setId(Integer.parseInt(userID));
        payload.setUsername(userName);
        payload.setFirstName(fname);
        payload.setLastName(lname);
        payload.setEmail(email);
        payload.setPassword(pwd);
        payload.setPhone(ph);

        Response res = UserEndPoints.createUser(payload);
        res.then().log().all();
        Assert.assertEquals(res.getStatusCode(),200);
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUser(String userName){
        Response res = UserEndPoints.deleteUser(userName);
        Assert.assertEquals(res.getStatusCode(), 404);
    }

}
