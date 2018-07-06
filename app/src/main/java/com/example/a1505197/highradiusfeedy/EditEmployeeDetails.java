package com.example.a1505197.highradiusfeedy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditEmployeeDetails extends AppCompatActivity
{
    ImageView back;
    ImageView save;
    ImageView camera;
    ImageView iv_user;
    ImageView iv_designation;
    DatabaseReference user;
    private static final int REQUEST_CODE=1;
    StorageReference storeUserPhoto;
    CircleImageView profilePhoto;
    TextView name,designation,email,phonenumber;
    int indexoffirst;
    String key;
    String useremail;
    DatabaseReference registeredemployees;
    DatabaseReference feedbackReference;
    DatabaseReference imageReference;
    String image_url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_details);
        back=findViewById(R.id.backArrow);
        save=findViewById(R.id.saveChanges);
        camera=findViewById(R.id.iv_camera);
        name=findViewById(R.id.username);
        iv_user=findViewById(R.id.iv_user);
        iv_designation=findViewById(R.id.iv_designation);
        designation=findViewById(R.id.designation);
        email=findViewById(R.id.email);
        UserSessiondata userSessiondata=new UserSessiondata();
        name.setText(userSessiondata.getName());
        useremail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        indexoffirst=useremail.indexOf('@');
        key=useremail.substring(0,indexoffirst);
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        designation.setText(userSessiondata.getDesignation());

        profilePhoto=findViewById(R.id.profile_photo);
        Picasso.with(getApplicationContext())
                .load(userSessiondata.getImage_url())
                .fit()
                .centerCrop()
                .into(profilePhoto);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditEmployeeDetails.this,EmployeeInfo.class);
                startActivity(intent);
                finish();

            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] item={"Camera","Gallery","Cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(EditEmployeeDetails.this);
                builder.setTitle("Add Users Image");
                builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(item[which].equals("Camera"))
                        {
                            if(ContextCompat.checkSelfPermission(EditEmployeeDetails.this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
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
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(EditEmployeeDetails.this);
                dialog.setContentView(R.layout.edit_name);
                dialog.show();
                Button upload=dialog.findViewById(R.id.btn_edit_name);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText usernameedit=dialog.findViewById(R.id.et_name);
                        String susernameedit=usernameedit.getText().toString();

                        user= FirebaseDatabase.getInstance().getReference("userinfo").child(""+key);

                        user.child("name").setValue((Object)susernameedit);
                        dialog.dismiss();
                    }
                });
            }
        });
        iv_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(EditEmployeeDetails.this);
                dialog.setContentView(R.layout.edit_designation);
                dialog.show();
                Button upload=dialog.findViewById(R.id.btn_edit_designation);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText designation=dialog.findViewById(R.id.et_designation);
                        String sdesignation=designation.getText().toString();

                        user= FirebaseDatabase.getInstance().getReference("userinfo").child(""+key);

                        user.child("designation").setValue((Object)sdesignation);
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode==REQUEST_CODE)
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,Init.CAMERA_REQUEST_CODE);
            }
        }
    }
    private void requestCameraPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(EditEmployeeDetails.this, android.Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Allow Camera Permission")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(EditEmployeeDetails.this,new String[] {android.Manifest.permission.CAMERA},REQUEST_CODE);

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
            ActivityCompat.requestPermissions(EditEmployeeDetails.this,new String[] {Manifest.permission.CAMERA},REQUEST_CODE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ProgressDialog progressDialog=new ProgressDialog(EditEmployeeDetails.this);
        progressDialog.setMessage("Please wait while we fetch your data");
        progressDialog.show();
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
        storeUserPhoto= FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getEmail());
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
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                   registeredemployees= FirebaseDatabase.getInstance().getReference("registeredemployees");
                registeredemployees.child(key).child("image_url").setValue(taskSnapshot.getDownloadUrl().toString());

                UserSessiondata userSessiondata=new UserSessiondata();
                user=FirebaseDatabase.getInstance().getReference("userinfo").child(userSessiondata.getDepartment());

                user.child(""+key).child("image_url").setValue(taskSnapshot.getDownloadUrl().toString());

                userSessiondata.setImage_url(taskSnapshot.getDownloadUrl().toString());
                    progressDialog.dismiss();
                   image_url=taskSnapshot.getDownloadUrl().toString();

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateimage(image_url);
                        Intent intent=new Intent(EditEmployeeDetails.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
    }
    public void updateimage(final String image_url)
    {

        final ProgressDialog progressDialog=new ProgressDialog(EditEmployeeDetails.this);
        progressDialog.setMessage("Please wait while we fetch your data");
        progressDialog.show();
        final UserSessiondata userSessiondata=new UserSessiondata();
        feedbackReference=FirebaseDatabase.getInstance().getReference("feedback").child(userSessiondata.getDepartment());
        feedbackReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {

                        Feedbacks feedbacks=dataSnapshot1.getValue(Feedbacks.class);

                        if(feedbacks.getGiven_by().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())&&feedbacks.getName().equals("änonymous")==false)
                        {
                            final UserSessiondata userSessiondata=new UserSessiondata();

                            imageReference=FirebaseDatabase.getInstance().getReference("feedback").child(""+userSessiondata.getDepartment()).child(dataSnapshot1.getKey());
                            imageReference.child("image_url").setValue((Object)image_url);

                        }
                    }
                }
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
//