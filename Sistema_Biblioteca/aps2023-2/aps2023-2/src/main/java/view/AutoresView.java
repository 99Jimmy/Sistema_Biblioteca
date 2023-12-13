package view;

import controller.AutoresController;
import controller.AutoresFormController;
import controller.EditorasController;
import controller.HomeController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AutoresView  extends JFrame {

    final Map<JButton, Integer> buttonIDMap = new HashMap<>();
    final Font font;


    public AutoresView(ResultSet registros){
        ConfiguracaoView.definirConfiguracao(this);
        this.font=  new Font("Arial", Font.BOLD , 20);

        setTitle("Autores");


        JPanel textGrid = new JPanel();
        GridLayout gl = new GridLayout (0,3);
        textGrid.setLayout(gl);

        try{
            while(registros.next())
                criarCampoRegistro(textGrid, registros.getString("nome"), registros.getInt("id"));
        }catch (SQLException e){

        }


        JScrollPane textGridScrollPane = new JScrollPane(textGrid,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textGridScrollPane.setBounds(5,0,515,340);
        add(textGridScrollPane);



        add(this.definirBotao("Incluir", "abrirIncluirAutores",170,380,200,60));
        add(definirBotao("HOME","voltarHome",170,480,200,60));

    }

    private void criarCampoRegistro(JPanel textGrid, String nome, Integer id) {
        JTextField textField = new JTextField(nome, 1);
        textField.setEditable(false);
        textGrid.add(textField);

        JButton modificarButton = definirBotao("modificar", "abrirFormAutores", 400, 10, 50, 10);
        buttonIDMap.put(modificarButton, id);
        textGrid.add(modificarButton);

        JButton excluirBotao = definirBotao("excluir", "excluir", 400, 10, 50, 10);
        textGrid.add(excluirBotao);
        buttonIDMap.put(excluirBotao, id);
    }


    private JButton definirBotao(String texto,String acao,Integer x ,Integer y, Integer width, Integer height){
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, width, height);
        botao.setForeground(new Color(0, 0, 0, 255));
        botao.setBackground(new Color(213, 210, 210, 255));
        botao.setBorder(new LineBorder(Color.black));
        this.definirAcao(acao, botao);
        return botao;
    }

    private void definirAcao(String acao, JButton botao){
        if(acao.equals("voltarHome"))
            botao.addActionListener(this::voltarHome);
        else if(acao.equals("abrirIncluirAutores"))
            botao.addActionListener(this::abrirFormAutores);
        else if(acao.equals("abrirFormAutores"))
            botao.addActionListener(this::abrirFormAutores);
        else if(acao.equals("excluir"))
            botao.addActionListener(this::excluir);

    }

    private void abrirFormAutores(ActionEvent e){
        JButton botao = (JButton) e.getSource();

        int idDoBotao = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        dispose();
        AutoresFormController.inicializar(idDoBotao);
    }

    private void excluir(ActionEvent e){
        JButton botao = (JButton) e.getSource();
        int id = (buttonIDMap.get(botao) == null ? 0 : buttonIDMap.get(botao));

        AutoresController.excluir(id);
        dispose();
    }

    private void voltarHome(ActionEvent e){
        dispose();
        HomeController.iniciaizar();
    }

}
