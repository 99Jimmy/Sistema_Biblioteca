package view;

import javax.swing.*;
import java.awt.*;


public class ConfiguracaoView {
    public static void definirConfiguracao(JFrame view){
        view.setPreferredSize(new Dimension(540, 640));
        view.setVisible(true);
        view.pack();
        view.setDefaultCloseOperation(view.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);
        view.setResizable(false);
        view.setLayout(null);
        view.getContentPane().setBackground(new Color(255, 255, 255));
    }
}
