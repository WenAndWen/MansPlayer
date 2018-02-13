package com.wen.mansplayer;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by wen on 2017/12/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<String> mTitle=new ArrayList<>();
    private ArrayList<String> mUrl=new ArrayList<>();

    public interface OnItemClickListener{
        void onClick(String vedioUrl,String picUrl,int position);
    }
    private OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    public RecyclerViewAdapter(Context context,ArrayList<String>title,ArrayList<String>url){
        mContext=context;
        mTitle=title;
        mUrl=url;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public   class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView2;
        TextView mTextView;
        ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.text1);
            mTextView2=(TextView)itemView.findViewById(R.id.text2);
            mImageView=(ImageView)itemView.findViewById(R.id.img2);
            mImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String vedioUrl = "https://www.3139v.com"+mTextView.getText().toString();
                    String picUrl=mTextView2.getText().toString();
                    clickListener.onClick(vedioUrl,picUrl,getAdapterPosition());


                }
            });
        }
    }

    public void remove(int position) {
        mTitle.remove(position);
        mUrl.remove(position);
        notifyItemRemoved(position);
    }

    public void add(String text,String url, int position) {
        mTitle.add(position, text);
       mUrl.add(position,url);
        notifyItemInserted(position);
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        //加载图片
        Glide.with(mContext).load(mTitle.get(position)).thumbnail(Glide.with(mContext).load(R.mipmap.load)).fitCenter().into(holder.mImageView);
        //获取播放地址
        holder.mTextView.setText(mUrl.get(position));
        holder.mTextView2.setText(mTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitle==null ? 0 : mTitle.size();
    }
}
