package com.example.schedulerv8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.User;
import com.example.schedulerv8.Entities.UserDisplayModel;
import com.example.schedulerv8.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserDisplayModel> mUserData;
    private final Context context;
    private final LayoutInflater mInflater;
    private final Repository repository;
    public class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView reportsUserId;
        private final TextView reportsTermsCount;
        private final TextView reportsCoursesCount;

        private UserViewHolder(View itemview) {
            super(itemview);
            reportsUserId = itemview.findViewById(R.id.reportsListUserID);
            reportsTermsCount = itemview.findViewById(R.id.reportsListTermsCount);
            reportsCoursesCount = itemview.findViewById(R.id.reportsListCoursesCount);
        }
    }

    public UserAdapter(Context context, Repository repository){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.user_list_item,null, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        if(mUserData!=null){
            UserDisplayModel currentData = mUserData.get(position);

            String userId = currentData.getUserId();
            int termsCount = currentData.getTermsCount();
            int coursesCount = currentData.getCoursesCount();

            holder.reportsUserId.setText(userId);
            holder.reportsTermsCount.setText(String.valueOf(termsCount));
            holder.reportsCoursesCount.setText(String.valueOf(coursesCount));
        } else {
            holder.reportsUserId.setText("No Data");
            holder.reportsTermsCount.setText("N/A");
            holder.reportsCoursesCount.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        return mUserData != null ? mUserData.size() : 0;
    }


    public void setUserDisplayData(List<UserDisplayModel> userData){
        mUserData = userData;
        notifyDataSetChanged();
    }

}
