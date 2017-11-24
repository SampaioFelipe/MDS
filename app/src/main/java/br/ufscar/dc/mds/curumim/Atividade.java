package br.ufscar.dc.mds.curumim;

import java.util.Date;

class Atividade {
    String nome;
    String local;
    Date horario;

    Atividade(String nome, String local, Date horario) {
        this.nome = nome;
        this.local = local;
        this.horario = horario;
    }

    Atividade() {
        this.nome = null;
        this.local = null;
        this.horario = null;
    }

}
