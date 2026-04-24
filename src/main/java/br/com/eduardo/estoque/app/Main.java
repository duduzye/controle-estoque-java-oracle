package br.com.eduardo.estoque.app;

import br.com.eduardo.estoque.dao.ProdutoDAO;
import br.com.eduardo.estoque.model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProdutoDAO dao = new ProdutoDAO();

        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE ESTOQUE ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto por ID");
            System.out.println("4 - Atualizar produto");
            System.out.println("5 - Remover produto");
            System.out.println("6 - Entrada de estoque");
            System.out.println("7 - Saída de estoque");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {

                case 1:
                    Produto novo = new Produto();

                    System.out.print("Nome: ");
                    novo.setNome(sc.nextLine());

                    System.out.print("Descrição: ");
                    novo.setDescricao(sc.nextLine());

                    System.out.print("Preço: ");
                    novo.setPreco(sc.nextDouble());

                    System.out.print("Quantidade: ");
                    novo.setQuantidade(sc.nextInt());
                    sc.nextLine();

                    System.out.print("Categoria: ");
                    novo.setCategoria(sc.nextLine());

                    dao.cadastrar(novo);
                    break;

                case 2:
                    List<Produto> lista = dao.listar();

                    if (lista.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        lista.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.print("ID: ");
                    int idBusca = sc.nextInt();
                    sc.nextLine();

                    Produto p = dao.buscarPorId(idBusca);

                    if (p != null) {
                        System.out.println(p);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 4:
                    Produto atualizar = new Produto();

                    System.out.print("ID: ");
                    atualizar.setId(sc.nextInt());
                    sc.nextLine();

                    System.out.print("Novo nome: ");
                    atualizar.setNome(sc.nextLine());

                    System.out.print("Nova descrição: ");
                    atualizar.setDescricao(sc.nextLine());

                    System.out.print("Novo preço: ");
                    atualizar.setPreco(sc.nextDouble());

                    System.out.print("Nova quantidade: ");
                    atualizar.setQuantidade(sc.nextInt());
                    sc.nextLine();

                    System.out.print("Nova categoria: ");
                    atualizar.setCategoria(sc.nextLine());

                    dao.atualizar(atualizar);
                    break;

                case 5:
                    System.out.print("ID para remover: ");
                    int idRemover = sc.nextInt();
                    sc.nextLine();

                    dao.remover(idRemover);
                    break;

                case 6:
                    System.out.print("ID do produto: ");
                    int idEntrada = sc.nextInt();

                    System.out.print("Quantidade de entrada: ");
                    int qtdEntrada = sc.nextInt();
                    sc.nextLine();

                    dao.entradaEstoque(idEntrada, qtdEntrada);
                    break;

                case 7:
                    System.out.print("ID do produto: ");
                    int idSaida = sc.nextInt();

                    System.out.print("Quantidade de saída: ");
                    int qtdSaida = sc.nextInt();
                    sc.nextLine();

                    dao.saidaEstoque(idSaida, qtdSaida);
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        sc.close();
    }
}