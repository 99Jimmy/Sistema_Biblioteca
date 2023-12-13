package controller;

import model.Editora;
import view.EditorasView;

import javax.swing.*;
import java.sql.ResultSet;

public class EditorasController {

    public static void inicializar() {
        Editora editora = new Editora();
        ResultSet registros = editora.listar();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new EditorasView(registros);
                    }
                }
        );
    }

    public static void excluir(int id){
        Editora editora = new Editora();
        editora.setId(id);
        editora.excluir();

        EditorasController.inicializar();
    }


}
