package tayler.ut.attendencemanagmentsystem.commonui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.activity.LoginOptionActivity;
import tayler.ut.attendencemanagmentsystem.model.teacher.TeacherData;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView mTeacherName, mTeacherEmail, mTeacherNumber, mTeacherSubjects, mLogout;
    ImageView mImageProfile;
    TeacherData mTeacherData;
    View mSujectViewUnderline;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTeacherName = (TextView) view.findViewById(R.id.mUserName);
        mTeacherEmail = (TextView) view.findViewById(R.id.mUserEmail);
        mTeacherNumber = (TextView) view.findViewById(R.id.mUserNumber);
        mTeacherSubjects = (TextView) view.findViewById(R.id.mTeacherSubject);
        mSujectViewUnderline = (View) view.findViewById(R.id.mSujectViewUnderline);
        mLogout = view.findViewById(R.id.mLogout);


        mTeacherData = FirebaseUtility.getTeacherProfileData();
        if (mTeacherData == null) {
            return;
        }

        mTeacherName.setText(mTeacherData.getName());
        mTeacherEmail.setText(mTeacherData.getEmailId());
        mTeacherNumber.setText(mTeacherData.getMobileNumber());
        if (TextUtils.isEmpty(mTeacherData.getSubjects())) {
            mTeacherSubjects.setVisibility(View.VISIBLE);
            mSujectViewUnderline.setVisibility(View.VISIBLE);
            mTeacherSubjects.setText(mTeacherData.getSubjects());
        } else {
            mTeacherSubjects.setVisibility(View.GONE);
            mSujectViewUnderline.setVisibility(View.GONE);
        }


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Signout");
        builder.setMessage("Are you sure you want to signout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), LoginOptionActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

}

