package controller;

import model.Autor;
import model.Editora;
import model.Livro;
import view.LivrosFormView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LivrosFormController {

    public static void iniciaizar(int id){
        ArrayList<String> editoras = new ArrayList<String>();
        ArrayList<String> autores = new ArrayList<String>();
        editoras.add("Selecione uma editora");
        autores.add("Selecione um autor");

        Livro livro = new Livro();
        livro.setId(id);

        ResultSet dados = livro.dados();
        ResultSet autoresRelacionados = livro.autores();

        try {
            Editora editora = new Editora();
            ResultSet registroEditoras = editora.listar();

            while (registroEditoras.next())
                editoras.add(registroEditoras.getString("nome"));

            Autor autor = new Autor();
            ResultSet registroAutores = autor.listar();

            while (registroAutores.next())
                autores.add(registroAutores.getString("nome"));

        }catch (SQLException e){

        }

        String[] listaAutores = autores.toArray(new String[autores.size()]);
        String[] listaEditoras = editoras.toArray(new String[editoras.size()]);

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new LivrosFormView(dados, listaEditoras, listaAutores, autoresRelacionados);
                    }
                }
        );
    }



    public static void incluir(String titulo, String editora, String preco, ArrayList<JComboBox<String>> listaAutores, String isbn){
        Livro livro = new Livro();

        livro.setIsbn(isbn);
        livro.setTitulo(titulo);
        livro.setPreco(preco);
        livro.setEditora(editora);
        livro.setListaAutores(listaAutores);

        livro.incluir();
        LivrosController.inicializar();
    }

    public static void alterar(String titulo, String editora, String preco, ArrayList<JComboBox<String>> listaAutores, int id,String isbn){
        Livro livro = new Livro();

        livro.setTitulo(titulo);
        livro.setPreco(preco);
        livro.setId(id);
        livro.setIsbn(isbn);
        livro.setEditora(editora);
        livro.setListaAutores(listaAutores);


        livro.alterar();
        LivrosController.inicializar();
    }


}
