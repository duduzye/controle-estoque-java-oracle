package br.com.eduardo.estoque.app;

import br.com.eduardo.estoque.dao.ProdutoDAO;
import br.com.eduardo.estoque.model.Produto;

public class TesteProdutoDAO {
    public static void main(String[] args) {

        ProdutoDAO dao = new ProdutoDAO();

        // CADASTRAR
        Produto produto = new Produto(
                "Monitor",
                "Monitor 24 polegadas",
                800.00,
                3,
                "Periféricos"
        );

        dao.cadastrar(produto);

        // LISTAR
        System.out.println("=== LISTA DE PRODUTOS ===");
        System.out.println(dao.listar());

        // BUSCAR
        Produto p = dao.buscarPorId(1);
        System.out.println("=== PRODUTO BUSCADO ===");
        System.out.println(p);

        // ATUALIZAR
        if (p != null) {
            p.setPreco(900.00);
            p.setQuantidade(5);
            dao.atualizar(p);
        }

        // 🔥 AQUI entra o que você perguntou
        dao.entradaEstoque(1, 5);
        dao.saidaEstoque(1, 3);

        // LISTAR DE NOVO
        System.out.println("=== ESTOQUE FINAL ===");
        System.out.println(dao.listar());

        // NÃO remove por enquanto
        // dao.remover(1);
    }
}