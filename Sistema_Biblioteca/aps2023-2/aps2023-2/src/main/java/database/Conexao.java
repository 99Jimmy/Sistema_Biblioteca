package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {

    private Connection conexao;

    public Conexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexao = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados");
        }
    }

    public ResultSet query(String query) {
        try {
            return conexao.createStatement().executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int update(String query) {
        try {
            return conexao.createStatement().executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
