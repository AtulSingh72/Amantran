package com.beatmockerz.amantran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button submit;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        submit = (Button) findViewById(R.id.submit);
        sharedPreferences = getSharedPreferences("Amantran", Context.MODE_PRIVATE);
        String person_found = sharedPreferences.getString("name", "-1");
        if(!person_found.equals("-1")) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("id", person_found);
            startActivity(intent);
            finish();
        }
    }

    public void submitName(View view) throws JSONException {
        submit.setClickable(false);
        String person_name = name.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://amantran.herokuapp.com/name";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", obj.getString("id"));
                    editor.commit();
                    Log.i("Name ID", obj.getString("id"));
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("id", obj.getString("id"));
                    submit.setClickable(true);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("POST Response", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", person_name);
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
}