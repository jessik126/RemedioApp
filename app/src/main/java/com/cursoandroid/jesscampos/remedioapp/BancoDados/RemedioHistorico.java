package com.cursoandroid.jesscampos.remedioapp.BancoDados;

/**
 * Created by Jessica on 05/09/2017.
 */
public class RemedioHistorico {

    int id;
    int idRemedio;
    String hora;
    String dia;


    // constructors
    public RemedioHistorico() {
    }

    public RemedioHistorico(int id, int idRemedio, String hora, String dia) {
        this.id = id;
        this.idRemedio = idRemedio;
        this.hora = hora;
        this.dia = dia;
    }


    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setIdRemedio(int idRemedio) {
        this.idRemedio = idRemedio;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    //getters

    public int getId() {
        return id;
    }

    public int getIdRemedio() {
        return idRemedio;
    }

    public String getHora() {
        return hora;
    }

    public String getDia() {
        return dia;
    }
}
