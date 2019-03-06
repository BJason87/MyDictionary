package com.example.bonaventurajason.mydictionary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder> {
    private ArrayList<DictionaryModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private View.OnClickListener mClickListener;


    public DictionaryAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

    @NonNull
    @Override
    public DictionaryAdapter.DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        RecyclerView.ViewHolder holder = new DictionaryHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(view);
            }
        });
        return new DictionaryHolder(view);
    }


    public void addItem(ArrayList<DictionaryModel> mData) {
        //menambahkan data
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.DictionaryHolder holder, int position) {
        holder.kata_textView.setText(mData.get(position).getKata());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;



    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class DictionaryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.kata_id)
        TextView kata_textView;


        public DictionaryHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
