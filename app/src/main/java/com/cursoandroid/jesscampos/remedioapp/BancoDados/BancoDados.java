package com.cursoandroid.jesscampos.remedioapp.BancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jessica on 03/09/2017.
 */
public class BancoDados {

    private SQLiteDatabase db;
    private CriaBancoDados banco;

    public BancoDados(Context context){
        banco = new CriaBancoDados(context);
    }

    public String addRemedio(Remedio rm) {
        db = banco.getWritableDatabase();
        long resultado;

        ContentValues values = new ContentValues();
        values.put(CriaBancoDados.KEY_NOME, rm.nome);
        values.put(CriaBancoDados.KEY_CAIXA, rm.caixa);
        values.put(CriaBancoDados.KEY_HORA, rm.hora);
        values.put(CriaBancoDados.KEY_MIN, rm.min);
        values.put(CriaBancoDados.KEY_MEDICO, rm.medico);

        // insert row
        resultado = db.insert(CriaBancoDados.TABLE_REMEDIOS, null, values);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.KEY_ID, banco.KEY_CAIXA,banco.KEY_NOME};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABLE_REMEDIOS, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;
        String[] campos = {banco.KEY_ID, banco.KEY_NOME, banco.KEY_CAIXA, banco.KEY_HORA, banco.KEY_MIN, banco.KEY_MEDICO};
        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBancoDados.TABLE_REMEDIOS,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alteraRegistro(int id, Remedio remedio){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = CriaBancoDados.KEY_ID + "=" + id;

        valores = new ContentValues();
        valores.put(CriaBancoDados.KEY_NOME, remedio.nome);
        valores.put(CriaBancoDados.KEY_CAIXA, remedio.caixa);
        valores.put(CriaBancoDados.KEY_HORA, remedio.hora);
        valores.put(CriaBancoDados.KEY_MIN, remedio.min);
        valores.put(CriaBancoDados.KEY_MEDICO, remedio.medico);

        db.update(CriaBancoDados.TABLE_REMEDIOS,valores,where,null);
        db.close();
    }

}
