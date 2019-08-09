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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Cadastro;
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
        btnSair.setOnAction(e -> {
            boolean r = Alerta.getAlertaSair();
            if (r) {
                System.exit(0);
            } else {
                e.consume();
            }
        });
        
        btnCadastro.setOnAction(e -> abrirCadastro());
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
}
