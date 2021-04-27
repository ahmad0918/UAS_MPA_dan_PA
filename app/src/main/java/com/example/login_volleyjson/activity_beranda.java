package com.example.login_volleyjson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class activity_beranda extends AppCompatActivity {

    private TextView txtData;
    private RequestQueue mQueue;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        mQueue = Volley.newRequestQueue(this);
        txtData = findViewById(R.id.txtData);
        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        uraiJson();
    }

    public void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Logout");
        builder.setMessage("Anda yakin ingin keluar ? ");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void uraiJson() {
        String nama = getIntent().getStringExtra("NAME_SESSION");
        String url = "http://192.168.0.15/PHP-Lanjutan/MPA/pertemuan%2012/APIData.php?nama=" + nama;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txtData.append("Nama : "+response.getString("nama") + "\n" + "Hobby : ");
                            JSONArray hobi = response.getJSONArray("kegemaran");
                            for (int i = 0; i < hobi.length(); i++) {
                                txtData.append(hobi.getJSONObject(i).getString("hobby")+ ", ");

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity_beranda.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(activity_beranda.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }

    public void logout(View view) {
        logout(this);
    }
}