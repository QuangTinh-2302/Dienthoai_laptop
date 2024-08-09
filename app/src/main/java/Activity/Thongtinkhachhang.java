package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dienthoai_laptop.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import util.check;
import util.server;

public class Thongtinkhachhang extends AppCompatActivity {
EditText ten, diachi,sdt, email;
Button trove, xacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtinkhachhang);
        AnhXa();
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(check.haveNetworkConnection(getApplicationContext()))
        {
            EventButton();
        }else{
            check.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối mạng.");
        }


    }

    private void EventButton() {
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenkh = ten.getText().toString().trim();
                String diachikh = diachi.getText().toString().trim();
                String sdtkh = sdt.getText().toString().trim();
                String emailkh = email.getText().toString().trim();
                if(tenkh.length() > 0 && diachikh.length() > 0 && sdtkh.length() > 0 && emailkh.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanttkh, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhangg",madonhang);
                            if(Integer.parseInt(madonhang) > 0)
                            {
                                RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, server.duongdanttdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.arraygiohang.clear();
                                            check.ShowToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu đơn hàng thành công.");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            check.ShowToast_Short(getApplicationContext(),"Trang chủ");
                                        }else{
                                            check.ShowToast_Short(getApplicationContext(),"Thêm dữ liệu đơn hàng thất bại");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < MainActivity.arraygiohang.size() ; i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.arraygiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.arraygiohang.get(i).getTensp());
                                                jsonObject.put("soluongsp",MainActivity.arraygiohang.get(i).getSoluongsp());
                                                jsonObject.put("giasanpham",MainActivity.arraygiohang.get(i).getGiasp());
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                request.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> param = new HashMap<String,String>();
                            param.put("tenkhachhang",String.valueOf(tenkh));
                            param.put("sdt",String.valueOf(sdtkh));
                            param.put("diachikh",String.valueOf(diachikh));
                            param.put("emailkh",String.valueOf(emailkh));
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else{
                    check.ShowToast_Short(getApplicationContext(),"Hãy điển đủ dữ liệu.");
                }
            }
        });
    }

    private void AnhXa() {
        ten = findViewById(R.id.edittexttenkh);
        diachi = findViewById(R.id.edittextdiachikh);
        sdt = findViewById(R.id.edittexttsdtkh);
        email = findViewById(R.id.edittexttemailkh);
        trove = findViewById(R.id.trovettkh);
        xacnhan = findViewById(R.id.xacnhanttkh);
    }
}