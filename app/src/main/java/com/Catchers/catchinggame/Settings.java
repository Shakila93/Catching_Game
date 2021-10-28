package com.Catchers.catchinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.*;
import android.content.pm.PackageManager;

import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.params.SessionConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private Button ChangeBG;
    private Button ReturnToMain;
    private CameraDevice camera;
    private SurfaceHolder Holder;
    private CameraCaptureSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ChangeBG = findViewById(R.id.ChangeView);
        ReturnToMain = findViewById(R.id.returnToMain);
        SurfaceView sv = findViewById(R.id.surfaceView);
        Holder = sv.getHolder();

        ReturnToMain.setOnClickListener(view -> finish());
        //tells us we have camera
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//            ChangeBG.setVisibility(View.INVISIBLE);
//        }
//        ChangeBG.setOnClickListener(View -> {
//
//        });
    }

    public void takePicture() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE); //gets the camera manager
        try {
            String[] ids = cameraManager.getCameraIdList();
            //check for permission if granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraManager.openCamera(ids[0], new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    camera = cameraDevice;
                    //create preview session

                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                    camera = null;
                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int i) {
                    Log.e("Catching Game", "Error occured");
                }
            }, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();

            }

        }
//        //showing the user what camera seeing
//        private void CreatePreviewSession(){
//            List<Surface> outPuts = new ArrayList<>();
//            outPuts.add(Holder.getSurface());
//            try {
//                final CaptureRequest.Builder builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//                builder.addTarget(Holder.getSurface());
//                camera.createCaptureSession(new SessionConfiguration(CameraDevice.TEMPLATE_PREVIEW, outPuts, new StateCallback(){
//
//                }));
//            }
//            catch (CameraAccessException e){
//                Log.e("Catching Game", "Failed to create session");
//            }
//        }
}