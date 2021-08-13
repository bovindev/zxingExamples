package com.google.zxing.client.android;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WelcomeActivity extends AppCompatActivity {

    private String[] permissions = {"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_CONTACTS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMissingPermissions();
        } else {
            startCaptureActivity();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkMissingPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            } else {
                startCaptureActivity();
            }
        }
    }

    private void startCaptureActivity() {
        startActivity(new Intent(this, CaptureActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length != 0) {
                for (int results : grantResults) {
                    if (results == PackageManager.PERMISSION_GRANTED) {
                        startCaptureActivity();
                    } else {
                        Toast.makeText(this, "你拒绝了权限",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }
}