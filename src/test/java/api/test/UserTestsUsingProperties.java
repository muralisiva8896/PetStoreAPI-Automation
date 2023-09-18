package api.test;
import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPointsUsingPropertiesFile;
import api.payload.User;
import api.utilities.XLUtility;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
public class UserTestsUsingProperties {
    Faker faker;
    User payLoad;
    XLUtility xl;
    Logger logger; // for logging purpose log42j xml should be added in resources to use this
    @BeforeClass
    public void setupData() throws IOException {
        faker = new Faker();
        payLoad = new User();
        String path = System.getProperty("user.dir") + "//testData//Userdata.xlsx";
        xl = new XLUtility(path);

        payLoad.setId(faker.idNumber().hashCode());
        payLoad.setUsername(faker.name().username());
        payLoad.setFirstName(faker.name().firstName());
        payLoad.setLastName(faker.name().lastName());
        payLoad.setEmail(faker.internet().safeEmailAddress());
        payLoad.setPassword(faker.internet().password(5, 10));
        payLoad.setPhone(faker.phoneNumber().cellPhone());

        logger = LogManager.getLogger(this.getClass()); // Getting logger for this class
    }
    @Test(priority=1)
    public void testPostUser(){

        logger.info("************* Creating New User *************");
        Response response= UserEndPointsUsingPropertiesFile.createUser(this.payLoad);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("************* User is created *************");

    }
    @Test(priority = 2)
    public void testGetUserByName(){
        logger.info("************* Reading User Info *************");
        Response response=UserEndPointsUsingPropertiesFile.readUser(this.payLoad.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),405);
        logger.info("************* User info is displayed *************");
    }
    @Test(priority = 3)
    public void testUpdateUserByName(){
        logger.info("************* Updating the User *************");
        //Update the details
        payLoad.setFirstName(faker.name().firstName());
        payLoad.setLastName(faker.name().lastName());
        payLoad.setEmail(faker.internet().safeEmailAddress());
        Response response=UserEndPointsUsingPropertiesFile.updateUser(payLoad, payLoad.getUsername());
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("************* User details updated successfully *************");
    }
    @Test(priority = 4)
    public void testDeleteUserByName(){
        logger.info("************* Deleting the User *************");
        Response res=UserEndPointsUsingPropertiesFile.deleteUser(this.payLoad.getUsername());
        res.then().log().all();
        Assert.assertEquals(res.getStatusCode(),200);
        logger.info("************* User is deleted *************");

    }
}

