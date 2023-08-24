package com.example.schedulerv8.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.UI.CourseDetails;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;

//    private List<Course> mAssociatedCourses;

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseTermId;
        private final TextView courseName;
        private final TextView courseType;
        private final TextView courseStart;
        private final TextView courseEnd;
        private final TextView courseStatus;
        private CourseViewHolder(View itemView){
            super(itemView);

            courseTermId = itemView.findViewById(R.id.courseListTermId);
            courseName = itemView.findViewById(R.id.courseListCourseName);
            courseType = itemView.findViewById(R.id.courseListCourseType);
            courseStart = itemView.findViewById(R.id.courseListCourseStart);
            courseEnd = itemView.findViewById(R.id.courseListCourseEnd);
            courseStatus = itemView.findViewById(R.id.courseListCourseStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("course_term_id", current.getTermId());
                    intent.putExtra("course_id", current.getCourseId());
                    intent.putExtra("course_title", current.getCourseTitle());
                    intent.putExtra("course_start_date", current.getCourseStartDate());
                    intent.putExtra("course_end_date", current.getCourseEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructor_name", current.getCourseInstructorName());
                    intent.putExtra("instructor_phone", current.getCourseInstructorPhone());
                    intent.putExtra("instructor_email", current.getCourseInstructorEmail());
                    intent.putExtra("course_note", current.getCourseNote());
                    context.startActivity(intent);
                }
            });
        }
    }
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;

    }
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course currentCourse = mCourses.get(position);

            String courseTermId = String.valueOf(currentCourse.getTermId());
            String courseName = currentCourse.getCourseTitle();
            String courseStart = currentCourse.getCourseStartDate();
            String courseEnd = currentCourse.getCourseEndDate();
            String courseStatus = currentCourse.getStatus();

            holder.courseTermId.setText(courseTermId);
            holder.courseName.setText(courseName);
            holder.courseStart.setText(courseStart);
            holder.courseEnd.setText(courseEnd);
            holder.courseStatus.setText(courseStatus);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("course_term_id", currentCourse.getTermId());
                    intent.putExtra("course_id", currentCourse.getCourseId());
                    intent.putExtra("course_title", currentCourse.getCourseTitle());
                    intent.putExtra("course_start_date", currentCourse.getCourseStartDate());
                    intent.putExtra("course_end_date", currentCourse.getCourseEndDate());
                    intent.putExtra("instructor_name", currentCourse.getCourseInstructorName());
                    intent.putExtra("instructor_phone", currentCourse.getCourseInstructorPhone());
                    intent.putExtra("instructor_email", currentCourse.getCourseInstructorEmail());
                    intent.putExtra("course_note", currentCourse.getCourseNote());
                    intent.putExtra("status", currentCourse.getStatus());

                    context.startActivity(intent);
                }
            });
        } else {
            holder.courseTermId.setText("N/A");
            holder.courseName.setText("No Courses");
            holder.courseStart.setText("N/A");
            holder.courseEnd.setText("N/A");
            holder.courseStatus.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        if(mCourses !=null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }



    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }


//
//    public void setAssociatedCourses(List<Course> associatedCourses) {
//        mAssociatedCourses = associatedCourses;
//        notifyDataSetChanged();
//    }
}
