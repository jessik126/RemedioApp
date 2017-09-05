package com.cursoandroid.jesscampos.remedioapp.BancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jessica on 28/08/2017.
 */
public class CriaBancoDados extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "remedioApp";

    // Table Names
    public static final String TABLE_REMEDIOS = "remedios";

    // NOTES Table - column names
    public static final String KEY_ID = "_id";
    public static final String KEY_NOME = "nome";
    public static final String KEY_CAIXA = "caixa";
    public static final String KEY_HORA = "hora";
    public static final String KEY_FREQDIA = "freq_dia";
    public static final String KEY_FREQHORA = "freq_hora";
    public static final String KEY_FUNCAO = "funcao";
    public static final String KEY_MEDICO = "medico";


    // Table Create Statements

    private static final String CREATE_TABLE_REMEDIO = "CREATE TABLE "
            + TABLE_REMEDIOS
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOME + " TEXT,"
            + KEY_CAIXA + " TEXT,"
            + KEY_HORA + " TEXT,"
            + KEY_FREQHORA + " INT,"
            + KEY_FREQDIA + " TEXT,"
            + KEY_FUNCAO + " TEXT,"
            + KEY_MEDICO + " TEXT"
            + ")";

    public CriaBancoDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_REMEDIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEDIOS);

        // create new tables
        onCreate(db);
    }
}


