import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class _02_ApiTestExtract {


    @Test
    public void extractingJsonPath(){

        String countryName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("country") // PATH i country olan değeri EXTRACT yap
                ;


        System.out.println("country = " + countryName);
        Assert.assertEquals(countryName,"United States"); // alınan değer buna eşit mi
    }


    @Test
    public void extractingJsonPath2(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız

        String stateName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("places[0].state")
                ;


        System.out.println("stateName = " + stateName);
        Assert.assertEquals(stateName,"California");
    }

    @Test
    public void extractingJsonPath3(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız

        String placeName=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("places[0].'place name'")  // places[0]["place name"] bu da olur
                ;


        System.out.println("placeName = " + placeName);
        Assert.assertEquals(placeName,"Beverly Hills");
    }

    @Test
    public void extractingJsonPath4(){
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

        int limit=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().all()
                        .extract().path("meta.pagination.limit")
                ;

        System.out.println("limit = " + limit);
        Assert.assertTrue(limit==10);
    }

}










