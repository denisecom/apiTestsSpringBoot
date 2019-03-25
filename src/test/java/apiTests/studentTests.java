package apiTests;

import api.SpringApplicationRunner;
import api.domain.Student;
import api.repositories.StudentRepository;
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
public class studentTests {
    @Autowired
    private StudentRepository studentRepository;

    @LocalServerPort
    private int port;

    private String ENDPOINT = "/students/";

    @Before
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    public void shouldReturnSuccessfullyWhenAddNewStudent() {
        Student studentMarketing = new Student();
        studentMarketing.setName("Denise Maia");
        studentMarketing.setRegisterNumber("R1310");
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(studentMarketing).
                when().
                post(ENDPOINT).
                then().
                statusCode(200).
                and().
                body("name", equalTo("Denise Maia")).
                body("registerNumber", equalTo("R1310"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenUpdateStudent(){
        Student studentMarketing = LoadDataDB.loadStudentsDB();
        Long studentID = studentRepository.save(studentMarketing).getId();
        studentMarketing.setName("João da Silva");
        studentMarketing.setRegisterNumber("R1234");
        RestAssured.
                given().
                header("Content-Type", "application/json").
                body(studentMarketing).
                when().
                put(ENDPOINT.concat(studentID.toString())).
                then().
                statusCode(200).
                and().
                body("id", equalTo(studentID.intValue())).
                body("name", equalTo("João da Silva")).
                body("registerNumber", equalTo("R1234"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenFindByStudent(){
        Student studentMarketing = LoadDataDB.loadStudentsDB();
        String studentID = studentRepository.save(studentMarketing).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                get(ENDPOINT.concat(studentID)).
                then().
                statusCode(200).
                and().
                body("name", equalTo("Denise Maia")).
                body("registerNumber", equalTo("R1310"));
    }

    @Test
    public void shouldReturnSuccessfullyWhenDeleteStudent(){
        Student studentMarketing = LoadDataDB.loadStudentsDB();
        String studentID = studentRepository.save(studentMarketing).getId().toString();
        RestAssured.
                given().
                header("Content-Type", "application/json").
                when().
                delete(ENDPOINT.concat(studentID)).
                then().
                statusCode(200);
        assertFalse(studentRepository.findAll().iterator().hasNext());
    }

    @After
    public void clearData(){
        studentRepository.deleteAll();
    }
}
