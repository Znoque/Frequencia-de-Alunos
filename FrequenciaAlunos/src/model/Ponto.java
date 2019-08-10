/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Znoque
 */
public class Ponto {
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty dia = new SimpleStringProperty();
    private StringProperty data = new SimpleStringProperty();
    private StringProperty hora = new SimpleStringProperty();

    public Ponto(String nome, String dia, String data, String hora) {
        this.nome.set(nome);
        this.dia.set(dia);
        this.data.set(data);
        this.hora.set(hora);
    }

    @Override
    public String toString() {
        return "Ponto{" + "nome=" + nome + ", dia=" + dia + ", data=" + data + ", hora=" + hora + '}';
    }
    
    /**
     * @return the nome
     */
    public StringProperty getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * @return the dia
     */
    public StringProperty getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia.set(dia);
    }

    /**
     * @return the data
     */
    public StringProperty getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data.set(data);
    }

    /**
     * @return the hora
     */
    public StringProperty getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora.set(hora);
    }
}
