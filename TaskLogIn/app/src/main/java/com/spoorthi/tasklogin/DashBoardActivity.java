package com.spoorthi.tasklogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    String dashResults = "http://52.224.222.102:9245/api/Dashboard" ;
    ProgressDialog progressDialog;
    JSONObject jobjj;
    String id;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<ItemDataModel> itemDataModelList = new ArrayList<ItemDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Intent i = getIntent();
        id = i.getStringExtra("id");

        if(!id.isEmpty()) {

            jobjj = new JSONObject();
            try {
                jobjj.put("Id", "" + id);

                Log.e("jobjj",""+jobjj);

                requestList(jobjj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter(itemDataModelList,this);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    private void requestList(String data)
    {
        progressDialog = new ProgressDialog(DashBoardActivity.this);
        if (progressDialog != null) {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        new RequestDash()
        {
            @Override
            public void onPostExecute(String result)
            {
                if (result!=null)
                {
                    Log.e("result",""+result);

                    JSONObject jsonObject = null;
                    try {
//                        jsonObject = new JSONObject(result);
//                        boolean status = (boolean) jsonObject.get("Details");
                        JSONObject json = new JSONObject(result);
                        JSONArray jArray = json.getJSONArray("Details");

                        System.out.println("*****JARRAY*****" + jArray.length());

                        for(int i=0; i<jArray.length(); i++){
                            JSONObject json_data = jArray.getJSONObject(i);

                            String name = json_data.getString("Name");
                            String img = json_data.getString("Image");
                            String Lat = json_data.getString("Lat");
                            String lang = json_data.getString("Long");
                            ItemDataModel itemDataModel = new ItemDataModel(name,Lat,lang,img);

                            Log.e("name",""+name);

                            itemDataModelList.add(itemDataModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }

        }.execute(dashResults,data);
    }
}
