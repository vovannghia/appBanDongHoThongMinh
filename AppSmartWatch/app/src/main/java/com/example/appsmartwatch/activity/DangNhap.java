package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.KhachHangModel;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.example.appsmartwatch.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangNhap extends AppCompatActivity {
    private Toolbar toolbarDangNhap;
    private EditText editTextTenDN, editTextMatKhauDN;
    private Button buttonDangNhap;
    private TextView textViewDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        widget();
        setMyActionBar();
    }

    private void widget() {
        toolbarDangNhap = findViewById(R.id.toolbarDN);
        editTextTenDN = findViewById(R.id.editTextHoTenDN);
        editTextMatKhauDN = findViewById(R.id.editTextMatKhauDN);
        buttonDangNhap = findViewById(R.id.buttonDangNhap);
        textViewDK = findViewById(R.id.textViewDK);

        textViewDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DangKy.class);
                startActivity(intent);
            }
        });

        //Sự kiện khi click vào Button "Đăng nhập"
        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
            setDangNhap();//cài đặt chức năng đăng nhập
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Check your internet connection");
            finish();
        }
    }

    private void setMyActionBar() {
        setSupportActionBar(toolbarDangNhap);
        getSupportActionBar().setHomeButtonEnabled(true);//Bật nút Home cho ActionBar
        toolbarDangNhap.setNavigationIcon(android.R.drawable.ic_media_previous);//set Icon cho ActionBar
        //Cài đặt sự kiện click
        toolbarDangNhap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//Đóng Activity hiện tại
            }
        });
    }

    private void setDangNhap(){
        //Sự kiện khi click button "Đăng nhập"
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tendn = editTextTenDN.getText().toString().trim();
                final String matkhau = editTextMatKhauDN.getText().toString().trim();

                if( tendn.length() > 0 && matkhau.length() > 0 ){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathdangnhap,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response != null && response.length() != 2 ) {
                                        int makh = 0;
                                        String tenkh = "";
                                        String matkhau = "";
                                        String dienthoai = "";
                                        String email = "";
                                        String diachi = "";
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                                makh = jsonObject.getInt("makh");
                                                tenkh = jsonObject.getString("tenkh");
                                                matkhau = jsonObject.getString("matkhau");
                                                dienthoai = jsonObject.getString("dienthoai");
                                                email = jsonObject.getString("email");
                                                diachi = jsonObject.getString("diachi");

                                                KhachHangModel khachHangModel = new KhachHangModel(makh, tenkh, matkhau, dienthoai, email, diachi);

                                                //Lưu thong tin dang nhap
                                                MainActivity.mapKhachHang.put(Integer.parseInt("TaiKhoan"), khachHangModel);
                                            }

                                            //Chuyển về màn hình trang chủ
                                            Toast.makeText(DangNhap.this, "Đăng nhập thành công, xin chào: " + tenkh + " !", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        Toast.makeText(DangNhap.this, "Tài khoản đăng nhập không tồn tại, vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(getClass().getSimpleName(), "Lỗi: "+ error.toString());
                                    CheckConnect.ShowToast_Short(getApplicationContext(), "Lỗi khi get data table KhachHang !!!");
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("TenDN",tendn);
                            params.put("MatKhau",matkhau);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(DangNhap.this, "Thông tin đăng nhập không được rỗng !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
