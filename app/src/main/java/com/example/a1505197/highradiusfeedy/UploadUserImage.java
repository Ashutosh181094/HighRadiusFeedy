package com.example.a1505197.highradiusfeedy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadUserImage extends AppCompatActivity {

    Button imageButton;
    private static final int REQUEST_CODE=1;
    CircleImageView profilePhoto;
    StorageReference storeUserPhoto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);


        UserSessiondata userSessiondata=new UserSessiondata();
        imageButton=findViewById(R.id.upload_img_btn);
        profilePhoto=findViewById(R.id.user_image);
        Picasso.with(getApplicationContext())
                .load(userSessiondata.getImage_url())
                .fit()
                .centerCrop()
                .into(profilePhoto);

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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Init.CAMERA_REQUEST_CODE&&resultCode== Activity.RESULT_OK)
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            profilePhoto.setImageBitmap(bitmap);
        }
        if (requestCode==Init.PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Uri selectedImageUri=data.getData();
            profilePhoto.setImageURI(selectedImageUri);
        }
        //storeUserPhoto= FirebaseStorage.getInstance().getReference(user.getEmail());
        profilePhoto.setDrawingCacheEnabled(true);
        profilePhoto.buildDrawingCache();
        Bitmap bitmap = profilePhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data1 = baos.toByteArray();


        UploadTask uploadTask = storeUserPhoto.putBytes(data1);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(UploadUserImage.this, "load"+taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();



                //userData.child(user.getUid()).child("image_url").setValue(taskSnapshot.getDownloadUrl().toString());
            }
        });
    }





}
