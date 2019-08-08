/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Cadastro;
import main.Principal;
import model.Alerta;
import model.Aluno;

/**
 * FXML Controller class
 *
 * @author Znoque
 */
public class CadastroController implements Initializable {

    @FXML
    private TableView<Aluno> tbAlunos;
    @FXML
    private TableColumn<Aluno, Integer> tcMatricula;
    @FXML
    private TableColumn<Aluno, String> tcNome;
    @FXML
    private TableColumn<Aluno, String> tcCurso;
    @FXML
    private TableColumn<Aluno, String> tcTurma;
    @FXML
    private TableColumn<Aluno, String> tcTurno;
    @FXML
    private TableColumn<Aluno, String> tcSerie;
    @FXML
    private TableColumn<Aluno, String> tcId;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtNome;
    @FXML
    private Label lbId;
    @FXML
    private Button btnId;
    @FXML
    private ComboBox<String> cbCurso;
    @FXML
    private ComboBox<String> cbTurma;
    @FXML
    private ComboBox<String> cbTurno;
    @FXML
    private ComboBox<String> cbSerie;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnVoltar;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btnPesquisa;
    @FXML
    private ComboBox<String> cbPorta;
    
    private ObservableList<String> porta = FXCollections.observableArrayList();
    private ObservableList<String> filtro = FXCollections.observableArrayList();
    private ObservableList<String> curso = FXCollections.observableArrayList();
    private ObservableList<String> turma = FXCollections.observableArrayList();
    private ObservableList<String> turno = FXCollections.observableArrayList();
    private ObservableList<String> serie = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregarCB();
        
        btnVoltar.setOnAction(e -> voltarMenu());
    }

    public void carregarCB(){
        //Colocando os valores nas Listas
        porta.addAll("COM1","COM3","COM4","COM5","COM9");
        filtro.addAll("Matrícula","Nome","Cartão","Curso","Turma","Turno","Serie");
        curso.addAll("Agropecuária", "Agroecologia", "Edificações", "Informática");
        turma.addAll("A", "B", "U");
        turno.addAll("Matutino", "Vespertino");
        serie.addAll("1ª", "2ª", "3ª", "4ª");
        
        //Colocando as Listas no ComboBox
        cbPorta.setItems(porta);
        cbFiltro.setItems(filtro);
        cbCurso.setItems(curso);
        cbTurma.setItems(turma);
        cbTurno.setItems(turno);
        cbSerie.setItems(serie);
        
        //Setando a 1ª Opçaõ do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(0);
        cbCurso.getSelectionModel().select(0);
        cbTurma.getSelectionModel().select(0);
        cbTurno.getSelectionModel().select(0);
        cbSerie.getSelectionModel().select(0);
    }
    
    public void voltarMenu() {
        Principal p = new Principal();
        Cadastro.getStage().close();
        try {
            p.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Cadastro", "Falha Ao Abrir Tela de Cadastro", "Não Foi Possivel Abir a Tela de Cadastro");
        }
    }

}
