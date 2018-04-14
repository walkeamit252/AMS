package tayler.ut.attendencemanagmentsystem.teacher.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.CommonPagerAdapter;
import tayler.ut.attendencemanagmentsystem.app.ApplicationContext;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.teacher.adapter.TeacherAddedCoursesAdapter;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.DateUtils;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

/**
 * Created by sibaprasad on 11/04/18.
 */

public class TakeOrViewAttendanceTabbedFragment extends Fragment  implements FirebaseUtility.OnFirebasseActionListener{

    RecyclerView mRecyclerViewAddedSubjectList;
    TextView txtNoDataFound;
    RecyclerView.LayoutManager manager;
    TeacherAddedCoursesAdapter mAdapter;
    ArrayList<CourseData> listData;


    CourseData mCourseData;


    private ProgressDialog progressDialog;
    private ApplicationContext mApplicationContext;
    private int tab;
    private ArrayList<AttendanceData> attendenceListModels = new ArrayList<>();
    private ArrayList<StudentData> listStudent = new ArrayList<>();

    // widgets
    private TabLayout tabLayout;
    private  ViewPager viewPager;

    public static TakeOrViewAttendanceTabbedFragment newInstance(CourseData courseData) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BundelKays.COURSE_DATA,courseData);
        TakeOrViewAttendanceTabbedFragment fragment = new TakeOrViewAttendanceTabbedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TakeOrViewAttendanceTabbedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressDialog = new ProgressDialog(getActivity());
        final View view = inflater.inflate(R.layout.fragment_tabbed_view_take_attendance, container, false);
        Bundle bundle= getArguments();
        if(bundle!=null){
            if(bundle.containsKey(Constants.BundelKays.COURSE_DATA)){
                mCourseData=bundle.getParcelable(Constants.BundelKays.COURSE_DATA);
            }
            fetchAttendenceList();
        }
        return view;
    }

    private void initTabs(){

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final CommonPagerAdapter adapter = new CommonPagerAdapter
                (getChildFragmentManager());
        adapter.addFragment(TakeOrViewAttendenceFragment.newInstance(Constants.TAB_TAKE_ATTENDANCE,attendenceListModels,mCourseData),"Attendance");
        adapter.addFragment(TakeOrViewAttendenceFragment.newInstance(Constants.TAB_VIEW_ATTENDANCE,attendenceListModels,mCourseData),"View Attendance");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewAddedSubjectList=view.findViewById(R.id.mRecyclerViewAddedSubjectList);
        txtNoDataFound=view.findViewById(R.id.txtNoDataFound);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.pager);


        progressDialog.setMessage("Loading");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                initTabs();
            }
        },3000);

    }
    private void fetchAttendenceList() {

        progressDialog.show();

        listStudent.addAll(FirebaseUtility.getStudentListByYears(mCourseData.getCourseYear() ));
        if(listStudent.size()==0){
            FirebaseUtility.getStudentByYear(mCourseData.getCourseYear(),this);
        }
        for(StudentData studentData : listStudent){
            AttendanceData attendanceData = new AttendanceData(
                    "","",studentData.getStudentId(),studentData.getName(),
                    mCourseData.getTeacherName(),mCourseData.getCourseYear(),
                    studentData.getEmailId(),studentData.getMobileNumber(),
                    DateUtils.getCurrentDateForStudent(),true,mCourseData.getCourseName()
            );

            attendenceListModels.add(attendanceData);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }
}
