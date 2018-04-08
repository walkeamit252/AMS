package tayler.ut.attendencemanagmentsystem.teacher.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.activity.BaseActivity;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

public class TeacherLoginActivity extends BaseActivity {

    private EditText etEmailid;
    private EditText etPassword;
    private Button btnLogin;
    private TextView txtSignup;
    DatabaseReference mDatabaseReference;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    SharedPreferences prefs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_teacher_login);

        initView();
    }

    private void initView() {


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        progressDialog = new ProgressDialog(this);

        etEmailid = (EditText) findViewById(R.id.edt_email);

        etPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin = (Button) findViewById(R.id.btn_login_with_teacher);

        txtSignup = (TextView) findViewById(R.id.txt_signup);

        setListener();
    }

    private void setListener() {

        btnLogin.setOnClickListener(new LoginButtonClickListner());

        txtSignup.setOnClickListener(new SignupClickListner());
    }

    private class LoginButtonClickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            loginUser();
           /* Intent intent = new Intent(TeacherLoginActivity.this, StudentMenuActivity.class);
            startActivity(intent);*/
        }
    }

    private void loginUser() {

        if (TextUtils.isEmpty(etEmailid.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_emailid), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        //authenticate user
        auth.signInWithEmailAndPassword(etEmailid.getText().toString().trim(), etPassword.getText().toString().trim())
                .addOnCompleteListener(TeacherLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {





                            // there was an error
                            Toast.makeText(TeacherLoginActivity.this, getString(R.string.invalid_login), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            String teacherId  = task.getResult().getUser().getUid();
                            if(!TextUtils.isEmpty(teacherId)) {
                                FirebaseUtility.saveTeacherProfile(teacherId);
                            }

                            prefs.edit().putBoolean("teacherlogin", true).commit();
                            Intent intent = new Intent(TeacherLoginActivity.this, TeacherMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }





    private class SignupClickListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(TeacherLoginActivity.this, TeacherSignupActivity.class);
            startActivity(intent);
        }
    }
}
