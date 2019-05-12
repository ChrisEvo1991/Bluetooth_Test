package com.example.bluetooth_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class DataReports extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_reports);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_pr);
        new FirebaseDatabaseHelper().readReports(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Report> reports, List<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerView, DataReports.this, reports, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}
