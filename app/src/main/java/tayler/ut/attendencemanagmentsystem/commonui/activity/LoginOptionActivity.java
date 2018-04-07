package tayler.ut.attendencemanagmentsystem.commonui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.student.activity.StudentLoginActivity;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherLoginActivity;


public class LoginOptionActivity extends BaseActivity {

    private RelativeLayout rltTeacher;
    private RelativeLayout rlttStudent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_option);

        initView();
    }

    private void initView() {

        rltTeacher = (RelativeLayout) findViewById(R.id.rlt_teacher);

        rlttStudent = (RelativeLayout) findViewById(R.id.rlt_student);

        setListners();
    }

    private void setListners() {

        rltTeacher.setOnClickListener(new TeacherOptionClickListner());

        rlttStudent.setOnClickListener(new StudentOptionClickListner());
    }

    private class TeacherOptionClickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(LoginOptionActivity.this, TeacherLoginActivity.class);

            startActivity(intent);

        }
    }

    private class StudentOptionClickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(LoginOptionActivity.this, StudentLoginActivity.class);
            startActivity(intent);

        }
    }
}
