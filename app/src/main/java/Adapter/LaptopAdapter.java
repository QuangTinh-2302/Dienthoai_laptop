package Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dienthoai_laptop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Model.Sanpham;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;

    public LaptopAdapter(Context context,ArrayList<Sanpham> arraylaptop){
        this.context = context;
        this.arraylaptop = arraylaptop;
    }
    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoder{
        public TextView txtlaptop,gialaptop,motalaptop;
        public ImageView imglaptop;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder = null;
        if(viewHoder == null){
            viewHoder = new ViewHoder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHoder.txtlaptop = view.findViewById(R.id.txtiddienthoai);
            viewHoder.gialaptop = view.findViewById(R.id.txtidgiadienthoai);
            viewHoder.motalaptop = view.findViewById(R.id.txtidmotadienthoai);
            viewHoder.imglaptop = view.findViewById(R.id.imgviewdienthoai);
            view.setTag(viewHoder);
        }else
        {
            viewHoder =(ViewHoder) view.getTag();
        }
        Sanpham sp =(Sanpham) getItem(i);
        viewHoder.txtlaptop.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoder.gialaptop.setText("Giá: "+decimalFormat.format(sp.getGiasp())+" VNĐ.");
        viewHoder.motalaptop.setMaxLines(2);
        viewHoder.motalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHoder.motalaptop.setText(sp.getMota());
        Picasso.get().load(sp.getHinhanhsp()).placeholder(R.drawable.load).error(R.drawable.error).into(viewHoder.imglaptop);
        return view;
    }
}
