package com.example.a1505197.highradiusfeedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText etName, etEmail, etPassword, etConfirmPassword, etDesignation;
    ImageView ivCamera;
    Button Register;
    private static final int REQUEST_CODE = 1;
    ProgressBar progressBar;
    String name,email,password,confirmpassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        Register=findViewById(R.id.btn_register);
        etName = findViewById(R.id.et_user_name);
        etEmail = findViewById(R.id.et_user_email);
        etPassword = findViewById(R.id.et_user_password);
        etConfirmPassword = findViewById(R.id.et_user_confirm_password);
        etDesignation = findViewById(R.id.et_user_confirm_password);
        initProgressBar();
        createNewUser();

    }
    public  void createNewUser()
    {
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                sendMessage.message=name;
                password=etPassword.getText().toString();
                confirmpassword=etConfirmPassword.getText().toString();
                showProgressBar();
                if (isStringNull(name)||isStringNull(email)||isStringNull(password)||isStringNull(confirmpassword))
                {
                    Toast.makeText(Register.this,"Fill in all the fields",Toast.LENGTH_LONG).show();
                    hideProgressBar();

                }
                else
                if (!isStringEqual(password,confirmpassword))
                {
                    Toast.makeText(Register.this,"Password and confirm password do not match",Toast.LENGTH_LONG).show();
                    hideProgressBar();
                    etConfirmPassword.setText("");
                    etPassword.setText("");
                }
                //g
                else
                {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            showProgressBar();
                            if (task.isSuccessful())
                            {
                                hideProgressBar();
                                sendVerificationEmail();
                                FirebaseAuth.getInstance().signOut();
                                redirectLoginScreen();
                                Toast.makeText(Register.this,"Verification link has been sent to registered Email",Toast.LENGTH_LONG).show();
                                hideProgressBar();

                            }
                            else
                            {
                                Toast.makeText(Register.this,"Email already registered",Toast.LENGTH_LONG).show();

                                hideProgressBar();

                            }

                        }
                    });
                }
            }
        });

    }
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {

                            }
                            else{
                                Toast.makeText(Register.this, "couldn't send email", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }
    public boolean isStringEqual(String password,String Confirmpassword)
    {
        if(password.equals(Confirmpassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isStringNull(String string)
    {
        if(string.equals(""))
            return true;
        else
            return false;
    }
    private void initProgressBar(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void redirectLoginScreen(){

        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        //finish();
    }
}