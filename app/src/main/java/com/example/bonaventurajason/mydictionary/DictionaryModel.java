package com.example.bonaventurajason.mydictionary;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryModel implements Parcelable {
    private int id;
    private String kata;
    private String keterangan;

    public DictionaryModel() {

    }

    public DictionaryModel(String kata, String keterangan) {
        this.kata = kata;
        this.keterangan = keterangan;
    }

    public DictionaryModel(int id, String kata, String keterangan) {
        this.id = id;
        this.kata = kata;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.keterangan);
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.kata = in.readString();
        this.keterangan = in.readString();
    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel in) {
            return new DictionaryModel(in);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
