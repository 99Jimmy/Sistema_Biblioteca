package controller;

import model.Autor;
import model.Editora;
import model.Livro;
import view.LivrosView;

import javax.swing.*;
import java.sql.ResultSet;

public class LivrosController {
    public static void inicializar(){
        Livro livro = new Livro();
        ResultSet registros = livro.listar();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new LivrosView(registros);
                    }
                }
        );
    }



    public static void excluir (int id){
        Livro livro = new Livro();
        livro.setId(id);
        livro.excluir();

        LivrosController.inicializar();
    }




}
