package apiTests;

import api.SpringApplicationRunner;
import api.domain.Course;
import api.services.CourseService;
import api.services.StudentService;
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

    @Autowired
    private StudentService studentService;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/courses/";
    private final String MARKETING_COURSE = "Marketing";
    private final String TECHNOLOGY_COURSE = "Technology";
    private LoadDataDB loadDataDB;

    @Before
    public void setPort() {
        RestAssured.port = port;
        loadDataDB = new LoadDataDB(courseService, studentService);
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
        Long courseID = loadDataDB.loadCoursesDB(MARKETING_COURSE).getId();
        Course technology = new Course();
        technology.setName(TECHNOLOGY_COURSE);
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(technology).
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
        String courseID = loadDataDB.loadCoursesDB(MARKETING_COURSE).getId().toString();
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
        loadDataDB.loadCoursesDB(MARKETING_COURSE);
        loadDataDB.loadCoursesDB(TECHNOLOGY_COURSE);
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
        String courseID = loadDataDB.loadCoursesDB(MARKETING_COURSE).getId().toString();
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
