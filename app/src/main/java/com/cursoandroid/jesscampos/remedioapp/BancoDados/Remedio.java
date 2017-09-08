package com.cursoandroid.jesscampos.remedioapp.BancoDados;

import java.util.Date;

/**
 * Created by Jessica on 01/09/2017.
 */
public class Remedio {
    int id;
    String nome;
    String caixa;
    String hora;
    String freqDia;
    int freqHora;
    String funcao;
    String medico;

    // constructors
    public Remedio() {
        this.nome = "";
        this.caixa = "";
        this.hora = "0:0";
        this.freqDia = "0000000";
        this.freqHora = 0;
        this.funcao = "";
        this.medico = "";
    }

    public Remedio(String nome, String caixa, String hora, String freqDia, int freqHora, String funcao, String medico) {
        this.nome = nome;
        this.caixa = caixa;
        this.hora = hora;
        this.freqDia = freqDia;
        this.freqHora = freqHora;
        this.funcao = funcao;
        this.medico = medico;
    }

    public Remedio(int id, String nome, String caixa, String hora, String freqDia, int freqHora, String funcao, String medico) {
        this.id = id;
        this.nome = nome;
        this.caixa = caixa;
        this.hora = hora;
        this.freqDia = freqDia;
        this.freqHora = freqHora;
        this.funcao = funcao;
        this.medico = medico;
    }

    // setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCaixa(String caixa) {
        this.caixa = caixa;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFreqDia(String freqDia) {
        this.freqDia = freqDia;
    }

    public void setFreqHora(int freqHora) {
        this.freqHora = freqHora;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    //getters


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCaixa() {
        return caixa;
    }

    public String getHora() {
        return hora;
    }

    public String getFreqDia() {
        return freqDia;
    }

    public int getFreqHora() {
        return freqHora;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getMedico() {
        return medico;
    }
}

