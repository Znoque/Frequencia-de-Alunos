/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comparator.cursoComparator;
import comparator.nomeComparator;
import comparator.serieComparator;
import comparator.turnoComparator;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Historico;
import main.Principal;
import model.Alerta;
import model.Aluno;
import model.ArduinoSerial;
import model.Conexao;
import model.Ponto;

/**
 * FXML Controller class
 *
 * @author Znoque
 */
public class HistoricoController implements Initializable {

    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private Button btnConsultar;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btnLimpar;
    @FXML
    private Label lbId;
    @FXML
    private Button btnId;
    @FXML
    private ComboBox<String> cbPorta;
    @FXML
    private TableView<Ponto> tvPonto;
    @FXML
    private TableColumn<Ponto, String> tcStatus;
    @FXML
    private TableColumn<Ponto, String> tcDia;
    @FXML
    private TableColumn<Ponto, String> tcData;
    @FXML
    private TableColumn<Ponto, String> tcHora;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lbNome;
    @FXML
    private Label lbMatricula;
    @FXML
    private Label lbCartao;
    @FXML
    private Label lbCurso;
    @FXML
    private Label lbTurma;
    @FXML
    private Label lbTurno;
    @FXML
    private Label lbSerie;

    private ObservableList<String> porta = FXCollections.observableArrayList();
    private ObservableList<String> filtro = FXCollections.observableArrayList();
    private ObservableList<Ponto> resultado = FXCollections.observableArrayList();
    private ArduinoSerial arduino = new ArduinoSerial("COM3");
    private static String id = " ";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregando os elementos da pagina
        cursorMao();
        carregarCB();
        carregarTB();
        limpar();

        //Ação dos Botões
        btnVoltar.setOnAction(e -> voltarMenu());
        btnId.setOnAction(e -> cartao());
        btnLimpar.setOnAction(e -> limpar());
        btnConsultar.setOnAction(e -> consultar(txtPesquisa.getText().trim(), cbFiltro.getSelectionModel().getSelectedItem()));
    }

    public void cursorMao() {
        btnVoltar.setCursor(Cursor.HAND);
        btnConsultar.setCursor(Cursor.HAND);
        btnId.setCursor(Cursor.HAND);
        btnLimpar.setCursor(Cursor.HAND);
        cbFiltro.setCursor(Cursor.HAND);
        cbPorta.setCursor(Cursor.HAND);
    }

    public void carregarTB() {
        tcStatus.setCellValueFactory(value -> value.getValue().getNome());
        tcDia.setCellValueFactory(value -> value.getValue().getDia());
        tcData.setCellValueFactory(value -> value.getValue().getData());
        tcHora.setCellValueFactory(value -> value.getValue().getHora());
        tvPonto.setItems(resultado);
    }

    public void carregarCB() {
        //Colocando os valores nas Listas
        porta.addAll("COM1", "COM3", "COM4", "COM5", "COM9");
        filtro.addAll("Matrícula", "Nome", "Cartão");

        //Colocando as Listas no ComboBox
        cbPorta.setItems(porta);
        cbFiltro.setItems(filtro);

        //Setando a Opção do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(1);
    }

    public void limpar() {
        txtPesquisa.setText("");
        lbCartao.setText("");
        lbNome.setText("");
        lbId.setText("");
        lbCurso.setText("");
        lbMatricula.setText("");
        lbSerie.setText("");
        lbTurma.setText("");
        lbTurno.setText("");
        cbPorta.getSelectionModel().select(1);
        cbFiltro.getSelectionModel().select(1);
        //Limpa a Lista Temporaria
        resultado.remove(0, resultado.size());
    }

    public void cartao() {
        lbId.setText("");
        arduino = new ArduinoSerial(cbPorta.getSelectionModel().getSelectedItem());
        arduino.initialize();
        int flag = 0;
        boolean a = true;
        if (a) {
            while (flag <= 40000) {
                System.out.println(arduino.read());
                if(flag==39998){
                    //System.out.println("oi");
                    id = arduino.read();
                }
                flag++;
            }
            System.out.println(id);
            lbId.setText(id);
        } else {
            arduino.close();
        }
        a = false;
        arduino.close();
    }

    public void consultar(String p, String f) {
        //Se a pesquisa for em branco
        if (p.equals("")) {
            Alerta.getAlertaInfo("Erro Ao Consultar Aluno", "Não Foi Possivel Consultar Aluno Pois o Campo de Consulta está em Branco", "");
            limpar();
        } else {
            limpar();
            if (f.equals("Nome")) {
                for (Aluno e : Conexao.getAlunos()) {
                    if (e.getNome().get().equals(p)) {
                        resultado.addAll(e.getPontos());
                        lbCartao.setText(e.getIdCartao().get());
                        lbNome.setText(e.getNome().get());
                        lbCurso.setText(e.getCurso().get());
                        lbMatricula.setText(String.valueOf(e.getMatricula().get()));
                        lbSerie.setText(e.getSerie().get());
                        lbTurma.setText(e.getTurma().get());
                        lbTurno.setText(e.getTurno().get());
                        break;
                    }
                }
            } else if (f.equals("Matrícula")) {
                for (Aluno e : Conexao.getAlunos()) {
                    String m = Integer.toString(e.getMatricula().get());
                    if (m.equals(p)) {
                        resultado.addAll(e.getPontos());
                        lbCartao.setText(e.getIdCartao().get());
                        lbNome.setText(e.getNome().get());
                        lbCurso.setText(e.getCurso().get());
                        lbMatricula.setText(String.valueOf(e.getMatricula().get()));
                        lbSerie.setText(e.getSerie().get());
                        lbTurma.setText(e.getTurma().get());
                        lbTurno.setText(e.getTurno().get());
                        break;
                    }
                }
            } else if (f.equals("Cartão")) {
                for (Aluno e : Conexao.getAlunos()) {
                    if (e.getIdCartao().get().equals(p)) {
                        resultado.addAll(e.getPontos());
                        lbCartao.setText(e.getIdCartao().get());
                        lbNome.setText(e.getNome().get());
                        lbCurso.setText(e.getCurso().get());
                        lbMatricula.setText(String.valueOf(e.getMatricula().get()));
                        lbSerie.setText(e.getSerie().get());
                        lbTurma.setText(e.getTurma().get());
                        lbTurno.setText(e.getTurno().get());
                        break;
                    }
                }
            }

            //Preenche a tabela com o resultado
            carregarTB();
            tvPonto.setItems(resultado);
        }
    }

    public void voltarMenu() {
        Principal p = new Principal();
        Historico.getStage().close();
        try {
            p.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Cadastro", "Falha Ao Abrir Tela de Cadastro", "Não Foi Possivel Abir a Tela de Cadastro");
        }
    }

}
