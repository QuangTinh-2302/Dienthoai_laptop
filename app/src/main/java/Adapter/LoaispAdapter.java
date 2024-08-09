package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dienthoai_laptop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import Model.Loaisp;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrraylistloaisp;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrraylistloaisp, Context context) {
        this.arrraylistloaisp = arrraylistloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ViewHoder{
        TextView txtloaisp;
        ImageView imgloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder = null;
        if(view == null)
        {
            viewHoder = new ViewHoder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHoder.txtloaisp =(TextView) view.findViewById(R.id.textviewloaisp);
            viewHoder.imgloaisp = view.findViewById(R.id.imageloaisp);
            view.setTag(viewHoder);
        }
        else {
            viewHoder =(ViewHoder) view.getTag();
        }
        Loaisp loaisp =(Loaisp) getItem(i);
        viewHoder.txtloaisp.setText(loaisp.getTenloaisanpham());
        Picasso.get().load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHoder.imgloaisp);
        return view;
    }
}
