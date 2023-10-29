import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _01_ApiTestStart {

    @Test
    public void test1() {
        given().
                // hazirlik islemleri

                        when().
                        // endpoint(url), methodu verip istek gonderme

                        then()
                        // assertion, test, GELEN data islemleri
        ;
    }


    @Test
    public void test2() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200) // assertions
                .contentType(ContentType.JSON)
        ;
    }


    @Test
    public void checkCountryName() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200) // assertions
                .body("country", equalTo("United States")) // assertions
        ;
    }

    // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    // place dizisinin ilk elemanının state değerinin  "California"
    // olduğunu doğrulayınız
    @Test
    public void checkStateNameOfPlace() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))

        ;
    }


    // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
    // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
    // olduğunu doğrulayınız
    @Test
    public void checkStateNameOfPlaces() {
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                .log().body()
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
                .statusCode(200)
        ;
    }

    // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
    // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.
    @Test
    public void checkToListSize() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)

        ;
    }

    @Test
    public void pathParam() {
        given()
                .pathParam("country", "us")
                .pathParam("postCod", 90210)
                .log().uri() // request link url before to send

                .when()
                .get("http://api.zippopotam.us/{country}/{postCod}")


                .then()
                .statusCode(200)

        ;
    }

    @Test
    public void queryParamTest(){
        // https://gorest.co.in/public/v1/users?page=3
        given()
                .param("page",1) // ?page=1  şeklinde linke ekleniyor  // queryParam ile de kullanılabilir
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users") // ?page=1

                .then()
                .statusCode(200)
                .log().body()
        ;
    }


    // https://gorest.co.in/public/v1/users?page=3
    // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
    // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.
    @Test
    public void queryParamTest2(){
        // https://gorest.co.in/public/v1/users?page=3

        for (int i = 1; i <= 10 ; i++) {
            given()
            .param("page",i) // ?page=1  şeklinde linke ekleniyor  // queryParam ile de kullanılabilir
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users") // ?page=1

                    .then()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(i))
            ;
        }
    }


    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setup(){
        baseURI = "https://gorest.co.in/public/v1";

        requestSpec= new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)  // log().uri()
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)  // statusCode(200)
                .log(LogDetail.BODY)  //log().body()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void requestResponseSpecifications(){
        given()
                .param("page",1)
                .spec(requestSpec)

                .when()
                .get("/users") // http hok ise baseUri baş tarafına gelir.

                .then()
                .spec(responseSpec)
        ;
    }




}
