package com.example.schedulerv8.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.schedulerv8.Adapters.TermAdapter;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TermsList extends AppCompatActivity {
    private Repository repository;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        repository = new Repository(getApplication());
        updateTermList(userId);

        FloatingActionButton addTermFAB = findViewById(R.id.termListAddTermFAB);
        addTermFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, TermDetails.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent backIntent = new Intent(this, MainActivity.class);
                backIntent.putExtra("userId", userId);
                startActivity(backIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        updateTermList(userId);
    }

    private void updateTermList(String userId) {
        RecyclerView termListRV = findViewById(R.id.termsListAllTermsRV);
        final TermAdapter termAdapter = new TermAdapter(this);
        termListRV.setAdapter(termAdapter);
        termListRV.setLayoutManager(new LinearLayoutManager(this));
        Log.d("TermsListDebug", "UserId passed: " + userId);

//        repository.getAllTerms().observe(this, allTerms -> {
//            Log.d("TermsListDebug", "All Terms Count: " + allTerms.size());
//            Log.d("TermsListDebug", "Setting terms to adapter, count: " + allTerms.size());
//            termAdapter.setTerms(allTerms);
//        });
        if ("Adm!n".equals(userId)) {
            repository.getAllTerms().observe(this, allTerms -> {
                Log.d("TermsListDebug", "All terms count: " + allTerms.size());
                termAdapter.setTerms(allTerms);
            });
        } else {
            repository.getTermsCreatedByUser(userId).observe(this, userTerms -> {
                Log.d("TermsListDebug", "User terms count: " + userTerms.size());
                termAdapter.setTerms(userTerms);
            });
        }
    }

}
