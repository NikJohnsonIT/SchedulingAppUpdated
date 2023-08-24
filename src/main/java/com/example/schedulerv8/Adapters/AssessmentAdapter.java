package com.example.schedulerv8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.R;
import com.example.schedulerv8.UI.AssessmentDetails;
import com.example.schedulerv8.UI.CourseDetails;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> mAllAssessments;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentStart;
        private final TextView assessmentEnd;
        private final TextView assesmentType;
        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentName =itemView.findViewById(R.id.assessListAssessName);
            assessmentStart =itemView.findViewById(R.id.assessListAssessStart);
            assessmentEnd =itemView.findViewById(R.id.assessListAssessEnd);
            assesmentType = itemView.findViewById(R.id.assessListAssessType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAllAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessment_id", current.getAssessmentId());
                    intent.putExtra("assessment_course_id", current.getCourseId());
                    intent.putExtra("assessment_title", current.getAssessmentTitle());
                    intent.putExtra("assessment_start_date", current.getAssessmentStartDate());
                    intent.putExtra("assessment_end_date", current.getAssessmentEndDate());
                    intent.putExtra("assessment_type", current.getAssessmentType());
                    context.startActivity(intent);
                }
            });
        }
    }
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;

    }
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAllAssessments!=null){
            Assessment currentAssessment = mAllAssessments.get(position);
            String assessName = currentAssessment.getAssessmentTitle();
            String assessStart = currentAssessment.getAssessmentStartDate();
            String assessEnd = currentAssessment.getAssessmentEndDate();
            String assessType = currentAssessment.getAssessmentType();

            holder.assessmentName.setText(assessName);
            holder.assessmentStart.setText(assessStart);
            holder.assessmentEnd.setText(assessEnd);
            holder.assesmentType.setText(assessType);
        } else {
            holder.assessmentName.setText("No Assessments");
            holder.assessmentStart.setText("N/A");
            holder.assessmentEnd.setText("N/A");
            holder.assesmentType.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        if(mAllAssessments != null) {
            return mAllAssessments.size();
        } else {
            return 0;
        }
    }
    public void setAssessments(List<Assessment> assessments) {
        mAllAssessments = assessments;
        notifyDataSetChanged();
    }

}

