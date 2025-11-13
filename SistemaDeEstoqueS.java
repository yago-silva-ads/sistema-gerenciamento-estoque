import java.util.Scanner;

public class SistemaDeEstoqueS {

    // Variáveis estáticas para acesso global na classe
    private static Scanner scanner = new Scanner(System.in);
    private static final int TAMANHO_MAXIMO_ESTOQUE = 100;

    // Vetores paralelos para armazenar os dados dos produtos
    private static String[] codigos = new String[TAMANHO_MAXIMO_ESTOQUE];
    private static String[] nomes = new String[TAMANHO_MAXIMO_ESTOQUE];
    private static int[] quantidades = new int[TAMANHO_MAXIMO_ESTOQUE];
    private static double[] precos = new double[TAMANHO_MAXIMO_ESTOQUE];

    // Contador de produtos cadastrados
    private static int totalProdutosCadastrados = 0;

    public static void main(String[] args) {
        int opcao = 0;

        do {
            exibirMenu();
            
            try {
                String entrada = scanner.nextLine();
                // Verifica se a entrada não está vazia antes de converter
                if (!entrada.isEmpty()) {
                    opcao = Integer.parseInt(entrada);
                } else {
                    opcao = 0;
                }
            } catch (NumberFormatException e) {
                opcao = 0; // Se digitar letra, considera inválido
            }

            switch (opcao) {
                case 1:
                    cadastrarNovoProduto();
                    break;
                case 2:
                    adicionarEstoque();
                    break;
                case 3:
                    removerEstoque();
                    break;
                case 4:
                    consultarProduto();
                    break;
                case 5:
                    listarTodosOsProdutos();
                    break;
                case 6:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            
            if (opcao != 6) { 
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }

        } while (opcao != 6);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n==================================");
        System.out.println("   SISTEMA DE GESTÃO DE ESTOQUE   ");
        System.out.println("==================================");
        System.out.println("1. Cadastrar novo produto");
        System.out.println("2. Adicionar estoque (Entrada)");
        System.out.println("3. Remover estoque (Saída)");
        System.out.println("4. Consultar produto por código");
        System.out.println("5. Listar todos os produtos");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Busca o índice do produto pelo código. Retorna -1 se não encontrar.
    private static int encontrarIndicePorCodigo(String codigoParaBuscar) {
        for (int i = 0; i < totalProdutosCadastrados; i++) {
            if (codigos[i].equalsIgnoreCase(codigoParaBuscar)) {
                return i;
            }
        }
        return -1;
    }

    private static void cadastrarNovoProduto() {
        System.out.println("\n--- Cadastro de Novo Produto ---");

        if (totalProdutosCadastrados >= TAMANHO_MAXIMO_ESTOQUE) {
            System.out.println("Erro: Estoque cheio.");
            return;
        }

        System.out.print("Digite o código (ex: CAM-001): ");
        String codigo = scanner.nextLine();

        if (encontrarIndicePorCodigo(codigo) != -1) {
            System.out.println("Erro: Já existe um produto com este código.");
            return;
        }

        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        int qtd = 0;
        double preco = 0.0;

        try {
            System.out.print("Digite a quantidade inicial: ");
            qtd = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o preço de venda: ");
            preco = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números válidos para quantidade e preço.");
            return;
        }

        // Armazena nos vetores
        codigos[totalProdutosCadastrados] = codigo;
        nomes[totalProdutosCadastrados] = nome;
        quantidades[totalProdutosCadastrados] = qtd;
        precos[totalProdutosCadastrados] = preco;

        totalProdutosCadastrados++;

        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void adicionarEstoque() {
        System.out.println("\n--- Entrada de Estoque ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) {
            System.out.println("Erro: Produto não encontrado.");
            return;
        }

        System.out.println("Produto: " + nomes[indice] + " | Atual: " + quantidades[indice]);
        System.out.print("Quantidade a adicionar: ");
        
        try {
            int qtd = Integer.parseInt(scanner.nextLine());
            if (qtd > 0) {
                quantidades[indice] += qtd;
                System.out.println("Sucesso! Novo saldo: " + quantidades[indice]);
            } else {
                System.out.println("Erro: A quantidade deve ser positiva.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um número inteiro válido.");
        }
    }

    private static void removerEstoque() {
        System.out.println("\n--- Saída de Estoque ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) {
            System.out.println("Erro: Produto não encontrado.");
            return;
        }

        System.out.println("Produto: " + nomes[indice] + " | Atual: " + quantidades[indice]);
        System.out.print("Quantidade a remover: ");

        try {
            int qtd = Integer.parseInt(scanner.nextLine());
            
            if (qtd > 0 && qtd <= quantidades[indice]) {
                quantidades[indice] -= qtd;
                System.out.println("Sucesso! Novo saldo: " + quantidades[indice]);
            } else {
                System.out.println("Erro: Quantidade inválida ou saldo insuficiente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um número inteiro válido.");
        }
    }

    private static void consultarProduto() {
        System.out.println("\n--- Consultar Produto ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) {
            System.out.println("Erro: Produto não encontrado.");
        } else {
            System.out.println("---------------------------------");
            System.out.println("Código: " + codigos[indice]);
            System.out.println("Nome: " + nomes[indice]);
            System.out.printf("Preço: R$ %.2f%n", precos[indice]);
            System.out.println("Estoque: " + quantidades[indice]);
            System.out.println("---------------------------------");
        }
    }

    private static void listarTodosOsProdutos() {
        System.out.println("\n--- Lista Geral de Estoque ---");

        if (totalProdutosCadastrados == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-10s%n", "CÓDIGO", "NOME", "QTD", "PREÇO");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < totalProdutosCadastrados; i++) {
            System.out.printf("%-10s | %-20s | %-10d | R$ %.2f%n", 
                codigos[i], nomes[i], quantidades[i], precos[i]);
        }
        System.out.println("----------------------------------------------------------");
    }
}
