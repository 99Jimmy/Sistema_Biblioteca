package view;


import controller.EditorasController;
import controller.EditorasFormController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditorasFormView extends JFrame {
    final Font font;
    private JTextArea nome;
    private JTextArea url;
    private int id;

    public EditorasFormView(ResultSet dados) {
        this.font = new Font("Arial", Font.BOLD, 20);

        try {
            if (dados.next())
                this.id = dados.getInt("id");
            else
                this.id = 0;

            ConfiguracaoView.definirConfiguracao(this);
            setTitle((this.id > 0 ? "Modificar" : "Incluir") + " Editora");

            this.nome = this.definirText((this.id > 0 ? dados.getString("nome") : ""), 150);
            this.url = this.definirText((this.id > 0 ? dados.getString("url") : ""), 270);

            add(this.nome);
            add(this.definirLabel("      NOME", 150));

            add(this.url);
            add(this.definirLabel("        URL", 270));


            add(this.definirBotao("Confirmar", "confirmarEditoras", 450));
            add(this.definirBotao("Voltar", "voltarEditoras", 520));

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private JTextArea definirText(String texto, Integer y) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(30, y, 300, 40);
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setBorder(new LineBorder(Color.BLUE));
        //textArea.setEditable(editable);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(font);
        return textArea;
    }


    private JLabel definirLabel(String texto, Integer y) {
        JLabel label = new JLabel(texto);
        label.setBounds(350, y, 130, 40);
        label.setBorder(new LineBorder(Color.yellow));
        label.setBackground(new Color(234, 160, 2, 147));
        label.setForeground(new Color(0, 0, 0, 150));
        label.setOpaque(true);
        label.setFont(font);

        return label;
    }


    private JButton definirBotao(String texto, String acao, Integer y) {
        JButton botao = new JButton(texto);
        botao.setBounds(170, y, 200, 60);
        botao.setForeground(new Color(0, 0, 0, 255));
        botao.setBackground(new Color(213, 210, 210, 255));
        botao.setBorder(new LineBorder(Color.black));
        this.definirAcao(acao, botao);
        return botao;
    }

    private void definirAcao(String acao, JButton botao) {
        if (acao.equals("voltarEditoras"))
            botao.addActionListener(this::voltarEditoas);
        else if (acao.equals("confirmarEditoras"))
            botao.addActionListener(this::confirmarEditoras);

    }

    private void confirmarEditoras(ActionEvent e) {
        String nome = this.nome.getText();
        String url = this.url.getText();

        if (this.id == 0)
            EditorasFormController.incluir(nome, url);
        else
            EditorasFormController.alterar(nome, url, this.id);

        dispose();
    }

    private void voltarEditoas(ActionEvent e) {
        dispose();
        EditorasController.inicializar();
    }


}
