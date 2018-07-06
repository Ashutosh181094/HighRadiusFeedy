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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Locale;

public class SecurityProducts extends AppCompatActivity {
    private ArrayList<FoodObject> al;
    private  ArrayList<EmployessCards> alpositives;
    private SecurityCardAdapter securityCardAdapter;
    private int i;
    DatabaseReference userdata;
    DatabaseReference feedbackpositivecount,feedbacknegative,feedback,userinfo;
    FirebaseAuth mAuth;
    Long pos,neg;
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
    String keyfood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_products);
        flingContainer = findViewById(R.id.frame);
        viewEmployeeBar=findViewById(R.id.viewEmployeeToolbar);
        searchBar=findViewById(R.id.search_toolbar);
        mAuth = FirebaseAuth.getInstance();
        final UserSessiondata userSessiondata=new UserSessiondata();
        final Intent intent=getIntent();
        final String key3=intent.getStringExtra("tag");
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
                securityCardAdapter.filter(text);
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
                    Toast.makeText(SecurityProducts.this, "No More Food Products", Toast.LENGTH_SHORT).show();
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
        userdata = FirebaseDatabase.getInstance().getReference("security");
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        FoodObject foodObject= dataSnapshot1.getValue(FoodObject.class);
                        al.add(foodObject);

                    }
                    if(al.size()==0){

                        progressDialog.dismiss();
                        Toast.makeText(SecurityProducts.this, "It seems you are the boss ;)", Toast.LENGTH_SHORT).show();


                    }
                    else {
                        progressDialog.dismiss();
                    }
                }

                securityCardAdapter= new SecurityCardAdapter(SecurityProducts.this, R.layout.give_security_feedback, al);
                flingContainer.setAdapter(securityCardAdapter);
                securityCardAdapter.notifyDataSetChanged();

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
                    securityCardAdapter.notifyDataSetChanged();
                }
                else if(al.size()==0)
                {
                    Toast.makeText(SecurityProducts.this, "No Employee Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLeftCardExit(final Object dataObject)
            {

                final FoodObject food = (FoodObject) dataObject;
                if(food.getQuestion().equals("Are we the best in service"))
                {
                    keyfood="security_best";
                }
                else if(food.getQuestion().equals("Are the security equipments like CCTV cameras maintained properly?"))
                {
                    keyfood="security_cctv";
                }
                else if(food.getQuestion().equals("Are the guards cooperative?"))
                {
                    keyfood="security_cooperative";
                }
                else if(food.getQuestion().equals("Do you feel safe with us?"))
                {
                    keyfood="security_safe";
                }

                userinfo=FirebaseDatabase.getInstance().getReference("security");
                userinfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        pos=(Long)dataSnapshot.child(""+keyfood).child("positive").getValue();


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
                    final Dialog dialog = new Dialog(SecurityProducts.this);
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
                                Toast.makeText(SecurityProducts.this, ""+pos, Toast.LENGTH_SHORT).show();
//                                String date;
//                                String feedback;
//                                String given_by;
//                                String image_url;
//                                String name;
//                                String type;


                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("Security");
                                FeedbackSecurityObject feedbacks = new FeedbackSecurityObject("30-12-2018",feedbacktext.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), "https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8", "änonymous","Positive",food.getQuestion());
                                feedback.push().setValue(feedbacks);

                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("security");
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        feedbackpositivecount.child(""+keyfood).child("positive").setValue((Object)(pos+1));


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                dialog.dismiss();




                            }



                            else
                            {

                                UserSessiondata userSessiondata1=new UserSessiondata();
                                FoodObject foodObject = (FoodObject) dataObjectleftswipe;
                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("Security");
                                FeedbackSecurityObject feedbacks = new FeedbackSecurityObject("30-12-2018",feedbacktext.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(),userSessiondata1.getImage_url() ,userSessiondata1.getName(),"Positive",food.getQuestion());
                                feedback.push().setValue(feedbacks);
                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("security");
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        feedbackpositivecount.child(""+keyfood).child("positive").setValue((Object)(pos+1));


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
                final FoodObject foodObject = (FoodObject) dataObject;
                if(foodObject.getQuestion().equals("Are we the best in service"))
                {
                    keyfood="security_best";
                }
                else if(foodObject.getQuestion().equals("Are the security equipments like CCTV cameras maintained properly?"))
                {
                    keyfood="security_cctv";
                }
                else if(foodObject.getQuestion().equals("Are the guards cooperative?"))
                {
                    keyfood="security_cooperative";
                }
                else if(foodObject.getQuestion().equals("Do you feel safe with us?"))
                {
                    keyfood="security_safe";
                }
                userinfo=FirebaseDatabase.getInstance().getReference("security");
                userinfo.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        neg=(Long)dataSnapshot.child(""+keyfood).child("negative").getValue();


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
                    final Dialog dialog = new Dialog(SecurityProducts.this);
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
                            UserSessiondata userSessiondata1=new UserSessiondata();
                            if (checked == 1)
                            {

                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("Security");
                                FeedbackSecurityObject feedbacks = new FeedbackSecurityObject("30-12-2018",feedbacktext.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), "https://firebasestorage.googleapis.com/v0/b/highradiusfeedy.appspot.com/o/icons8-male-user-100.png?alt=media&token=bda9da85-87b9-4933-90f8-250c7e67baa8", "änonymous","Negative",foodObject.getQuestion());
                                feedback.push().setValue(feedbacks);
                                dialog.dismiss();
                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("security");
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        feedbackpositivecount.child(""+keyfood).child("negative").setValue((Object)(neg+1));


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            else
                            {
                                FoodObject foodObject = (FoodObject) dataObject;
                                feedback = FirebaseDatabase.getInstance().getReference("feedback").child("Security");
                                FeedbackSecurityObject feedbacks = new FeedbackSecurityObject("30-12-2018",feedbacktext.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail(),userSessiondata1.getImage_url() ,userSessiondata1.getName(),"Negative",foodObject.getQuestion());
                                feedback.push().setValue(feedbacks);
                                feedbackpositivecount=FirebaseDatabase.getInstance().getReference("security");
                                feedbackpositivecount.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        feedbackpositivecount.child(""+keyfood).child("negative").setValue((Object)(neg+1));


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
                securityCardAdapter.notifyDataSetChanged();
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
