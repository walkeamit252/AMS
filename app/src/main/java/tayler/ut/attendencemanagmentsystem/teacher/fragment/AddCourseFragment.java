package tayler.ut.attendencemanagmentsystem.teacher.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.CourseListAdapter;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

import static android.app.Activity.RESULT_OK;


public class AddCourseFragment extends Fragment implements View.OnClickListener,CourseListAdapter.ItemClickListener{

    RecyclerView mRecyclerViewAddSubject;
    TextView tvFirst,tvSecond,tvThird,tvFourth;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    CourseListAdapter mCourseListAdapter;
    ArrayList<CourseData> listData=new ArrayList<>();
    RecyclerView.LayoutManager manager;
    CourseData courseDataAfterUpload;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_syllabus, container, false);
        //getting firebase objects
        progressDialog = new ProgressDialog(getActivity());
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        initView(view);
        setListner();
        return view;
    }

    private void initView(View view) {
        mRecyclerViewAddSubject=view.findViewById(R.id.mRecyclerViewAddSubject);
        tvFirst=view.findViewById(R.id.tvFirst);
        tvSecond=view.findViewById(R.id.tvSecond);
        tvThird=view.findViewById(R.id.tvThird);
        tvFourth=view.findViewById(R.id.tvFourth);
        setRecyclerView();
        setFirstYearSubject();




    }

    private void setRecyclerView() {
        mCourseListAdapter=new CourseListAdapter(getActivity(),listData,this);
        manager=new LinearLayoutManager(getActivity());
        mRecyclerViewAddSubject.setLayoutManager(manager);
        mRecyclerViewAddSubject.setAdapter(mCourseListAdapter);
    }

    private void setListner() {
        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
        tvThird.setOnClickListener(this);
        tvFourth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvFirst:
                setFirstYearSubject();
                break;
            case R.id.tvSecond:
                setSecondYearSubject();
                break;
            case R.id.tvThird:
                setThirdYearSubject();
                break;
            case R.id.tvFourth:
                setFourthYearSubject();
                break;
        }
    }






    private void getPDF() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
            return;
        }

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), Constants.PICK_PDF_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                uploadFile(data.getData());
            } else {
                Toast.makeText(getActivity(), "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.cancel();
                        String url = taskSnapshot.getDownloadUrl().toString();
                        if(courseDataAfterUpload!=null ) {
                            courseDataAfterUpload.setSyllabusFilePath(url);
                            FirebaseUtility.updateCourse(courseDataAfterUpload);
                            FirebaseUtility.updateTeacherProfileData(courseDataAfterUpload.getCourseName());
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.cancel();
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onUploadFileClick(CourseData courseData) {
        this.courseDataAfterUpload = courseData;
        getPDF();
    }

    private void setFirstYearSubject()
    {
        tvFirst.setSelected(true);
        tvSecond.setSelected(false);
        tvThird.setSelected(false);
        tvFourth.setSelected(false);
        listData.clear();
        listData.addAll(FirebaseUtility.getCourses(FirebaseUtility.FirebaseConstants.FIRSTYEAR));
     //   listData= (ArrayList<CourseData>) FirebaseUtility.getCourses(FirebaseUtility.FirebaseConstants.FIRSTYEAR);
        mCourseListAdapter.notifyDataSetChanged();
    }

    private void setSecondYearSubject()
    {
        tvFirst.setSelected(false);
        tvSecond.setSelected(true);
        tvThird.setSelected(false);
        tvFourth.setSelected(false);
        listData.clear();
        listData.addAll(FirebaseUtility.getCourses(FirebaseUtility.FirebaseConstants.SECOND_YEAR));
        mCourseListAdapter.notifyDataSetChanged();
    }

    private void setThirdYearSubject()
    {
        tvFirst.setSelected(false);
        tvSecond.setSelected(false);
        tvThird.setSelected(true);
        tvFourth.setSelected(false);
        listData.clear();
        listData.addAll(FirebaseUtility.getCourses(FirebaseUtility.FirebaseConstants.THIRD_YEAR));
        mCourseListAdapter.notifyDataSetChanged();
    }

    private void setFourthYearSubject()
    {
        tvFirst.setSelected(false);
        tvSecond.setSelected(false);
        tvThird.setSelected(false);
        tvFourth.setSelected(true);
        listData.clear();
        listData.addAll(FirebaseUtility.getCourses(FirebaseUtility.FirebaseConstants.FOURTH_YEAR));
        mCourseListAdapter.notifyDataSetChanged();
    }
}