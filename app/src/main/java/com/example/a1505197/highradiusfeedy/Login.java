package com.example.a1505197.highradiusfeedy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
Button login;TextView register;
    ProgressBar progressBar;
    EditText etemail,etpassword;
    TextView ForgotPassword;
    String email,password;
    private FirebaseAuth mAuth;
    Dialog dialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);
        ForgotPassword=findViewById(R.id.tvForgotPassword);
        login=findViewById(R.id.btn_login);
        etemail=findViewById(R.id.et_email);
        etpassword=findViewById(R.id.et_password);
        register=findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        setupsignin();
        initProgressBar();
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 dialog=new Dialog(Login.this);
                dialog.setContentView(R.layout.forgot_password);
                dialog.show();
                final EditText forgot_email=dialog.findViewById(R.id.et_email);
                Button update=dialog.findViewById(R.id.btn_update_forword_password);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        email=forgot_email.getText().toString();
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),"Email sent",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Email not sent",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });

            }
        });

    }
    public void setupsignin()
    {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=etemail.getText().toString();
                password=etpassword.getText().toString();

                showProgressBar();
                if(isStringNull(email)||isStringNull(password))
                {

                    Toast.makeText(Login.this,"Fill in all the fields",Toast.LENGTH_LONG).show();
                }

                else

                {

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                            if(task.isSuccessful()&&user.isEmailVerified())
                            {

                                Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_LONG).show();

                                hideProgressBar();
                                Intent intent=new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                                //if the authentication is successfull
                            }
                            else
                            {


                                Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();


                                //if the authentication is not sucessful
                                hideProgressBar();
                            }
                        }
                    });
                }
            }
        });


    }


    public boolean isStringNull(String string)
    {
        if(string.equals(""))
            return true;
        else
            return false;
    }
    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void initProgressBar(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
