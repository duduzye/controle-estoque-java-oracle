package br.com.eduardo.estoque.app;

import br.com.eduardo.estoque.connection.ConnectionFactory;

import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {

        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conectado com sucesso ao Oracle!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }
}