package tayler.ut.attendencemanagmentsystem.teacher.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.Map;

import tayler.ut.attendencemanagmentsystem.app.ApplicationContext;
import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.AttendenceListAdapter;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.DateUtils;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;


public class TakeAttendenceFragment extends Fragment
        implements AttendenceListAdapter.ItemClickListener,
        View.OnClickListener,
        FirebaseUtility.OnFirebasseActionListener {

    private AttendenceListAdapter mListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private Button mButtonSave;

    private DatabaseReference mDatabaseRefrence;
    private FirebaseDatabase database;
    private ArrayList<AttendanceData> attendenceListModels = new ArrayList<>();
    private ArrayList<StudentData> listStudent = new ArrayList<>();

    CourseData mCourseData;


    private ProgressDialog progressDialog;
    private ApplicationContext mApplicationContext;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_attendence_list, container, false);
        Bundle bundle= getArguments();
        mCourseData=bundle.getParcelable(Constants.COURSE_DATA);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...,Please Wait");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        fetchAttendenceList();
    }

    private void fetchAttendenceList() {

        progressDialog.show();

        listStudent.addAll(FirebaseUtility.getStudentListByYears(mCourseData.getCourseYear()));
        for(StudentData studentData : listStudent){
            AttendanceData attendanceData = new AttendanceData(
                    "","",studentData.getName(),
                    mCourseData.getTeacherName(),mCourseData.getCourseYear(),
                    studentData.getEmailId(),studentData.getMobileNumber(),
                    DateUtils.getCurrentDateForStudent(),true
            );

            attendenceListModels.add(attendanceData);
        }
        progressDialog.dismiss();
        mListAdapter.notifyDataSetChanged();
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


        mListAdapter = new AttendenceListAdapter(getActivity(), attendenceListModels, "MarkAttendence");
        recyclerView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        mListAdapter.setClickListener(this);
    }

    @Override

    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + attendenceListModels.get(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

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
        mListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mButtonSave :

                progressDialog.show();

                for(AttendanceData attendanceData :attendenceListModels) {
                    FirebaseUtility.updateAttendance(attendanceData,mCourseData.getCourseYear());
                }

                Toast.makeText(mApplicationContext, "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();

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
}