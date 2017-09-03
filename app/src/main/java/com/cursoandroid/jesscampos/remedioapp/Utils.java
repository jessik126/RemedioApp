package com.cursoandroid.jesscampos.remedioapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jessica on 26/07/2017.
 */
public class Utils {
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public void alert(String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
