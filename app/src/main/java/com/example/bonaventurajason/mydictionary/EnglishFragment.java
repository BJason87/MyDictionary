package com.example.bonaventurajason.mydictionary;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishFragment extends Fragment {

    //bind
    View rootView;
    @BindView(R.id.text_eng)
    EditText txt_eng;
    @BindView(R.id.btn_clear_text_eng)
    ImageView btn_clear_eng;
    @BindView(R.id.rv_eng)
    RecyclerView recyclerView;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;
    ArrayList<DictionaryModel> dictionaryModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_english, container, false);
        ButterKnife.bind(this, rootView);

        btn_clear_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_eng.setText("");
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dictionaryHelper = new DictionaryHelper(getActivity());
        dictionaryHelper.open();

        populateRecyclerView();

        txt_eng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, final int count) {
                dictionaryModels.clear();

                dictionaryAdapter = new DictionaryAdapter(getActivity());
                DictionaryModel dictionaryModel;
                dictionaryHelper.open();
                Cursor cursor = dictionaryHelper.retrieveEng(s.toString());

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String kata = cursor.getString(1);
                    String keterangan = cursor.getString(2);

                    dictionaryModel = new DictionaryModel();
                    dictionaryModel.setId(id);
                    dictionaryModel.setKata(kata);
                    dictionaryModel.setKeterangan(keterangan);

                    dictionaryModels.add(dictionaryModel);
                }
                dictionaryHelper.close();
                dictionaryAdapter.addItem(dictionaryModels);
                recyclerView.setAdapter(dictionaryAdapter);
                recyclerView.setVisibility(View.VISIBLE);

                dictionaryAdapter.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = recyclerView.indexOfChild(v);
                        Intent intent = new Intent(getContext(), ResultActivity.class);
                        intent.putExtra("kata", dictionaryModels.get(position).getKata());
                        intent.putExtra("keterangan", dictionaryModels.get(position).getKeterangan());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void populateRecyclerView() {
        dictionaryModels = dictionaryHelper.getDataEng();
        dictionaryAdapter = new DictionaryAdapter(getActivity());
        dictionaryAdapter.addItem(dictionaryModels);
        recyclerView.setAdapter(dictionaryAdapter);

        dictionaryHelper.close();
    }

}
