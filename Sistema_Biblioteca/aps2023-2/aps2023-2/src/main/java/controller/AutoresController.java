package controller;

import model.Autor;
import model.Editora;
import  view.AutoresView;
import javax.swing.*;
import java.sql.ResultSet;

public class AutoresController {
    public static void inicializar(){
        Autor autor = new Autor();
        ResultSet registros = autor.listar();

        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run(){new AutoresView(registros); }
                }
        );
    }

    public static void excluir(int id){
        Autor autor = new Autor();
        autor.setId(id);
        autor.excluir();

       AutoresController.inicializar();
    }
}
