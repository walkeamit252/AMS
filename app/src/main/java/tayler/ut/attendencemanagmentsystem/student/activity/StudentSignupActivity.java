package tayler.ut.attendencemanagmentsystem.student.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.activity.BaseActivity;
import tayler.ut.attendencemanagmentsystem.model.SignupModel;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherLoginActivity;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;


public class StudentSignupActivity extends BaseActivity {

    private Button btnSignup;
    private TextView txtLogin;
    private EditText etName;
    private EditText etEmail;
    private EditText etNumber;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    RadioGroup mRadioGroup;
    RadioButton mRadioFirst, mRadioSecond, mRadioThird, mRadioFourth;
    String selectedYear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
    }

    private void initView() {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        txtLogin = (TextView) findViewById(R.id.txt_login);

        etName = (EditText) findViewById(R.id.edt_name);
        etEmail = (EditText) findViewById(R.id.edt_email);
        etNumber = (EditText) findViewById(R.id.edt_numbet);
        etPassword = (EditText) findViewById(R.id.edt_password);
        etConfirmPassword = (EditText) findViewById(R.id.edt_confirm_password);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioFirst = (RadioButton) findViewById(R.id.radioFirst);
        mRadioSecond = (RadioButton) findViewById(R.id.radioSecond);
        mRadioThird = (RadioButton) findViewById(R.id.radioThird);
        mRadioFourth = (RadioButton) findViewById(R.id.radioFourth);
        setListener();
    }

    private void setListener() {

        btnSignup.setOnClickListener(new SignupButtonClickListener());
        txtLogin.setOnClickListener(new LoginClickListener());

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    selectedYear = rb.getText().toString();
                    Toast.makeText(StudentSignupActivity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private class SignupButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            registerUser();
        }
    }

    private void registerUser() {

        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_name), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_emailid), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etNumber.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_number), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(etConfirmPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
            return;
        }
        if (!etPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.password_and_confirm_password_are_not_matched), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(selectedYear)) {
            Toast.makeText(this, getString(R.string.please_select_year), Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());

                        } else {
                            //display some message here
                            Toast.makeText(StudentSignupActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {

        // Write new user
        writeNewUser(user.getUid(), etName.getText().toString().trim(), user.getEmail(), etNumber.getText().toString().trim());
        saveValueInSharedPrefrnce();
        Toast.makeText(StudentSignupActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(StudentSignupActivity.this, StudentMenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveValueInSharedPrefrnce() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.STUDENT_LOGIN_FLAG, true);
        editor.putBoolean(Constants.TEACHER_LOGIN_FLAG, false);
        editor.commit();
    }

    private void writeNewUser(String userId, String name, String email, String number) {

        SignupModel signupModel = new SignupModel(userId, name, email, number);
        signupModel.setUserid(userId);
        signupModel.setName(name);
        signupModel.setEmail(email);
        signupModel.setNumber(number);
        mDatabase.child("studentsignup").child(userId).setValue(signupModel);
        StudentData mStudentData = new StudentData(userId, name, email, number, selectedYear, etPassword.getText().toString());
        FirebaseUtility.saveStudentProfile(userId, selectedYear);
        FirebaseUtility.updateStudent(mStudentData);
    }

    private class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(StudentSignupActivity.this, TeacherLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
