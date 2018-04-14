package tayler.ut.attendencemanagmentsystem.commonui.fragments;


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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.adapter.PresentAbsentAdapter;
import tayler.ut.attendencemanagmentsystem.commonui.adapter.CustomDropDownAdapter;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.teacher.activity.TeacherMenuActivity;
import tayler.ut.attendencemanagmentsystem.utils.Constants;
import tayler.ut.attendencemanagmentsystem.utils.DateUtils;
import tayler.ut.attendencemanagmentsystem.utils.FirebaseUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAttendanceDetailFragment extends Fragment implements FirebaseUtility.OnAttendanceActionListener{

    CustomDropDownAdapter mAdapter;
    Spinner mSpinner;
    List<SpinnerModel> listSpinnerModel;
    AttendanceData attendanceData;


    Button btn_get_attendance_data;
    TextView tvStudentName;
    TextView tvSubject;
    TextView tvMonthName;
    TextView tvYear;
    TextView txt_total_number_of_days_in_month;
    TextView txt_total_present;
    TextView txt_total_absent;
    TextView txt_attendance_percentage;
    private RecyclerView recyclerAttendanceDetails;

    String monthSelected;

    List<AttendanceData> studentAttendanceList = new ArrayList<>();
    PresentAbsentAdapter presentAbsentAdapter;

    FirebaseUtility.OnAttendanceActionListener onAttendanceActionListener;

    public ShowAttendanceDetailFragment() {
        // Required empty public constructor
    }

    public static ShowAttendanceDetailFragment newInstance(AttendanceData attendanceData) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.BundelKays.ATTENDANCE_DATA,attendanceData);
        ShowAttendanceDetailFragment fragment = new ShowAttendanceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_show_attendance_detail, container, false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            if(bundle.containsKey(Constants.BundelKays.ATTENDANCE_DATA)) {
                attendanceData = bundle.getParcelable(Constants.BundelKays.ATTENDANCE_DATA);
            }
        }

        onAttendanceActionListener = this;

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerAttendanceDetails = view.findViewById(R.id.recyclerAttendanceDetails);
        mSpinner = (Spinner) view.findViewById(R.id.mSpinnerItem);
        btn_get_attendance_data = (Button) view.findViewById(R.id.btn_get_attendance_data);
        tvStudentName  = (TextView) view.findViewById(R.id.tvStudentName);
        tvSubject  = (TextView) view.findViewById(R.id.tvSubject);
        tvMonthName  = (TextView) view.findViewById(R.id.tvMonthName);
        tvYear  = (TextView) view.findViewById(R.id.tvYear);
        txt_total_number_of_days_in_month  = (TextView) view.findViewById(R.id.txt_total_number_of_days_in_month);
        txt_total_present  = (TextView) view.findViewById(R.id.txt_total_present);
        txt_total_absent  = (TextView) view.findViewById(R.id.txt_total_absent);
        txt_attendance_percentage  = (TextView) view.findViewById(R.id.txt_attendance_percentage);

        setMonthSpinner();

        recyclerAttendanceDetails.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        presentAbsentAdapter = new PresentAbsentAdapter(getActivity(),studentAttendanceList);
        recyclerAttendanceDetails.setAdapter(presentAbsentAdapter);
        tvStudentName.setText("Student Name : "+attendanceData.getStudentName());
        tvSubject.setText("SUbject : "+attendanceData.getSubjectName());
        tvMonthName.setText("");
        tvYear.setText("Year : "+attendanceData.getYear());


        if(getActivity() instanceof TeacherMenuActivity){
            ((TeacherMenuActivity)getActivity()).setToolbarTitleFromFragment("Student Attendance Details");
        }

        btn_get_attendance_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(monthSelected)) {
                    Toast.makeText(getActivity(), "Cant View Future Months attendance", Toast.LENGTH_SHORT).show();
                } else {
                    List<AttendanceData> listData = FirebaseUtility.getAttendanceListById(attendanceData.getYear(),
                            attendanceData.getStudentId(), "", monthSelected, onAttendanceActionListener);
                }
            }
        });



    }

    private void setMonthSpinner(){
        final String[] totlaMonth = getResources().getStringArray(R.array.total_months);
        listSpinnerModel = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            SpinnerModel spinner = new SpinnerModel(totlaMonth[i],i+1);
            listSpinnerModel.add(spinner);
        }
        mAdapter = new CustomDropDownAdapter(getActivity(), listSpinnerModel);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSelected = listSpinnerModel.get(i).getMonthName();
                int monthNumber = listSpinnerModel.get(i).getMonthNumber();

                tvMonthName.setText(monthSelected);

                if (DateUtils.getCurrentJodaMonth() < monthNumber) {
                    monthSelected = "";
                    Toast.makeText(getActivity(), "Cant View Future Months attendance", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onSuccess(List<AttendanceData> list) {
        if(list!=null && list.size()>0){

            studentAttendanceList.clear();
            studentAttendanceList.addAll(list);
            presentAbsentAdapter.notifyDataSetChanged();

            int absentCount = 0;
            int presentCount = 0;
            int total = 0;
            for(AttendanceData attendanceData : studentAttendanceList){
                total++;
                if(attendanceData.isPresent()){
                    presentCount++;
                }
                else{
                    absentCount++;

                }
            }
            txt_total_absent.setText(""+absentCount);
            txt_total_present.setText(""+presentCount);
            txt_total_number_of_days_in_month.setText(""+total);
            if(presentCount!=0 && total!=0){
                float percentage = (presentCount/total)*100;
                txt_attendance_percentage.setText(""+percentage);
            }
        }
        else{
            Toast.makeText(getActivity(), "Details Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFail() {

    }
}

