package model;

import database.Dao;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Livro implements Dao {

    private int id;
    private String editora;
    private String preco;
    private String titulo;
    private String isbn;
    private ArrayList<JComboBox<String>> listaAutores;

    @Override
    public ResultSet listar(){
        return this.conexao.query("SELECT * FROM livros");
    };

    @Override
    public Boolean incluir() {
        this.conexao.update(
                "INSERT INTO livros " +
                        "(isbn, titulo, idEditora, preco) VALUES " +
                        "('" + this.isbn + "','" + this.titulo + "',(SELECT id from editoras WHERE nome = '"+ this.editora +"')," + this.preco + ")"
        );

        for (int i = 0; i < this.listaAutores.size(); i++) {
            JComboBox<String> comboBox = this.listaAutores.get(i);

            Object selectedItem = comboBox.getSelectedItem();

            this.conexao.update(
                    "INSERT INTO autores_livros " +
                            "(isbn, id_autor) VALUES " +
                            "((SELECT isbn from livros WHERE titulo = '"+ this.titulo +"')," +
                            " (SELECT id from autores WHERE nome = '" + selectedItem + "'))"
            );

        }

        return true;
    };
    @Override
    public Boolean excluir(){
        this.conexao.update(
                "DELETE FROM livros WHERE isbn = " + this.id
        );
        return true; };




    @Override
    public Boolean alterar() {
        this.conexao.update(
                "UPDATE livros SET " +
                        "isbn = " + this.isbn + "," +
                        "titulo = '" + this.titulo + "', " +
                        "preco = " + this.preco + ", " +
                        "idEditora = (SELECT id from editoras WHERE nome ='" + this.editora + "') " +
                        "WHERE isbn = " + this.id
        );

        this.excluirRelacaoAutores();

        for (int i = 0; i < this.listaAutores.size(); i++) {
            JComboBox<String> comboBox = this.listaAutores.get(i);

            Object selectedItem = comboBox.getSelectedItem();

            this.conexao.update(
                    "INSERT INTO autores_livros " +
                            "(isbn, id_autor) VALUES " +
                            "('" + this.id + "', (SELECT id from autores WHERE nome = '" + selectedItem + "'))"
            );

        }

        return true;
    };

    public ResultSet dados(){
        return this.conexao.query(
                "SELECT livro.*, editora.nome as editora FROM livros as livro " +
                        "LEFT JOIN editoras as editora " +
                        "ON livro.idEditora = editora.id " +
                        "WHERE livro.isbn = " + this.id
        );
    };


    public ResultSet autores(){
        return this.conexao.query(
                "SELECT autores.* FROM autores_livros as autores_livros" +
                " LEFT JOIN autores as autores" +
                " ON autores.id = autores_livros.id_autor" +
                " WHERE autores_livros.isbn = " + this.id
        );
    };


        public void setIsbn(String isbn) { this.isbn = isbn; }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setListaAutores(ArrayList<JComboBox<String>> listaAutores) {
        this.listaAutores = listaAutores;
    }

    private void excluirRelacaoAutores(){
        this.conexao.update(
                "DELETE FROM autores_livros WHERE isbn = " + this.id
        );

    }
}
