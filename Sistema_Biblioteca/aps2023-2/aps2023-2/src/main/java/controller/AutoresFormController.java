package controller;

import model.Autor;
import model.Editora;
import view.AutoresFormView;

import javax.swing.*;
import java.sql.ResultSet;

public class AutoresFormController {


    public static void inicializar(int id) {
        Autor autor = new Autor();
        autor.setId(id);

        ResultSet dados = autor.dados();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new AutoresFormView(dados);
                    }
                }
        );
    }
    public static void incluir(String nome, String fnome){
        Autor autor = new Autor();

        autor.setNome(nome);
        autor.setFnome(fnome);

        autor.incluir();
        AutoresController.inicializar();
    }

    public static void alterar(String nome, String fnome, int id){
        Autor autor = new Autor();

        autor.setNome(nome);
        autor.setFnome(fnome);
        autor.setId(id);

        autor.alterar();
        AutoresController.inicializar();
    }
}
