package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dienthoai_laptop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import Model.Giohang;
import Model.Sanpham;

public class chitiet_sp extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgviewchitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int idchitiet = 0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhanhchitiet = "";
    String motachitiet = "";
    int idsp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chitiet_sp);
            AnhXa();
            Acctiontoolbar();
            GetInformation();
            CatchEventspiner();
            EventButton();
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.arraygiohang.size() > 0)
                {
                    int sl1 = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exitst = false;
                    for(int i = 0 ; i < MainActivity.arraygiohang.size() ; i++){
                        if(MainActivity.arraygiohang.get(i).getIdsp() == idchitiet){
                            MainActivity.arraygiohang.get(i).setSoluongsp(MainActivity.arraygiohang.get(i).getSoluongsp() + sl1);
                            if (MainActivity.arraygiohang.get(i).getSoluongsp() >= 10)
                            {
                                MainActivity.arraygiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.arraygiohang.get(i).setGiasp(giachitiet * MainActivity.arraygiohang.get(i).getSoluongsp());
                            exitst = true;
                        }
                    }
                    if(exitst == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long tonggiasp = giachitiet * soluong;
                        MainActivity.arraygiohang.add(new Giohang(idchitiet,tenchitiet,tonggiasp,hinhanhchitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long tonggiasp = giachitiet * soluong;
                    MainActivity.arraygiohang.add(new Giohang(idchitiet,tenchitiet,tonggiasp,hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventspiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham =(Sanpham) getIntent().getSerializableExtra("thongtinsp");
        idchitiet = sanpham.getId();
        tenchitiet = sanpham.getTensanpham();
        giachitiet = sanpham.getGiasp();
        hinhanhchitiet = sanpham.getHinhanhsp();
        motachitiet = sanpham.getMota();
        idsp = sanpham.getIdsanpham();
        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(giachitiet) + " VNĐ.");
        txtmota.setText(motachitiet);
        Picasso.get().load(hinhanhchitiet)
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(imgviewchitiet);

    }

    private void Acctiontoolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarchitiet = findViewById(R.id.idtoolbarchitietsp);
        imgviewchitiet = findViewById(R.id.imgviewchitietsp);
        txtten = findViewById(R.id.tenchitietsanpham);
        txtgia = findViewById(R.id.giachitietsanpham);
        txtmota = findViewById(R.id.motachitietsanpham);
        spinner = findViewById(R.id.spiner);
        btndatmua = findViewById(R.id.buttonthemgiohang);
    }
}