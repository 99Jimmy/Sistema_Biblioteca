package view;

import controller.AutoresController;
import controller.EditorasController;
import controller.LivrosController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HomeView extends JFrame {
    final Font font;

    public HomeView() {
        ConfiguracaoView.definirConfiguracao(this);
        this.font = new Font("Arial", Font.BOLD , 40);
        setTitle("Home");

        add(this.definirBotao("Livros", "abrirLivros",1));
        add(this.definirBotao("Autores","abrirAutores", 2));
        add(this.definirBotao("Editoras","abrirEditoras", 3));
    }

    private JButton definirBotao(String texto,String acao, Integer index){
        JButton botao = new JButton(texto);
        botao.setBounds(145, 100 * index, 250, 70);
        botao.setFont(font);
        botao.setForeground(new Color(255, 255, 255));
        botao.setBackground(new Color(0, 0, 0));
        botao.setBorderPainted(false);
        this.definirAcao(acao, botao);
        return botao;
    }

    private void definirAcao(String acao, JButton botao){
        if(acao.equals("abrirLivros"))
            botao.addActionListener(this::abrirLivros);
        if(acao.equals("abrirAutores"))
            botao.addActionListener(this::abrirAutores);
        if(acao.equals("abrirEditoras"))
            botao.addActionListener(this::abrirEditoras);
    }


    private void abrirLivros(ActionEvent e){
        dispose();
        LivrosController.inicializar();
    }
    private void abrirAutores(ActionEvent e){
        dispose();
        AutoresController.inicializar();
    }

    private void abrirEditoras(ActionEvent e){
        dispose();
        EditorasController.inicializar();
    }

}
