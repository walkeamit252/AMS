package tayler.ut.attendencemanagmentsystem.student.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.student.adapter.StudentCourseYearWiseListAdapter;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

public class ViewStudentSyllabusListFragment extends Fragment implements StudentCourseYearWiseListAdapter.ItemClickListener {

    RecyclerView mRecyclerViewStudenSubjectList;
    StudentCourseYearWiseListAdapter mAdapter;
    RecyclerView.LayoutManager manager;
    ArrayList<CourseData> data;
    private StudentData mStudentData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_syllabus, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewStudenSubjectList = (RecyclerView) view.findViewById(R.id.mRecyclerViewStudenSubjectList);
        manager = new LinearLayoutManager(getActivity());
        data = new ArrayList<>();

        //    mAdapter=new StudentCourseYearWiseListAdapter(getActivity(),data,this);
        mRecyclerViewStudenSubjectList.setLayoutManager(manager);


        mStudentData = FirebaseUtility.getStudentProfileData();
        if (mStudentData == null) {
            return;
        }
        data.clear();
        data = (ArrayList<CourseData>) FirebaseUtility.getCourses(mStudentData.getYear());
        mAdapter = new StudentCourseYearWiseListAdapter(getActivity(), data, this);
        mRecyclerViewStudenSubjectList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onUploadFileClick(CourseData courseData) {
        if (TextUtils.isEmpty(courseData.getSyllabusFilePath()) ||
                (!TextUtils.isEmpty(courseData.getSyllabusFilePath()) &&
                        courseData.getSyllabusFilePath().equalsIgnoreCase("-"))) {
            Toast.makeText(getActivity(), "Syllabus Not Uploaded Yet", Toast.LENGTH_SHORT).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseData.getSyllabusFilePath()));
            startActivity(browserIntent);

        }

    }
}