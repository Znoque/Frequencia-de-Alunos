/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Znoque
 */
public class Aluno {

    private IntegerProperty matricula = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty idCartao = new SimpleStringProperty();
    private StringProperty curso = new SimpleStringProperty();
    private StringProperty turma = new SimpleStringProperty();
    private StringProperty turno = new SimpleStringProperty();
    private StringProperty serie = new SimpleStringProperty();

    public Aluno(int matricula, String nome, String idCartao, String curso, String turma, String turno, String serie) {
        this.matricula.set(matricula);
        this.nome.set(nome);
        this.idCartao.set(idCartao);
        this.curso.set(curso);
        this.turma.set(turma);
        this.turno.set(turno);
        this.serie.set(serie);
    }

    /**
     * @return the matricula
     */
    public IntegerProperty getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(IntegerProperty matricula) {
        this.matricula = matricula;
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
    public void setNome(StringProperty nome) {
        this.nome = nome;
    }

    /**
     * @return the idCartao
     */
    public StringProperty getIdCartao() {
        return idCartao;
    }

    /**
     * @param idCartao the idCartao to set
     */
    public void setIdCartao(StringProperty idCartao) {
        this.idCartao = idCartao;
    }

    /**
     * @return the curso
     */
    public StringProperty getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(StringProperty curso) {
        this.curso = curso;
    }

    /**
     * @return the turma
     */
    public StringProperty getTurma() {
        return turma;
    }

    /**
     * @param turma the turma to set
     */
    public void setTurma(StringProperty turma) {
        this.turma = turma;
    }

    /**
     * @return the turno
     */
    public StringProperty getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(StringProperty turno) {
        this.turno = turno;
    }

    /**
     * @return the serie
     */
    public StringProperty getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(StringProperty serie) {
        this.serie = serie;
    }

}
