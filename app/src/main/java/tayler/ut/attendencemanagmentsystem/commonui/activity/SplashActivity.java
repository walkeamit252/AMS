package tayler.ut.attendencemanagmentsystem.commonui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;
import tayler.ut.attendencemanagmentsystem.student.activity.StudentMenuActivity;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherMenuActivity;
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


//      StudentData studentData = new StudentData("","Siba","sp.dobest@gmail.com","9768235871", FirebaseUtility.FirebaseConstants.FIRSTYEAR,"123");
//      FirebaseUtility.updateStudent(SplashActivity.this,studentData);
//
//        TeacherData teacherData = new TeacherData("","SibaM","sp.dobest@gmail.com","9768235871", "","123");
//        FirebaseUtility.updateTeacher(SplashActivity.this,teacherData);


//        int z  = 0;
//        for(int i = 0;i<20;i++) {
//            TeacherData teacherData = new TeacherData("","Teacher  "+(++z),"teacher"+z+"@gmail.com","9848234242","","");
//            FirebaseUtility.updateTeacher(SplashActivity.this,teacherData);
//        }


       // int j  = 0;
      /*      for(int i = 0;i<50;i++) {
                StudentData studentData = new StudentData("","Siba","sp.dobest@gmail.com","9768235871", FirebaseUtility.FirebaseConstants.FIRSTYEAR,"123");
                FirebaseUtility.updateStudent(SplashActivity.this,studentData);
            }
        int k  =0;
        for(int i = 0;i<50;i++) {
            StudentData studentData = new StudentData("","Siba","sp.dobest@gmail.com","9768235871", FirebaseUtility.FirebaseConstants.FIRSTYEAR,"123");
            FirebaseUtility.updateStudent(SplashActivity.this,studentData);
        }
        int l  =0;
        for(int i = 0;i<50;i++) {
            StudentData studentData = new StudentData("","Siba","sp.dobest@gmail.com","9768235871", FirebaseUtility.FirebaseConstants.FIRSTYEAR,"123");
            FirebaseUtility.updateStudent(SplashActivity.this,studentData);
        }
        int m  =0;
        for(int i = 0;i<50;i++) {
            StudentData studentData = new StudentData("","Siba","sp.dobest@gmail.com","9768235871", FirebaseUtility.FirebaseConstants.FIRSTYEAR,"123");
            FirebaseUtility.updateStudent(SplashActivity.this,studentData);
        }*/


       // moveToNextScreen();
    }

    private void initUI() {

        mContext = getApplicationContext();

        isStudent = prefs.getBoolean("studentlogin", false);

        isteacher = prefs.getBoolean("teacherlogin", false);
    }

    private void moveToNextScreen() {

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            public void run() {
                // TODO: Your application init goes here.

                if (auth.getCurrentUser() != null && isteacher ) {
                    startActivity(new Intent(SplashActivity.this, TeacherMenuActivity.class));
                    finish();
                }
                else if (auth.getCurrentUser() !=null && isStudent){
                    startActivity(new Intent(SplashActivity.this, StudentMenuActivity.class));
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
