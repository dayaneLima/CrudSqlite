package br.com.dayane.crudsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.dayane.crudsqlite.model.Pessoa;

/**
 * Created by Dayane on 04/09/2016.
 */
public class PessoaDAO {
    private SQLiteDatabase db;

    public PessoaDAO(Context context){
        DataBase auxDb = new DataBase(context);
        db = auxDb.getWritableDatabase();
    }

    public long insert(Pessoa pessoa){
        ContentValues valores = new ContentValues();
        valores.put(PessoaContract.NOME,pessoa.getNome());
        valores.put(PessoaContract.EMAIL,pessoa.getEmail());
        valores.put(PessoaContract.ENDERECO,pessoa.getEndereco());
        valores.put(PessoaContract.SEXO,pessoa.getSexo());

        long itemInserido = db.insert(
                //Nome Tabela
            PessoaContract.TABLE_NAME,
            // Valor para tratar colunas nulas
            null,
            // Conjunto de chave/valores para inserir
            valores
        );

        return itemInserido;
    }

    public void update(Pessoa pessoa){
        ContentValues valores = new ContentValues();
        valores.put(PessoaContract.NOME,pessoa.getNome());
        valores.put(PessoaContract.EMAIL,pessoa.getEmail());
        valores.put(PessoaContract.ENDERECO,pessoa.getEndereco());
        valores.put(PessoaContract.SEXO,pessoa.getSexo());

        db.update(
                //Nome Tabela
                PessoaContract.TABLE_NAME,
                // Conjunto de chave/valores para atualizar
                valores,
                // Clausula WHERE
                PessoaContract._ID + " = ?",
                // Valores da clausula WHERE
                new String[]{""+pessoa.getId()}
        );
    }

    public void delete(Pessoa pessoa){
        db.delete(
                //Nome Tabela
                PessoaContract.TABLE_NAME,
                //Cláusula WHERE
                PessoaContract._ID + " = ?",
                //Valores clausula where
                new String[]{""+pessoa.getId()}
        );
    }

    public List<Pessoa> getAll(){
        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        String[] colunas = new String[]{
            PessoaContract._ID,
            PessoaContract.NOME,
            PessoaContract.ENDERECO,
            PessoaContract.EMAIL,
            PessoaContract.SEXO
        };

        Cursor cursor = db.query(
            //NOME TABELA
            PessoaContract.TABLE_NAME,
            //colunas retornadas
            colunas,
            //Clausula Where
            null,
            //valores da clausula where
            null,
            //group by
            null,
            //having
            null,
            //order by
            PessoaContract.NOME + " ASC "
        );

        if(cursor.getCount() > 0){
            //aponta para o primeiro resultado
            cursor.moveToFirst();
            do{
                Pessoa pessoa = new Pessoa();
                pessoa.setId(cursor.getLong(0));
                pessoa.setNome(cursor.getString(1));
                pessoa.setEndereco(cursor.getString(2));
                pessoa.setEmail(cursor.getString(3));
                pessoa.setSexo(cursor.getString(4));

                pessoas.add(pessoa);
            }while(cursor.moveToNext());
        }

        return pessoas;
    }

}
