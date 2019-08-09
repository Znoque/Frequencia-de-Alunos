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
import javax.xml.bind.Marshaller;
import main.Cadastro;
import main.Principal;
import model.Alerta;
import model.Aluno;
import model.Conexao;

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
    private int mat = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Carregando os elementos da pagina
        carregarTabela();
        carregarCB();
        lbId.setText("");

        //Ação dos Botões
        btnVoltar.setOnAction(e -> voltarMenu());
        btnLimpar.setOnAction(e -> limpar());
        btnAdicionar.setOnAction(e -> cadastro());
        btnExcluir.setOnAction(e -> excluir());
        btnEditar.setOnAction(e -> editar());

        //Ação Selecionar Item da Tabela
        tbAlunos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarTabela(newValue));
    }

    public void cadastro() {
        boolean b = false;
        if (txtMatricula.getText().equals("") || txtNome.getText().equals("")) {
            Alerta.getAlertaErro("Erro Ao Cadastrar Aluno", "Erro ao realizar cadastro do aluno", "Por Favor Preencha o Campo de texto 'Matrícula' e/ou 'Nome'");
        } else {
            String tempNome = "";
            mat = Integer.parseInt(txtMatricula.getText().trim());
            for (Aluno a : Conexao.getAlunos()) {
                if (a.getMatricula().get() == mat) {
                    b = true;
                    tempNome = a.getNome().get();
                    break;
                } else {
                    b = false;
                }
            }
            if (b) {
                Alerta.getAlertaInfo("Erro Ao Cadastrar Aluno", "Não é Possivel Cadastrar o Aluno", "Já existe um aluno com a matricula digitada:\nMatrícula: " + mat + "\nNome: " + tempNome);
            } else {
                try {
                    boolean r = Alerta.getAlertaConfirma("Deseja Inseir o Cadastro?", "Deseja inserir um novo Cadastro?");
                    if (r) {
                        mat = Integer.parseInt(txtMatricula.getText().trim());
                        Conexao.getAlunos().add(new Aluno(mat, txtNome.getText().trim(), cbCurso.getSelectionModel().getSelectedItem(), cbTurma.getSelectionModel().getSelectedItem(), cbTurno.getSelectionModel().getSelectedItem(), cbSerie.getSelectionModel().getSelectedItem()));
                        carregarTabela();
                        limpar();
                        mat = 0;
                    }
                } catch (NumberFormatException ex) {
                    Alerta.getAlertaErro("Erro Ao Cadastrar Aluno", "Erro Ao Realizar Cadastro do Aluno", "Por Favor Preencha o Campo 'Matricula' Utilizando Apenas Números!!");
                }
            }
        }
        System.out.println(Conexao.getAlunos());
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
        System.out.println(Conexao.getAlunos());
    }

    public void editar() {
        boolean b = false;
        if (txtMatricula.getText().equals("") || txtNome.getText().equals("")) {
            Alerta.getAlertaErro("Erro Ao Editar Aluno", "Erro ao editar cadastro do aluno", "Por Favor Selecione Um Item Na Tabela");
        } else {
            String tempNome = "";
            int id = tbAlunos.getSelectionModel().getSelectedItem().getId().get();
            mat = Integer.parseInt(txtMatricula.getText().trim());
            for (Aluno a : Conexao.getAlunos()) {
                if ((a.getMatricula().get() == mat) && (a.getId().get() != id)) {
                    b = true;
                    tempNome = a.getNome().get();
                    break;
                } else {
                    b = false;
                }
            }
            if (b) {
                Alerta.getAlertaInfo("Erro ao Editar Cadastro Aluno", "Não é Possivel Editar o Cadastro do Aluno", "Já existe um aluno com a matricula digitada:\nMatrícula: " + mat + "\nNome: " + tempNome);
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
        }
        System.out.println(Conexao.getAlunos());
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

        //Setando a 1ª Opção do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(0);
        cbCurso.getSelectionModel().select(0);
        cbTurma.getSelectionModel().select(0);
        cbTurno.getSelectionModel().select(0);
        cbSerie.getSelectionModel().select(0);
    }

    public void limpar() {
        //Setando a 1ª Opção do ComboBox
        cbPorta.getSelectionModel().select(2);
        cbFiltro.getSelectionModel().select(0);
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
