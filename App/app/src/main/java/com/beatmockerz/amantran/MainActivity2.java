package com.beatmockerz.amantran;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
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
                        Button btn = new Button(MainActivity2.this);
                        btn.setText(other_users.get(i));
                        btn.setId(i);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = 10;
                        params.rightMargin = 10;
                        params.gravity = Gravity.CENTER_VERTICAL;
                        btn.setLayoutParams(params);
                        btn.setOnClickListener(other_click);
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
}