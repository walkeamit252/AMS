package tayler.ut.attendencemanagmentsystem.fragment.teacher;


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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tayler.ut.attendencemanagmentsystem.ApplicationContext;
import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.AttendenceListAdapter;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;

public class ViewAttendenceFragment extends Fragment implements AttendenceListAdapter.ItemClickListener {

    private AttendenceListAdapter mListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private DatabaseReference mDatabaseRefrence;
    private FirebaseDatabase database;
    private ArrayList<StudentListModel> attendenceListModels = new ArrayList<>();;

    private ProgressDialog progressDialog;
    private ApplicationContext mApplicationContext;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_attendence_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initview(view);
        fetchAttendenceList();
    }

    private void fetchAttendenceList() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching data..., Please Wait");
        progressDialog.show();

        mDatabaseRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Map<String, Object> objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        StudentListModel match = new StudentListModel();
                        match.setStudentName((String) mapObj.get("name"));
                        match.setStudentNumber((String) mapObj.get("number"));
                        attendenceListModels.add(match);
                    }
                }
                progressDialog.dismiss();
                mListAdapter.updateData(attendenceListModels);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                progressDialog.dismiss();
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void initview(View view) {

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


        mListAdapter = new AttendenceListAdapter(getActivity(), attendenceListModels,"ViewAttendence");
        recyclerView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        mListAdapter.setClickListener(this);
    }

    @Override

    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + mListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }
}