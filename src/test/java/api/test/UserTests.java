package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.XLUtility;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class UserTests {

    Faker faker;
    User payLoad;
    XLUtility xl;
    Logger logger; // for logging purpose log42j xml should be added in resources to use this
    @BeforeClass
    public void setupData() throws IOException {
        faker = new Faker();
        payLoad = new User();
        String path=System.getProperty("user.dir")+"//testData//Userdata.xlsx";
        xl = new XLUtility(path);

        payLoad.setId(faker.idNumber().hashCode());
        payLoad.setUsername(faker.name().username());
        payLoad.setFirstName(faker.name().firstName());
        payLoad.setLastName(faker.name().lastName());
        payLoad.setEmail(faker.internet().safeEmailAddress());
        payLoad.setPassword(faker.internet().password(5,10));
        payLoad.setPhone(faker.phoneNumber().cellPhone());

        logger = LogManager.getLogger(this.getClass()); // Getting logger for this class

//        int i = xl.getRowCount("Sheet1");
//        int cellNum = xl.getCellCount("Sheet1", i);
//        Map<String, Object[]> userData
//                = new TreeMap<String, Object[]>();
//        userData.put(payLoad.getUsername(), new Object[]{payLoad.getFirstName(),payLoad.getLastName(),payLoad.getEmail(),payLoad.getPassword(),payLoad.getPhone()});
//        Object[] objArr = userData.get(payLoad.getUsername());
//        for(int j=0; j<cellNum; j++) {
//           xl.setCellData("Sheet1", i, j, Arrays.toString(objArr));
//            xl.setCellData("Sheet1", i, j, String.valueOf(payLoad.getId()));
//            xl.setCellData("Sheet1", i, j+1, payLoad.getUsername());
//            xl.setCellData("Sheet1", i, j+2, payLoad.getFirstName());
//            xl.setCellData("Sheet1", i, j+3, payLoad.getFirstName());
//            xl.setCellData("Sheet1", i, j+4, payLoad.getLastName());
//            xl.setCellData("Sheet1", i, j+5, payLoad.getEmail());
//            xl.setCellData("Sheet1", i, j+6, payLoad.getPassword());
//            xl.setCellData("Sheet1", i, j+7, payLoad.getPhone());
//        }

    }
    @Test(priority=1)
    public void testPostUser(){

        logger.info("************* Creating New User *************");
        Response response=UserEndPoints.createUser(this.payLoad);
        //System.out.println(payLoad.getUsername());
        System.out.println(response.getBody());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("************* User is created *************");

    }
    @Test(priority = 2)
    public void testGetUserByName(){
        logger.info("************* Reading User Info *************");
        Response response=UserEndPoints.readUser(this.payLoad.getUsername());
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
        Response response=UserEndPoints.updateUser(payLoad, payLoad.getUsername());
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("************* User details updated successfully *************");
    }
    @Test(priority = 4)
    public void testDeleteUserByName(){
        logger.info("************* Deleting the User *************");
        Response res=UserEndPoints.deleteUser(this.payLoad.getUsername());
        res.then().log().all();
        Assert.assertEquals(res.getStatusCode(),200);
        logger.info("************* User is deleted *************");
    }
}
