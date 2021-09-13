package com.devsaidur.saidur.playnearn.ui.home.adapters;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.ui.home.datamodel.TopernrModel;

import java.util.List;

public class TopernrAdapter extends RecyclerView.Adapter<TopernrAdapter.TEVH> {


    Context context;
    Application application;
    List<TopernrModel> topernrsList;

    public TopernrAdapter(Context context,  List<TopernrModel> topernrsList) {
        this.context = context;
        this.topernrsList = topernrsList;
    }

    @NonNull
    @Override
    public TEVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lay_item_topernr, parent, false);
        TEVH vh = new TEVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TEVH holder, int position) {
        if (topernrsList != null) {

            TopernrModel ct = topernrsList.get(position);
            holder.topernr_nameShowTv.setText(ct.getUserModel().getName());
            holder.topernr_earnShowTv.setText(ct.getUserModel().getEarnamount());
            holder.topernr_joinShowTv.setText(ct.getUserModel().getJoindate());
            Glide.with(holder.itemView)
                    .load(ct.getUserModel().getImage())
                    .into( holder.topernr_Image);
        } else {
            Toast.makeText(context, "No Services", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        if(topernrsList!=null)
        { return topernrsList.size();}
        else {
            return 0;
        }

    }

    public class TEVH extends RecyclerView.ViewHolder {

        TextView topernr_nameShowTv,topernr_earnShowTv,topernr_joinShowTv;
        ImageView topernr_Image;

        public TEVH(@NonNull View v) {
            super(v);
            topernr_Image = v.findViewById(R.id.topernr_Image);
            topernr_nameShowTv = v.findViewById(R.id.topernr_nameShowTv);
            topernr_earnShowTv = v.findViewById(R.id.topernr_earnShowTv);
            topernr_joinShowTv = v.findViewById(R.id.topernr_joinShowTv);

        }
    }
}
