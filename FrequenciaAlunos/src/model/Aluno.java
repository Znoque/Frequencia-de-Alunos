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
    private IntegerProperty id = new SimpleIntegerProperty();
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
        this.id.set(Conexao.cont++);
    }
    
    public Aluno(int matricula, String nome, String curso, String turma, String turno, String serie) {
        this.matricula.set(matricula);
        this.nome.set(nome);
        this.curso.set(curso);
        this.turma.set(turma);
        this.turno.set(turno);
        this.serie.set(serie);
        this.id.set(Conexao.cont++);
    }

    @Override
    public String toString() {
        return "ID: "+getId().get()+"\nMatricula: "+getMatricula().get()+"\nNome: "+getNome().get();
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
    public void setMatricula(int matricula) {
        this.matricula.set(matricula);
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
     * @return the idCartao
     */
    public StringProperty getIdCartao() {
        return idCartao;
    }

    /**
     * @param idCartao the idCartao to set
     */
    public void setIdCartao(String idCartao) {
        this.idCartao.set(idCartao);
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
    public void setCurso(String curso) {
        this.curso.set(curso);
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
    public void setTurma(String turma) {
        this.turma.set(turma);
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
    public void setTurno(String turno) {
        this.turno.set(turno);
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
    public void setSerie(String serie) {
        this.serie.set(serie);
    }
    
    /**
     * @return the matricula
     */
    public IntegerProperty getId() {
        return id;
    }

}
