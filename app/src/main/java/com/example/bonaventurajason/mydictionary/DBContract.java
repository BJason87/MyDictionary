package com.example.bonaventurajason.mydictionary;

import android.provider.BaseColumns;

public class DBContract {
    public static String TABLE_IND = "table_ind";
    public static String TABLE_ENG = "table_eng";



    public static final class Column implements BaseColumns {

        public static String KATA = "kata";
        public static String KETERANGAN = "keterangan";

    }
}
