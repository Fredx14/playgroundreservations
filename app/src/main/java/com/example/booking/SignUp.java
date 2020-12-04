package com.example.booking;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {
    EditText editTextFullname,editTextEmail,editTextPhone,editTextAddress,editTextPassword;
    Button buttonSignup;
    TextView textViewLogin;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFullname = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.email);
        editTextPhone = findViewById(R.id.phone);
        editTextAddress = findViewById(R.id.address);
        editTextPassword = findViewById(R.id.password);

        buttonSignup = findViewById(R.id.btnSignup);
        textViewLogin = findViewById(R.id.txtLogin);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname, email, phone, address,password;
                fullname = String.valueOf(editTextFullname.getText());
                email = String.valueOf(editTextEmail.getText());
                phone = String.valueOf(editTextPhone.getText());
                address = String.valueOf(editTextAddress.getText());
                password = String.valueOf(editTextPassword.getText());
                if (!fullname.equals("") && !email.equals("") && !phone.equals("") && !address.equals("")&& !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(view.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //Starting Write and Read data with  URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "fullname";
                            field[1] = "email";
                            field[2] = "phone";
                            field[3] = "address";
                            field[4] = "password";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = fullname;
                            data[1] = email;
                            data[2] = phone;
                            data[3] = address;
                            data[4] = password;
                            PutData putData = new PutData("http://192.168.8.106/playground_reservation/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"all fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}