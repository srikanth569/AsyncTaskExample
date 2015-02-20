package com.srikanth.asynctaskexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ProgressBar mProgressBar;
    Button mStart;
    Button mCancel;
    boolean isProgressBarLoading = false;
    int progressbarStatus = 0;
    ProgressBarAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mStart = (Button) findViewById(R.id.start);
        mCancel = (Button) findViewById(R.id.cancel);
        mStart.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start) {
            if (!isProgressBarLoading) {
                isProgressBarLoading = true;
                asyncTask = new ProgressBarAsyncTask();
                asyncTask.execute();
            } else {
                Toast.makeText(this, "Progressbar already loading", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.cancel) {
            if (isProgressBarLoading) {
                isProgressBarLoading = false;
                mProgressBar.setProgress(0);
                asyncTask.cancel(true);
                progressbarStatus = 0;
            } else {
                Toast.makeText(this, "Progressbar is not loading, cannot cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void doWork() {
        try {
            Thread.sleep(400);
            progressbarStatus++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ProgressBarAsyncTask extends AsyncTask<Void, Integer, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            while (progressbarStatus < 100 && !isCancelled()) {
                doWork();
                if (isProgressBarLoading) {
                    mProgressBar.setProgress(progressbarStatus);
                }
            }
            return null;
        }
    }
}
