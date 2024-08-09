package Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.dienthoai_laptop.R;

import java.text.DecimalFormat;

import Adapter.GiohangAdapter;
import util.check;

public class GiohangActivity extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        Acctiontoolbar();
        CheckData();
        EvenUtils();
        CatchOnitemlistview();
        EvenButton();
    }

    private void EvenButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arraygiohang.size() > 0)
                {
                    Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);
                    startActivity(intent);
                }else{
                    check.ShowToast_Short(getApplicationContext(),"Bạn chưa có sản phẩm nào.");
                }
            }
        });
    }

    private void CatchOnitemlistview() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                android.app.AlertDialog.Builder builder= new android.app.AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm!");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.arraygiohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.arraygiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EvenUtils();
                            if(MainActivity.arraygiohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                giohangAdapter.notifyDataSetChanged();
                                EvenUtils();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUtils() {
        long tongtien = 0;
        for(int i = 0 ; i < MainActivity.arraygiohang.size() ; i++)
        {
            tongtien = tongtien + MainActivity.arraygiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + " VNĐ.");
    }

    private void CheckData() {
        if(MainActivity.arraygiohang.size() <= 0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void Acctiontoolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.txtthongbaogiohang);
        btnthanhtoan = findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua = findViewById(R.id.btntieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        txttongtien = findViewById(R.id.tonggiatrihang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this,MainActivity.arraygiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}