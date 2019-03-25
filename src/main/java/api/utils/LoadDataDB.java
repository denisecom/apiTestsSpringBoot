package api.utils;

import api.domain.Course;
import api.domain.Student;

public class LoadDataDB {

    public static Course loadCoursesDB(){
        Course marketing = new Course();
        marketing.setName("Marketing");
        return marketing;
    }

    public static Student loadStudentsDB(){
        Student studentMarketing = new Student();
        studentMarketing.setName("Denise Maia");
        studentMarketing.setRegisterNumber("R1310");
        return studentMarketing;
    }
}
