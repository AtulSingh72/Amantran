package com.beatmockerz.amantran;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        submit = (Button) findViewById(R.id.submit);

    }

    public void submitName(View view) {
        String person_name = name.getText().toString();
        JSONObject data = new JSONObject();
        data.put("name", person_name);
    }
}