package com.example.schedulerv8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.R;
import com.example.schedulerv8.UI.TermDetails;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {


        private final TextView termName;
        private final TextView termStart;
        private final TextView termEnd;
        private TermViewHolder(View itemview){
            super(itemview);


            termName = itemview.findViewById(R.id.termListItemTermName);
            termStart = itemview.findViewById(R.id.termListItemTermStart);
            termEnd = itemview.findViewById(R.id.termListItemTermEnd);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("term_id", current.getTermId());
                    intent.putExtra("term_title", current.getTermTitle());
                    intent.putExtra("term_start_date", current.getStartDate());
                    intent.putExtra("term_end_date", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current = mTerms.get(position);

            String termIdTestString = String.valueOf(current.getTermId());

            String termName = current.getTermTitle();
            String termStart = current.getStartDate();
            String termEnd = current.getEndDate();


            holder.termName.setText(termName);
            holder.termStart.setText(termStart);
            holder.termEnd.setText(termEnd);
        } else {
            holder.termName.setText(R.string.no_terms_item);
            holder.termStart.setText(R.string.notAvail);
            holder.termEnd.setText(R.string.notAvail);
        }
    }

    @Override
    public int getItemCount() {
        return mTerms != null ? mTerms.size() : 0;
    }

    public void setTerms(List<Term> terms) {
        Log.d("TermAdapterDebug", "Inside setTerms, count: " + terms.size());
        mTerms = terms;
        notifyDataSetChanged();
    }
}
