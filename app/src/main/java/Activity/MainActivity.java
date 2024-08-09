package Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.Toolbar;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dienthoai_laptop.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.LoaispAdapter;
import Adapter.SanphamAdapter;
import Model.Giohang;
import Model.Loaisp;
import Model.Sanpham;
import util.check;
import util.server;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewflipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    ArrayList<Sanpham> mangsp;
    SanphamAdapter sanphamAdapter;
    public static ArrayList<Giohang> arraygiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AnhXa();
        if(check.haveNetworkConnection(getApplicationContext()))
        {
            Actionbar();
            ActionViewFlipper();
            GetDLloaisp();
            GetDLsanphammoinhat();
            GetOnItemListview();
        }else
        {
            check.ShowToast_Short(this,"Bạn kiểm tra lại kết nối mạng");
            finish();
        }
    }
    @Override
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
    private void GetOnItemListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        if(check.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
                        }else{
                            check.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(check.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(i).getId());
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intent);
                        }else{
                            check.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng!");
                        }

                        break;
                    case 2:
                        if(check.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(i).getId());
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(intent);
                        }else{
                            check.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng!");
                        }
                        break;
                    case 3:
                        if(check.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,Lienhe.class);
                            startActivity(intent);
                        }else{
                            check.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(check.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this,thongtin.class);
                            startActivity(intent);
                        }else{
                            check.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối mạng!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDLsanphammoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null)
                {
                    int id = 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String hinhanhsp = "";
                    String motasp = "";
                    int idsanpham = 0;
                    for (int i = 0 ; i <response.length() ; i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensanpham");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("mota");
                            idsanpham = jsonObject.getInt("idsanpham");
                            mangsp.add(new Sanpham(id,tensp,giasp,hinhanhsp,motasp,idsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDLloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String tenloaisanpham = "";
                    String hinhanh = "";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
                            hinhanh = jsonObject.getString("hinhanh");
                            mangloaisp.add(new Loaisp(id, tenloaisanpham, hinhanh));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Volley", "JSON Parsing error: " + e.getMessage());
                        }
                    }
                    mangloaisp.add(new Loaisp(0, "Liên hệ", "https://cdn-icons-png.flaticon.com/128/3095/3095583.png"));
                    mangloaisp.add(new Loaisp(0, "Thông Tin", "https://cdn-icons-png.flaticon.com/128/665/665049.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Volley", "Error response: " + error.getMessage());
                check.ShowToast_Short(MainActivity.this, error.toString());
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
//    private void GetDLloaisp() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanloaisp, new  Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response != null)
//                {
//                    for(int i = 0 ; i < response.length() ; i++)
//                    {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
//                            hinhanh = jsonObject.getString("hinhanh");
//                            mangloaisp.add(new Loaisp(id,tenloaisanpham,hinhanh));
//                            loaispAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    mangloaisp.add(new Loaisp(0,"Liên hệ","https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2020/4/7/796660/Ronaldo.jpg"));
//                    mangloaisp.add(new Loaisp(0,"Thông Tin","https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2020/4/7/796660/Ronaldo.jpg"));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                check.ShowToast_Short(getApplicationContext(),error.toString());
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void ActionViewFlipper() {
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("https://cdn.xtmobile.vn/vnt_upload/news/11_2023/28/dien-thoai-chup-anh-dep-xtmobile-11.jpg");
        quangcao.add("https://cdn.tgdd.vn/Files/2016/06/20/844343/thumb1_800x450_800x450.jpg");
        quangcao.add("https://suachualaptop24h.com/upload_images/images/2023/07/12/nhung-mau-laptop-man-hinh-12-inch-dep-nhat-hien-nay-02.jpg");
        for (String url : quangcao)
        {
            ImageView imageView = new ImageView(this);
            Picasso.get().load(url).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewflipper.addView(imageView);
        }
        viewflipper.setFlipInterval(3000);
        viewflipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this,R.anim.slide_out_right);
        viewflipper.setInAnimation(animation_slide_in);
        viewflipper.setOutAnimation(animation_slide_out);
    }


    private void AnhXa()
    {
        toolbar = findViewById(R.id.manhinhchinh);
        viewflipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.Navigationview);
        listView = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerLayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","https://png.pngtree.com/png-vector/20190129/ourlarge/pngtree-home-icon-graphic-design-template-vector-png-image_358126.jpg"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispAdapter);
        mangsp = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);
        if(arraygiohang != null)
        {

        }else {
            arraygiohang = new ArrayList<>();
        }
    }
}
