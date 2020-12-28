package com.example.flaslighttourch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private ImageButton torchbutton;
    boolean hascameraflash;
    boolean flashon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torchbutton = (ImageButton) findViewById(R.id.torch_button);

        hascameraflash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        torchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hascameraflash) {
                    if (flashon) {
                        flashon = false;
                        try {
                            flashlightoff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        flashon = true;
                        try {
                            flashlighton();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No flash available on your device ", LENGTH_LONG).show();
                }
            }
        });

    }

    public void flashlightoff() throws CameraAccessException {
        torchbutton.setImageResource(R.drawable.torch_off);
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraid = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraid, false);

    }

    public void flashlighton() throws CameraAccessException {
        torchbutton.setImageResource(R.drawable.torch_on);
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraid = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraid, true);

    }
}