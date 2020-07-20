package com.example.appsmartwatch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.appsmartwatch.R;
import com.example.appsmartwatch.model.KhachHangModel;
import com.example.appsmartwatch.ultil.CheckConnect;
import com.example.appsmartwatch.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    private Toolbar toolbarDangKy;
    private EditText editTextTen, editTextMatKhau, editTextEmail, editTextDienThoai, editTextDiaChi;
    private Button buttonXacNhanDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_dangky);

        widget();
        ActionToolBar();
    }

    private void widget() {
        toolbarDangKy = findViewById(R.id.toolbarDangKy);
        editTextTen = findViewById(R.id.editTextHoTenDK);
        editTextMatKhau = findViewById(R.id.editTextMatKhauDK);
        editTextEmail = findViewById(R.id.editTextEmailDK);
        editTextDienThoai = findViewById(R.id.editTextDienThoaiDK);
        editTextDiaChi = findViewById(R.id.editTextDiaChiDK);
        buttonXacNhanDangKy = findViewById(R.id.buttonXacNhanDangKy);

        //Sự kiện khi click vào Button "Đăng ký"
        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
            setDangKyTaiKhoan();//cài đặt chức năng đăng nhập
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(), "Vui lòng kiểm tra kết nối Internet !");
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDangKy);
        getSupportActionBar().setHomeButtonEnabled(true);//Bật nút Home cho ActionBar
        //Cài đặt sự kiện click
        toolbarDangKy.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//Đóng Activity hiện tại
            }
        });
    }

    private void setDangKyTaiKhoan(){
        //Sự kiện khi click button "Đăng ký"
        buttonXacNhanDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenKH = editTextTen.getText().toString().trim();
                final String matKhau = editTextMatKhau.getText().toString().trim();
                final String dienThoai = editTextDienThoai.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String diaChi = editTextDiaChi.getText().toString().trim();

                if( tenKH.length() > 0 && matKhau.length() > 0 && dienThoai.length() > 0 && email.length() > 0 && diaChi.length() > 0){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathdangky,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success") ) {
                                        Toast.makeText(DangKy.this, "Đăng ký thành công !", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(DangKy.this, "Mời đăng nhập vào tài khoản !", Toast.LENGTH_LONG).show();
                                            startActivity(intent);
                                    }else{
                                        Toast.makeText(DangKy.this, "Lỗi khi thêm KhachHang !", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(getClass().getSimpleName(), "Lỗi: "+ error.toString());
                                    CheckConnect.ShowToast_Short(getApplicationContext(), "Lỗi khi thêm KhachHang !!!");
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("tenKH",tenKH);
                            params.put("matKhau",matKhau);
                            params.put("dienThoai",dienThoai);
                            params.put("email",email);
                            params.put("diaChi",diaChi);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(DangKy.this, "Thông tin đăng ký không được rỗng !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

