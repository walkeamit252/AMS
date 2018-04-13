package tayler.ut.attendencemanagmentsystem.commonui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.adapter.CustomDropDownAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAttendanceDetailFragment extends Fragment {

    CustomDropDownAdapter mAdapter;
    Spinner mSpinner;
    List<SpinnerModel> model;


    public ShowAttendanceDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_attendance_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpinner = (Spinner) view.findViewById(R.id.mSpinnerItem);

        String[] totlaMonth = getResources().getStringArray(R.array.total_months);

        model = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            SpinnerModel spinner = new SpinnerModel();
            spinner.setMonthName(totlaMonth[i]);
            model.add(spinner);
        }

        mAdapter = new CustomDropDownAdapter(getActivity(), model);
        mSpinner.setAdapter(mAdapter);
    }

}

