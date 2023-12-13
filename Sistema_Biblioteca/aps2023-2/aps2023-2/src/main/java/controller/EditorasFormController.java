package controller;

import model.Editora;
import view.EditorasFormView;

import javax.swing.*;
import java.sql.ResultSet;

public class EditorasFormController {
    public static void inicializar(int id){
        Editora editora = new Editora();
        editora.setId(id);

        ResultSet dados = editora.dados();
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {new EditorasFormView(dados);}
                }
        );
    }

    public static void incluir(String nome, String url){
        Editora editora = new Editora();

        editora.setNome(nome);
        editora.setUrl(url);

        editora.incluir();
        EditorasController.inicializar();
    }

    public static void alterar(String nome, String url, int id){
        Editora editora = new Editora();

        editora.setNome(nome);
        editora.setUrl(url);
        editora.setId(id);

        editora.alterar();
        EditorasController.inicializar();
    }
}
