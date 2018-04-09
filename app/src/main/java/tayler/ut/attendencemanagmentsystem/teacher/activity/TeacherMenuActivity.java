package tayler.ut.attendencemanagmentsystem.teacher.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.activity.LoginOptionActivity;
import tayler.ut.attendencemanagmentsystem.teacher.fragment.AddCourseFragment;
import tayler.ut.attendencemanagmentsystem.commonui.fragments.ProfileFragment;
import tayler.ut.attendencemanagmentsystem.teacher.fragment.ViewTeacherAddedCourseFragment;

public class TeacherMenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        //   actionbar.setTitle("Attendece System");
        mToolbarTitle = findViewById(R.id.mToolbarTitle);
        mToolbarTitle.setText("Subject List");

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_take_attendance:
                                selectedFragment = new ViewTeacherAddedCourseFragment();
                                mToolbarTitle.setText("Subject List");
                                break;
                            case R.id.nav_add_subject:
                                selectedFragment = new AddCourseFragment();
                                mToolbarTitle.setText("Add Subject");
                                break;
                            case R.id.nav_view_profile:
                                selectedFragment = new ProfileFragment();
                                mToolbarTitle.setText("Profile");
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        setTitle(item.getTitle());
                        return true;
                    }
                });

    }




    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Signout");
        builder.setMessage("Are you sure you want to signout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                Intent intent = new Intent(TeacherMenuActivity.this, LoginOptionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}