package com.beatmockerz.amantran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    LinearLayout proceed;
    EditText guestname;
    EditText guestplace;
    String type;
    String user_id;
    String guest_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        proceed = (LinearLayout) findViewById(R.id.proceed);
        guestname = (EditText) findViewById(R.id.guestname);
        guestplace = (EditText) findViewById(R.id.guestplace);
        if(getIntent().getStringExtra("edit").equals("false")) {
            // new guest entry
            user_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");

            Button save = new Button(MainActivity4.this);
            save.setText("Save");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            params.gravity = Gravity.CENTER;
            save.setLayoutParams(params);
            save.setOnClickListener(save_click);
            proceed.addView(save);
        }
        else {
            // edit or delete guest entry
            user_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            guest_id = getIntent().getStringExtra("guestid");
            guestname.setText(getIntent().getStringExtra("guestname"));
            guestplace.setText(getIntent().getStringExtra("guestplace"));

            Button save = new Button(MainActivity4.this);
            save.setText("Save");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            params.gravity = Gravity.CENTER;
            save.setLayoutParams(params);
            save.setOnClickListener(edit_click);
            Button delete = new Button(MainActivity4.this);
            delete.setText("Delete");
            delete.setLayoutParams(params);
            delete.setOnClickListener(delete_click);
            proceed.addView(delete);
            proceed.addView(save);
        }
    }

    View.OnClickListener save_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = guestname.getText().toString();
            String place = guestplace.getText().toString();
            String url = "http://192.168.1.5:8000/" + type + "/" + user_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("POST Response", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("place", place);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        }
    };

    View.OnClickListener edit_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = guestname.getText().toString();
            String place = guestplace.getText().toString();
            String url = "http://192.168.1.5:8000/" + user_id + "/" + type + "/" + guest_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("PUT Response", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("place", place);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        }
    };

    View.OnClickListener delete_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "http://192.168.1.5:8000/" + user_id + "/" + type + "/" + guest_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("DELETE Response", error.toString());
                }
            });
            requestQueue.add(stringRequest);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
        intent.putExtra("type", type);
        intent.putExtra("id", user_id);
        startActivity(intent);
        finish();
    }
}