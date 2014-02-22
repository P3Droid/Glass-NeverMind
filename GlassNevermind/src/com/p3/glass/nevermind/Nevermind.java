package com.p3.glass.nevermind;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Nevermind extends Activity {
	private TextView mTextView;
	private ProgressBar mProgressBar;
	private TextToSpeech mSpeech;
	private boolean mSpoken = false;
	
	private long WaitTime = 500;
	private Handler mHandler = new Handler();
	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			
			if (!mSpoken) {
				// This is to avoid repetition
				mSpeech.speak("Canceling", TextToSpeech.QUEUE_FLUSH, null);
				mSpoken = true;
			}
	        
			mProgressBar.setVisibility(ProgressBar.GONE);

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					finish();
				}
			};
			Handler handler = new Handler();
			handler.postDelayed(runnable, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);	       
        mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // Do nothing.
            }
        });

		mTextView = (TextView)findViewById(R.id.textView);
		mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		mProgressBar.setVisibility(ProgressBar.VISIBLE);
		glasscancel();
	}
	
	@Override
    public void onDestroy() {
		mSpeech.shutdown();
        mSpeech = null;
        super.onDestroy();
	}
	
	
	private void glasscancel() {
		mTextView.setText("Returning to OK Glass");
		mHandler.postDelayed(mRunnable, WaitTime);
	}
}