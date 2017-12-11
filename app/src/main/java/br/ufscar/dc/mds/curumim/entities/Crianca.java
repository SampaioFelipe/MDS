package br.ufscar.dc.mds.curumim.entities;

public class Crianca {
    String nome;
    String idade;
    String sexo;
    String tipoSanguineo;
    String alergias;
    String restricoes;
    String patologias;

    public String getPatologias() {return patologias;}

    public void setPatologias(String patologias) {this.patologias = patologias;}

    public String getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }

    public String getSexo() {return sexo;}

    public void setSexo(String sexo) {this.sexo = sexo;}

    public String getTipoSanguineo() {return tipoSanguineo;}

    public void setTipoSanguineo(String tipoSanguineo) {this.tipoSanguineo = tipoSanguineo;}

    public String getAlergias() {return alergias;}

    public void setAlergias(String alergias) {this.alergias = alergias;}

    public String getIdade() {return idade;}

    public void setIdade(String idade) {this.idade = idade;}

    /*public Crianca(String nome){
        this.nome = nome;
    }*/
    public void setNome(String nome) {this.nome = nome;}

    public String getNome(){
        return this.nome;
    }

    public Crianca (){}

}
