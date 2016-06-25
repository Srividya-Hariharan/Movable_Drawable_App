package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private ImageView imageView;

    int left, right, top, bottom;

    String shape;

    int size= 100;

    int height, width;

    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.activity_main);

        // Get display items for later interaction
        Button speakButton = (Button) findViewById(R.id.button);

        imageView= (ImageView)findViewById(R.id.imageView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
        }

        else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }

    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            startVoiceRecognitionActivity();
        }
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recognising speech");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            if(!matches.isEmpty()){
                if (matches.get(0).contains("square")) {
                    shape= "square";
                    Drawable vDrawable = new Drawable(shape, size);
                    imageView.setImageDrawable(vDrawable);
                }

                else if (matches.get(0).contains("rectangle")) {
                    shape= "rectangle";
                    Drawable vDrawable = new Drawable(shape, size);
                    imageView.setImageDrawable(vDrawable);
                }

                else if (matches.get(0).contains("circle")) {
                    shape= "circle";
                    Drawable vDrawable = new Drawable(shape, size);
                    imageView.setImageDrawable(vDrawable);
                }

                else if (matches.get(0).contains("down")) {
                    left = imageView.getLeft();
                    top = imageView.getTop();
                    bottom = imageView.getBottom();
                    if((bottom+10) < (height-20)) {
                        imageView.setLeft(left);
                        imageView.setTop(top + 10);
                        imageView.setBottom(bottom + 10);
                    }
                    imageView.invalidate();
                    }

                else if (matches.get(0).contains("up")) {
                    left = imageView.getLeft();
                    top = imageView.getTop();
                    bottom = imageView.getBottom();
                    if ((top-10) > 0) {
                        imageView.setLeft(left);
                        imageView.setTop(top - 10);
                        imageView.setBottom(bottom - 10);
                    }
                    imageView.invalidate();
                }

                else if (matches.get(0).contains("right")) {
                    left = imageView.getLeft();
                    right = imageView.getRight();
                    top = imageView.getTop();
                    if((right+10) < width) {
                        imageView.setLeft(left + 10);
                        imageView.setRight(right + 10);
                        imageView.setTop(top);
                    }
                    imageView.invalidate();
                }

                else if (matches.get(0).contains("left")) {
                    left = imageView.getLeft();
                    right = imageView.getRight();
                    top = imageView.getTop();
                    if((left-10) > 0) {
                        imageView.setLeft(left - 10);
                        imageView.setRight(right - 10);
                        imageView.setTop(top);
                    }
                    imageView.invalidate();
                }

                else if (matches.get(0).contains("small")) {
                    if( (size - 10) > 10) {
                        size= size - 10;
                    }
                    Drawable vDrawable = new Drawable(shape, size);
                    imageView.setImageDrawable(vDrawable);
                }

                else if (matches.get(0).contains("big")) {
                    if( (size + 10) < width) {
                        size= size + 10;
                    }
                    Drawable vDrawable = new Drawable(shape, size);
                    imageView.setImageDrawable(vDrawable);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
