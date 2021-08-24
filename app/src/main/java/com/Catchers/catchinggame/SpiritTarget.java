package com.Catchers.catchinggame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpiritTarget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpiritTarget extends Fragment {

    private float X;
    private float Y;

    private float xVelocity;
    private float yVelocity;

    private ImageView img;
    private DisplayMetrics metrics;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SpiritTarget.
     */
    public static SpiritTarget newInstance() {
        SpiritTarget fragment = new SpiritTarget();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_spirit_target, container, false);

        img = root.findViewById(R.id.Target);
        metrics = Resources.getSystem().getDisplayMetrics();

        return root;
    }

    public void SetPosition(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void SetVelocity(int x, int y) {
        this.xVelocity = x;
        this.yVelocity = y;
    }

    public void OnUpdate(float deltaTime) {
        this.X = this.X + (this.xVelocity * deltaTime);
        this.Y = this.Y + (this.yVelocity * deltaTime);

        // Detect Horizontal Bounce
        if (this.X <= 0) {
            this.xVelocity = Math.abs(this.xVelocity);
            this.X = 0;
        } else if (this.X >= metrics.widthPixels) {
            this.xVelocity = -Math.abs(this.xVelocity);
            this.X = metrics.widthPixels;
        }
        if (this.Y <= 0) {
            this.yVelocity = Math.abs(this.yVelocity);
            this.Y = 0;
        } else if (this.Y >= metrics.heightPixels) {
            this.yVelocity = -Math.abs(this.yVelocity);
            this.Y = metrics.heightPixels;
        }

        getView().setLeft((int)Math.round(X));
        getView().setTop((int)Math.round(Y));
    }
}