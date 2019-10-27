package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.spinner) Spinner spinner;
//    @BindView(R.id.mybrowser) WebView webView;

    private static final String TAG = "SignUpActivity";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    // [END declare_database_ref]

    private EditText mNameField;
    private EditText mPhoneField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;

    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.register_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("file:///android_asset/click.html");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mNameField = findViewById(R.id.input_name);
        mPhoneField = findViewById(R.id.input_phone);
        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);
        mConfirmPasswordField = findViewById(R.id.input_password_confirm);

        mSignUpButton = findViewById(R.id.register_button);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

//        System.out.println(email);
//        System.out.println(password);
//        System.out.println("adksafdfasdfsdfsfsf");

//        User user = new User(name, phoneNumber, email, password);
//
//        mDatabase.child("users").setValue(user);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
//                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(), "Password must be at least 6 characters!",
                                        Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "Email already exists!",
                                        Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPhoneField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        if (TextUtils.isEmpty(mConfirmPasswordField.getText().toString())) {
            mConfirmPasswordField.setError("Required");
            result = false;
        } else if (mConfirmPasswordField.getText().toString() == (mPasswordField.getText().toString())){
            mConfirmPasswordField.setError("Passwords must match!");
            result = false;
        } else {
            mConfirmPasswordField.setError(null);
        }


        return result;
    }


    private void onAuthSuccess(FirebaseUser user) {
        String name = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), name, user.getEmail());
//        writeNewUser(user.getUid(), name, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    //     [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, phoneNumber, email, password);
        User user = new User(name, email);
        mDatabase.child("userAuth").child(userId).setValue(user);


        String firstname = mNameField.getText().toString();
        String phoneNumber = mPhoneField.getText().toString();
        String password = mPasswordField.getText().toString();
        User userInfo = new User(firstname, phoneNumber, email, password);
        mDatabase.child("userInfo").setValue(userInfo);
    }

//    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }

    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register_button) {
            signUp();
        }
    }
}
