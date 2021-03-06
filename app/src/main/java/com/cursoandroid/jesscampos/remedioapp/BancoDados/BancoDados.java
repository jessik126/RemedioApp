package com.cursoandroid.jesscampos.remedioapp.BancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public String inserirRemedio(Remedio remedio) {
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
            return "Erro ao remedio_inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaRemedios(){
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

    public Cursor carregaRemedioPorId(int id){
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

    public Remedio carregaRemedioPorCaixa(String caixa){
        Cursor cursor;
        Remedio remedio = null;
        String[] campos = {banco.KEY_ID, banco.KEY_NOME, banco.KEY_CAIXA, banco.KEY_HORA, banco.KEY_FREQHORA, banco.KEY_MEDICO, banco.KEY_FREQDIA};
        String where = CriaBancoDados.KEY_CAIXA + "=\"" + caixa + "\"";
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBancoDados.TABLE_REMEDIOS,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        try {
            if (cursor.moveToFirst()) {

                remedio = new Remedio();
                remedio.setId(cursor.getInt(cursor.getColumnIndex(CriaBancoDados.KEY_ID)));
                remedio.setNome(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_NOME)));
                remedio.setCaixa(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_CAIXA)));
                remedio.setHora(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_HORA)));
                remedio.setFreqHora((cursor.getInt(cursor.getColumnIndex(CriaBancoDados.KEY_FREQHORA))));
                remedio.setMedico(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_MEDICO)));
                remedio.setFreqDia(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_FREQDIA)));
                remedio.setFuncao("");

            }

        } finally {
            cursor.close();
        }
        db.close();

        return remedio;
    }

    public void alteraRemedio(int id, Remedio remedio){
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

    public void deletaRemedio(int id){
        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBancoDados.TABLE_REMEDIOS,where,null);
        db.close();
    }

    public void desativaRemedio(int id){
        ContentValues valores = new ContentValues();
        valores.putNull(CriaBancoDados.KEY_CAIXA);
        
        String where = CriaBancoDados.KEY_ID + "=" + id;
        db = banco.getWritableDatabase();
        db.update(CriaBancoDados.TABLE_REMEDIOS,valores,where,null);
        db.close();
    }

        public List<Remedio> listarRemedios(){
        Cursor cursor;
        String[] campos = {banco.KEY_ID, banco.KEY_NOME, banco.KEY_CAIXA, banco.KEY_HORA, banco.KEY_FREQHORA, banco.KEY_MEDICO, banco.KEY_FREQDIA};
        String where = CriaBancoDados.KEY_CAIXA + " IS NOT NULL";
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBancoDados.TABLE_REMEDIOS,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return listarRemedioPorCursor(cursor);
    }

    private List<Remedio> listarRemedioPorCursor(Cursor cursor) {
        List<Remedio> remedios = new ArrayList<Remedio>();
        if(cursor == null)
            return remedios;

        try {
            if (cursor.moveToFirst()) {
                do {

                    Remedio remedio = new Remedio();
                    remedio.setId(cursor.getInt(cursor.getColumnIndex(CriaBancoDados.KEY_ID)));
                    remedio.setNome(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_NOME)));
                    remedio.setCaixa(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_CAIXA)));
                    remedio.setHora(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_HORA)));
                    remedio.setFreqHora((cursor.getInt(cursor.getColumnIndex(CriaBancoDados.KEY_FREQHORA))));
                    remedio.setMedico(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_MEDICO)));
                    remedio.setFreqDia(cursor.getString(cursor.getColumnIndex(CriaBancoDados.KEY_FREQDIA)));
                    remedio.setFuncao("");

                    remedios.add(remedio);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return remedios;
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
            return "Erro ao remedio_inserir registro";
        else
            return "Registro Inserido com sucesso";
    }

    public Cursor carregaHistoricos(){
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

    public Cursor carregaHistoricosPorRemedio(int idRemedio){
        Cursor cursor;
        String[] campos = {banco.KEY_ID_HISTORICO, banco.KEY_ID_REMEDIO, banco.KEY_DIA_HISTORICO, banco.KEY_HORA_HISTORICO};
        String where = banco.KEY_ID_REMEDIO + "=" + idRemedio + " ORDER BY " + banco.KEY_DIA_HISTORICO + " , " + banco.KEY_HORA_HISTORICO ;
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABLE_REMEDIOS_HISTORICO,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


}
