package apiTests;

import api.SpringApplicationRunner;
import api.domain.Course;
import api.domain.Student;
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
public class studentTests {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/students/";
    private String ENDPOINT_COURSE_ID;
    private final String STUDENT_MARKETING_NAME = "João da Silva";
    private final String STUDENT_MARKETING_RN = "R1234";
    private final String STUDENT_TECHNOLOGY_NAME = "Denise Maia";
    private final String STUDENT_TECHNOLOGY_RN = "R1310";
    private Course course;
    private LoadDataDB loadDataDB;

    @Before
    public void setPort() {
        RestAssured.port = port;
        loadDataDB = new LoadDataDB(courseService, studentService);
        course = loadDataDB.loadCoursesDB("Marketing");
        ENDPOINT_COURSE_ID = "/courses/".concat(course.getId().toString());
    }

    @Test
    public void shouldReturnSuccessfullyWhenAddNewStudent() {
        Student studentMarketing = new Student();
        studentMarketing.setName(STUDENT_MARKETING_NAME);
        studentMarketing.setRegisterNumber(STUDENT_MARKETING_RN);
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(studentMarketing).
                when().
                post(ENDPOINT_COURSE_ID.concat(ENDPOINT)).
                then().
                statusCode(200).
                and().
                body("name", equalTo(STUDENT_MARKETING_NAME)).
                body("registerNumber", equalTo(STUDENT_MARKETING_RN));
    }

    @Test
    public void shouldReturnSuccessfullyWhenUpdateStudent(){
        Long studentID = loadDataDB.loadStudentDB(course, STUDENT_MARKETING_NAME, STUDENT_MARKETING_RN).getId();
        Student studentTechnology = new Student();
        studentTechnology.setName(STUDENT_TECHNOLOGY_NAME);
        studentTechnology.setRegisterNumber(STUDENT_TECHNOLOGY_RN);
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(studentTechnology).
                when().
                put(ENDPOINT_COURSE_ID.concat(ENDPOINT).concat(studentID.toString())).
                then().
                statusCode(200).
                and().
                body("id", equalTo(studentID.intValue())).
                body("name", equalTo(STUDENT_TECHNOLOGY_NAME)).
                body("registerNumber", equalTo(STUDENT_TECHNOLOGY_RN));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindByStudentId(){
        String studentID = loadDataDB.loadStudentDB(course, STUDENT_MARKETING_NAME, STUDENT_MARKETING_RN).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT_COURSE_ID.concat(ENDPOINT).concat(studentID)).
                then().
                statusCode(200).
                and().
                body("name", equalTo(STUDENT_MARKETING_NAME)).
                body("registerNumber", equalTo(STUDENT_MARKETING_RN));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindAllStudentsByCourse(){
        loadDataDB.loadStudentDB(course, STUDENT_MARKETING_NAME, STUDENT_MARKETING_RN);
        loadDataDB.loadStudentDB(course, STUDENT_TECHNOLOGY_NAME, STUDENT_TECHNOLOGY_RN);

        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT_COURSE_ID.concat(ENDPOINT)).
                then().
                statusCode(200).
                and().
                body("name[0]", equalTo(STUDENT_MARKETING_NAME)).
                body("registerNumber[0]", equalTo(STUDENT_MARKETING_RN)).
                body("name[1]", equalTo(STUDENT_TECHNOLOGY_NAME)).
                body("registerNumber[1]", equalTo(STUDENT_TECHNOLOGY_RN));
    }

    @Test
    public void shouldReturnSuccessfullyWhenDeleteStudent(){
        String studentID = loadDataDB.loadStudentDB(course, STUDENT_MARKETING_NAME, STUDENT_MARKETING_RN).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                delete(ENDPOINT_COURSE_ID.concat(ENDPOINT).concat(studentID)).
                then().
                statusCode(200);
        assertFalse(studentService.getAllStudents(course.getId()).iterator().hasNext());
    }

    @After
    public void clearData(){
        studentService.deleteAllStudents();
    }
}
