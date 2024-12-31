package api_endpoints;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api_payloads.User;  

//Created for CRUD operations

public class UserEndPoints {

    public static Response readUser(int page)
    {

        Response response= given()
             .queryParam("page", page)
    
        .when()
            .get(Routes.get_url);

        return response;
    }   


    public static Response createUser(User payload)
    {

        Response response= given()
            .contentType(ContentType.JSON)
            .body(payload)
    
        .when()
            .post(Routes.post_url);

        return response;
    }

    public static Response updateUser(int id,User payload)
    {

        Response response= given()
            .contentType(ContentType.JSON)
            .body(payload)
    
        .when()
            .put(Routes.put_url(id));

        return response;
    }

    public static Response deleteUser(int id)
    {

        Response response= given()
        
        .when()
            .delete(Routes.delete_url(id));

        return response;
    }   

    
}
