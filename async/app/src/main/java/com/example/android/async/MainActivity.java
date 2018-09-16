package com.example.android.async;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "ThreadingAsyncTask";

    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private int mDelay = 500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);;
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Button button = (Button) findViewById(R.id.loadButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new LoadIconTask().execute(R.drawable.savewater);
            }
        });

        final Button otherButton = (Button) findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "I'm Working",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    class LoadIconTask extends AsyncTask<Integer, Integer, Bitmap> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(Integer... resId) {
            Bitmap tmp = BitmapFactory.decodeResource(getResources(), resId[0]);
            // simulating long-running operation
            for (int i = 1; i < 11; i++) {
                sleep();
                publishProgress(i * 10);
            }
            return tmp;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mImageView.setImageBitmap(result);
        }
        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}


