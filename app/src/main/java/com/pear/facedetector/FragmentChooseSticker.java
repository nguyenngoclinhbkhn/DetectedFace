package com.pear.facedetector;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pear.facedetector.adapter.AdapterSticker;

public class FragmentChooseSticker extends DialogFragment implements AdapterSticker.OnStickerListener {


    private AdapterSticker adapterSticker;
    private RecyclerView recyclerView;
    private OnFragmentListener onFragmentListener;
    private LinearLayout linear;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentListener = (OnFragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_sticker, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;


        recyclerView = view.findViewById(R.id.recyclerView);
        linear = view.findViewById(R.id.linearFragment);

        linear.post(new Runnable() {
            @Override
            public void run() {
                linear.getLayoutParams().width = width * 3 / 4;
                linear.getLayoutParams().height = height * 3 / 8;
                linear.requestLayout();
            }
        });

        adapterSticker = new AdapterSticker(getActivity(), this);
        recyclerView.setAdapter(adapterSticker);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        adapterSticker.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onItemStickerClicked(int sticker) {
        onFragmentListener.onItemFragmentClicked(sticker);
        dismiss();

    }

    public interface OnFragmentListener{
        void onItemFragmentClicked(int item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onFragmentListener != null){
            onFragmentListener = null;

        }
    }
}
