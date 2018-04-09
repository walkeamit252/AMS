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

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.teacher.adapter.TeacherAddedCoursesAdapter;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

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
        List<CourseData> listTeachersCourse = FirebaseUtility.getTeachersAddedCourses();
        if(listTeachersCourse!=null && listTeachersCourse.size()>0){
            listData.addAll(listTeachersCourse);
        }
        mAdapter=new TeacherAddedCoursesAdapter(getActivity(),listData,this);
        mRecyclerViewAddedSubjectList.setLayoutManager(manager);
        mRecyclerViewAddedSubjectList.setAdapter(mAdapter);
    }

    private void setCourseDatafromServer(){

    }

    @Override
    public void onItemClick(View view, int position) {
        TakeAttendenceFragment fragment=new TakeAttendenceFragment();
        Bundle bundle =new Bundle();
        bundle.putParcelable(Constants.COURSE_DATA,listData.get(position));
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}
