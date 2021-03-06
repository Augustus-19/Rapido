package com.cs442.iitc_fall2016_g13.mad_proj.Seller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.iitc_fall2016_g13.mad_proj.R;
import com.cs442.iitc_fall2016_g13.mad_proj.ServerConnect.LoginActivity;
import com.cs442.iitc_fall2016_g13.mad_proj.ServerConnect.SignUpActivity;
import com.cs442.iitc_fall2016_g13.mad_proj.ServerConnect.SignUpProcess;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SellerSignUp extends AppCompatActivity {

    EditText username,password,conf_password,name,address,phone;
    Button signup;


    Context mContext;
    boolean mFlag = true;

    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seller_sign_up);
        mContext = this;
        username = (EditText)findViewById(R.id.input_username_seller);
        password = (EditText)findViewById(R.id.input_password_seller);
        conf_password = (EditText)findViewById(R.id.input_confirm_password_seller);

        name = (EditText)findViewById(R.id.input_name_seller);
        address = (EditText)findViewById(R.id.input_address_seller);
        phone = (EditText)findViewById(R.id.input_phone_seller);

        signup = (Button)findViewById(R.id.btn_signup_seller);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname = username.getText().toString();
                final String pwd = password.getText().toString();
                String cnf_pwd = conf_password.getText().toString();
                final String nm = name.getText().toString();
                final String addr = address.getText().toString();
               final String ph = phone.getText().toString();
                if(uname.equals("")||pwd.equals("")||cnf_pwd.equals("")||nm.equals("")||addr.equals("")||ph.equals(""))
                {
                    Toast toast = Toast.makeText(mContext, "Please Enter all details", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    if (cnf_pwd.equals(pwd)) {
                        String restaurantName = nm;
                        mFlag = false;
                        new SellerSignUpProcess(mContext).execute(uname, pwd, nm, ph, addr);


                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not Match", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });



               // final ProgressDialog dialog = ProgressDialog.show(mContext, "",
                 //       "Loading. Please wait...", true);






    }



}
