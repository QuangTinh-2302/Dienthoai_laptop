package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dienthoai_laptop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Activity.GiohangActivity;
import Activity.MainActivity;
import Model.Giohang;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoder{
        public TextView tenhang, giahang;
        public ImageView anhhang;
        public Button cong,tru,values;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHoder viewHoder;
        if(view == null){
            viewHoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHoder.tenhang = view.findViewById(R.id.txttenspgiohang);
            viewHoder.giahang = view.findViewById(R.id.giatrihang);
            viewHoder.anhhang = view.findViewById(R.id.idanhgiohang);
            viewHoder.tru = view.findViewById(R.id.btntru);
            viewHoder.cong = view.findViewById(R.id.btncong);
            viewHoder.values = view.findViewById(R.id.btnvaluesgiohang);
            view.setTag(viewHoder);
        }
        else{
            viewHoder =(ViewHoder) view.getTag();
        }
        Giohang giohang =(Giohang) getItem(i);
        viewHoder.tenhang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoder.giahang.setText(decimalFormat.format(giohang.getGiasp())+" VNĐ.");
        Picasso.get().load(giohang.getHinhanhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHoder.anhhang);
        viewHoder.values.setText(giohang.getSoluongsp()+"");
        int soluonghang =Integer.parseInt(viewHoder.values.getText().toString());
        if(soluonghang >= 10)
        {
            viewHoder.cong.setVisibility(View.INVISIBLE);
            viewHoder.tru.setVisibility(View.VISIBLE);
        } else if (soluonghang <=1 ) {
            viewHoder.tru.setVisibility(View.INVISIBLE);
        }
        else if (soluonghang >= 1) {
            viewHoder.cong.setVisibility(View.VISIBLE);
            viewHoder.tru.setVisibility(View.VISIBLE);
        }

        viewHoder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(viewHoder.values.getText().toString()) + 1;
                int slhientai = MainActivity.arraygiohang.get(i).getSoluongsp();
                long giahientai = MainActivity.arraygiohang.get(i).getGiasp();
                MainActivity.arraygiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai ;
                MainActivity.arraygiohang.get(i).setGiasp(giamoinhat);
//                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//                viewHoder.giahang.setText(decimalFormat.format(giamoinhat+" VNĐ.")); lỗi out app
                GiohangActivity.EvenUtils();
                notifyDataSetChanged();
            }
        });
        viewHoder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(viewHoder.values.getText().toString()) - 1;
                int slhientai = MainActivity.arraygiohang.get(i).getSoluongsp();
                long giahientai = MainActivity.arraygiohang.get(i).getGiasp();
                MainActivity.arraygiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai ;
                MainActivity.arraygiohang.get(i).setGiasp(giamoinhat);
//                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//                viewHoder.giahang.setText(decimalFormat.format(giamoinhat+" VNĐ.")); lỗi out app
                GiohangActivity.EvenUtils();
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
