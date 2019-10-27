package com.example.android.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocusignActivity extends AppCompatActivity {

    private String email;

    boolean registered = false;

    @BindView(R.id.web_view) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docusign);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Agreement");
        actionBar.setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                email = null;
            } else {
                email = extras.getString(SignUpActivity.EMAIL);
            }
        } else {
            email = (String) savedInstanceState.getSerializable(SignUpActivity.EMAIL);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://docusign-host.herokuapp.com/?email=" + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String inline = "";
        if (id == R.id.next) {
            // TODO: Check if registered!
            HttpGetRequest getRequest = new HttpGetRequest();
            String result = null;
            JSONObject myResponse = null;
            try {
                result = getRequest.execute("https://demo.docusign.net/clickapi/v1/accounts/3d72c2cd-fb2e-48dd-894c-2b7a6807ea44/clickwraps/43d6f948-e43a-4c6d-a1b0-f7c826ed7da1/users").get();
                myResponse = new JSONObject(result);
                JSONArray allUsers = myResponse.getJSONArray("userAgreements");
                for (int i = 0; i < allUsers.length(); i++) {
                    if (allUsers.getJSONObject(i).getString("clientUserId").equals(email)) {
                        registered = true;
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (registered) {
                startActivity(new Intent(DocusignActivity.this, MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }


    private class HttpGetRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            int status = 0;
            try {
                url = new URL(urls[0]);
                HttpURLConnection con = null;
                con = (HttpURLConnection) url.openConnection();
                String auth = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjY4MTg1ZmYxLTRlNTEtNGNlOS1hZjFjLTY4OTgxMjIwMzMxNyJ9.eyJUb2tlblR5cGUiOjUsIklzc3VlSW5zdGFudCI6MTU3MjE1MzIxMSwiZXhwIjoxNTcyMTgyMDExLCJVc2VySWQiOiJmN2JiZjVhYS0zOWZjLTQ2MmEtYjBiYi0zY2JiNDRhN2Y4MmUiLCJzaXRlaWQiOjEsInNjcCI6WyJzaWduYXR1cmUiLCJjbGljay5tYW5hZ2UiLCJvcmdhbml6YXRpb25fcmVhZCIsImdyb3VwX3JlYWQiLCJwZXJtaXNzaW9uX3JlYWQiLCJ1c2VyX3JlYWQiLCJ1c2VyX3dyaXRlIiwiYWNjb3VudF9yZWFkIiwiZG9tYWluX3JlYWQiLCJpZGVudGl0eV9wcm92aWRlcl9yZWFkIiwiZHRyLnJvb21zLnJlYWQiLCJkdHIucm9vbXMud3JpdGUiLCJkdHIuZG9jdW1lbnRzLnJlYWQiLCJkdHIuZG9jdW1lbnRzLndyaXRlIiwiZHRyLnByb2ZpbGUucmVhZCIsImR0ci5wcm9maWxlLndyaXRlIiwiZHRyLmNvbXBhbnkucmVhZCIsImR0ci5jb21wYW55LndyaXRlIl0sImF1ZCI6ImYwZjI3ZjBlLTg1N2QtNGE3MS1hNGRhLTMyY2VjYWUzYTk3OCIsImlzcyI6Imh0dHBzOi8vYWNjb3VudC1kLmRvY3VzaWduLmNvbS8iLCJzdWIiOiJmN2JiZjVhYS0zOWZjLTQ2MmEtYjBiYi0zY2JiNDRhN2Y4MmUiLCJhbXIiOlsibWFuYWdlZCIsImludGVyYWN0aXZlIl0sImF1dGhfdGltZSI6MTU3MjE1MzIwOH0.RaVBdMCzZzliV2oxekcmGM1QH8hIAJQQR3BXsyaCMYj6nniYq89bXu47Gjkqgba6uegvRhec6Wg3ebeYwQEfP65pbkzIuMOb6plqg6fuMpqZu_NlxBySBRGpjCdIqD-L18cnIdoyrMjCtd_xGB2MFx-aAQpLAMgPR6UFSFCN9XCF054-vQv0TWda0Cm2uzEaRoxO8-djMvP9nZ8cSTNIiK_1LSrgyqBNtKM2St4S88tLRePVWwrszzQfOW8CBRovcEfl7ui4xy7XuzWAfRgYk1LYkcvDQ2gxxqzIRehttipoR3fq4WI3R6DzMcHvdxkolOtz0adNAVkAu_Va0nmgKg";
                con.setRequestProperty("Authorization", auth);
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("GET");
                con.connect();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.toString(status);
        }
    }
}
