package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dienthoai_laptop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Activity.chitiet_sp;
import Model.Sanpham;
import util.check;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.itemHoder> {

    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public itemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham,null);
        itemHoder itHoder = new itemHoder(v);
        return itHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHoder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" VND.");
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imgsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class itemHoder extends RecyclerView.ViewHolder{
        public ImageView imgsanpham;
        public TextView txttensp,txtgiasp;

        public itemHoder( View itemView) {
            super(itemView);
            imgsanpham = (ImageView) itemView.findViewById(R.id.imgviewsp);
            txttensp = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            txtgiasp = (TextView) itemView.findViewById(R.id.textviewgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, chitiet_sp.class);
                    intent.putExtra("thongtinsp",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    check.ShowToast_Short(context,arraysanpham.get(getPosition()).tensanpham);
                }
            });
        }
    }
}
