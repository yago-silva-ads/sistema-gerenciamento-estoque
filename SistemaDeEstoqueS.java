// Importamos a classe Scanner para ler dados do teclado [cite: 1295]
import java.util.Scanner;

/**
 * CLASSE PRINCIPAL DO SISTEMA (ARQUIVO ÚNICO)
 * Este arquivo deve se chamar SistemaDeEstoque.java
 *
 * Este código segue a Programação Estruturada ensinada no PDF:
 * - Usa uma classe única.
 * - Usa Arrays (vetores) paralelos[cite: 1576, 1578].
 * - Usa métodos estáticos (funções e procedimentos).
 * - Usa variáveis "globais" (static) e "locais"[cite: 1515, 1517].
 */
public class SistemaDeEstoqueS {

    // --- Variáveis "Globais" da Classe [cite: 1517] ---
    // Usamos 'static' para que todos os métodos possam acessá-las.
    
    // 1. O Scanner para ler dados em todos os métodos [cite: 1298]
    private static Scanner scanner = new Scanner(System.in);

    // 2. Uma Constante para definir o tamanho máximo do estoque [cite: 1239]
    private static final int TAMANHO_MAXIMO_ESTOQUE = 100;

    // 3. Os Arrays (Vetores) Paralelos para guardar os dados [cite: 1576, 1587]
    // Todos terão o mesmo tamanho máximo.
    private static String[] codigos = new String[TAMANHO_MAXIMO_ESTOQUE];
    private static String[] nomes = new String[TAMANHO_MAXIMO_ESTOQUE];
    private static int[] quantidades = new int[TAMANHO_MAXIMO_ESTOQUE];
    private static double[] precos = new double[TAMANHO_MAXIMO_ESTOQUE];

    // 4. Variável para controlar quantos produtos já foram cadastrados
    // (Começa em 0 e vai até TAMANHO_MAXIMO_ESTOQUE)
    private static int totalProdutosCadastrados = 0;


    /**
     * MÉTODO MAIN (PRINCIPAL)
     * Onde o programa começa a ser executado.
     */
    public static void main(String[] args) {
        // Variável local para o menu [cite: 1515]
        int opcao = 0;

        // Usamos um laço 'do...while' para o menu [cite: 1560]
        // Ele executa pelo menos uma vez [cite: 1559]
        do {
            exibirMenu(); // Chama o procedimento que exibe o menu
            
            // Lê a opção do usuário
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner (para ler texto depois)

            // Estrutura de decisão ESCOLHA-CASO (switch) [cite: 1500, 1502]
            switch (opcao) {
                case 1:
                    cadastrarNovoProduto();
                    break; // 'break' é necessário em cada 'case' [cite: 1503]
                case 2:
                    adicionarEstoque(); // Entrada
                    break;
                case 3:
                    removerEstoque(); // Saída
                    break;
                case 4:
                    consultarProduto();
                    break;
                case 5:
                    listarTodosOsProdutos();
                    break;
                case 6:
                    System.out.println("Saindo do programa...");
                    break;
                default: // 'default' é como o "caso contrário" [cite: 1504]
                    System.err.println("Opção inválida. Tente novamente.");
            }
            
            if (opcao != 6) { 
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }

        } while (opcao != 6); // O laço repete ENQUANTO a opção for diferente de 6

        scanner.close(); // Fecha o scanner no fim do programa
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Apenas exibe o menu na tela[cite: 1053].
     */
    private static void exibirMenu() {
        System.out.println("==================================");
        System.out.println("   SISTEMA DE GESTÃO DE ESTOQUE   ");
        System.out.println("==================================");
        System.out.println("1. Cadastrar novo produto");
        System.out.println("2. Adicionar estoque (Entrada)");
        System.out.println("3. Remover estoque (Saída)");
        System.out.println("4. Consultar produto por código");
        System.out.println("5. Listar todos os produtos");
        System.out.println("6. Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    /**
     * FUNÇÃO (Método com retorno [cite: 1514])
     * Recebe um 'codigo' por parâmetro [cite: 1520] e procura no array 'codigos'.
     * Retorna o índice (posição) se achar, ou -1 se não achar.
     */
    private static int encontrarIndicePorCodigo(String codigoParaBuscar) {
        // Laço de repetição PARA (for) [cite: 1532, 1598]
        // O 'i' vai de 0 até o número de produtos que já cadastramos
        for (int i = 0; i < totalProdutosCadastrados; i++) {
            // Estrutura de decisão SE (if) [cite: 1471]
            if (codigos[i].equalsIgnoreCase(codigoParaBuscar)) {
                return i; // Retorna a posição (índice)
            }
        }
        return -1; // Retorna -1 se o 'for' terminar e não encontrar
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Pede os dados ao usuário e salva nos arrays.
     */
    private static void cadastrarNovoProduto() {
        System.out.println("--- 1. Cadastro de Novo Produto ---");

        // Estrutura de decisão IF...ELSE [cite: 1479]
        // Verifica se o estoque (array) está cheio
        if (totalProdutosCadastrados >= TAMANHO_MAXIMO_ESTOQUE) {
            System.err.println("Erro: Estoque cheio. Não é possível cadastrar mais produtos.");
            return; // Sai do método
        }

        System.out.print("Digite o código (ex: 'CAM-001'): ");
        String codigo = scanner.nextLine(); // Lê uma String [cite: 1307]

        // Usamos a função para verificar se o código já existe
        int indiceExistente = encontrarIndicePorCodigo(codigo);

        if (indiceExistente != -1) { // Operador relacional "diferente" [cite: 1433]
            System.err.println("Erro: Já existe um produto com este código.");
            return;
        }

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a quantidade inicial: ");
        int qtd = scanner.nextInt(); // Lê um Inteiro [cite: 1307]

        System.out.print("Digite o preço de venda (ex: 29,99): ");
        double preco = scanner.nextDouble(); // Lê um Double [cite: 1307]
        scanner.nextLine(); // Limpa o buffer

        // --- Armazenamento nos Arrays ---
        // Guardamos os dados novos na próxima posição livre
        // A posição livre é controlada pela variável 'totalProdutosCadastrados'
        codigos[totalProdutosCadastrados] = codigo; // Atribui valor ao array [cite: 1592]
        nomes[totalProdutosCadastrados] = nome;
        quantidades[totalProdutosCadastrados] = qtd;
        precos[totalProdutosCadastrados] = preco;

        // Incrementa o contador de produtos [cite: 1262]
        totalProdutosCadastrados++;

        System.out.println("Produto '" + nome + "' cadastrado com sucesso!");
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Adiciona itens a um produto já existente.
     */
    private static void adicionarEstoque() {
        System.out.println("--- 2. Entrada de Estoque ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        // Procura o produto
        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) { // Se 'encontrarIndicePorCodigo' retornou -1
            System.err.println("Erro: Produto não encontrado.");
        } else {
            System.out.print("Digite a quantidade a ADICIONAR: ");
            int quantidadeParaAdicionar = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            // Operador relacional "maior que" [cite: 1433]
            if (quantidadeParaAdicionar > 0) {
                // Atualiza o valor no array 'quantidades' [cite: 1255]
                quantidades[indice] = quantidades[indice] + quantidadeParaAdicionar;
                System.out.println("Estoque atualizado. Nova quantidade: " + quantidades[indice]);
            } else {
                System.err.println("Erro: A quantidade deve ser um número positivo.");
            }
        }
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Remove itens de um produto já existente.
     */
    private static void removerEstoque() {
        System.out.println("--- 3. Saída de Estoque ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) {
            System.err.println("Erro: Produto não encontrado.");
        } else {
            System.out.print("Digite a quantidade a REMOVER: ");
            int quantidadeParaRemover = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            int quantidadeAtual = quantidades[indice];

            // Usamos o operador lógico E (&&) [cite: 1439]
            // A quantidade a remover deve ser positiva E menor/igual ao estoque
            if (quantidadeParaRemover > 0 && quantidadeParaRemover <= quantidadeAtual) {
                // Atualiza o valor [cite: 1255]
                quantidades[indice] = quantidadeAtual - quantidadeParaRemover;
                System.out.println("Estoque atualizado. Nova quantidade: " + quantidades[indice]);
            } else {
                System.err.println("Erro: Quantidade inválida ou estoque insuficiente (" + quantidadeAtual + " unidades).");
            }
        }
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Mostra os dados de um único produto.
     */
    private static void consultarProduto() {
        System.out.println("--- 4. Consultar Produto ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        int indice = encontrarIndicePorCodigo(codigo);

        if (indice == -1) {
            System.err.println("Erro: Produto não encontrado.");
        } else {
            // Exibe os dados pegando de cada array na mesma posição (índice)
            System.out.println("---------------------------------");
            // Usamos o operador + para concatenar Strings [cite: 1334]
            System.out.println("Código: " + codigos[indice]);
            System.out.println("Nome: " + nomes[indice]);
            System.out.println("Preço: R$ " + precos[indice]);
            System.out.println("Quantidade em Estoque: " + quantidades[indice]);
            System.out.println("---------------------------------");
        }
    }

    /**
     * PROCEDIMENTO (Método sem retorno [cite: 1513])
     * Mostra todos os produtos cadastrados.
     */
    private static void listarTodosOsProdutos() {
        System.out.println("--- 5. Lista de Todos os Produtos ---");

        if (totalProdutosCadastrados == 0) { // Operador relacional "igual" [cite: 1433]
            System.out.println("O estoque está vazio. Cadastre um produto primeiro.");
            return;
        }

        // Usamos um 'for' para "varrer" o array [cite: 1598]
        // Ele vai de i=0 até o total de produtos que cadastramos
        for (int i = 0; i < totalProdutosCadastrados; i++) {
            System.out.println("---------------------------------");
            System.out.println("Código: " + codigos[i]);
            System.out.println("Nome: " + nomes[i]);
            System.out.println("Preço: R$ " + precos[i]);
            System.out.println("Quantidade: " + quantidades[i]);
        }
        System.out.println("---------------------------------");
    }
}