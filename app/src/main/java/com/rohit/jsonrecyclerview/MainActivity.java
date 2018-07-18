package com.rohit.jsonrecyclerview;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Model> mUserList;

    private String TAG = MainActivity.class.getSimpleName();
    ProgressDialog progressDialog;
    ListView mListView;

    // Server url to get the Json data
    String reqUrl = "http://pusuluribalaji66.000webhostapp.com/RetrofitExample/public/user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserList = new ArrayList<>();
        mListView = findViewById(R.id.listView);


        new GetUsers().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class GetUsers extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing())
                progressDialog.dismiss();


            ListAdapter listAdapter = new ListAdapter(MainActivity.this,mUserList);
            mListView.setAdapter(listAdapter);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(reqUrl);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray article = jsonObject.getJSONArray("users");

                    for (int i = 0; i < article.length(); i++) {
                        JSONObject jsonObject1 = article.optJSONObject(i);
                        Model mUser = new Model();
                        mUser.setId(jsonObject1.getString("id"));
                        mUser.setEmail(jsonObject1.getString("email"));
                        mUser.setName(jsonObject1.getString("name"));
                        mUser.setGender(jsonObject1.getString("gender"));
                        mUserList.add(mUser);
                    }
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "parser error", Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "can't get json file", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
    }

}
