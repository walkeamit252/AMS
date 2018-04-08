package tayler.ut.attendencemanagmentsystem.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.model.course.CourseData;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private ArrayList<CourseData> listData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public CourseListAdapter(Context context, ArrayList<CourseData> data,ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.listData = data;
        this.mClickListener=mClickListener;
        this.context=context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.itemview_course, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CourseData courseData = listData.get(position);
        if(courseData!=null){
            holder.tvSubjectName.setText(courseData.getCourseName());
            holder.tvYear.setText(courseData.getCourseYear());
            if(TextUtils.isEmpty(courseData.getSyllabusFilePath()) ||
                    (!TextUtils.isEmpty(courseData.getSyllabusFilePath()) &&
                            courseData.getSyllabusFilePath().equalsIgnoreCase("-"))) {
                holder.textViewUpload.setText("Upload File");

                holder.textViewUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          mClickListener.onUploadFileClick(listData.get(position));
                    }
                });
                holder.textViewUpload.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            }
            else{
                holder.textViewUpload.setText("File Uploaded");
                holder.textViewUpload.setTextColor(ContextCompat.getColor(context, R.color.button_color_pressed));
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return listData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView tvSubjectName;
        private AppCompatTextView tvYear;
        private AppCompatTextView textViewUpload;

        ViewHolder(View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvYear = itemView.findViewById(R.id.tvYear);
            textViewUpload = itemView.findViewById(R.id.textViewUpload);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onUploadFileClick(CourseData courseData);
    }
}