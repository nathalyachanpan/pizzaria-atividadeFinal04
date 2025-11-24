package Projeto.atividade.nova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Projeto.atividade.nova.Pizza.TamanhoPizza;

public class Pizzaria {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();
        List<Pedido> listaPedidos = new ArrayList<>();

        // Adicionando clientes automaticamente para facilitar a execução
        listaClientes.add(new Cliente("Joao", "Rua A, 123", "111-111", "joao@email.com"));
        listaClientes.add(new Cliente("Maria", "Rua B, 456", "222-222", "maria@email.com"));

        boolean continuar = true;
        while (continuar) {
            System.out.println();
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Fazer um novo pedido");
            System.out.println("2 - Alterar um pedido");
            System.out.println("3 - Adicionar um cliente");
            System.out.println("4 - Gerar relatório de vendas");
            System.out.println("5 - Gerar lista de clientes");
            System.out.println("9 - Sair");

            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (opcao) {
                case 1:
                    fazerPedido(scanner, listaPedidos, listaClientes);
                    break;
                case 2:
                    alterarPedido(scanner, listaPedidos);
                    break;
                case 3:
                    listaClientes.add(adicionarCliente(scanner));
                    System.out.println("Cliente adicionado com sucesso!");
                    break;
                case 4:
                    gerarRelatorio(listaPedidos);
                    break;
                case 5:
                    gerarListaClientes(listaClientes);
                    break;
                case 9:
                    System.out.println("Até amanha...");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    break;
            }
        }

    }

    private static void fazerPedido(Scanner scanner, List<Pedido> listaPedidos, List<Cliente> listaClientes) {
        List<Pizza> pizzas = new ArrayList<>();
        System.out.println("FAZER PEDIDO");

        int x = 1;
        System.out.println("Selecione um cliente: ");
        for (Cliente cliente : listaClientes) {
            System.out.println(x+" - "+cliente.getNome());
            x++;
        }
        System.out.print("Opção: ");
        int cliente = scanner.nextInt();
        scanner.nextLine();

        boolean continuar = true;
        while (continuar) {
            x = 1;
            System.out.println("Qual o tamanho da pizza? ");
            System.out.println("Selecione um tamanho: ");
            for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
                System.out.println(x+" - "+tamanhos);
                x++;
            }
            System.out.print("Opção: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("Digite a quantidade de sabores: 1 - 4 ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            Cardapio cardapio = new Cardapio();
            List<String> saboresList = new ArrayList<>();
            List<String> saboresSelect = new ArrayList<>();

            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione um sabor: ");

                x = 1;
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x+" - "+sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                saboresSelect.add(saboresList.get(opcao-1));
            }

            Pizza pizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho-1));
            pizzas.add(pizza);

            System.out.println("Pizza cadastrada com sucesso!");
            System.out.println();
            System.out.println("Deseja cadastrar mais uma pizza no pedido?");
            System.out.print("1 - Sim, 2 - Não: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if(opcao != 1){
                continuar = false;
            }
        }
        double frete = calcularFrete(scanner, pizzas.size());
        Pedido pedido = new Pedido(listaPedidos.size()+1,listaClientes.get(cliente-1), pizzas, somarPizzas(pizzas), frete);
        listaPedidos.add(pedido);
    }

    private static double somarPizzas(List<Pizza> pizzas) {
        double valorTotal = 0;
        for (Pizza pizza : pizzas) {
            valorTotal += pizza.getPreco();
        }
        return valorTotal;
    }

    private static Cliente adicionarCliente(Scanner scanner) {
        System.out.println("ADICIONAR CLIENTE");
        System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.println();
        System.out.print("Digite o email do cliente: ");
        String email = scanner.nextLine();
        System.out.println();

        Cliente cliente = new Cliente(nome, endereco, telefone, email);
        return cliente;
    }

    private static void alterarPedido(Scanner scanner, List<Pedido> listaPedidos) {
        System.out.println("ALTERAR PEDIDO");
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido para alterar.");
            return;
        }

        for (Pedido p : listaPedidos) {
            System.out.println(p);
        }

        System.out.println("Selecione o pedido a ser alterado:");

        System.out.print("Digite o ID do pedido: ");
        int idPedido = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoParaAlterar = null;
        for (Pedido p : listaPedidos) {
            if (p.getId() == idPedido) {
                pedidoParaAlterar = p;
                break;
            }
        }

        if (pedidoParaAlterar == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }

        System.out.println("O que você deseja fazer?");
        System.out.println("1 - Adicionar pizza");
        System.out.println("2 - Remover pizza");
        System.out.println("3 - Alterar sabor de uma pizza");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                adicionarPizza(scanner, pedidoParaAlterar);
                break;
            case 2:
                removerPizza(scanner, pedidoParaAlterar);
                break;
            case 3:
                alterarSaborPizza(scanner, pedidoParaAlterar);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void adicionarPizza(Scanner scanner, Pedido pedidoParaAlterar) {
        System.out.println("ADICIONAR PIZZA AO PEDIDO");

        int x = 1;
        System.out.println("Qual o tamanho da pizza? ");
        System.out.println("Selecione um tamanho: ");
        for (TamanhoPizza tamanhos : Pizza.TamanhoPizza.values()) {
            System.out.println(x+" - "+tamanhos);
            x++;
        }
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        int quantiSabores = 0;
        while (quantiSabores < 1 || quantiSabores > 4) {
            System.out.println("Digite a quantidade de sabores: 1 - 4 ");
            System.out.print("Opção: ");
            quantiSabores = scanner.nextInt();
            scanner.nextLine();
        }

        Cardapio cardapio = new Cardapio();
        List<String> saboresList = new ArrayList<>();
        List<String> saboresSelect = new ArrayList<>();

        for (int i = 0; i < quantiSabores; i++) {
            System.out.println("Selecione um sabor: ");

            x = 1;
            for (String sabor : cardapio.getCardapio().keySet()) {
                saboresList.add(sabor);
                System.out.println(x+" - "+sabor);
                x++;
            }
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            saboresSelect.add(saboresList.get(opcao-1));
        }

        Pizza novaPizza = new Pizza(saboresSelect, cardapio.getPrecoJusto(saboresSelect), TamanhoPizza.getByIndex(tamanho-1));
        pedidoParaAlterar.getPizzas().add(novaPizza);

        pedidoParaAlterar.setValorTotal(somarPizzas(pedidoParaAlterar.getPizzas()));

        System.out.println("Pizza adicionada com sucesso ao pedido!");
    }

    private static void removerPizza(Scanner scanner, Pedido pedidoParaAlterar) {
        System.out.println("REMOVER PIZZA DO PEDIDO");

        if (pedidoParaAlterar.getPizzas().isEmpty()) {
            System.out.println("Não há pizzas neste pedido para remover.");
            return;
        }

        System.out.println("Pizzas no pedido:");
        int x = 1;
        for (Pizza pizza : pedidoParaAlterar.getPizzas()) {
            System.out.println(x + " - Sabores: " + pizza.getSabores() +
                    " | Tamanho: " + pizza.getTamanho() +
                    " | Preço: R$ " + String.format("%.2f", pizza.getPreco()));
            x++;
        }

        System.out.print("Digite o número da pizza a ser removida: ");
        int indicePizza = scanner.nextInt();
        scanner.nextLine();

        if (indicePizza < 1 || indicePizza > pedidoParaAlterar.getPizzas().size()) {
            System.out.println("Número de pizza inválido.");
            return;
        }

        pedidoParaAlterar.getPizzas().remove(indicePizza - 1);

        pedidoParaAlterar.setValorTotal(somarPizzas(pedidoParaAlterar.getPizzas()));

        System.out.println("Pizza removida com sucesso!");
    }

    private static void alterarSaborPizza(Scanner scanner, Pedido pedidoParaAlterar) {
        System.out.println("ALTERAR SABOR DE UMA PIZZA");

        if (pedidoParaAlterar.getPizzas().isEmpty()) {
            System.out.println("Não há pizzas neste pedido para alterar.");
            return;
        }

        System.out.println("Pizzas no pedido:");
        int x = 1;
        for (Pizza pizza : pedidoParaAlterar.getPizzas()) {
            System.out.println(x + " - Sabores: " + pizza.getSabores() +
                    " | Tamanho: " + pizza.getTamanho() +
                    " | Preço: R$ " + String.format("%.2f", pizza.getPreco()));
            x++;
        }

        System.out.print("Digite o número da pizza a ser alterada: ");
        int indicePizza = scanner.nextInt();
        scanner.nextLine();

        if (indicePizza < 1 || indicePizza > pedidoParaAlterar.getPizzas().size()) {
            System.out.println("Número de pizza inválido.");
            return;
        }

        Pizza pizzaParaAlterar = pedidoParaAlterar.getPizzas().get(indicePizza - 1);

        System.out.println("Sabores atuais: " + pizzaParaAlterar.getSabores());
        System.out.println("Deseja:");
        System.out.println("1 - Alterar todos os sabores");
        System.out.println("2 - Alterar um sabor específico");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        Cardapio cardapio = new Cardapio();
        List<String> saboresList = new ArrayList<>();

        if (opcao == 1) {
            int quantiSabores = 0;
            while (quantiSabores < 1 || quantiSabores > 4) {
                System.out.println("Digite a quantidade de sabores: 1 - 4 ");
                System.out.print("Opção: ");
                quantiSabores = scanner.nextInt();
                scanner.nextLine();
            }

            List<String> novosSabores = new ArrayList<>();
            for (int i = 0; i < quantiSabores; i++) {
                System.out.println("Selecione o sabor " + (i + 1) + ": ");
                saboresList.clear();

                x = 1;
                for (String sabor : cardapio.getCardapio().keySet()) {
                    saboresList.add(sabor);
                    System.out.println(x+" - "+sabor);
                    x++;
                }
                System.out.print("Opção: ");
                int saborOpcao = scanner.nextInt();
                scanner.nextLine();
                novosSabores.add(saboresList.get(saborOpcao-1));
            }

            pizzaParaAlterar.setSabores(novosSabores);
            pizzaParaAlterar.setPreco(cardapio.getPrecoJusto(novosSabores));

        } else if (opcao == 2) {
            System.out.println("Qual sabor deseja alterar?");
            x = 1;
            for (String sabor : pizzaParaAlterar.getSabores()) {
                System.out.println(x + " - " + sabor);
                x++;
            }
            System.out.print("Opção: ");
            int indiceSabor = scanner.nextInt();
            scanner.nextLine();

            if (indiceSabor < 1 || indiceSabor > pizzaParaAlterar.getSabores().size()) {
                System.out.println("Número de sabor inválido.");
                return;
            }

            System.out.println("Selecione o novo sabor: ");
            saboresList.clear();
            x = 1;
            for (String sabor : cardapio.getCardapio().keySet()) {
                saboresList.add(sabor);
                System.out.println(x+" - "+sabor);
                x++;
            }
            System.out.print("Opção: ");
            int novoSaborOpcao = scanner.nextInt();
            scanner.nextLine();

            pizzaParaAlterar.getSabores().set(indiceSabor - 1, saboresList.get(novoSaborOpcao - 1));
            pizzaParaAlterar.setPreco(cardapio.getPrecoJusto(pizzaParaAlterar.getSabores()));

        } else {
            System.out.println("Opção inválida.");
            return;
        }

        pedidoParaAlterar.setValorTotal(somarPizzas(pedidoParaAlterar.getPizzas()));

        System.out.println("Sabor(es) alterado(s) com sucesso!");
    }

    private static void gerarRelatorio(List<Pedido> listaPedidos) {
        System.out.println("GERAR RELATÓRIO");
        if (listaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido para gerar relatório.");
            return;
        }

        double faturamentoTotal = 0;
        Map<String, Integer> saboresMaisPedidos = new HashMap<>();

        for (Pedido pedido : listaPedidos) {
            faturamentoTotal += pedido.getValorTotal() + pedido.getValorFrete();
            for (Pizza pizza : pedido.getPizzas()) {
                for (String sabor : pizza.getSabores()) {
                    saboresMaisPedidos.put(sabor, saboresMaisPedidos.getOrDefault(sabor, 0) + 1);
                }
            }
        }

        System.out.printf("Faturamento Total: R$ %.2f\n", faturamentoTotal);
        System.out.println("Sabores mais pedidos: " + saboresMaisPedidos);
    }

    private static double calcularFrete(Scanner scanner, int quantidadePizzas) {
        System.out.println("CALCULAR FRETE");
        System.out.print("Digite a distância em km: ");
        double distancia = scanner.nextDouble();
        scanner.nextLine();

        double frete = distancia * 2.5 + quantidadePizzas * 1.5;
        System.out.printf("Frete: R$ %.2f\n", frete);
        return frete;
    }

    private static void gerarListaClientes(List<Cliente> listaClientes) {
        int x = 1;
        if (listaClientes.isEmpty()) {
            System.out.println("Lista de clientes esta vazia");
        } else {
            for (Cliente cliente : listaClientes) {
                System.out.println("Cliente "+x);
                System.out.println(cliente.getNome());
                System.out.println(cliente.getEndereco());
                System.out.println(cliente.getTelefone());
                System.out.println(cliente.getEmail());
                System.out.println();
                x++;
            }
        }
    }
}
