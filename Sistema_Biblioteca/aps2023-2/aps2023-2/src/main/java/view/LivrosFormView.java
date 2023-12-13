package view;

import controller.LivrosController;
import controller.LivrosFormController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class LivrosFormView extends JFrame {

    final Font font;
    private JPanel autoresPanel;
    private String[] autores;
    private int id;
    private JTextArea titulo;
    private JFormattedTextField preco;
    private JComboBox editoras;
    private JTextArea isbn;
    private ArrayList<JComboBox<String>> listaAutores = new ArrayList<>();
    public LivrosFormView(ResultSet dados, String[] editoras, String[] autores, ResultSet autoresRelacionados) {
        this.autores = autores;
        ConfiguracaoView.definirConfiguracao(this);
        this.font = new Font("Arial", Font.BOLD, 20);

        try {
            if (dados.next())
                this.id = dados.getInt("isbn");
            else
                this.id = 0;

            setTitle((this.id > 0 ? "Modificar" : "Incluir") + " Livro");


            this.titulo = this.definirText((this.id > 0 ? dados.getString("titulo") : ""),40);

            add(this.titulo);
            add(this.definirLabel("    TITULO", 40));


            NumberFormat doubleFormat = new DecimalFormat("#0.00");

            NumberFormatter doubleFormatter = new NumberFormatter(doubleFormat);
            doubleFormatter.setValueClass(Double.class);
            doubleFormatter.setMinimum(0.0);
            doubleFormatter.setMaximum(1000000.0);

            JFormattedTextField doubleField = new JFormattedTextField(doubleFormatter);
            doubleField.setBounds(30, 120, 300, 40);
            doubleField.setText((this.id > 0 ? dados.getString("preco").replace(".", ",") : "0,00"));
            doubleField.setFont(font);
            doubleField.setBorder(new LineBorder(Color.BLUE));

            add(this.definirLabel("    PREÇO", 120));
            add(doubleField);
            this.preco = doubleField;

            this.isbn = this.definirText((this.id > 0 ? dados.getString("isbn") : ""),200);
            add(this.definirLabel("    ISBN", 200));

            add(this.isbn);




            JComboBox<String> autoresComboBox = new JComboBox<>(autores);
            autoresComboBox.setSelectedItem((autoresRelacionados.next() ? autoresRelacionados.getString("nome") : "Selecione um autor"));
            autoresComboBox.setFont(font);

            this.listaAutores.add(autoresComboBox);


            autoresPanel = new JPanel();
            GridLayout gl = new GridLayout(0, 1);
            autoresPanel.setLayout(gl);
            autoresPanel.setBackground(new Color(255, 255, 255));
            autoresPanel.setBounds(80, 270, 250, 40);
            autoresPanel.add(autoresComboBox);



            while (autoresRelacionados.next()) {
                JComboBox autor = definirComboBox(autores, autoresRelacionados.getString("nome"));
                this.listaAutores.add(autor);
                autoresPanel.add(autor);
                autoresPanel.revalidate();
            }


            JScrollPane autoresScrollPane = new JScrollPane(autoresPanel);
            autoresScrollPane.setBounds(80, 270, 250, 80);
            autoresScrollPane.setBorder(null);
            add(autoresScrollPane);

            add(definirBotao("adicionar autor", "addAutor", 350, 270, 130, 40));


            this.editoras = this.definirComboBox(editoras, (this.id > 0 ? dados.getString("editora") : "Selecione uma editora"));
            add(this.editoras);
            add(this.definirLabel("    EDITORA", 370));


            add(this.definirBotao("Confirmar", "confirmarLivros", 170, 450, 200, 60));
            add(this.definirBotao("Voltar", "voltarLivros", 170, 520, 200, 60));

        } catch (SQLException e) {
            System.out.println(e);
        }
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


    private JTextArea definirText(String texto,Integer y) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(30, y, 300, 40);
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setBorder(new LineBorder(Color.BLUE));
        textArea.setEditable(true);
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

    private JComboBox<String> definirComboBox(String[] boxOptions, String selectedOption) {
        JComboBox<String> comboBox = new JComboBox<>(boxOptions);
        comboBox.setBounds(80, 370, 250, 40);
        comboBox.setSelectedItem(selectedOption);
        comboBox.setFont(font);

        return comboBox;
    }

    private void definirAcao(String acao, JButton botao) {
        if (acao.equals("voltarLivros"))
            botao.addActionListener(this::voltarLivros);
        else if (acao.equals("addAutor"))
            botao.addActionListener(this::addAutor);
        else if (acao.equals("confirmarLivros"))
            botao.addActionListener(this::confirmarLivros);
    }

    private void voltarLivros(ActionEvent e) {
        dispose();
        LivrosController.inicializar();
    }

    private void addAutor(ActionEvent e) {
        JComboBox autor = definirComboBox(autores, "Selecione um autor,");
        this.listaAutores.add(autor);
        autoresPanel.add(autor);
        autoresPanel.revalidate();
    }

    private void confirmarLivros(ActionEvent e) {


        String isbn = this.isbn.getText();
        String titulo = this.titulo.getText();
        String preco = this.preco.getText().replace(",", ".");
        String editora = this.editoras.getSelectedItem().toString();


       // boolean isbnExistente = LivrosFormController.verificarISBNExistente(isbn);

       // if(isbnExistente){
           // JOptionPane.showMessageDialog(this,"ISBN já cadastrada", "aviso", JOptionPane.WARNING_MESSAGE);
        //}else {

            if (this.id == 0)
                LivrosFormController.incluir(titulo, editora, preco, this.listaAutores, isbn);
            else
                LivrosFormController.alterar(titulo, editora, preco, this.listaAutores, this.id,isbn);

            dispose();
        }
    }


