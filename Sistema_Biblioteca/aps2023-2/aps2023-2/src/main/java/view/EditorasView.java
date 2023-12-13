package view;

import controller.EditorasController;
import controller.EditorasFormController;
import controller.HomeController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EditorasView extends JFrame {

    final Font font;
    final Map<JButton, Integer> buttonIDMap = new HashMap<>();

    public EditorasView(ResultSet registros) {
        ConfiguracaoView.definirConfiguracao(this);
        this.font = new Font("Arial", Font.BOLD, 20);

        setTitle("Editoras");
        JPanel textGrid = new JPanel();
        GridLayout gl = new GridLayout(0, 3);
        textGrid.setLayout(gl);

        try {
            while (registros.next())
                criarCampoRegistro(textGrid, registros.getString("nome"), registros.getInt("id"));
        } catch (SQLException e) {

        }


        JScrollPane textGridScrollPane = new JScrollPane(textGrid,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textGridScrollPane.setBounds(5, 0, 515, 340);
        add(textGridScrollPane);


        add(this.definirBotao("Home", "voltarHome", 170, 480, 200, 60));
        add(this.definirBotao("Incluir", "abrirIncluirEditoras", 170, 380, 200, 60));

    }

    private void criarCampoRegistro(JPanel textGrid, String nome, int id) {
        JTextField textField = new JTextField(nome, 1);
        textField.setEditable(false);
        textGrid.add(textField);

        JButton modificarButton = definirBotao("modificar", "abrirFormEditoras", 400, 10, 50, 10);
        buttonIDMap.put(modificarButton, id);
        textGrid.add(modificarButton);

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
        else if (acao.equals("abrirIncluirEditoras"))
            botao.addActionListener(this::abrirFormEditoras);
        else if (acao.equals("excluir"))
            botao.addActionListener(this::excluir);
        else if (acao.equals("abrirFormEditoras"))
            botao.addActionListener(this::abrirFormEditoras);
    }

    private void voltarHome(ActionEvent e) {
        dispose();
        HomeController.iniciaizar();
    }

    private void abrirFormEditoras(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        int id = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        dispose();
        EditorasFormController.inicializar(id);
    }

    private void excluir(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        int id = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        EditorasController.excluir(id);
        dispose();
    }

}
