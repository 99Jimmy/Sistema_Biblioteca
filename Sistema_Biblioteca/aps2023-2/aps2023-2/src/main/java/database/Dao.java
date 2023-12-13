package database;

import java.sql.ResultSet;

public interface Dao {
    Conexao conexao = new Conexao();
    ResultSet listar();
    Boolean incluir();
    Boolean excluir();
    Boolean alterar();
    ResultSet dados();
}
