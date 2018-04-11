package tayler.ut.attendencemanagmentsystem.utils;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherLocalData;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherLoginActivity;

/**
 * Created by sibaprasad on 12/04/18.
 */

public class TeacherDataManager {

    private static final String TAG = "TeacherDataManager";
    Context context;

    public TeacherDataManager(Context context) {
        this.context = context;
    }

    public TeacherLocalData getTeacherLocalDataModel(){
        TeacherLocalData teacherLocalData = new TeacherLocalData();
        try {
            ObjectMapper mObjectMapper = new ObjectMapper();
            teacherLocalData = mObjectMapper.readValue(AppPreferences.getTeacherLocalData(context),
                    TeacherLocalData.class);

        } catch (Exception e) {
            Log.i(TAG, "getWishlistGuestUserModel: Error " + e.getMessage());
        }
        return teacherLocalData;
    }

    public List<CourseData> getTeachersCourseList(){
        TeacherLocalData teacherLocalData = getTeacherLocalDataModel();
        List<CourseData> courseDataList;
        courseDataList = teacherLocalData.getTeacherCourseList();
        if(teacherLocalData!=null && courseDataList!=null && courseDataList.size()>0){
            return courseDataList;
        }
        else{
            return new ArrayList<>();
        }
    }

    public void updateTeacherCourseData(List<CourseData> productList) {
        TeacherLocalData teacherLocalData = getTeacherLocalDataModel();
        if (teacherLocalData != null && productList!=null && productList.size()>0) {
            ArrayList<CourseData> listWishlistProduct = new ArrayList<>();
            listWishlistProduct.addAll(productList);
            teacherLocalData.setTeacherCourseList(listWishlistProduct);

            AppPreferences.setTeacherLocalData(context,teacherLocalData);
        }
    }

    public void updateTeacherData(TeacherData teacherData) {
        TeacherLocalData teacherLocalData = getTeacherLocalDataModel();
        if (teacherLocalData != null) {
            teacherLocalData.setTeacherData(teacherData);
            AppPreferences.setTeacherLocalData(context,teacherLocalData);
        }
    }

}
