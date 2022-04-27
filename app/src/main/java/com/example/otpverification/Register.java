package com.example.otpverification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText email = findViewById(R.id.email);
        EditText mobile = findViewById(R.id.mobile);
        AppCompatButton login = findViewById(R.id.login);
        AppCompatButton signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String mobileString = mobile.getText().toString();
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                long otp = (int)(Math.random()*1000000);
                String otpString = otp+"";
                String verified = "unverified";
                user User = new user(usernameString, emailString, mobileString,passwordString,
                        otpString, verified);
                Database db = new Database(Register.this);
                int count = db.checkEmail(emailString);
                if(count>0){
                    Toast.makeText(Register.this,"Email already under use", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Register.class);
                    startActivity(intent);
                }
                Intent intent = new Intent(Register.this, OTP.class);
                db.addUser(User);
                intent.putExtra("username",usernameString);
                intent.putExtra("password",passwordString);
                intent.putExtra("email",emailString);
                intent.putExtra("mobile",mobileString);
                intent.putExtra("otp", otpString);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("DESTINATION NUMBER", null, otpString,
                        null, null);
                startActivity(intent);
            }
        });
    }
}
