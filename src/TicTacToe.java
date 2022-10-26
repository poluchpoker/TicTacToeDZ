import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final char DOT_EMPTY = '#';
    public static final char DOT_O = 'O';
    public static final char DOT_X = 'X';
    char[][] table;
    public static Scanner inp = new Scanner(System.in);
    public static Random rand = new Random();

    int countCellX = 0; int countCellO = 0; int countFreeCell = 0;
    int countLine = 0; int countColumn = 0; int running = 0;

    public static void main(String[] args) {
        new TicTacToe().game();
    }

    TicTacToe(){
        table = new char[5][5];

        this.initTable();
    }

    void game(){
        while (true) {
            this.humanTurn();

            if (checkWin(DOT_X)) {
                System.out.println("Победа!");
                break;
            }

            if (this.isFullTable()){
                System.out.println("Таблица заполнена полностью!");
                break;
            }

            this.aiTurn();
            this.printTable();

            if (checkWin(DOT_O)){
                System.out.println("Вы проиграли, комп вин))0))0))");
                break;
            }

            if (this.isFullTable()){
                System.out.println("Таблица заполнена полностью!");
                break;
            }

        }

        System.out.println("Игра закончена!");
        this.printTable();
    }

    void initTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                this.table[line][column] = DOT_EMPTY;
            }
        }
    }

    void printTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                System.out.print(this.table[line][column] + " ");
            }
            System.out.print("\n");
        }
    }

    boolean isFullTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                if (this.table[line][column] == DOT_EMPTY) return false;
            }
        }

        return true;
    }

    int getNum(){
        int trying;
        String symbol;

        while (true){
            if (inp.hasNext()){
                trying = inp.nextInt();
                break;
            }else{
                symbol = inp.next();
                System.out.println("Введены невереные символы" + symbol);
            }
        }
        return trying;
    }

    void humanTurn(){
        int x, y;
        do{
            System.out.println("Введите число из диапазона между отчанием и надеждой (1..5)");
            x = getNum() - 1;
            y = getNum() - 1;
        }while(!isCellValidHuman(x, y));

        table[y][x] = DOT_X;
    }

    void aiTurn(){
        if (this.check1Dig()) return;

        if (this.check2Diagonal()) return;

        if (this.check3Diagonal()) return;

        if (this.check4Diagonal()) return;

        if (this.check5Diagonal()) return;

        if (this.check6Diagonal()) return;

        if (this.checkLines()) return;

        if (this.checkColumns()) return;

        this.randomWalk();
    }

    boolean isCellValidHuman(int x, int y){
        if (x < 0 || x > 4 || y < 0 || y > 4){
            System.out.println("Вы вышли из диапазона (1..5)");
            return false;
        }

        if (this.table[y][x] != DOT_EMPTY){
                System.out.println("Ячейка занята");
                return false;
        }

        return true;
    }

    boolean isCellValidAI(int x, int y){
        if (x < 0 || x > 4 || y < 0 || y > 4) return false;

        return this.table[y][x] == DOT_EMPTY;
    }

    void count(int line, int column){
        if (this.table[line][column] == DOT_X) countCellX++;

        if (this.table[line][column] == DOT_O) countCellO++;

        if (this.table[line][column] == DOT_EMPTY) countFreeCell++;
    }

    void initCount(){
        this.countCellX = 0;
        this.countCellO = 0;
        this.countFreeCell = 0;
    }

    boolean isLosingForAI(){
        return (this.countFreeCell == 2 && this.countCellX == 3 || this.countFreeCell == 1 && this.countCellX == 3);
    }

    boolean isWining(){
        return (this.countFreeCell == 2 && this.countCellO == 3 || this.countFreeCell == 1 && this.countCellO == 3);
    }

    boolean checkWin(char dot){
        for (int element_line = 0; element_line < 5; element_line++){
            for (int element_column = 0; element_column < 4; element_column++){
                if (table[element_line][element_column] == dot && table[element_line][element_column + 1] == dot)
                    countLine++;

                if (countLine >= 3){
                    return true;
                }
            }
            countLine = 0;
        }

        for (int element_column = 0; element_column < 5; element_column++){
            for (int element_line = 0; element_line < 4; element_line++){
                if (table[element_line][element_column] == dot && table[element_line + 1][element_column] == dot)
                    countColumn++;

                if (countColumn >= 3){
                    return true;
                }
            }
            countColumn = 0;
            running += 1;
        }

        return (table[0][1] == dot && table[1][2] == dot && table[2][3] == dot && table[3][4] == dot) ||
                (table[0][3] == dot && table[1][2] == dot && table[2][1] == dot && table[3][0] == dot) ||
                (table[1][0] == dot && table[2][1] == dot && table[3][2] == dot && table[4][3] == dot) ||
                (table[1][4] == dot && table[2][3] == dot && table[3][2] == dot && table[4][1] == dot) ||
                (table[0][0] == dot && table[1][1] == dot && table[2][2] == dot && table[3][3] == dot &&
                        table[4][4] == dot)||
                (table[0][0] == dot && table[1][1] == dot && table[2][2] == dot && table[3][3] == dot) ||
                (table[1][1] == dot && table[2][2] == dot && table[3][3] == dot && table[4][4] == dot) ||
                (table[0][4] == dot && table[1][3] == dot && table[2][2] == dot && table[3][1] == dot &&
                        table[4][0] == dot)||
                (table[0][4] == dot && table[1][3] == dot && table[2][2] == dot && table[3][1] == dot)||
                (table[1][3] == dot && table[2][2] == dot && table[3][1] == dot && table[4][0] == dot);
    }

    void randomWalk(){
        int x, y;
        do{
            x = rand.nextInt(5);
            y = rand.nextInt(5);
        }while (!isCellValidAI(x,y));

        this.table[y][x] = DOT_O;
    }

    boolean putO(int line, int column){
        if (this.table[line][column] == DOT_EMPTY){
            this.table[line][column] = DOT_O;
            return true;
        }

        return false;
    }

    boolean check1Dig(){
        this.initCount();
        for (int line = 0; line < 5; line++){
            this.count(line, line);
        }

        if (this.isWining() || this.isLosingForAI()){
            for (int line = 0; line < 5; line++){
                if (this.putO(line, line)) return true;
            }
        }

        return false;
    }

    boolean check2Diagonal() {
        this.initCount();
        int column = 4;
        for (int line = 0; line < 5; line++) {
            this.count(line, column);
            column--;
        }

        if (this.isWining() || this.isLosingForAI()) {
            column = 4;
            for (int line = 0; line < 5; line++) {
                if (this.putO(line, column)) return true;
                column--;
            }
        }

        return false;
    }

    boolean check3Diagonal() {
        this.initCount();
        int column = 1;
        for (int line = 0; line < 4; line++) {
            this.count(line, column);
            column++;
        }

        if (this.isWining() || this.isLosingForAI()) {
            column = 1;
            for (int line = 0; line < 4; line++) {
                if (this.putO(line, column)) return true;
                column++;
            }
        }

        return false;
    }

    boolean check4Diagonal() {
        this.initCount();
        int column = 0;
        for (int line = 1; line < 5; line++) {
            this.count(line, column);
            column++;
        }

        if (this.isWining() || this.isLosingForAI()) {
            column = 0;
            for (int line = 1; line < 5; line++) {
                if (this.putO(line, column)) return true;
                column++;
            }
        }

        return false;
    }

    boolean check5Diagonal() {
        this.initCount();
        int column = 3;
        for (int line = 0; line < 4; line++) {
            this.count(line, column);
            column--;
        }

        if (this.isWining() || this.isLosingForAI()) {
            column = 3;
            for (int line = 0; line < 4; line++) {
                if (this.putO(line, column)) return true;
                column--;
            }
        }

        return false;
    }

    boolean check6Diagonal() {
        this.initCount();
        int column = 4;
        for (int line = 1; line < 5; line++) {
            this.count(line, column);
            column--;
        }

        if (this.isWining() || this.isLosingForAI()) {
            column = 4;
            for (int line = 1; line < 5; line++) {
                if (this.putO(line, column)) return true;
                column--;
            }
        }

        return false;
    }

    boolean checkLines() {
        for (int line = 0; line < 5; line++){
            this.initCount();
            for (int column = 0; column < 5; column++) {
                this.count(line, column);
            }

            if (this.isWining() || this.isLosingForAI()) {
                for (int column = 0; column < 5; column++) {
                    if (this.putO(line,column)) return true;
                }
            }
        }
        return false;
    }

    boolean checkColumns() {
        for (int columns = 0; columns < 5; columns++) {
            this.initCount();
            for (int line = 0; line < 5; line++) {
                this.count(line, columns);
            }

            if (this.isWining() || this.isLosingForAI()) {
                for (int line = 0; line < 5; line++) {
                    if (this.putO(line, columns)) return true;
                }
            }
        }
        return false;
    }
}