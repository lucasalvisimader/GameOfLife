//        O jogo da vida se passa em um arranjo bidimensional infinito de células que podem estar em um de dois estados, vivo ou morto.
//        Cada célula interage com suas oito vizinhas, as células adjacentes horizontal, vertical e diagonalmente.
//        O jogo evolui em unidades de tempo discretas chamadas de gerações.
//        A cada nova geração, o estado do jogo é atualizado pela aplicação das seguintes regras:
//
//        Toda célula morta com exatamente três vizinhos vivos torna-se viva (nascimento).
//        Toda célula viva com menos de dois vizinhos vivos morre por isolamento.
//        Toda célula viva com mais de três vizinhos vivos morre por superpopulação.
//        Toda célula viva com dois ou três vizinhos vivos permanece viva.[3]
//        As regras são aplicadas simultaneamente em todas as células para chegar ao estado da próxima geração.
//        Sem vida -> "░"
//        Com vida -> "▓"

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Bem vindo ao jogo da vida do John Conway!");
        System.out.println("Para iniciar, qual seria a quantidade de linhas no tabuleiro?");
        int lines = sc.nextInt();
        System.out.println("E a quantidade de colunas?");
        int columns = sc.nextInt();
        char[][] board = new char[lines][columns];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((int) (Math.random() * 3) == 0) {
                    board[i][j] = '░';
                } else {
                    board[i][j] = '▓';
                }
            }
        }
        for (int i = 0; true; i++) {
            System.out.println("Onde quer colocar um bloco de vida?");
            System.out.println(returnBoard(board));

            System.out.println("Qual linha você gostaria de colocar?");
            int line = sc.nextInt();
            System.out.println("Qual coluna você gostaria de colocar?");
            int columnn = sc.nextInt();
            updateBoard(board, line, columnn);

            System.out.println("Quer colocar mais um?\n1 - Sim\n2 - Não");
            if (sc.nextInt() == 2) {
                System.out.println(i + "ª geração");
                startGame(board);
            }
        }
    }

    public static String returnBoard(char[][] board) {
        String finalText = "   ";
        finalText += "\n";
        for (int i = 0; i < board.length; i++) {
            finalText += i + " ";
            if (i < 10) {
                finalText += " ";
            }
            for (int j = 0; j < board[i].length; j++) {
                finalText += board[i][j] + " ";
                if (j == board[i].length - 1) {
                    finalText += "\n";
                }
            }
        }
        return finalText;
    }

    public static void updateBoard(char[][] board, int line, int column) {
        board[line][column] = '▓';
    }

    public static void startGame(char[][] board) {
        System.out.println("Você quer que seja automática as gerações?\n1 - Sim\n2 - Não");
        int choice = sc.nextInt();
        for (int i = 0; true; i++) {
            if (choice == 2) {
                System.out.println(i + "ª geração: ");
                System.out.println(returnBoard(board));
                System.out.println("Você quer continuar a geração?\n1 - Sim\n2 - Não");
                if (sc.nextInt() == 1) {
                    board = logicGeneration(board);
                } else if (sc.nextInt() == 2) {
                    System.exit(0);
                }
            } else {
                board = logicGeneration(board);
                System.out.println(returnBoard(board));
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static char[][] logicGeneration(char[][] board) {
//        Sem vida -> "░"
//        Com vida -> "▓"
        char[][] auxBoard = Arrays.stream(board).map(char[]::clone).toArray(char[][]::new);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int qtyNeighborsAlive = 0;
                boolean hasUp = j > 0, hasDown = j < board[i].length - 1, hasLeft = i > 0, hasRight = i < board.length - 1;
                if (hasUp) {
                    if (board[i][j - 1] == '▓') {
                        qtyNeighborsAlive++;
                    }
                    if (hasLeft) {
                        if (board[i - 1][j] == '▓') {
                            qtyNeighborsAlive++;
                        }
                        if (board[i - 1][j - 1] == '▓') {
                            qtyNeighborsAlive++;
                        }
                    }
                    if (hasRight) {
                        if (board[i + 1][j] == '▓') {
                            qtyNeighborsAlive++;
                        }
                        if(board[i + 1][j - 1] == '▓') {
                            qtyNeighborsAlive++;
                        }
                    }
                }
                if (hasDown) {
                    if (board[i][j + 1] == '▓') {
                        qtyNeighborsAlive++;
                    }
                    if (hasLeft) {
                        if (!hasUp) {
                            if (board[i - 1][j] == '▓') {
                                qtyNeighborsAlive++;
                            }
                        }
                        if (board[i - 1][j + 1] == '▓') {
                            qtyNeighborsAlive++;
                        }
                    }
                    if (hasRight) {
                        if (!hasUp) {
                            if (board[i + 1][j] == '▓') {
                                qtyNeighborsAlive++;
                            }
                        }
                        if(board[i + 1][j + 1] == '▓') {
                            qtyNeighborsAlive++;
                        }
                    }
                }
//        Toda célula morta com exatamente três vizinhos vivos torna-se viva (nascimento).
                if (board[i][j] == '░' && qtyNeighborsAlive == 3) {
                    auxBoard[i][j] = '▓';
                }
//        Toda célula viva com menos de dois vizinhos vivos morre por isolamento.
//        Toda célula viva com mais de três vizinhos vivos morre por superpopulação.
//        Toda célula viva com dois ou três vizinhos vivos permanece viva.
//        As regras são aplicadas simultaneamente em todas as células para chegar ao estado da próxima geração.
                if (board[i][j] == '▓' && (qtyNeighborsAlive < 2 || qtyNeighborsAlive > 3)) {
                    auxBoard[i][j] = '░';
                }
            }
        }
        return auxBoard;
    }
}
