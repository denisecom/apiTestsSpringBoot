package apiTests;

import api.SpringApplicationRunner;
import api.domain.Course;
import api.repositories.CourseRepository;
import api.utils.LoadDataDB;
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
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class courseTests {
    @Autowired
    private CourseRepository courseRepository;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/courses/";

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
        Course course = LoadDataDB.loadCoursesDB();
        Long courseID = courseRepository.save(course).getId();
        course.setName("Technology");
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(course).
                when().
                put(ENDPOINT.concat(courseID.toString())).
                then().
                statusCode(200).
                and().
                body("id", equalTo(courseID.intValue())).
                body("name", equalTo("Technology"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindByCourse(){
        Course course = LoadDataDB.loadCoursesDB();
        String courseID = courseRepository.save(course).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT.concat(courseID)).
                then().
                statusCode(200).
                and().
                body("name", equalTo("Marketing"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenDeleteCourse(){
        Course course = LoadDataDB.loadCoursesDB();
        String courseID = courseRepository.save(course).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                delete(ENDPOINT.concat(courseID)).
                then().
                statusCode(200);
        assertFalse(courseRepository.findAll().iterator().hasNext());
    }

    @After
    public void clearData(){
        courseRepository.deleteAll();
    }
}
