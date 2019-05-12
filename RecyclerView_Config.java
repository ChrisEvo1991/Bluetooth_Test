package com.example.bluetooth_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private ReportAdapter mReportAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Report> reports, List<String> keys){
        mContext = context;
        mReportAdapter = new ReportAdapter(reports, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mReportAdapter);
    }

    class ReportItemView extends RecyclerView.ViewHolder{
        private TextView mGoals;
        private TextView mPoints;
        private TextView mScore;
        private TextView mSteps;

        private String key;

        public ReportItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.report_list_item, parent,false));

            mGoals = (TextView) itemView.findViewById(R.id.goals_txtView);
            mPoints = (TextView) itemView.findViewById(R.id.point_txtView);
            mScore = (TextView) itemView.findViewById(R.id.score_txtView);
            mSteps = (TextView) itemView.findViewById(R.id.steps_txtView);

        }
        public void bind(Report report, String key){
            mGoals.setText(report.getGoals());
            mPoints.setText(report.getPoints());
            mScore.setText(report.getScore());
            mSteps.setText(report.getSteps());
            this.key = key;
        }
    }
    class ReportAdapter extends RecyclerView.Adapter<ReportItemView>{
        private List<Report> mReportList;
        private List<String> mKeys;

        public ReportAdapter(List<Report> mReportList, List<String> mKeys) {
            this.mReportList = mReportList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ReportItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ReportItemView(parent);
        }

        @Override
        public void onBindViewHolder(ReportItemView holder, int position) {
            holder.bind(mReportList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mReportList.size();
        }
    }
}
