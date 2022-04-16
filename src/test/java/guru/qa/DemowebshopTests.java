package guru.qa;

import org.junit.jupiter.api.Test;

import static guru.qa.listeners.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DemowebshopTests {

    @Test
    void addToCartAsNewUserTest() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void addToWishListTest() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=ba78fc97-1802-4fed-a5d5-ba6860f8d4bb;")
                .body("addtocart_14.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/14/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("updatetopwishlistsectionhtml", is("(1)"));
    }

    @Test
    void subscribeTest() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=ba78fc97-1802-4fed-a5d5-ba6860f8d4bb;")
                .body("email=fgdf@test.ur")
                .when()
                .post("http://demowebshop.tricentis.com/subscribenewsletter")
                .then()
                .log().all()
                .statusCode(200)
                .body("Result", is("Thank you for signing up!" +
                        " A verification email has been sent. We appreciate your interest."));
    }

    @Test
    void voteTest() {
        given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("pollAnswerId=2")
                .when()
                .post("http://demowebshop.tricentis.com/poll/vote")
                .then()
                .log().all()
                .statusCode(200)
                .body("error", is("Only registered users can vote."));
    }
}
