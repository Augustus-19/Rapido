package com.cs442.iitc_fall2016_g13.mad_proj.ServerConnect;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.cs442.iitc_fall2016_g13.mad_proj.R;

public class SignUpActivity extends AppCompatActivity {
    public static int err=0;
    EditText username,password,conf_password,name,address,phone;
    Button signup;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;
        setTitle("Sign Up Customer");
        username = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        conf_password = (EditText)findViewById(R.id.input_confirm_password);

        name = (EditText)findViewById(R.id.input_name);
        address = (EditText)findViewById(R.id.input_address);
        phone = (EditText)findViewById(R.id.input_phone);

        signup = (Button)findViewById(R.id.btn_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pwd = password.getText().toString();
                String cnf_pwd = conf_password.getText().toString();
                String nm = name.getText().toString();
                String addr = address.getText().toString();
                String ph = phone.getText().toString();
if(uname.equals("")||pwd.equals("")||cnf_pwd.equals("")||nm.equals("")||addr.equals("")||ph.equals(""))
{
    Toast toast = Toast.makeText(mContext, "Please Enter all details", Toast.LENGTH_LONG);
    toast.show();
}
                else {
    if (cnf_pwd.equals(pwd)) {
        new SignUpProcess(mContext).execute(uname, pwd, nm, ph, addr);

    } else {
        Toast toast = Toast.makeText(mContext, "Passwords do not Match", Toast.LENGTH_LONG);
        toast.show();
    }
}

            }
        });
    }


}
