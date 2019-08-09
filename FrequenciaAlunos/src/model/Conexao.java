/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Znoque
 */
public class Conexao {
    public static int cont=0;
    private static ObservableList<Aluno> alunos = FXCollections.observableArrayList();

    /**
     * @return the alunos
     */
    public static ObservableList<Aluno> getAlunos() {
        return alunos;
    }

    /**
     * @param aAlunos the alunos to set
     */
    public static void setAlunos(ObservableList<Aluno> aAlunos) {
        alunos = aAlunos;
    }
}
