/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import comparator.cursoComparator;
import java.net.URL;
import java.util.Collections;
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
import model.Conexao;
import comparator.nomeComparator;
import comparator.serieComparator;
import comparator.turnoComparator;
import javafx.scene.Cursor;
import model.ArduinoSerial;

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
    private Button btnExcluir;
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
    private ObservableList<Aluno> resultado = FXCollections.observableArrayList();
    private int mat = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregando os elementos da pagina
        cursorMao();
        carregarTabela();
        carregarCB();
        lbId.setText("");

        //Ação dos Botões
        btnVoltar.setOnAction(e -> voltarMenu());
        btnLimpar.setOnAction(e -> limpar());
        btnAdicionar.setOnAction(e -> cadastro());
        btnExcluir.setOnAction(e -> excluir());
        btnEditar.setOnAction(e -> editar());
        btnPesquisa.setOnAction(e -> pesquisar(txtPesquisa.getText().trim(), cbFiltro.getSelectionModel().getSelectedItem()));
        btnId.setOnAction(e -> cartao());

        //Ação Selecionar Item da Tabela
        tbAlunos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarTabela(newValue));
    }

    public void cursorMao() {
        btnVoltar.setCursor(Cursor.HAND);
        btnAdicionar.setCursor(Cursor.HAND);
        btnEditar.setCursor(Cursor.HAND);
        btnExcluir.setCursor(Cursor.HAND);
        btnId.setCursor(Cursor.HAND);
        btnLimpar.setCursor(Cursor.HAND);
        btnPesquisa.setCursor(Cursor.HAND);
        cbFiltro.setCursor(Cursor.HAND);
        cbPorta.setCursor(Cursor.HAND);
        cbCurso.setCursor(Cursor.HAND);
        cbSerie.setCursor(Cursor.HAND);
        cbTurma.setCursor(Cursor.HAND);
        cbTurno.setCursor(Cursor.HAND);
        tbAlunos.setCursor(Cursor.HAND);
    }

    public void cartao() {
        ArduinoSerial arduino = new ArduinoSerial(cbPorta.getSelectionModel().getSelectedItem());
        System.out.println(arduino.getNamePort());
        arduino.initialize();
        int flag = 0;
        boolean a = true;
        if (a) {
            while (flag <= 40000) {
                //lbId.setText(String.valueOf(flag));
                lbId.setText(arduino.read());
                flag++;
            }
        } else {
            arduino.close();
        }
        a = false;
        arduino.close();
    }

    public void cadastro() {
        boolean b = false;
        if (txtMatricula.getText().equals("") || txtNome.getText().equals("")) {
            Alerta.getAlertaErro("Erro Ao Cadastrar Aluno", "Erro ao realizar cadastro do aluno", "Por Favor Preencha o Campo de texto 'Matrícula' e/ou 'Nome'");
        } else {
            try {
                String tempNome = "";
                String cartao = "";
                mat = Integer.parseInt(txtMatricula.getText().trim());
                for (Aluno a : Conexao.getAlunos()) {
                    if (a.getMatricula().get() == mat) {
                        b = true;
                        tempNome = a.getNome().get();
                        cartao = a.getIdCartao().get();
                        break;
                    } else if (a.getIdCartao().get().equals(lbId.getText())) {
                        b = true;
                        tempNome = a.getNome().get();
                        cartao = a.getIdCartao().get();
                        break;
                    } else {
                        b = false;
                    }
                }
                if (b) {
                    Alerta.getAlertaInfo("Erro Ao Cadastrar Aluno", "Não é Possivel Cadastrar o Aluno", "Já Existe Um Aluno Com a Mesma Matricula ou Id do Cartão digitada:\nMatrícula: " + mat + "\nNome: " + tempNome + "\nCartão: " + cartao);
                } else {

                    boolean r = Alerta.getAlertaConfirma("Deseja Inseir o Cadastro?", "Deseja inserir um novo Cadastro?");
                    if (r) {
                        mat = Integer.parseInt(txtMatricula.getText().trim());
                        Conexao.getAlunos().add(new Aluno(mat, txtNome.getText().trim(), lbId.getText(), cbCurso.getSelectionModel().getSelectedItem(), cbTurma.getSelectionModel().getSelectedItem(), cbTurno.getSelectionModel().getSelectedItem(), cbSerie.getSelectionModel().getSelectedItem()));
                        carregarTabela();
                        limpar();
                        mat = 0;
                    }
                }
            } catch (NumberFormatException ex) {
                Alerta.getAlertaErro("Erro Ao Cadastrar Aluno", "Erro Ao Realizar Cadastro do Aluno", "Por Favor Preencha o Campo 'Matricula' Utilizando Apenas Números!!");
            }
        }
    }

    public void excluir() {
        if (txtMatricula.getText().equals("") || txtNome.getText().equals("")) {
            Alerta.getAlertaErro("Erro ao Excluir Link", "Por Favor Selecione Um Item Na Tabela", "");
        } else {
            boolean r = Alerta.getAlertaConfirma("Deseja Excluir o Cadastro?", "Deseja Excluir o Cadastro Selecionado?");
            if (r) {
                mat = tbAlunos.getSelectionModel().getSelectedItem().getId().get();
                Conexao.getAlunos().removeIf(Aluno -> mat == Aluno.getId().get());
            }
        }
        if (Conexao.getAlunos().size() == 0) {
            limpar();
        }
    }

    public void editar() {
        boolean b = false;
        if (txtMatricula.getText().equals("") || txtNome.getText().equals("")) {
            Alerta.getAlertaErro("Erro Ao Editar Aluno", "Erro ao editar cadastro do aluno", "Por Favor Selecione Um Item Na Tabela");
        } else {
            try {
                String tempNome = "";
                String cartao = "";
                int id = tbAlunos.getSelectionModel().getSelectedItem().getId().get();
                mat = Integer.parseInt(txtMatricula.getText().trim());
                for (Aluno a : Conexao.getAlunos()) {
                    if ((a.getMatricula().get() == mat) && (a.getId().get() != id)) {
                        b = true;
                        tempNome = a.getNome().get();
                        cartao = a.getIdCartao().get();
                        break;
                    }else if ((a.getIdCartao().get().equals(lbId.getText())) && (a.getId().get() != id)) { 
                        b = true;
                        tempNome = a.getNome().get();
                        cartao = a.getIdCartao().get();
                        break;
                    }else {
                        b = false;
                    }
                }
                if (b) {
                    Alerta.getAlertaInfo("Erro ao Editar Cadastro Aluno", "Não é Possivel Editar o Cadastro do Aluno", "Já Existe Um Aluno Com a Mesma Matricula ou Id do Cartão digitada:\nMatrícula: " + mat + "\nNome: " + tempNome + "\nCartão: " + cartao);
                } else {
                    boolean r = Alerta.getAlertaConfirma("Deseja Alterar o Cadastro?", "Deseja Alterar o Cadastro Selecionado?");
                    if (r) {
                        int posicao = 0;
                        int idTabela = tbAlunos.getSelectionModel().getSelectedItem().getId().get();
                        for (Aluno l : Conexao.getAlunos()) {
                            if (idTabela == l.getId().get()) {
                                Conexao.getAlunos().get(posicao).setMatricula(Integer.parseInt(txtMatricula.getText().trim()));
                                Conexao.getAlunos().get(posicao).setNome(txtNome.getText().trim());
                                Conexao.getAlunos().get(posicao).setIdCartao(lbId.getText());
                                Conexao.getAlunos().get(posicao).setCurso(cbCurso.getSelectionModel().getSelectedItem());
                                Conexao.getAlunos().get(posicao).setIdCartao(lbId.getText());
                                Conexao.getAlunos().get(posicao).setTurma(cbTurma.getSelectionModel().getSelectedItem());
                                Conexao.getAlunos().get(posicao).setTurno(cbTurno.getSelectionModel().getSelectedItem());
                                Conexao.getAlunos().get(posicao).setSerie(cbSerie.getSelectionModel().getSelectedItem());
                                break;
                            } else {
                                posicao++;
                            }
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                Alerta.getAlertaErro("Erro Ao Editar Aluno", "Erro Ao Editar Aluno", "Por Favor Preencha o Campo 'Matricula' Utilizando Apenas Números!!");
            }
        }
    }

    public void carregarTabela() {
        tcMatricula.setCellValueFactory(value -> value.getValue().getMatricula().asObject());
        tcNome.setCellValueFactory(value -> value.getValue().getNome());
        tcId.setCellValueFactory(value -> value.getValue().getIdCartao());
        tcCurso.setCellValueFactory(value -> value.getValue().getCurso());
        tcTurma.setCellValueFactory(value -> value.getValue().getTurma());
        tcTurno.setCellValueFactory(value -> value.getValue().getTurno());
        tcSerie.setCellValueFactory(value -> value.getValue().getSerie());
        tbAlunos.setItems(Conexao.getAlunos());
        Collections.sort(Conexao.getAlunos(), new nomeComparator());
    }

    public void carregarCB() {
        //Colocando os valores nas Listas
        porta.addAll("COM1", "COM3", "COM4", "COM5", "COM9");
        filtro.addAll("Matrícula", "Nome", "Cartão", "Curso", "Turma", "Turno", "Serie");
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

        //Setando a Opção do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(1);
        cbCurso.getSelectionModel().select(0);
        cbTurma.getSelectionModel().select(0);
        cbTurno.getSelectionModel().select(0);
        cbSerie.getSelectionModel().select(0);
    }

    public void pesquisar(String p, String f) {
        //Limpa a Lista Temporaria
        resultado.remove(0, resultado.size());

        //Se a pesquisa for em branco
        if (p.equals("")) {
            carregarTabela();
            limpar();
        }

        if (f.equals("Nome")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getNome().get().contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Matrícula")) {
            for (Aluno e : Conexao.getAlunos()) {
                String m = Integer.toString(e.getMatricula().get());
                if (m.contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Cartão")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getIdCartao().get().contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Curso")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getCurso().get().contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Turma")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getTurma().get().contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Turno")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getTurno().get().contains(p)) {
                    resultado.add(e);
                }
            }
        } else if (f.equals("Serie")) {
            for (Aluno e : Conexao.getAlunos()) {
                if (e.getSerie().get().contains(p)) {
                    resultado.add(e);
                }
            }
        }

        //Preenche a tabela com o resultado
        tbAlunos.setItems(resultado);

        //Lista por Nome, Curso, Serie ou Turno
        if (f.equals("Curso")) {
            Collections.sort(resultado, new cursoComparator());
        } else if (f.equals("Serie")) {
            Collections.sort(resultado, new serieComparator());
        } else if (f.equals("Turno")) {
            Collections.sort(resultado, new turnoComparator());
        } else {
            Collections.sort(resultado, new nomeComparator());
        }
    }

    public void limpar() {
        //Setando a 1ª Opção do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(1);
        cbCurso.getSelectionModel().select(0);
        cbTurma.getSelectionModel().select(0);
        cbTurno.getSelectionModel().select(0);
        cbSerie.getSelectionModel().select(0);
        //Setando TextField
        txtMatricula.setText("");
        txtNome.setText("");
        txtPesquisa.setText("");
        lbId.setText("");
    }

    public void selecionarTabela(Aluno a) {
        try {
            txtMatricula.setText(String.valueOf(a.getMatricula().get()));
            txtNome.setText(a.getNome().get());
            lbId.setText(a.getIdCartao().get());
            //Setando ComboBox Curso
            for (String c : curso) {
                if (a.getCurso().get().equals(c)) {
                    cbCurso.getSelectionModel().select(c);
                    break;
                }
            }
            //Setando ComboBox Turma
            for (String t : turma) {
                if (a.getTurma().get().equals(t)) {
                    cbTurma.getSelectionModel().select(t);
                    break;
                }
            }
            //Setando ComboBox Turno
            for (String t2 : turno) {
                if (a.getTurno().get().equals(t2)) {
                    cbTurno.getSelectionModel().select(t2);
                    break;
                }
            }
            //Setando ComboBox Serie
            for (String s : serie) {
                if (a.getSerie().get().equals(s)) {
                    cbSerie.getSelectionModel().select(s);
                    break;
                }
            }
        } catch (NullPointerException ex) {
            Alerta.getAlertaInfo("Tabela Vazia", "Não há conteúdo na tabela", "Tabela Vazia, Cadastre Um Aluno!");
        }
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
