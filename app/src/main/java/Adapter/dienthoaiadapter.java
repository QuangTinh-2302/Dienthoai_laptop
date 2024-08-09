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

public class dienthoaiadapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydienthoai;

    public dienthoaiadapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHoder
    {
        public TextView txtdienthoai,giadienthoai,motadienthoai;
        public ImageView imgdienthoai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder = null;
        if (viewHoder == null){
            viewHoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHoder.txtdienthoai = view.findViewById(R.id.txtiddienthoai);
            viewHoder.giadienthoai = view.findViewById(R.id.txtidgiadienthoai);
            viewHoder.motadienthoai = view.findViewById(R.id.txtidmotadienthoai);
            viewHoder.imgdienthoai = view.findViewById(R.id.imgviewdienthoai);
            view.setTag(viewHoder);
        }else{
            viewHoder = (ViewHoder) view.getTag();
        }
        Sanpham sanpham =(Sanpham) getItem(i);
        viewHoder.txtdienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoder.giadienthoai.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" VND.");
        viewHoder.motadienthoai.setMaxLines(2);
        viewHoder.motadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHoder.motadienthoai.setText(sanpham.getMota());
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHoder.imgdienthoai);
        return view;
    }
}
