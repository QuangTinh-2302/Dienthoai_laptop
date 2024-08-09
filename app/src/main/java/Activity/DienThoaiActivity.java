package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.dienthoaiadapter;
import Model.Sanpham;
import util.check;
import util.server;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView listViewdt;
    dienthoaiadapter dtadapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    mHander mHander;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        if(check.haveNetworkConnection(getApplicationContext()))
        {
            Getidloaisp();
            Actiontoolbar();
            GetData(page);
            Loadmoredata();
        }else {
            check.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại internet");
            finish();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menugh)
        {
            Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Loadmoredata() {
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), chitiet_sp.class);
                intent.putExtra("thongtinsp",mangdt.get(i));
                startActivity(intent);

            }
        });
        listViewdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firtitem, int visibleitem, int totalitem) {
                if(firtitem + visibleitem == totalitem && totalitem != 0 && isLoading == false && limitdata == false)
                {
                    isLoading = true;
                    Threadata threadata = new Threadata();
                    threadata.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = server.duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tendt = "";
                int giadt = 0;
                String hinhanhdt = "";
                String mota = "";
                int idsp = 0;
                if(response != null && response.length() != 2)
                {
                    listViewdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensanpham");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("mota");
                            idsp = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id,tendt,giadt,hinhanhdt,mota,idsp));
                            dtadapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    limitdata = true;
                    listViewdt.removeFooterView(footerview);
                    check.ShowToast_Short(DienThoaiActivity.this,"Đã hết dữ liệu");
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
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Getidloaisp() {
        iddt = getIntent().getIntExtra("idloaisp",-1);
    }

    private void AnhXa() {
        toolbardt =(Toolbar)findViewById(R.id.toolbardienthoai);
        listViewdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dtadapter = new dienthoaiadapter(getApplicationContext(),mangdt);
        listViewdt.setAdapter(dtadapter);
        LayoutInflater inflater =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progessbar,null);
        mHander = new mHander();
    }
    public class mHander extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listViewdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
        }
    }
    public class Threadata extends Thread{
        @Override
        public void run() {
            mHander.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = mHander.obtainMessage(1);
            mHander.sendMessage(message);
            super.run();
        }
    }
}