package view;

import controller.AutoresController;
import controller.HomeController;
import controller.LivrosController;
import controller.LivrosFormController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class LivrosView extends JFrame {

    final Font font;
    final Map<JButton, Integer> buttonIDMap = new HashMap<>();

    public LivrosView(ResultSet registros) {
        ConfiguracaoView.definirConfiguracao(this);
        this.font = new Font("Arial", Font.BOLD, 20);
        setTitle("Livros");


        JPanel textGrid = new JPanel();
        GridLayout gl = new GridLayout(0, 3);
        textGrid.setLayout(gl);


        try {
            while (registros.next())
                criarCampoRegistro(textGrid, registros.getString("titulo"), registros.getInt("isbn"));
        } catch (SQLException e) {
            System.out.println(e);
        }


        JScrollPane textGridScrollPane = new JScrollPane(textGrid,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textGridScrollPane.setBounds(5, 0, 515, 340);
        add(textGridScrollPane);


        add(this.definirBotao("Home", "voltarHome", 170, 480, 200, 60));
        add(this.definirBotao("Incluir", "abrirIncluirLivros", 170, 380, 200, 60));
    }

    private void criarCampoRegistro(JPanel textGrid, String nome, Integer id) {
        JTextField textField = new JTextField(nome, 1);
        textField.setEditable(false);
        textGrid.add(textField);

        JButton modificarButton = definirBotao("modificar", "abrirModificarLivros", 400, 10, 50, 10);
        buttonIDMap.put(modificarButton, id);
        textGrid.add(modificarButton);


       // textGrid.add(definirBotao("excluir", "excluir", 400, 10, 50, 10));
        JButton excluirBotao = definirBotao("excluir", "excluir", 400, 10, 50, 10);
        textGrid.add(excluirBotao);
        buttonIDMap.put(excluirBotao, id);

    }


    private JButton definirBotao(String texto, String acao, Integer x, Integer y, Integer width, Integer height) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, width, height);
        botao.setForeground(new Color(0, 0, 0, 255));
        botao.setBackground(new Color(213, 210, 210, 255));
        botao.setBorder(new LineBorder(Color.black));
        this.definirAcao(acao, botao);
        return botao;
    }


    private void definirAcao(String acao, JButton botao) {
        if (acao.equals("voltarHome"))
            botao.addActionListener(this::voltarHome);
        if (acao.equals("abrirIncluirLivros"))
            botao.addActionListener(this::abrirFormLivros);
        if (acao.equals("abrirModificarLivros"))
            botao.addActionListener(this::abrirFormLivros);
        else if(acao.equals("excluir"))
            botao.addActionListener(this::excluir);
    }


    private void voltarHome(ActionEvent e) {
        dispose();
        HomeController.iniciaizar();
    }

    private void abrirFormLivros(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        int idDoBotao = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        dispose();
        LivrosFormController.iniciaizar(idDoBotao);
    }

    private void excluir(ActionEvent e){
        JButton botao = (JButton) e.getSource();
        int id = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        LivrosController.excluir(id);
        dispose();
    }
}
