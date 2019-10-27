package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

    @BindView(R.id.buy_or_sell) Spinner spinner;
//    @BindView(R.id.mybrowser) WebView webView;

    private static final String TAG = "SignUpActivity";
    private static final String BUYER_INDICATOR = "I am looking for a subletter";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    // [END declare_database_ref]

    private EditText mNameField;
    private EditText mPhoneField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;
    private String mBuyerOrSeller;

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
        Spinner spinBuyOrSell = findViewById(R.id.buy_or_sell);
        mBuyerOrSeller = spinBuyOrSell.getSelectedItem().toString();

        mSignUpButton = findViewById(R.id.register_button);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        // NOTE: TOGGLE FOR AUTOMATIC LOGIN!
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
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(),
                                        "Password must be at least 6 characters!",
                                        Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "Invalid Email Entered!",
                                        Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "Email already exists!",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            mNameField.setError("Required");
            result = false;
        } else {
            mNameField.setError(null);
        }

        if (TextUtils.isEmpty(mPhoneField.getText().toString())) {
            mPhoneField.setError("Required");
            result = false;
        } else {
            mPhoneField.setError(null);
        }


        String inputEmail = mEmailField.getText().toString();

        if (TextUtils.isEmpty(inputEmail)) {
            mEmailField.setError("Required");
            result = false;
        } else if (!isEduEmail(inputEmail)) {
            mEmailField.setError("Email must be .edu");
            result = false;
        } else {
            mEmailField.setError(null);
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
        } else if (!mConfirmPasswordField.getText().toString().equals(mPasswordField.getText().toString())){
            mConfirmPasswordField.setError("Passwords must match!");
            result = false;
        } else {
            mConfirmPasswordField.setError(null);
        }


        return result;
    }


    private void onAuthSuccess(FirebaseUser user) {
        // Write new user

        if (mBuyerOrSeller.equals(BUYER_INDICATOR)) {
            writeNewBuyer(user.getUid(), user.getEmail());
        } else {
            writeNewSeller(user.getUid(), user.getEmail());
        }

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

    private boolean isEduEmail(String email) {
        return email.substring(email.length() - 3).equals("edu");
    }


    //     [START basic_write]

    private void writeNewBuyer(String userId, String email) {
        String firstName = mNameField.getText().toString();
        String phoneNumber = mPhoneField.getText().toString();
        Buyer buyer = new Buyer(firstName, phoneNumber, email);
        mDatabase.child("buyer").child(userId).setValue(buyer);
    }

    private void writeNewSeller(String userId, String email) {
        String firstName = mNameField.getText().toString();
        String phoneNumber = mPhoneField.getText().toString();
        Seller seller = new Seller(firstName, phoneNumber, email);
        mDatabase.child("seller").child(userId).setValue(seller);
    }

    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register_button) {
            signUp();
        }
    }
}
