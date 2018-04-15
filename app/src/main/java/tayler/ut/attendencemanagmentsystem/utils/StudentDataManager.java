package tayler.ut.attendencemanagmentsystem.utils;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherLocalData;

/**
 * Created by sibaprasad on 15/04/18.
 */



public class StudentDataManager {

    private static final String TAG = "StudentDataManager";
    Context context;

    public StudentDataManager(Context context) {
        this.context = context;
    }

    public StudentData getStudentData(){
        StudentData teacherLocalData = new StudentData();
        try {
            ObjectMapper mObjectMapper = new ObjectMapper();
            teacherLocalData = mObjectMapper.readValue(AppPreferences.getStudentLocalData(context),
                    StudentData.class);

        } catch (Exception e) {
            Log.i(TAG, "getWishlistGuestUserModel: Error " + e.getMessage());
        }
        return teacherLocalData;
    }
}
