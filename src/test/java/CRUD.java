import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CRUD {

    @Test
    public void verifyCRUDProjects() {

        JSONObject projectBody = new JSONObject();
        projectBody.put("Content", "Grupo 1");
        projectBody.put("Icon", 1);

        // Create
        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic("jbapi@jb.com", "12345")
                        .log()
                        .all()
                        .body(projectBody.toString())
                        .when()
                        .post("http://todo.ly/api/projects.json");

        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo("Grupo 1"))
                .body("Icon", equalTo(1));

        int idProject = response.then().extract().path("Id");
        System.out.println("ID PROJECT: " + idProject);

        // Update
        projectBody.put("Content", "Grupo 1 UPDATE");

        given()
                .auth()
                .preemptive()
                .basic("jbapi@jb.com", "12345")
                .log()
                .all()
                .body(projectBody.toString())
                .when()
                .put("http://todo.ly/api/projects/" + idProject + ".json")
                .then()
                .statusCode(200)
                .body("Content", equalTo("Grupo 1 UPDATE"))
                .body("Icon", equalTo(1))
                .log().all();

        // Read
        given()
                .auth()
                .preemptive()
                .basic("jbapi@jb.com", "12345")
                .log()
                .all()
                .when()
                .get("http://todo.ly/api/projects/" + idProject + ".json")
                .then()
                .statusCode(200)
                .body("Content", equalTo("Grupo 1 UPDATE"))
                .body("Icon", equalTo(1))
                .log().all();

        // Delete
        given()
                .auth()
                .preemptive()
                .basic("jbapi@jb.com", "12345")
                .log()
                .all()
                .when()
                .delete("http://todo.ly/api/projects/" + idProject + ".json")
                .then()
                .statusCode(200)
                .body("Content", equalTo("Grupo 1 UPDATE"))
                .body("Icon", equalTo(1))
                .body("Deleted", equalTo(true))
                .log().all();
    }
}