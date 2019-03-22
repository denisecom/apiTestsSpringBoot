package apiTests;

import api.SpringApplicationRunner;
import api.domain.Course;
import api.repositories.CourseRepository;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class courseTests {
    @Autowired
    private CourseRepository courseRepository;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/courses";

    @Before
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnSuccessfullyWhenAddNewCourse() {
        Course marketing = new Course();
        marketing.setName("Marketing");
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(marketing).
                when().
                post(ENDPOINT).
                then().
                statusCode(200).
                and().
                body("name", equalTo("Marketing"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenUpdateCourse(){

    }

    @Test
    public void shouldReturnSuccessfullyWhenFindByCourse(){

    }

    @Test
    public void shouldReturnSuccessfullyWhenDeleteCourse(){

    }

    @After
    public void clearData(){
        courseRepository.deleteAll();
    }
}
