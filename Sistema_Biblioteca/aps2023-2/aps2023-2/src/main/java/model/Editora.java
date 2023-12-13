package model;

import database.Dao;

import java.sql.ResultSet;

public class Editora implements Dao {

    private String nome;
    private String url;
    private int id;

    @Override
    public ResultSet listar() {
        return this.conexao.query("SELECT * FROM editoras");
    }

    ;

    @Override
    public Boolean incluir() {
        this.conexao.update(
                "INSERT INTO editoras (nome,url) VALUES ('" + this.nome + "','" + this.url + "')"
        );

        return true;
    };

    @Override
    public Boolean excluir() {
        this.conexao.update(
                "DELETE FROM editoras WHERE id = " + this.id
        );

        return true;
    };

    @Override
    public Boolean alterar() {
        this.conexao.update(
                "UPDATE editoras SET " +
                        "nome = '" + this.nome + "', " +
                        "url = '" + this.url + "' " +
                        "WHERE id = " + this.id
        );

        return true;
    };

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResultSet dados(){
        return this.conexao.query("SELECT * FROM editoras where id = " + this.id);
    };
}
