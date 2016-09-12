package br.com.dayane.crudsqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dayane on 04/09/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final String NOME_DB = "crudSqlite";
    private static final int VERSAO_DB = 1;

    public DataBase(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PessoaContract.CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
