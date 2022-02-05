package com.geekbrains.test.spoonacular;

import java.io.IOException;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpoonaccularTest {

    private static String API_KEY = "b9987d1690b540fda43b57b485e38c93";
    private static String FILL_IGRIDIENTS = "true";
    private static int ICLUDE_IGRIDIENTS_COUNT = 1;

    private static String FOOD1 = "Pasta";
    private static String ICLUDE_IGRIDIENTS1 = "parmesan cheese";
    private static int NUMBER1 = 1;
    private static int OFFSET1 = 0;

    private static String FOOD2 = "salad";
    private static String ICLUDE_IGRIDIENTS2 = "egg";
    private static int NUMBER2 = 1;
    private static int OFFSET2 = 3;

    private static String FOOD3 = "chicken";
    private static String ICLUDE_IGRIDIENTS3 = "potato";
    private static int NUMBER3 = 1;
    private static int OFFSET3 = 1;

    private static String FOOD4 = "sauce";
    private static String ICLUDE_IGRIDIENTS4 = "garlic";
    private static int NUMBER4 = 1;
    private static int OFFSET4 = 1;

    private static String[] FOOD = {FOOD1, FOOD2, FOOD3, FOOD4};
    private static String[] ICLUDE_IGRIDIENTS = {ICLUDE_IGRIDIENTS1, ICLUDE_IGRIDIENTS2, ICLUDE_IGRIDIENTS3, ICLUDE_IGRIDIENTS4};
    private static int[] NUMBER = {NUMBER1, NUMBER2, NUMBER3, NUMBER4};
    private static int[] OFFSET = {OFFSET1, OFFSET2, OFFSET3, OFFSET4};

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com";
    }

    @Test
    void testGetRecipesComplexSearch() throws IOException, JSONException {
        for (int i = 0; i <= 3; i++) {
            given()
                    .param("apiKey", API_KEY)
                    .param("fillIngredients", FILL_IGRIDIENTS)
                    .param("query", FOOD[i])
                    .param("includeIngredients", ICLUDE_IGRIDIENTS[i])
                    .param("number", NUMBER[i])
                    .param("offset", OFFSET[i])
                    .log()
                    .parameters()
                    .expect()
                    .statusCode(200)
                    .time(Matchers.lessThan(1000L))
                    .body("totalResults", greaterThan(1))
                    .body("offset", is(OFFSET[i]))
                    .body("number", is(NUMBER[i]))
                    .body("results[0].usedIngredientCount", is(ICLUDE_IGRIDIENTS_COUNT))
                    .body("results[0].usedIngredients[0].name", containsStringIgnoringCase(ICLUDE_IGRIDIENTS[i]))
                    .body("results[0].title", containsStringIgnoringCase(FOOD[i]))
                    .log()
                    .body()
                    .when()
                    .get("recipes/complexSearch")
                    .body()
                    .asPrettyString();
        }
    }
}
