package com.pear.facedetector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pear.facedetector.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSticker extends RecyclerView.Adapter<AdapterSticker.ViewHolder> {
    private Context context;
    private List<Integer> list;
    private LayoutInflater inflater;
    private OnStickerListener onStickerListener;


    public AdapterSticker(Context context, OnStickerListener onStickerListener) {
        this.context = context;
        this.onStickerListener = onStickerListener;
        this.list = getList();
    }

    private List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.f1_01);
        list.add(R.drawable.f2_01);
        list.add(R.drawable.f3_01);
        list.add(R.drawable.f4_1);
        list.add(R.drawable.f5_01);
        list.add(R.drawable.f6_01);
        list.add(R.drawable.f9_01);
        list.add(R.drawable.f10_01);
        list.add(R.drawable.f11_01);
        list.add(R.drawable.f12_01);
        list.add(R.drawable.f13_01);
        list.add(R.drawable.f14_01);
        list.add(R.drawable.f15_01);
        list.add(R.drawable.f16_01);
        list.add(R.drawable.f17_01);
        list.add(R.drawable.f18_01);
        list.add(R.drawable.f19_01);
        list.add(R.drawable.m1_01);
        list.add(R.drawable.m2_01);
        list.add(R.drawable.m3_01);
        list.add(R.drawable.m4_01);
        list.add(R.drawable.m5_01);
        list.add(R.drawable.m6_01);
        list.add(R.drawable.m7_01);
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sticker, parent, false);
        view.getLayoutParams().height = parent.getWidth() / 2;
        view.getLayoutParams().width = parent.getWidth() / 2;
        view.requestLayout();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int sticker = list.get(position);
        Glide.with(context).load(sticker).into(holder.img);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStickerListener.onItemStickerClicked(sticker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearSticker);
            img = itemView.findViewById(R.id.imageView);
        }
    }

    public interface OnStickerListener {
        void onItemStickerClicked(int sticker);
    }
}
