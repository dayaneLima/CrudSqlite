package br.com.dayane.crudsqlite.database;

import android.provider.BaseColumns;

/**
 * Created by Dayane on 04/09/2016.
 */
public interface PessoaContract extends BaseColumns{

    String TABLE_NAME = "pessoa";

    String NOME = "nome";
    String EMAIL = "email";
    String ENDERECO = "endereco";
    String SEXO = "sexo";

    String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOME + " TEXT NOT NULL, " +
            EMAIL + " TEXT NOT NULL, " +
            ENDERECO + " TEXT NOT NULL, " +
            SEXO + " TEXT NOT NULL );";

}
