package com.cursoandroid.jesscampos.remedioapp.BancoDados;

import java.util.Date;

/**
 * Created by Jessica on 01/09/2017.
 */
public class Remedio {
    int id;
    String nome;
    String caixa;
    int hora;
    int min;
    String freqDia;
    int freqHora;
    String funcao;
    String medico;

    // constructors
    public Remedio() {
    }

    public Remedio(String nome, String caixa, int hora, int min,String freqDia, int freqHora, String funcao, String medico) {
        this.nome = nome;
        this.caixa = caixa;
        this.hora = hora;
        this.min = min;
        this.freqDia = freqDia;
        this.freqHora = freqHora;
        this.funcao = funcao;
        this.medico = medico;
    }

    public Remedio(int id, String nome, String caixa, Date data, String freqDia, int freqHora, String funcao, String medico) {
        this.id = id;
        this.nome = nome;
        this.caixa = caixa;
        this.hora = hora;
        this.min = min;
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

    public void setCaixa(String caixa) { this.caixa = caixa;  }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMin(int min) {
        this.min = min;
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

    public void setMedico(String medico) {this.medico = medico; }


    // getters
    public int getId() {
        return this.id;
    }

    public String getNome() { return this.nome; }

    public String getCaixa() { return this.caixa; }

    public int getHora() { return this.hora; }

    public int getMin() { return this.min; }

    public String getFreqDia() { return this.freqDia; }

    public int getFreqHora() {
        return this.freqHora;
    }

    public String getFuncao() { return this.funcao; }

    public String getMedico() { return this.medico; }



}

