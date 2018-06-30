package com.example.a1505197.highradiusfeedy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UploadUserImage extends AppCompatActivity {

    Button imageButton;
    private static final int REQUEST_CODE=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        imageButton=findViewById(R.id.upload_img_btn);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CharSequence[] item={"Camera","Gallery","Cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(UploadUserImage.this);
                builder.setTitle("Add Users Image");
                builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(item[which].equals("Camera"))
                        {
                            if(ContextCompat.checkSelfPermission(UploadUserImage.this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                            {
                                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent,Init.CAMERA_REQUEST_CODE);
                            }
                            else
                            {
                                requestCameraPermission();
                            }


                        }
                        else
                        if(item[which].equals("Gallery"))
                        {
                            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent,Init.PICKFILE_REQUEST_CODE);
                        }
                        else
                        if(item[which].equals("Cancel"))
                        {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });

    }

    private void requestCameraPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(UploadUserImage.this, android.Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Allow Camera Permission")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(UploadUserImage.this,new String[] {android.Manifest.permission.CAMERA},REQUEST_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(UploadUserImage.this,new String[] {Manifest.permission.CAMERA},REQUEST_CODE);
        }
    }






}
