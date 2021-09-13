package com.devsaidur.saidur.playnearn.ui.home.ldokng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devsaidur.saidur.playnearn.R;
import com.devsaidur.saidur.playnearn.models.Match;

import java.util.List;

public class LdomatchAdapter extends RecyclerView.Adapter<LdomatchAdapter.AUVH> {

    private Context context;
    private List<Match> noticeList;
    LdoKngIf ldoKngIf;

    public LdomatchAdapter(Context context, List<Match> noticeList,LdoKngIf ldoKngIf) {
        this.context = context;
        this.noticeList = noticeList;
        this.ldoKngIf = ldoKngIf;
    }

    @NonNull
    @Override
    public AUVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_matches, parent, false);
        AUVH dvh = new AUVH(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AUVH holder, int position) {
        Match nm = noticeList.get(position);

        holder.tv_matchtype.setText(nm.getMatch_type());
        holder.tv_matchfee.setText(nm.getMatch_fee());
        holder.tv_matchprize.setText(nm.getMatch_prize());
        holder.tv_matchseat.setText(nm.getMatch_seat());
        holder.tv_matchtime.setText(nm.getMatch_styme());

        holder.btn_mtchjoin.setOnClickListener(v -> {
            ldoKngIf.checkbal(position,nm.getMatch_fee(),nm.getMatch_cat(),nm.getMatch_id(),nm.getMatch_seat());
        });

        // holder.userbalShowTv.setText(ac.getCurrent_bal());
//        Picasso.get().load(nm.getImage()).into(holder.notice_Image);
        //Picasso.get().load(Const.Base_Url + Const.Image_path + nm.getImage()).into(holder.notice_Image);
       /* if(nm.getImage().equals("no file"))
        {
            holder.notice_Image.setVisibility(View.GONE);
        }
        else {
            holder.notice_Image.setVisibility(View.VISIBLE);
            //Picasso.get().load(Constant.ImageBaseUrl+Constant.BlogImgPath+bloog.getBlogImage()).into(holder.notice_Image);
            Picasso.get().load(nm.getImage()).into(holder.notice_Image);
           // Picasso.get().load(nm.ge()).into(holder.notice_Image);
        }*/
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class AUVH extends RecyclerView.ViewHolder {
        TextView tv_matchtype, tv_matchfee, tv_matchprize, tv_matchseat, tv_matchtime;
        ImageView user_Image;
        Button btn_mtchjoin;


        public AUVH(@NonNull View v) {
            super(v);
            tv_matchtype = v.findViewById(R.id.tv_matchtype);
            tv_matchfee = v.findViewById(R.id.tv_matchfee);
            tv_matchprize = v.findViewById(R.id.tv_matchprize);
            tv_matchseat = v.findViewById(R.id.tv_matchseat);
            tv_matchtime = v.findViewById(R.id.tv_matchtime);
            btn_mtchjoin = v.findViewById(R.id.btn_mtchjoin);
            // user_Image = v.findViewById(R.id.userImg);


        }
    }
}
