package br.ufscar.dc.mds.curumim.entities;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Atividade implements Comparable<Atividade> {
    public String nome;
    public String local;
    public Date horario;

    private final static SimpleDateFormat horarioFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm", Locale.US);
    private final static SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yy, HH:mm", Locale.US);

    public Atividade(String nome, String local, Date horario) {
        this.nome = nome;
        this.local = local;
        this.horario = horario;
    }

    public Atividade() {
        this.nome = null;
        this.local = null;
        this.horario = null;
    }

    public Atividade(String data, String atividadeCodificada) {
        String[] tokens = atividadeCodificada.split("@@sep@@");

        nome = tokens[0];
        local = tokens[1];
        try {
            horario = horarioFormat.parse(data + "T" + tokens[2]);
        } catch (ParseException e) {
            horario = new Date();
        }
    }

    @Override
    public int compareTo(@NonNull Atividade atividade) {
        return this.horario.compareTo(atividade.horario);
    }

    public String getHorarioString() {
        return dataFormat.format(horario);
    }

}
