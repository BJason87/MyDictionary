package com.example.bonaventurajason.mydictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionaryActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar) ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Integer, Void> {

        DictionaryHelper dictionaryHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(DictionaryActivity.this);
            appPreference = new AppPreference(DictionaryActivity.this);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            // Pengambilan data dari raw dan dimasukkan ke sqllite
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<DictionaryModel> kamusModelsInd = preLoadRaw("ind");
                ArrayList<DictionaryModel> kamusModelsEng = preLoadRaw("eng");

                dictionaryHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusModelsInd.size() + kamusModelsEng.size());

                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel modelInd : kamusModelsInd) {
                        dictionaryHelper.insertTransaction(modelInd, "ind");
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    for (DictionaryModel modelEng : kamusModelsEng) {
                        dictionaryHelper.insertTransaction(modelEng, "eng");
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dictionaryHelper.endTransaction();

                dictionaryHelper.close();
                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //intent ke mainactivity
            Intent intent = new Intent(DictionaryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public ArrayList<DictionaryModel> preLoadRaw(String language) {
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        InputStream raw_dict;
        try {
            Resources resources = getResources();
            if (language.equalsIgnoreCase("ind")) {
                raw_dict = resources.openRawResource(R.raw.indonesia_english);
            } else if (language.equalsIgnoreCase("eng"))
                raw_dict = resources.openRawResource(R.raw.english_indonesia);
            else raw_dict = null;

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            do {
                line = reader.readLine();
                String[] items = line.split("\t");
                DictionaryModel dictionaryModel;
                dictionaryModel = new DictionaryModel(items[0], items[1]);
                dictionaryModels.add(dictionaryModel);
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
