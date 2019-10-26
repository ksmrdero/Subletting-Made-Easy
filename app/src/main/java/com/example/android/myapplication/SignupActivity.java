package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.register_button) Button registerButton;

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

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}
