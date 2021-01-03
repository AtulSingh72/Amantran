package com.beatmockerz.amantran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.util.TypedValue;
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
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {

    LinearLayout proceed;
    EditText guestname;
    EditText guestplace;
    String type;
    String user_id;
    String guest_id;
    MaterialButton save;
    MaterialButton delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        proceed = (LinearLayout) findViewById(R.id.proceed);
        guestname = (EditText) findViewById(R.id.guestname);
        guestplace = (EditText) findViewById(R.id.guestplace);
        Typeface font = ResourcesCompat.getFont(MainActivity4.this, R.font.semibold);
        if(getIntent().getStringExtra("edit").equals("false")) {
            // new guest entry
            user_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");

            save = new MaterialButton(MainActivity4.this);
            save.setText("Save");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            save.setLayoutParams(params);
            save.setOnClickListener(save_click);
            save.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            save.setTextColor(Color.parseColor("#FFFFFF"));
            save.setTypeface(font);
            save.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity4.this, R.color.bhagwa));
            save.setCornerRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()));
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            save.setPadding(0, padding, 0, padding);
            proceed.addView(save);
        }
        else {
            // edit or delete guest entry
            user_id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            guest_id = getIntent().getStringExtra("guestid");
            guestname.setText(getIntent().getStringExtra("guestname"));
            guestplace.setText(getIntent().getStringExtra("guestplace"));

            save = new MaterialButton(MainActivity4.this);
            save.setText("Save");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);
            params.leftMargin = 10;
            params.rightMargin = 10;
            params.gravity = Gravity.CENTER;
            save.setLayoutParams(params);
            save.setOnClickListener(edit_click);
            delete = new MaterialButton(MainActivity4.this);
            delete.setText("Delete");
            delete.setLayoutParams(params);
            delete.setOnClickListener(delete_click);
            save.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            delete.setTextColor(Color.parseColor("#FFFFFF"));
            delete.setTypeface(font);
            save.setTextColor(Color.parseColor("#FFFFFF"));
            save.setTypeface(font);
            delete.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity4.this, R.color.bhagwa));
            delete.setCornerRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()));
            save.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity4.this, R.color.bhagwa));
            save.setCornerRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()));
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            save.setPadding(0, padding, 0, padding);
            delete.setPadding(0, padding, 0, padding);
            delete.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            proceed.addView(delete);
            proceed.addView(save);
        }
    }

    View.OnClickListener save_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            save.setClickable(false);
            String name = guestname.getText().toString();
            String place = guestplace.getText().toString();
            String url = "https://amantran.herokuapp.com/" + type + "/" + user_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    save.setClickable(true);
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
            save.setClickable(false);
            delete.setClickable(false);
            String name = guestname.getText().toString();
            String place = guestplace.getText().toString();
            String url = "https://amantran.herokuapp.com/" + user_id + "/" + type + "/" + guest_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    save.setClickable(true);
                    delete.setClickable(true);
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
            save.setClickable(false);
            delete.setClickable(false);
            String url = "https://amantran.herokuapp.com/" + user_id + "/" + type + "/" + guest_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("type", type);
                    intent.putExtra("id", user_id);
                    save.setClickable(true);
                    delete.setClickable(true);
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