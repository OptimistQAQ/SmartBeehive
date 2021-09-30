package cn.nuc.edu.smartbeehive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class CameraActivity extends AppCompatActivity  {
    private Button scan;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmera);

        //调取系统照相




//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        !=
//                        PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        } else {
////            wxat = 200;
//        }






    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//    private void goCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(CameraActivity.this, "com.example.hxd.pictest.fileprovider", cameraSavePath);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        } else {
//            uri = Uri.fromFile(cameraSavePath);
//        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        CameraActivity.this.startActivityForResult(intent, 1);
//    }

}