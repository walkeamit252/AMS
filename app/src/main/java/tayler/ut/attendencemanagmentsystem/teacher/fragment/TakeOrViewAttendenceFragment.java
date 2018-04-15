package tayler.ut.attendencemanagmentsystem.teacher.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tayler.ut.attendencemanagmentsystem.app.ApplicationContext;
import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.AttendenceListAdapter;
import tayler.ut.attendencemanagmentsystem.commonui.fragments.ShowAttendanceDetailFragment;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.DateUtils;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;


public class TakeOrViewAttendenceFragment extends Fragment
        implements AttendenceListAdapter.ItemClickListener,
        View.OnClickListener,
        FirebaseUtility.OnFirebasseActionListener {

    private AttendenceListAdapter mListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private Button mButtonSave;

    private DatabaseReference mDatabaseRefrence;
    private FirebaseDatabase database;
    private ArrayList<AttendanceData> attendenceListModels ;
    private CourseData mCourseData;
    private List<StudentData> listStudent = new ArrayList<>();


    private ProgressDialog progressDialog;
    private ApplicationContext mApplicationContext;
    private int tab;

    public static TakeOrViewAttendenceFragment newInstance(int tabType,List<AttendanceData> listAttendance,CourseData courseData) {
        Bundle args = new Bundle();
        args.putInt(Constants.BundelKays.TAB_TYPE,tabType);
        args.putParcelable(Constants.BundelKays.COURSE_DATA,courseData);
        args.putParcelableArrayList(Constants.BundelKays.ATTENDANCE_LIST, (ArrayList<? extends Parcelable>) listAttendance);
        TakeOrViewAttendenceFragment fragment = new TakeOrViewAttendenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_attendence_list, container, false);
        Bundle bundle= getArguments();
        if(bundle!=null){
            if(bundle.containsKey(Constants.BundelKays.ATTENDANCE_LIST)){
                attendenceListModels=bundle.getParcelableArrayList(Constants.BundelKays.ATTENDANCE_LIST);
            }
            if(bundle.containsKey(Constants.BundelKays.COURSE_DATA)){
                mCourseData=bundle.getParcelable(Constants.BundelKays.COURSE_DATA);
            }
            if(bundle.containsKey(Constants.BundelKays.TAB_TYPE)){
                tab = bundle.getInt(Constants.BundelKays.TAB_TYPE);
            }
        }


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...,Please Wait");

        initview(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initview(View view) {
        mButtonSave=(Button)view.findViewById(R.id.mButtonSave);
        mButtonSave.setOnClickListener(this);
        mApplicationContext = new ApplicationContext(getActivity());
        database = ApplicationContext.getDatabase();

        mDatabaseRefrence = database.getReference().child("studentsignup");
        mDatabaseRefrence.keepSynced(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_attendence);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        if(attendenceListModels!=null && attendenceListModels.size()>0){
            setRecyclerAdapter();
        }
        else{
            fetchAttendenceList();
        }

        if(tab== Constants.TAB_VIEW_ATTENDANCE){
            mButtonSave.setVisibility(View.GONE);
        }
        else {
            mButtonSave.setVisibility(View.VISIBLE);

        }


    }

    private void setRecyclerAdapter(){
        mListAdapter = new AttendenceListAdapter(getActivity(), attendenceListModels, tab);
        recyclerView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        mListAdapter.setClickListener(this);
    }

    @Override

    public void onItemClick(View view, int position) {
        AttendanceData attendanceData = attendenceListModels.get(position);
        if(tab == Constants.TAB_TAKE_ATTENDANCE){

            if (attendanceData != null) {
                attendanceData.setPresent(!attendanceData.isPresent());
            }
        }
        else {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_layout,
                    ShowAttendanceDetailFragment.newInstance(attendanceData)).commit();
        }
    }

    @Override
    public void onAbsentPresentClick(int position) {
        AttendanceData attendanceData = attendenceListModels.get(position);
        if(attendanceData.isPresent()){
            attendanceData.setPresent(false);
        }
        else{
            attendanceData.setPresent(true);
        }
     //   mListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mButtonSave :

                progressDialog.show();

                for(AttendanceData attendanceData :attendenceListModels) {
                    FirebaseUtility.updateAttendance(attendanceData,mCourseData.getCourseYear());
                }

                Toast.makeText(getActivity(), "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();


                break;
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

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
            setRecyclerAdapter();
        }
        progressDialog.dismiss();
    }
}