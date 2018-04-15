package tayler.ut.attendencemanagmentsystem.student.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.fragments.ShowAttendanceDetailFragment;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.student.adapter.StudentCourseWiseAttendanceListAdapter;
import tayler.ut.attendencemanagmentsystem.utils.AppPreferences;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewStudentSyllabuswiseAttendanceFragment extends Fragment implements StudentCourseWiseAttendanceListAdapter.ItemClickListener {

    RecyclerView mRecyclerViewStudentAttendance;
    StudentCourseWiseAttendanceListAdapter mStudentCourseWiseAttendanceListAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<CourseData> courseData;
    private StudentData mStudentData;

    public ViewStudentSyllabuswiseAttendanceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_student_syllabuswise_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewStudentAttendance = (RecyclerView) view.findViewById(R.id.mRecyclerViewStudentAttendance);
        manager = new LinearLayoutManager(getActivity());
        courseData = new ArrayList<>();
        mStudentData = FirebaseUtility.getStudentProfileData();
        if (mStudentData == null) {
            return;
        }
        courseData.clear();
        courseData = (ArrayList<CourseData>) FirebaseUtility.getCourses(mStudentData.getYear());
        mStudentCourseWiseAttendanceListAdapter = new StudentCourseWiseAttendanceListAdapter(getActivity(), courseData, this);
        mRecyclerViewStudentAttendance.setLayoutManager(manager);
        mRecyclerViewStudentAttendance.setAdapter(mStudentCourseWiseAttendanceListAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onUploadFileClick(CourseData courseData) {
        AttendanceData attendanceData = new AttendanceData();
        attendanceData.setStudentId(AppPreferences.getStudentId(getActivity()));

        StudentData studentData = FirebaseUtility.getStudentProfileData();

            attendanceData.setSubjectName(courseData.getCourseName());
            if(studentData!=null) {
                attendanceData.setStudentName(studentData.getName());
                attendanceData.setStudentId(studentData.getStudentId());
                attendanceData.setYear(courseData.getCourseYear());
            }

            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.frame_layout,
                    ShowAttendanceDetailFragment.newInstance(attendanceData)).commit();

    }
}
