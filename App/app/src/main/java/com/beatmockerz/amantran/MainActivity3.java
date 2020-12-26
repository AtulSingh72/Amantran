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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity3 extends AppCompatActivity {

    TextView text;
    ListView list;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        text = (TextView) findViewById(R.id.text);
        list = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if(getIntent().getStringExtra("type").equals("friends")) {
            text.setText("Friends List");
            String user_id = getIntent().getStringExtra("id");
            String url = "http://192.168.1.5:8000/friends/" + user_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity3.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                                intent.putExtra("type", "friends");
                                intent.putExtra("edit", "false");
                                intent.putExtra("id", user_id);
                                startActivity(intent);
                                finish();
                            }
                        });
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("friends");
                        String guest_list[] = new String[jsonArray.length()];
                        String guest_id[] = new String[jsonArray.length()];
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject new_obj = new JSONObject(jsonArray.getString(i));
                            guest_list[i] = new_obj.getString("name");
                            guest_id[i] = new_obj.getString("_id");
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1, guest_list);
                        list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", error.toString());
                }
            });
            requestQueue.add(stringRequest);
        }
        else {
            text.setText("Family List");
            String user_id = getIntent().getStringExtra("id");
            String url = "http://192.168.1.5:8000/family/" + user_id;
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity3.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                                intent.putExtra("edit", "false");
                                intent.putExtra("type", "family");
                                intent.putExtra("id", user_id);
                                startActivity(intent);
                                finish();
                            }
                        });
                        JSONObject obj = new JSONObject(response);
                        JSONArray jsonArray = obj.getJSONArray("family");
                        String guest_list[] = new String[jsonArray.length()];
                        String guest_id[] = new String[jsonArray.length()];
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject new_obj = new JSONObject(jsonArray.getString(i));
                            guest_list[i] = new_obj.getString("name");
                            guest_id[i] = new_obj.getString("_id");
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1, guest_list);
                        list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", error.toString());
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}