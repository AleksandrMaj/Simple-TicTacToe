import java.util.Scanner;
import java.util.ArrayList;

//Linked with GitHub
public class Game {

    private int turns = 0;
    private final char[] slots = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

    public Game() {
        ShowGame();
        PlaceSymbol(RequestValue());
    }

    public int RequestValue() {
        System.out.println("\nWhere do you wanna place your cross?");

        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

    public void PlaceSymbol(int value) {
        if (value > 9 || value < 0) {
            System.out.println("Please select a number between 1 - 9");
            PlaceSymbol(RequestValue());
            return;
        }

        if (slots[value - 1] != ' ') {
            System.out.println("Here is already a symbol. Please choose another place!");
            PlaceSymbol(RequestValue());
            return;
        }

        slots[value - 1] = 'X';
        turns++;

        if (EndGame(true)) return;

        ComputerSetSymbol();
        ShowGame();

        if (EndGame(true)) return;

        PlaceSymbol(RequestValue());
    }

    public void ShowGame() {
        System.out.println();
        System.out.println(" " + slots[0] + " |" + " " + slots[1] + " |" + " " + slots[2] + "");
        System.out.println("------------");
        System.out.println(" " + slots[3] + " |" + " " + slots[4] + " |" + " " + slots[5] + "");
        System.out.println("------------");
        System.out.println(" " + slots[6] + " |" + " " + slots[7] + " |" + " " + slots[8] + "");
        System.out.println();
    }

    public void ComputerSetSymbol() {
        ArrayList<Integer> freeSpace = new ArrayList<>();

        for (int slot = 0; slot < 9; slot++) {
            if (slots[slot] == ' ') {
                freeSpace.add(slot);
            }
        }

        int slotToPlace = (int) Math.floor(Math.random() * freeSpace.size());
        slots[freeSpace.get(slotToPlace)] = 'O';
        turns++;
    }

    public char CheckWinner() {
        char activeWinner = ' ';

        //Horizontally
        for (int turn = 0; turn < 7; turn = turn + 3) {
            if (slots[turn] == ' ') continue;
            if (slots[turn] == slots[turn + 1] && slots[turn + 1] == slots[turn + 2]) {
                activeWinner = slots[turn];
                return activeWinner;
            }
        }

        //Vertically
        for (int turn = 0; turn < 3; turn++) {
            if (slots[turn] == ' ') continue;
            if (slots[turn] == slots[turn + 3] && slots[turn + 3] == slots[turn + 6]) {
                activeWinner = slots[turn];
                return activeWinner;
            }
        }

        //Crosswise
        if (slots[0] == slots[4] && slots[4] == slots[8]) {
            activeWinner = slots[4];
            return activeWinner;
        }

        if (slots[2] == slots[4] && slots[4] == slots[6]) {
            activeWinner = slots[4];
            return activeWinner;
        }

        return activeWinner;
    }

    public boolean CheckDraw() {
        ArrayList<Integer> freeSpace = new ArrayList<>();

        for (int slot = 0; slot < 9; slot++) {
            if (slots[slot] == ' ') {
                freeSpace.add(slot);
            }
        }
        return freeSpace.size() == 0;
    }

    public boolean EndGame(boolean showGame) {
        char checkWinner = CheckWinner();
        if (checkWinner != ' ') {
            if (showGame) ShowGame();
            if (checkWinner == 'X') {
                System.out.println("You won! Rounds: " + turns);
            } else {
                System.out.println("You have lost! Rounds: " + turns);
            }
            return true;
        }

        if (CheckDraw()) {
            if (showGame) ShowGame();
            System.out.println("There is a tie!");
            return true;
        }
        return false;
    }
}
