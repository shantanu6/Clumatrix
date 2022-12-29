package com.developer.example.clumatrix.menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.example.clumatrix.R;
import com.developer.example.clumatrix.adapter.ApiAdapter;
import com.developer.example.clumatrix.adapter.ApiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiActivity extends AppCompatActivity {

    String TAG="API";
    EditText edtName;
    Button btnSubmit;
    CardView cardViewApi;
    RecyclerView recylerApi;
    ArrayList<ApiModel> apiModel;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        edtName=findViewById(R.id.edtName);
        btnSubmit=findViewById(R.id.btnSubmit);
        cardViewApi=findViewById(R.id.cardViewApi);
        recylerApi=findViewById(R.id.recylerApi);
        recylerApi.setLayoutManager(new LinearLayoutManager(this));

        pd=new ProgressDialog(this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) ApiActivity.this.getSystemService(ApiActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnSubmit.getWindowToken(), 0);

                if(!edtName.getText().toString().equals("")){
                    submitData(edtName.getText().toString());
                    edtName.setText("");
                }else{
                    Toast.makeText(ApiActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    cardViewApi.setVisibility(View.GONE);
                }
            }
        });
    }

    void submitData(String name){

        pd.show();

        RequestQueue req = Volley.newRequestQueue(this);
        String url ="https://btl.cluematrix.com/api/postData";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("message").equals("Success")){
                        getData();
                    }else {
                        pd.dismiss();
                        Toast.makeText(ApiActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApiActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onError: "+error);
                Toast.makeText(ApiActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap params = new HashMap<String,String>();
                params.put("rf_id",name);
                return params;
            }
        };

        req.add(stringRequest);
    }

    void getData(){

        pd.dismiss();

        RequestQueue req = Volley.newRequestQueue(this);
        String url ="https://btl.cluematrix.com/api/get";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    Log.d(TAG, "jsonArray: "+jsonArray.getJSONObject(0));
                    
                    apiModel=new ArrayList<>();
                    
                    for(int i=0;i<jsonArray.length();i++){
                        ApiModel am=new ApiModel();
                        am.setRf_id(jsonArray.getJSONObject(i).getString("rf_id"));
                        am.setCreated_at(jsonArray.getJSONObject(i).getString("created_at"));
                        am.setUpdated_at(jsonArray.getJSONObject(i).getString("updated_at"));
                        apiModel.add(am);
                    }

                    cardViewApi.setVisibility(View.VISIBLE);
                    ApiAdapter recycleAdapter=new ApiAdapter(ApiActivity.this,apiModel);
                    recylerApi.setAdapter(recycleAdapter);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApiActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onError: "+error);
                Toast.makeText(ApiActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        req.add(stringRequest);
    }

}