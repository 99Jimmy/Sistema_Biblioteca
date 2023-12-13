package model;

import database.Dao;

import java.sql.ResultSet;

public class Autor implements Dao {
    private int id;
    private String nome;
    private String fnome;
    @Override
    public ResultSet listar() {
        return this.conexao.query("SELECT * FROM autores");
    }
    @Override
    public Boolean incluir() {
        this.conexao.update(
                "INSERT INTO autores (nome, fnome) VALUES ('" + this.nome + "','" + this.fnome + "')"
        );

        return true;
    };

    @Override
    public Boolean excluir() {
        this.conexao.update(
                "DELETE FROM autores WHERE id = " + this.id
        );

        return true;
    };

    @Override
    public Boolean alterar() {
        this.conexao.update(
                "UPDATE autores SET " +
                        "nome = '" + this.nome + "', " +
                        "fnome  = '" + this.fnome + "' " +
                        "WHERE id = " + this.id
        );

        return true;
    };

    public ResultSet dados(){
        return this.conexao.query("SELECT * FROM autores where id = " + this.id);
    };

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFnome(String fnome) {
        this.fnome = fnome;
    }
}
