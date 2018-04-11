package tayler.ut.attendencemanagmentsystem.model.teacher;

import java.util.List;

import tayler.ut.attendencemanagmentsystem.model.course.CourseData;

/**
 * Created by sibaprasad on 10/04/18.
 */

public class TeacherLocalData {

    public static final List<CourseData> getCourseDataList() {
        return courseDataList;
    }

    public void setCourseDataList(List<CourseData> courseDataList) {
        this.courseDataList = courseDataList;
    }

    public static TeacherData getTeacherData() {
        return teacherData;
    }

    public  void setTeacherData(TeacherData teacherData) {
        this.teacherData = teacherData;
    }

    static List<CourseData> courseDataList;
    static TeacherData teacherData;
}
