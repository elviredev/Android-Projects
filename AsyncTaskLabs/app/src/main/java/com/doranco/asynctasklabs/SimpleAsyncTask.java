package com.doranco.asynctasklabs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Void, String> {

    private WeakReference<TextView> mTextView;
    private Context mContext;
    // constructor
    public SimpleAsyncTask(Context context, TextView tv) {
        mContext = context;
        mTextView = new WeakReference<>(tv);

    }

    // doInBackground()
    @Override
    protected String doInBackground(Void... voids) {

        Random rdm = new Random();
        int n = rdm.nextInt(11);
        int sec = n * 500;

        try {
            Thread.sleep(sec);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return mContext.getString(R.string.reveil2, String.valueOf(sec/1000));
        //return mContext.getString(R.string.reveil) + " " + sec / 1000 + " " + mContext.getString(R.string.secondes);

    }

    protected void onPostExecute(String result){
        TextView tv = mTextView.get();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextColor(mContext.getColor(R.color.red));
        }
        tv.setText(result);

    }
}
