package tayler.ut.attendencemanagmentsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.StudentListModel;

public class AttendenceListAdapter extends RecyclerView.Adapter<AttendenceListAdapter.ViewHolder> {

    private ArrayList<StudentListModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String activityName;

    // data is passed into the constructor
    public AttendenceListAdapter(Context context, ArrayList<StudentListModel> data, String activityName) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mtxtName.setText("Name: " + "" + mData.get(position).getStudentName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mtxtName;
        private Switch mSwitch;

        ViewHolder(View itemView) {
            super(itemView);
            mtxtName = itemView.findViewById(R.id.mTextViewStudentName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getStudentName();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void updateData(ArrayList<StudentListModel> data) {
        this.mData = data;
        notifyDataSetChanged();

    }
}