package com.beatmockerz.amantran;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity2 extends AppCompatActivity {

    String user_id;
    TextView userName;
    String user_name="";
    ArrayList<String> other_users = new ArrayList<>();
    ArrayList<String> other_ids = new ArrayList<>();
    LinearLayout other_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user_id = getIntent().getStringExtra("id");
        String url = "http://192.168.1.5:8000/userlist";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject response_get = new JSONObject(response);
                    JSONObject obj = new JSONObject(response_get.getString("userList"));
                    user_name = obj.getString(user_id);
                    Iterator<String> keys = obj.keys();
                    while(keys.hasNext()) {
                        String id = keys.next();
                        String name = obj.getString(id);
                        if(id.equals(user_id)) continue;
                        other_users.add(name);
                        other_ids.add(id);
                    }
                    userName = (TextView) findViewById(R.id.userName);
                    userName.setText(user_name);
                    other_list = (LinearLayout) findViewById(R.id.other_list);
                    for(int i = 0; i < other_users.size(); i++)
                    {
                        MaterialButton btn = new MaterialButton(MainActivity2.this);
                        btn.setText(other_users.get(i));
                        btn.setId(i);
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
                        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics());
                        int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                        btn.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity2.this, R.color.bg_dark));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                        params.leftMargin = 10;
                        params.rightMargin = 10;
                        params.gravity = Gravity.CENTER_VERTICAL;
                        btn.setLayoutParams(params);
                        btn.setOnClickListener(other_click);
                        btn.setTextColor(Color.parseColor("#FFFFFF"));
                        Typeface font = ResourcesCompat.getFont(MainActivity2.this, R.font.semibold);
                        btn.setTypeface(font);
                        btn.setCornerRadius(radius);
                        other_list.addView(btn);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                user_name = "Some Error Occured!";
            }
        });
        requestQueue.add(stringRequest);
    }

    View.OnClickListener other_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int view_id = v.getId();
            Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
            intent.putExtra("id", other_ids.get(view_id));
            startActivity(intent);
            finish();
        }
    };

    public void familyClick(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("type", "family");
        intent.putExtra("id", user_id);
        startActivity(intent);
        finish();
    }

    public void friendsClick(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("type", "friends");
        intent.putExtra("id", user_id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = getSharedPreferences("Amantran", Context.MODE_PRIVATE);
        String owner_id = sharedPreferences.getString("name", "-1");
        if(!owner_id.equals(user_id)) {
            Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
            intent.putExtra("id", owner_id);
            startActivity(intent);
            finish();
        }
    }
}