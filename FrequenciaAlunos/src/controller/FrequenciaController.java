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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Frequencia;
import main.Principal;
import model.Alerta;
import model.Aluno;
import model.ArduinoSerial;
import model.Conexao;
import model.ManipulandoDataHora;
import model.Ponto;

/**
 * FXML Controller class
 *
 * @author Znoque
 */
public class FrequenciaController implements Initializable {

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
    @FXML
    private ComboBox<String> cbPorta;
    @FXML
    private Label lbId;
    @FXML
    private Button btnId;
    @FXML
    private Label lbStatus;
    @FXML
    private Button btnVerificar;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnVoltar;
    
    private ObservableList<String> porta = FXCollections.observableArrayList();
    ManipulandoDataHora Mdata = new ManipulandoDataHora();
    String data = Mdata.getDataFormatada();
    String dia = Mdata.getDiaSemana();
    String hora;
    int posicao;
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
        limpar();
        
        //A????o dos Bot??es
        btnVoltar.setOnAction(e -> voltarMenu());
        btnId.setOnAction(e -> cartao());
        btnLimpar.setOnAction(e -> limpar());
        btnVerificar.setOnAction(e -> verificar());
    }
    
    public void cursorMao() {
        btnVoltar.setCursor(Cursor.HAND);
        btnVerificar.setCursor(Cursor.HAND);
        btnId.setCursor(Cursor.HAND);
        btnLimpar.setCursor(Cursor.HAND);
        cbPorta.setCursor(Cursor.HAND);
    }
    
    public void limpar(){
        lbStatus.setText("");
        lbCartao.setText("");
        lbNome.setText("");
        lbId.setText("");
        lbCurso.setText("");
        lbMatricula.setText("");
        lbSerie.setText("");
        lbTurma.setText("");
        lbTurno.setText("");
        cbPorta.getSelectionModel().select(1);
        posicao=0;
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
    
    public void carregarCB() {
        //Colocando os valores nas Listas
        porta.addAll("COM1", "COM3", "COM4", "COM5", "COM9");

        //Colocando as Listas no ComboBox
        cbPorta.setItems(porta);

        //Setando a Op????o do ComboBox
        cbPorta.getSelectionModel().select(2);
    }
    
    public void consultar(String idc) {
        //Se o Id for em branco
        if(idc.equals("")){
            limpar();
        }else if (idc != ""){ //Se o IdCart??o for diferente de vazio
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getIdCartao().get().contains(idc)) { //Se encontrar o IdCart??o em um dos alunos 
                    lbCartao.setText(e.getIdCartao().get());
                    lbNome.setText(e.getNome().get());
                    lbCurso.setText(e.getCurso().get());
                    lbMatricula.setText(String.valueOf(e.getMatricula().get()));
                    lbSerie.setText(e.getSerie().get());
                    lbTurma.setText(e.getTurma().get());
                    lbTurno.setText(e.getTurno().get());
                    break;
                }
                posicao++;
            }
        }
    }
    
    public void verificar() {
        if (lbId.getText().equals("")) {
            Alerta.getAlertaInfo("Erro Ao Tentar Verificar", "N??o Foi Poss??vel Encontrar o Cart??o", "Por Favor Passe O Cart??o");
        }else{
            if (Conexao.getAlunos().get(posicao).getStatus().get().equals("Entrada")) {
                hora = Mdata.getHoraFormatada();
                lbStatus.setStyle("-fx-text-fill: green;");
                lbStatus.setText("Entrada");
                Conexao.getAlunos().get(posicao).getPontos().add(new Ponto("Entrada", dia, data, hora));
                Conexao.getAlunos().get(posicao).setStatus("Sa??da");
            } else {
                hora = Mdata.getHoraFormatada();
                lbStatus.setStyle("-fx-text-fill: red;");
                lbStatus.setText("Sa??da");
                Conexao.getAlunos().get(posicao).getPontos().add(new Ponto("Sa??da", dia, data, hora));
                Conexao.getAlunos().get(posicao).setStatus("Entrada");
            }
        }
    }
    
    public void voltarMenu() {
        Principal p = new Principal();
        Frequencia.getStage().close();
        try {
            p.start(new Stage());
        } catch (Exception e) {
            Alerta.getAlertaErro("Erro Ao Abrir Tela de Cadastro", "Falha Ao Abrir Tela de Cadastro", "N??o Foi Possivel Abir a Tela de Cadastro");
        }
    }
    
}
