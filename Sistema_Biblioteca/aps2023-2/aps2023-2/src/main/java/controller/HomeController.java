package controller;

import view.HomeView;

import javax.swing.*;

public class HomeController {
    public static void iniciaizar(){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new HomeView();
                    }
                }
        );
    }

}
