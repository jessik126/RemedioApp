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

    //TABLE_REMEDIOS

    public String addRemedio(Remedio remedio) {
        db = banco.getWritableDatabase();
        long resultado;

        ContentValues valores = new ContentValues();
        valores.put(CriaBancoDados.KEY_NOME, remedio.nome);
        valores.put(CriaBancoDados.KEY_CAIXA, remedio.caixa);
        valores.put(CriaBancoDados.KEY_HORA, remedio.hora);
        valores.put(CriaBancoDados.KEY_FREQHORA, remedio.freqHora);
        valores.put(CriaBancoDados.KEY_MEDICO, remedio.medico);
        valores.put(CriaBancoDados.KEY_FREQDIA, remedio.freqDia);
        valores.put(CriaBancoDados.KEY_FUNCAO, remedio.funcao);

        // insert row
        resultado = db.insert(CriaBancoDados.TABLE_REMEDIOS, null, valores);
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
        String[] campos = {banco.KEY_ID, banco.KEY_NOME, banco.KEY_CAIXA, banco.KEY_HORA, banco.KEY_FREQHORA, banco.KEY_MEDICO, banco.KEY_FREQDIA};
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
        ContentValues valores = new ContentValues();
        valores.put(CriaBancoDados.KEY_NOME, remedio.nome);
        valores.put(CriaBancoDados.KEY_CAIXA, remedio.caixa);
        valores.put(CriaBancoDados.KEY_HORA, remedio.hora);
        valores.put(CriaBancoDados.KEY_FREQHORA, remedio.freqHora);
        valores.put(CriaBancoDados.KEY_MEDICO, remedio.medico);
        valores.put(CriaBancoDados.KEY_FREQDIA, remedio.freqDia);
        valores.put(CriaBancoDados.KEY_FUNCAO, remedio.funcao);

        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getWritableDatabase();
        db.update(CriaBancoDados.TABLE_REMEDIOS,valores,where,null);
        db.close();
    }

    public void deletaRegistro(int id){
        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBancoDados.TABLE_REMEDIOS,where,null);
        db.close();
    }

    public void desativaRegistro(int id){
        ContentValues valores = new ContentValues();
        valores.put(CriaBancoDados.KEY_CAIXA, "");
        
        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getWritableDatabase();
        db.update(CriaBancoDados.TABLE_REMEDIOS,valores,where,null);
        db.close();
    }

    //TABLE_REMEDIOS_HISTORICO
    public String insereHistorico(RemedioHistorico historico){
        db = banco.getWritableDatabase();
        long resultado;

        ContentValues valores = new ContentValues();
        valores.put(CriaBancoDados.KEY_ID_REMEDIO, historico.idRemedio);
        valores.put(CriaBancoDados.KEY_DIA_HISTORICO, historico.dia);
        valores.put(CriaBancoDados.KEY_HORA_HISTORICO, historico.hora);

        // insert row
        resultado = db.insert(CriaBancoDados.TABLE_REMEDIOS_HISTORICO, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor carregaDadosHistorico(){
        Cursor cursor;
        String[] campos = {banco.KEY_ID_HISTORICO, banco.KEY_ID_REMEDIO, banco.KEY_DIA_HISTORICO, banco.KEY_HORA_HISTORICO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABLE_REMEDIOS_HISTORICO, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDadosHistoricosByRemedio(int idRemedio){
        Cursor cursor;
        String[] campos = {banco.KEY_ID_HISTORICO, banco.KEY_ID_REMEDIO, banco.KEY_DIA_HISTORICO, banco.KEY_HORA_HISTORICO};
        String where = CriaBancoDados.KEY_ID_REMEDIO + "=" + idRemedio;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBancoDados.TABLE_REMEDIOS_HISTORICO,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


}
