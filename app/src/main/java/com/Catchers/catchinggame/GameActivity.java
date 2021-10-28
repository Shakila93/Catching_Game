package com.Catchers.catchinggame;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.Catchers.catchinggame.databinding.ActivityGameBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    //game variables
    private int score = 0;
    private TextView scoreText;
    private TextView timerText;
    private View GameOverPanel;
    private Button ReturnButton;


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.getMainLooper());
    private View mContentView;
    private FrameLayout Layout;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

        }
    };
    //handles when the action bar should show or not
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

//list of clickable spirits
    private List<SpiritTarget> targets = new ArrayList<>();
    private static final int FRAME_DELAY = 16; //# of milisec between processing
    private int time = 10000;  //# of milisec before the game is over
    private final Handler mLoopHandler = new Handler(Looper.getMainLooper());  //what handles our main loop, allows to consistenly run on our specified time

    //the runnable that performs our main loops. every frame delay mili sec
    private final Runnable mMainLoop = new Runnable() {
        @Override
        public void run() {
            time -= FRAME_DELAY;
            if (time < 0){
                for(int i = targets.size()-1; i >= 0; i--){
                    SpiritTarget target = targets.get(i);
                    targets.remove(i);
                    getSupportFragmentManager().beginTransaction().remove(target).commit();

                }
                //doesnt show negetive number
                timerText.setText("Time 00:00");
                GameOverPanel.setVisibility(View.VISIBLE);
                //connecting to the user service on the ddevice and geting user name
                //UserManager userM = (UserManager)getApplicationContext().getSystemService(Context.USER_SERVICE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                //user.put("name", userM.getUserName());
                user.put("name","Test");
                user.put("score", score);
                user.put("timestamp", Timestamp.now());

                // Add a new document with a generated ID
                db.collection("Leaderboard")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("CatchingGame", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("CatchingGame", "Error adding document", e);
                            }
                        });
                return; //game over finishes
            }
            //game play -116
            timerText.setText(String.format(Locale.US, "Time 00:%02.0f", ((float)time)/1000));
            for(SpiritTarget target : targets) {
                target.OnUpdate(0.016f);
            }

            mLoopHandler.postDelayed(mMainLoop, FRAME_DELAY);  //tells the loop handler to run in frame delay time
        }
    };

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
//    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            switch (motionEvent.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    if (AUTO_HIDE) {
//                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    view.performClick();
//                    break;
//                default:
//                    break;
//            }
//            return false;
//        }
//    };
    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;
        scoreText = findViewById(R.id.ScoreText);
        timerText = findViewById(R.id.Timer);
        // Set up the user interaction to manually show or hide the system UI.
        GameOverPanel = findViewById(R.id.GamePanel);
        ReturnButton = findViewById(R.id.ReturnButton);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        Layout = findViewById(R.id.GameActivity);
        //Layout.addView(SpiritTarget.newInstance().getView());

        //number of spirit
       for(int i = 0; i < 10; i++) SpawnSpirit();

        mLoopHandler.postDelayed(mMainLoop, FRAME_DELAY);
        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void SpawnSpirit()
    {
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        SpiritTarget ST = SpiritTarget.newInstance();
        FT.add(R.id.GameActivity, ST, "Spirit");
        FT.commit();
        Random random = new Random();

        targets.add(ST);
        ST.SetVelocity((random.nextInt(300) +150) *(random.nextInt(100)> 50?1:-1),
                (random.nextInt(300) +150)*(random.nextInt(100)> 50?1:-1));
        DisplayMetrics metrics= Resources.getSystem().getDisplayMetrics();
        ST.SetPosition(random.nextInt(metrics.widthPixels),random.nextInt(metrics.heightPixels));
        ST.setOnClick(new Runnable() {
            @Override
            public void run() {
                //targets.remove(ST);
                ST.getView().setVisibility(View.INVISIBLE);
                score ++;
                scoreText.setText(String.format(Locale.US,"Score: %d", score));
                //remove from the screen
                //getSupportFragmentManager().beginTransaction().remove(ST).commit();
                mLoopHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ST.SetPosition(random.nextInt(metrics.widthPixels),random.nextInt(metrics.heightPixels));
                        ST.SetVelocity((random.nextInt(300) +150) *(random.nextInt(100)> 50?1:-1),
                                (random.nextInt(300) +150)*(random.nextInt(100)> 50?1:-1));
                        ST.getView().setVisibility(View.VISIBLE);
                    }

                },1000);
            }
        });


    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @SuppressLint("NewApi")
    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void show() {
        // Show the system bar
        mContentView.getWindowInsetsController().show(
                WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}