package tayler.ut.attendencemanagmentsystem.teacher.fragment;


import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherLocalData;
import tayler.ut.attendencemanagmentsystem.teacher.adapter.TeacherAddedCoursesAdapter;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;
import tayler.ut.attendencemanagmentsystem.utils.TeacherDataManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTeacherAddedCourseFragment extends Fragment implements TeacherAddedCoursesAdapter.ItemClickListener{

    RecyclerView mRecyclerViewAddedSubjectList;
    TextView txtNoDataFound;
    RecyclerView.LayoutManager manager;
    TeacherAddedCoursesAdapter mAdapter;
    ArrayList<CourseData> listData;
    private ProgressDialog progressDialog;

    public ViewTeacherAddedCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_teacher_added_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        mRecyclerViewAddedSubjectList=view.findViewById(R.id.mRecyclerViewAddedSubjectList);
        txtNoDataFound=view.findViewById(R.id.txtNoDataFound);
        setRecyclerView();
    }

    private void setRecyclerView() {
        manager=new LinearLayoutManager(getActivity());
        listData = new ArrayList<>();
        List<CourseData> listTeachersCourse = new TeacherDataManager(getActivity()).getTeachersCourseList();
        if(listTeachersCourse!=null && listTeachersCourse.size()>0){
            listData.addAll(listTeachersCourse);
        }
        else{
            List<CourseData> listTeachCourses =  new TeacherDataManager(getActivity()).getTeachersCourseList();
            if(listTeachCourses!=null && listTeachCourses.size()>0){
                listData.addAll(listTeachCourses);
            }
        }
        if(listData.size()>0) {
            mAdapter = new TeacherAddedCoursesAdapter(getActivity(), listData, this);
            mRecyclerViewAddedSubjectList.setLayoutManager(manager);
            mRecyclerViewAddedSubjectList.setAdapter(mAdapter);
        }
        else{
            Toast.makeText(getActivity(),"No Courses Added Yet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_layout,
                TakeOrViewAttendanceTabbedFragment.newInstance(listData.get(position))).commit();
    }
}
