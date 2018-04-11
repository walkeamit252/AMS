package tayler.ut.attendencemanagmentsystem.model.teacher;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.model.course.CourseData;

/**
 * Created by sibaprasad on 10/04/18.
 */

public class TeacherLocalData {

    private static TeacherLocalData instance = null;
    private static Context context;
    private List<CourseData> courseDataList;

    public TeacherData getTeacherData() {
        return teacherData;
    }

    public void setTeacherData(TeacherData teacherData) {
        this.teacherData = teacherData;
    }

    private TeacherData teacherData;

    public TeacherLocalData newInstance(Context mCotext) {
        context = mCotext;
        if (instance == null)
            instance = new TeacherLocalData();
        return instance;
    }




    public List<CourseData> getTeacherCourseList() {
        return courseDataList;
    }

    public void setTeacherCourseList(List<CourseData> mWishLisProducts) {
        this.courseDataList = mWishLisProducts;
    }




}
