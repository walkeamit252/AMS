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

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;
import tayler.ut.attendencemanagmentsystem.model.attendance.AttendanceData;
import tayler.ut.attendencemanagmentsystem.model.student.StudentData;

public class AttendenceListAdapter extends RecyclerView.Adapter<AttendenceListAdapter.ViewHolder> {

    private ArrayList<AttendanceData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String activityName;

    // data is passed into the constructor
    public AttendenceListAdapter(Context context, ArrayList<AttendanceData> data, String activityName) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.activityName = activityName;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_layout_attendance, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AttendanceData attendanceData = mData.get(position);
        holder.mTextViewStudentName.setText("Name : " + "" + mData.get(position).getStudentName());
        holder.textViewDate.setText("Date : " + "" + mData.get(position).getAttandanceDate());
        holder.llPresentAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null){
                    mClickListener.onAbsentPresentClick(position);
                }
            }
        });

        if(attendanceData.isPresent()){
            holder.mSwitch.setChecked(true);
            holder.textViewPresentOrAbsent.setText("Present");
            holder.textViewPresentOrAbsent.setTextColor(ContextCompat.getColor(holder.textViewPresentOrAbsent.getContext(),
                    R.color.menu_user_type_text_color));
        }
        else{
            holder.mSwitch.setChecked(false);
            holder.textViewPresentOrAbsent.setText("Absent");
            holder.textViewPresentOrAbsent.setTextColor(ContextCompat.getColor(holder.textViewPresentOrAbsent.getContext(),
                    R.color.button_color));
        }

        holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mClickListener!=null){
                    mClickListener.onAbsentPresentClick(position);
                }
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewStudentName;
        private TextView textViewPresentOrAbsent;
        private TextView textViewDate;
        private LinearLayout llPresentAbsent;
        private Switch mSwitch;

        ViewHolder(View itemView) {
            super(itemView);
            mTextViewStudentName = itemView.findViewById(R.id.mTextViewStudentName);
            textViewPresentOrAbsent = itemView.findViewById(R.id.textViewPresentOrAbsent);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            llPresentAbsent = itemView.findViewById(R.id.llPresentAbsent);
            mSwitch = itemView.findViewById(R.id.mSwitchAttendance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onAbsentPresentClick(int position);
    }

    public void updateData(ArrayList<AttendanceData> data) {
        this.mData = data;
        notifyDataSetChanged();

    }
}