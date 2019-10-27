package com.example.android.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocusignActivity extends AppCompatActivity {

    private String email;

    boolean registered;

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

            try {
                URL url = new URL("https://account-d.docusign.com/v1/accounts/3d72c2cd-fb2e-48dd-894c-2b7a6807ea44/clickwraps/43d6f948-e43a-4c6d-a1b0-f7c826ed7da1/users")
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("param1", email);

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(getParamsString(parameters));
                out.flush();
                out.close();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setInstanceFollowRedirects(false);
                int status = con.getResponseCode();

                if(responsecode != 200) {
                    registered = false;
                }
                else
                {
                    registered = true;
                }



                con.disconnect();
            }
            catch(Exception e) {
                registered = false;
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

}
