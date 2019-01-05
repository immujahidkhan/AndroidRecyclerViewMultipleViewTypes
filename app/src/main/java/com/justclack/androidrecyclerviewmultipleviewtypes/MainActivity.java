package com.justclack.androidrecyclerviewmultipleviewtypes;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    MultiViewTypeAdapter adapter;
    ArrayList<Model> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new MultiViewTypeAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        ViewsData();
    }

    private void ViewsData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://incometax.aliakhtaradnan.com/api/chapters",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(TAG, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jObject = new JSONObject(response);
                            if (jObject.has("response_code")) {
                                String response_code;
                                response_code = jObject.getString("response_code");
                                if (response_code.equals("200")) {
                                    JSONArray responseArrayChapters = jObject.getJSONArray("Chapters");
                                    for (int i = 0; i < responseArrayChapters.length(); i++) {
                                        JSONObject jsonObject = responseArrayChapters.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        list.add(new Model(Model.TEXT_TYPE, name, 0));
                                        list.add(new Model(Model.IMAGE_TYPE, "Hi. I display a cool image too besides the omnipresent TextView.", R.drawable.wtc));
                                        list.add(new Model(Model.AUDIO_TYPE, "Hey. Pressing the FAB button will playback an audio file on loop.", R.raw.sound));
                                        list.add(new Model(Model.IMAGE_TYPE, "Hi again. Another cool image here. Which one is better?", R.drawable.snow));

                                    }
                                    JSONArray responseArraySchedules = jObject.getJSONArray("Schedules");
                                    for (int i = 0; i < responseArraySchedules.length(); i++) {
                                        JSONObject jsonObject = responseArraySchedules.getJSONObject(i);
                                        String name = jsonObject.getString("name");
                                        list.add(new Model(Model.TEXT_TYPE, name, 0));
                                        list.add(new Model(Model.IMAGE_TYPE, "Hi. I display a cool image too besides the omnipresent TextView.", R.drawable.wtc));
                                        list.add(new Model(Model.AUDIO_TYPE, "Hey. Pressing the FAB button will playback an audio file on loop.", R.raw.sound));
                                        list.add(new Model(Model.IMAGE_TYPE, "Hi again. Another cool image here. Which one is better?", R.drawable.snow));

                                    }
                                    adapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(MainActivity.this, "Error 404", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIkMnkkMTAkTHNpVjFCdktmb2FQUmZHVE14SEY3dUt0RTRXdDJua04yalNTMDk5OW52SjguOW9aQW9RM2UiLCJzdWIiOjIsImlhdCI6MTU0MzkyMTEzNX0.R9t75ZtxQW07GfvzLl077MXv1G91tZvaiyX_w-pLE1s");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}