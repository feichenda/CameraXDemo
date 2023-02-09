package com.feichen.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;

import com.feichen.cameraxdemo.base.BaseActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class DoubleCameraPreviewActivity extends BaseActivity {

    private static final String TAG = "DoubleCameraPreviewAct";

    private PreviewView mBackCamera;
    private PreviewView mFrontCamera;

    public DoubleCameraPreviewActivity() {
        super(R.layout.activity_double_camera_preview);
    }

    @Override
    protected void findView() {
        mBackCamera = findViewById(R.id.back_camera);
        mFrontCamera = findViewById(R.id.front_camera);
    }

    @Override
    protected void initView() {
        CameraXConfig cameraXConfig = CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig()).build();
        //ProcessCameraProvider.initializeInstance(this,cameraXConfig);
        CameraX.initialize(this, cameraXConfig);
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider processCameraProvider = cameraProviderListenableFuture.get();
                    Preview backPreview = new Preview.Builder()
                            .setTargetResolution(new Size(1920, 1080))
                            .build();
                    Preview frontPreview = new Preview.Builder()
                            .setTargetResolution(new Size(1920, 1080))
                            .build();

                    backPreview.setSurfaceProvider(mBackCamera.getPreviewSurfaceProvider());
                    frontPreview.setSurfaceProvider(mFrontCamera.getPreviewSurfaceProvider());

                    Camera backCamera = processCameraProvider.bindToLifecycle(DoubleCameraPreviewActivity.this, CameraSelector.DEFAULT_BACK_CAMERA, backPreview);
                    Camera frontCamera = processCameraProvider.bindToLifecycle(DoubleCameraPreviewActivity.this, CameraSelector.DEFAULT_FRONT_CAMERA, frontPreview);


                } catch (ExecutionException e) {
                    Log.e(TAG, "ExecutionException: " + e);
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException: " + e);
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
}