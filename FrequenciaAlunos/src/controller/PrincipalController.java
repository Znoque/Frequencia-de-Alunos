/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Cadastro;
import main.Frequencia;
import main.Historico;
import main.Principal;
import model.Alerta;

/**
 * FXML Controller class
 *
 * @author Znoque
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnCadastro;
    @FXML
    private Button btnFre;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnHistorico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cursorMao();
        
        btnSair.setOnAction(e -> {
            boolean r = Alerta.getAlertaSair();
            if (r) {
                System.exit(0);
            } else {
                e.consume();
            }
        });
        
        btnCadastro.setOnAction(e -> abrirCadastro());
        btnFre.setOnAction(e -> abrirFrequencia());
        btnHistorico.setOnAction(e -> abrirHistorico());
    }

    public void cursorMao() {
        btnCadastro.setCursor(Cursor.HAND);
        btnFre.setCursor(Cursor.HAND);
        btnHistorico.setCursor(Cursor.HAND);
        btnSair.setCursor(Cursor.HAND);
    }
    
    public void abrirCadastro() {
        Cadastro c = new Cadastro();
        Principal.getStage().close();
        try {
            c.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Cadastro", "Falha Ao Abrir Tela de Cadastro", "Não Foi Possivel Abir a Tela de Cadastro");
        }
    }
    
    public void abrirFrequencia() {
        Frequencia f = new Frequencia();
        Principal.getStage().close();
        try {
            f.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Frequencia", "Falha Ao Abrir Tela de Frequencia", "Não Foi Possivel Abir a Tela de Frequencia");
        }
    }
    
    public void abrirHistorico() {
        Historico h = new Historico();
        Principal.getStage().close();
        try {
            h.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Histórico", "Falha Ao Abrir Tela de Histórico", "Não Foi Possivel Abir a Tela de Histórico");
        }
    }
}
