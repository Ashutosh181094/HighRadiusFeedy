package com.example.a1505197.highradiusfeedy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Employess extends AppCompatActivity {

    private ArrayList<EmployessCards> al;
    private EmployessCardAdapter arrayAdapter;
    private int i;
    DatabaseReference userdata;
    DatabaseReference feedback,feedbackgiven;
    FirebaseAuth mAuth;
    int indexoffirst,indexofsecond;
    String key,key2;
    int checked;
    Object dataObjectleftswipe;

    SwipeFlingAdapterView flingContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employess);
        flingContainer = findViewById(R.id.frame);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        indexoffirst = email.indexOf('@');
        key = email.substring(0, indexoffirst);

        al = new ArrayList<EmployessCards>();
        userdata = FirebaseDatabase.getInstance().getReference("userinfo");
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        EmployessCards userinfo = dataSnapshot1.getValue(EmployessCards.class);
                        al.add(userinfo);
                    }
                }

                arrayAdapter = new EmployessCardAdapter(Employess.this, R.layout.give_feedback, al);
                flingContainer.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.add(al.remove(0));
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject)
            {
                dataObjectleftswipe=dataObject;
                final Dialog dialog=new Dialog(Employess.this);
                dialog.setContentView(R.layout.tell_us_more);
                dialog.show();
                final EditText feedbacktext=dialog.findViewById(R.id.et_feedback_text);
                Switch switchanonymous=dialog.findViewById(R.id.simpleSwitch);
                Button buttonsubmitfeedback=dialog.findViewById(R.id.submit_feedback);
                switchanonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if(buttonView.isChecked())
                        {
                            checked=1;
                        }
                        else
                        {
                            checked=0;
                        }

                    }
                });
                buttonsubmitfeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checked==1)
                        {
                            EmployessCards employee=(EmployessCards)dataObjectleftswipe;
                            String email=employee.getEmail();
                            indexoffirst=email.indexOf('@');
                            key=email.substring(0,indexoffirst);
                            String emailcurrentuser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            indexofsecond=emailcurrentuser.indexOf('@');
                            key2=emailcurrentuser.substring(0,indexofsecond);

                            feedback=FirebaseDatabase.getInstance().getReference("feedback").child(""+key).child(""+key2);
                            Feedbacks feedbacks=new Feedbacks(feedbacktext.getText().toString(),"änonymous","https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8","30/6/2018","Positive");
                            feedback.setValue(feedbacks);

                            feedbackgiven=FirebaseDatabase.getInstance().getReference("feedbackgiven");
                            Feedbacks feedbacksgiven=new Feedbacks(feedbacktext.getText().toString(),UserSessiondata.getName(),UserSessiondata.getImage_url(),"30/6/2018","Positive");
                            feedbackgiven.push().setValue(feedbacksgiven);
                            dialog.dismiss();
                        }
                        else
                        {
                            EmployessCards employee=(EmployessCards)dataObjectleftswipe;
                            String email=employee.getEmail();
                            indexoffirst=email.indexOf('@');
                            key=email.substring(0,indexoffirst);
                            String emailcurrentuser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            indexofsecond=emailcurrentuser.indexOf('@');
                            key2=emailcurrentuser.substring(0,indexofsecond);
                            feedback=FirebaseDatabase.getInstance().getReference("feedback").child(""+key).child(""+key2);
                            Feedbacks feedbacks=new Feedbacks(feedbacktext.getText().toString(),UserSessiondata.getName(),UserSessiondata.getImage_url(),"30/6/2018","Positive");
                            feedback.setValue(feedbacks);

                            feedbackgiven=FirebaseDatabase.getInstance().getReference("feedbackgiven");
                            feedbackgiven.push().setValue(feedbacks);
                            dialog.dismiss();
                        }
                    }
                });

            }

            @Override
            public void onRightCardExit(final Object dataObject)
            {
                final Dialog dialog=new Dialog(Employess.this);
                dialog.setContentView(R.layout.tell_us_more);
                dialog.show();
                final EditText feedbacktext=dialog.findViewById(R.id.et_feedback_text);
                Switch switchanonymous=dialog.findViewById(R.id.simpleSwitch);
                Button buttonsubmitfeedback=dialog.findViewById(R.id.submit_feedback);
                switchanonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                      if(buttonView.isChecked())
                      {
                          checked=1;
                      }
                      else
                      {
                          checked=0;
                      }

                    }
                });
                buttonsubmitfeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checked==1)
                        {
                            EmployessCards employee=(EmployessCards)dataObject;
                            String email=employee.getEmail();
                            indexoffirst=email.indexOf('@');
                            key=email.substring(0,indexoffirst);
                            String emailcurrentuser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            indexofsecond=emailcurrentuser.indexOf('@');
                            key2=emailcurrentuser.substring(0,indexofsecond);

                            feedback=FirebaseDatabase.getInstance().getReference("feedback").child(""+key).child(""+key2);
                            Feedbacks feedbacks=new Feedbacks(feedbacktext.getText().toString(),"änonymous","https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8","30/6/2018","Negative");
                            feedback.setValue(feedbacks);

                            feedbackgiven=FirebaseDatabase.getInstance().getReference("feedbackgiven");
                            Feedbacks feedbacksgiven=new Feedbacks(feedbacktext.getText().toString(),UserSessiondata.getName(),UserSessiondata.getImage_url(),"30/6/2018","Negative");
                            feedbackgiven.push().setValue(feedbacksgiven);
                            dialog.dismiss();
                        }
                        else
                        {
                            EmployessCards employee=(EmployessCards)dataObject;
                            String email=employee.getEmail();
                            indexoffirst=email.indexOf('@');
                            key=email.substring(0,indexoffirst);
                            String emailcurrentuser=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            indexofsecond=emailcurrentuser.indexOf('@');
                            key2=emailcurrentuser.substring(0,indexofsecond);
                            feedback=FirebaseDatabase.getInstance().getReference("feedback").child(""+key).child(""+key2);
                            Feedbacks feedbacks=new Feedbacks(feedbacktext.getText().toString(),UserSessiondata.getName(),UserSessiondata.getImage_url(),"30/6/2018","Negative");
                            feedback.setValue(feedbacks);

                            feedbackgiven=FirebaseDatabase.getInstance().getReference("feedbackgiven");
                            feedbackgiven.push().setValue(feedbacks);
                            dialog.dismiss();
                        }
                    }
                });


            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
            }
        });

    }



    public void right()
    {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }


    public void left() {

        flingContainer.getTopCardListener().selectLeft();
    }





}
//