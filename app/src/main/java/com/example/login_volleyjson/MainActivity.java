package com.example.login_volleyjson;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edt_nama, edt_password;
    private String nama,password ;
    Button login;
    private String url = "http://192.168.0.15/PHP-Lanjutan/MPA/pertemuan%2012/APILogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nama = password = "";
        edt_nama = findViewById(R.id.nama);
        edt_password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        nama = edt_nama.getText().toString().trim();
        password = edt_password.getText().toString().trim();

        if (!nama.equals("") && !password.equals("")) {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.contains("success")) {
                        Intent inten = new Intent(getApplicationContext(),activity_beranda.class);
                        inten.putExtra("NAME_SESSION", nama);
                        startActivity(inten);
                    }else {
                        pesan("nama atau password salah");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pesan(error.toString().trim());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("nama", nama);
                    data.put("password", password);

                    return data;
                }
            };
            Volley.newRequestQueue(this).add(request);
        } else {
            pesan("Untuk login, masukkan nama dan password Anda.");
        }
    }
    public void pesan(String isi) {
        Toast.makeText(this, isi, Toast.LENGTH_SHORT).show();
    }
}