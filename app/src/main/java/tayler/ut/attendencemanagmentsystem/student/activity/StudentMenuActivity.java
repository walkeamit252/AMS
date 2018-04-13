package tayler.ut.attendencemanagmentsystem.student.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.fragments.ProfileFragment;
import tayler.ut.attendencemanagmentsystem.student.fragment.ViewStudentSyllabusListFragment;
import tayler.ut.attendencemanagmentsystem.student.fragment.ViewStudentSyllabuswiseAttendanceFragment;

public class StudentMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        mToolbarTitle = findViewById(R.id.mToolbarTitle);
        mToolbarTitle.setText("Subject List");

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_view_student_subject:
                                selectedFragment = new ViewStudentSyllabusListFragment();
                                mToolbarTitle.setText("Subject List");
                                break;
                            case R.id.nav_view_student_attendance:
                                selectedFragment = new ViewStudentSyllabuswiseAttendanceFragment();
                                mToolbarTitle.setText("Attendance List");
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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new ViewStudentSyllabusListFragment());
        transaction.commit();
        mToolbarTitle.setText("Subject List");

    }
}