package tayler.ut.attendencemanagmentsystem.commonui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherLocalData;
import tayler.ut.attendencemanagmentsystem.student.activity.StudentMenuActivity;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherMenuActivity;
import tayler.ut.attendencemanagmentsystem.utils.AppPreferences;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.DateUtils;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private Context mContext;
    private FirebaseAuth auth;
    private SharedPreferences prefs;
    private boolean isteacher = false;
    private boolean isStudent = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_splash);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        initUI();

        Log.i(TAG, "onCreate: "+ DateUtils.getCurrentDateForStudent());



        TeacherLocalData teacherLocalData = new TeacherLocalData();
        TeacherData teacherData = new TeacherData();
        teacherData.setTeacherId("123");
        teacherData.setEmailId("s@s.com");
        teacherData.setSubjects("s@s.com");
        teacherData.setYear("asdasdas");
        CourseData courseData = new CourseData();
        courseData.setSyllabusFilePath("cdfsdfds");
        courseData.setCourseName("dasdasda");
        courseData.setCourseYear("FirstYear");
        List<CourseData> listC = new ArrayList<>();
        listC.add(courseData);
        teacherLocalData.setTeacherData(teacherData);
        teacherLocalData.setCourseDataList(listC);

        AppPreferences.setTeacherLocalData(SplashActivity.this,teacherLocalData);

        TeacherLocalData teacherLocalData1 = AppPreferences.getTeacherLocalData(SplashActivity.this);


         moveToNextScreen();
    }

    private void initUI() {

        mContext = getApplicationContext();

        isStudent = prefs.getBoolean(Constants.STUDENT_LOGIN_FLAG, false);
        isteacher = prefs.getBoolean(Constants.TEACHER_LOGIN_FLAG, false);
    }

    private void moveToNextScreen() {

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            public void run() {
                // TODO: Your application init goes here.

                if (auth.getCurrentUser() != null && isteacher ) {
                    startActivity(new Intent(SplashActivity.this, TeacherMenuActivity.class));
                    FirebaseUtility.saveTeacherProfile(SplashActivity.this,auth.getCurrentUser().getUid());
                    finish();
                }
                else if (auth.getCurrentUser() !=null && isStudent){
                    startActivity(new Intent(SplashActivity.this, StudentMenuActivity.class));
                  //  FirebaseUtility.saveStudentProfile(auth.getCurrentUser().getUid());
                    FirebaseUtility.saveStudentProfile(auth.getCurrentUser().getUid(), FirebaseUtility.FirebaseConstants.FIRSTYEAR);
                    finish();
                }
                 else {
                    Intent intent = new Intent(mContext, LoginOptionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);

    }
}
