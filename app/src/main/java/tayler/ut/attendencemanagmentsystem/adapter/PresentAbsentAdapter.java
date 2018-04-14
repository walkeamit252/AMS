package tayler.ut.attendencemanagmentsystem.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;
import tayler.ut.attendencemanagmentsystem.utils.Constants;

public class PresentAbsentAdapter extends RecyclerView.Adapter<PresentAbsentAdapter.ViewHolder> {

    private List<AttendanceData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public PresentAbsentAdapter(Context context,List<AttendanceData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_layout_present_absent, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final AttendanceData attendanceData = mData.get(position);




        holder.txt_date.setText(mData.get(position).getAttandanceDate());


        if(attendanceData.isPresent()){
            holder.txt_PresentOrAbsent.setText("Present");
            holder.txt_PresentOrAbsent.setTextColor(ContextCompat.getColor(holder.txt_PresentOrAbsent.getContext(),
                    R.color.menu_user_type_text_color));
        }
        else{
            holder.txt_PresentOrAbsent.setText("Absent");
            holder.txt_PresentOrAbsent.setTextColor(ContextCompat.getColor(holder.txt_PresentOrAbsent.getContext(),
                    R.color.button_color));
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder   {

        private TextView txt_PresentOrAbsent;
        private TextView txt_date;

        ViewHolder(View itemView) {
            super(itemView);
            txt_PresentOrAbsent = itemView.findViewById(R.id.txt_PresentOrAbsent);
            txt_date = itemView.findViewById(R.id.txt_date);
        }
    }


}