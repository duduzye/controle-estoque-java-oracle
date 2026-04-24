package br.com.eduardo.estoque.dao;

import br.com.eduardo.estoque.connection.ConnectionFactory;
import br.com.eduardo.estoque.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // CREATE
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco, quantidade, categoria) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getCategoria());

            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // READ (LISTAR TODOS)
    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setDataCadastro(rs.getDate("data_cadastro"));

                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    // READ (BUSCAR POR ID)
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoria(rs.getString("categoria"));
                    produto.setDataCadastro(rs.getDate("data_cadastro"));
                    return produto;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }

        return null;
    }

    // UPDATE
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome=?, descricao=?, preco=?, quantidade=?, categoria=? WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getCategoria());
            stmt.setInt(6, produto.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // DELETE
    public void remover(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }

    // ENTRADA DE ESTOQUE
    public void entradaEstoque(int id, int quantidadeEntrada) {
        String sql = "UPDATE produto SET quantidade = quantidade + ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeEntrada);
            stmt.setInt(2, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Entrada de estoque realizada com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro na entrada de estoque: " + e.getMessage());
        }
    }

    // SAÍDA DE ESTOQUE (COM VALIDAÇÃO)
    public void saidaEstoque(int id, int quantidadeSaida) {
        Produto produto = buscarPorId(id);

        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (produto.getQuantidade() < quantidadeSaida) {
            System.out.println("Estoque insuficiente.");
            return;
        }

        String sql = "UPDATE produto SET quantidade = quantidade - ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeSaida);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Saída de estoque realizada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro na saída de estoque: " + e.getMessage());
        }
    }
}