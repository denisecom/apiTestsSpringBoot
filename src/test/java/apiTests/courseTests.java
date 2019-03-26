package apiTests;

import api.SpringApplicationRunner;
import api.domain.Course;
import api.services.CourseService;
import api.utils.LoadDataDB;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringApplicationRunner.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class courseTests {
    @Autowired
    private CourseService courseService;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/courses/";
    private final String MARKETING_COURSE = "Marketing";
    private final String TECHNOLOGY_COURSE = "Technology";

    @Before
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnSuccessfullyWhenAddNewCourse() {
        Course marketing = new Course();
        marketing.setName(MARKETING_COURSE);
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(marketing).
                when().
                post(ENDPOINT).
                then().
                statusCode(200).
                and().
                body("name", equalTo(MARKETING_COURSE));
    }

    @Test
    public void shouldReturnSuccessfullyWhenUpdateCourse(){
        Course course = LoadDataDB.loadCoursesDB(MARKETING_COURSE);
        Long courseID = courseService.addCourse(course).getId();
        course.setName(TECHNOLOGY_COURSE);
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
                body("name", equalTo(TECHNOLOGY_COURSE));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindByCourse(){
        Course course = LoadDataDB.loadCoursesDB(MARKETING_COURSE);
        String courseID = courseService.addCourse(course).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT.concat(courseID)).
                then().
                statusCode(200).
                and().
                body("name", equalTo(MARKETING_COURSE));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindAllCourses(){
        Course marketing = LoadDataDB.loadCoursesDB(MARKETING_COURSE);
        Course technology = LoadDataDB.loadCoursesDB(TECHNOLOGY_COURSE);
        courseService.addCourse(marketing);
        courseService.addCourse(technology);
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT).
                then().
                statusCode(200).
                and().
                body("name[0]", equalTo(MARKETING_COURSE)).
                body("name[1]", equalTo(TECHNOLOGY_COURSE));
    }

    @Test
    public void shouldReturnSuccessfullyWhenDeleteCourse(){
        Course course = LoadDataDB.loadCoursesDB(MARKETING_COURSE);
        String courseID = courseService.addCourse(course).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                delete(ENDPOINT.concat(courseID)).
                then().
                statusCode(200);
        assertFalse(courseService.getAllCourses().iterator().hasNext());
    }

    @After
    public void clearData(){
        courseService.deleteAllCourses();
    }
}
