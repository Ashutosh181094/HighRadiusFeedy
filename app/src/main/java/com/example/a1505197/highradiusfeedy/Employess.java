package com.example.a1505197.highradiusfeedy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Locale;

public class Employess extends AppCompatActivity {

    private ArrayList<EmployessCards> al;
    private  ArrayList<EmployessCards> alpositives;
    private EmployessCardAdapter arrayAdapter;
    private int i;
    DatabaseReference userdata;
    DatabaseReference feedbackpositivecount,feedbacknegative,feedback,userinfo;
    FirebaseAuth mAuth;
    int indexoffirst,indexofsecond;
    Long levelemp,pos,neg;
    String key,key2;
    int checked;
    Object dataObjectleftswipe;
    public ProgressDialog progressDialog;
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;
    private AppBarLayout viewEmployeeBar, searchBar;
    SwipeFlingAdapterView flingContainer;
    ImageView ivBackArrow;
    ImageView ivSearchicon;
    Button skip;
    Boolean skiptonext=false;
    EditText searchEmployess;
    LikeButton happysmiley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employess);
        flingContainer = findViewById(R.id.frame);
        viewEmployeeBar=findViewById(R.id.viewEmployeeToolbar);
        searchBar=findViewById(R.id.search_toolbar);
        /* happysmiley=findViewById(R.id.happy); */
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        indexoffirst = email.indexOf('@');
        key = email.substring(0, indexoffirst);
        final UserSessiondata userSessiondata=new UserSessiondata();
        levelemp=userSessiondata.getLevel();
        final Intent intent=getIntent();
        String key3=intent.getStringExtra("tag");
        ivBackArrow=findViewById(R.id.ivBackArrow);
        searchEmployess=findViewById(R.id.etSearchEmployees);


        searchEmployess.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=searchEmployess.getText().toString().toLowerCase(Locale.getDefault());
                skiptonext=true;
                  right();
                arrayAdapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (al.size()!=0) {
                    right();
                    skiptonext = true;
                }
                else {
                    Toast.makeText(Employess.this, "No More Employees", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ivSearchicon=findViewById(R.id.ivSearchIcon);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                 toggleToolbarState();
            }
        });
        ivSearchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             toggleToolbarState();
            }
        });

        setAppBarState(STANDARD_APPBAR);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        al = new ArrayList<>();
        userdata = FirebaseDatabase.getInstance().getReference("userinfo").child(""+key3);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        EmployessCards userinfo = dataSnapshot1.getValue(EmployessCards.class);
                        if(levelemp-1>0&&userinfo.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())==false) {
                            if (userinfo.level == levelemp || userinfo.level == levelemp - 1) {

                                al.add(userinfo);
                            }
                        }
                    }
                    if(al.size()==0){

                    progressDialog.dismiss();
                        Toast.makeText(Employess.this, "It seems you are the boss ;)", Toast.LENGTH_SHORT).show();


                    }
                    else {
                        progressDialog.dismiss();
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

               if(!al.isEmpty()) {
                   al.add(al.remove(0));
                   arrayAdapter.notifyDataSetChanged();
               }
               else if(al.size()==0)
               {
                   Toast.makeText(Employess.this, "No Employee Available", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onLeftCardExit(final Object dataObject)
            {
                final EmployessCards employee = (EmployessCards) dataObject;
                String email = employee.getEmail();
                indexoffirst = email.indexOf('@');
                key = email.substring(0, indexoffirst);
                userinfo=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                userinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        pos=(Long)dataSnapshot.child(""+key).child("positive").getValue();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(skiptonext)
                {
                skiptonext=false;
                }
                else {
                    dataObjectleftswipe = dataObject;
                    final Dialog dialog = new Dialog(Employess.this);
                    dialog.setContentView(R.layout.tell_us_more);
                    dialog.show();
                    final EditText feedbacktext = dialog.findViewById(R.id.et_feedback_text);
                    Switch switchanonymous = dialog.findViewById(R.id.simpleSwitch);
                    TextView buttonsubmitfeedback = dialog.findViewById(R.id.submit_feedback);
                    switchanonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                checked = 1;

                            } else {
                                checked = 0;
                            }

                        }
                    });
                    buttonsubmitfeedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if (checked == 1)
                            {
                                Toast.makeText(Employess.this, ""+pos, Toast.LENGTH_SHORT).show();



                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child(employee.getDepartment());
                                Feedbacks feedbacks = new Feedbacks(feedbacktext.getText().toString(), "änonymous", "https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8", "30/6/2018", "Positive",employee.getEmail(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),employee.getLevel());
                                feedback.push().setValue(feedbacks);

                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                         feedbackpositivecount.child(""+key).child("positive").setValue((Object)(pos+1));


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                dialog.dismiss();




                            }



                            else
                                {


                                    EmployessCards employee = (EmployessCards) dataObjectleftswipe;
                                    feedback = FirebaseDatabase.getInstance().getReference("feedback").child(employee.getDepartment());
                                    Feedbacks feedbacks = new Feedbacks(feedbacktext.getText().toString(), employee.getName(), employee.getImage_url(), "30/6/2018", "Positive",employee.getEmail(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),employee.getLevel());
                                    feedback.push().setValue(feedbacks);
                                    feedbackpositivecount=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                                    feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot)
                                        {
                                            feedbackpositivecount.child(""+key).child("positive").setValue((Object)(pos+1));


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    dialog.dismiss();



                                }
                        }
                    });
                }

            }

            @Override
            public void onRightCardExit(final Object dataObject)
            {
                final EmployessCards employee = (EmployessCards) dataObject;
                String email = employee.getEmail();
                indexoffirst = email.indexOf('@');
                key = email.substring(0, indexoffirst);
                userinfo=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                userinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        neg=(Long)dataSnapshot.child(""+key).child("negative").getValue();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(skiptonext)
                {
                 skiptonext=false;
                }
                else
                    {
                    final Dialog dialog = new Dialog(Employess.this);
                    dialog.setContentView(R.layout.tell_us_more);
                    dialog.show();
                    final EditText feedbacktext = dialog.findViewById(R.id.et_feedback_text);
                    Switch switchanonymous = dialog.findViewById(R.id.simpleSwitch);
                    TextView buttonsubmitfeedback = dialog.findViewById(R.id.submit_feedback);
                    switchanonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                checked = 1;
                            } else {
                                checked = 0;
                            }

                        }
                    });
                    buttonsubmitfeedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if (checked == 1)
                            {

                                EmployessCards employee = (EmployessCards) dataObject;
                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("" + employee.getDepartment());
                                Feedbacks feedbacks = new Feedbacks(feedbacktext.getText().toString(), "änonymous", "https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8", "30/6/2018", "Negative",employee.getEmail(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),employee.getLevel());
                                feedback.push().setValue(feedbacks);
                                dialog.dismiss();
                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        feedbackpositivecount.child(""+key).child("negative").setValue((Object)(neg+1));


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            else
                                {
                                EmployessCards employee = (EmployessCards) dataObject;
                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("" + employee.getDepartment());
                                Feedbacks feedbacks = new Feedbacks(feedbacktext.getText().toString(), employee.getName(), employee.getImage_url(), "30/6/2018", "Negative",employee.getEmail(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),employee.getLevel());
                                    feedback.push().setValue(feedbacks);
                                    feedbackpositivecount=FirebaseDatabase.getInstance().getReference("userinfo").child(""+userSessiondata.getDepartment());
                                    feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot)
                                        {
                                            feedbackpositivecount.child(""+key).child("negative").setValue((Object)(neg+1));


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    dialog.dismiss();
                            }
                        }
                    });
                }

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
    private void toggleToolbarState() {
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {
        mAppBarState=state;
        if(mAppBarState==STANDARD_APPBAR)
        {
            searchBar.setVisibility(View.GONE);
            viewEmployeeBar.setVisibility(View.VISIBLE);

        }
        else if(mAppBarState==SEARCH_APPBAR)
        {
            viewEmployeeBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }





}
//