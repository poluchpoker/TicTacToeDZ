import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final char DOT_EMPTY = '#';
    public static final char DOT_O = 'O';
    public static final char DOT_X = 'X';
    char[][] table;
    public static Scanner inp = new Scanner(System.in);
    public static Random rand = new Random();

    int countCellX = 0; int countCellO = 0; int countFreeCell = 0; //счетчики клеток X, O, а также пустые
    int countLine = 0; int countColumn = 0; int running = 0; //счетчики линий, столбцов и для прогона

    public static void main(String[] args) {
        new TicTacToe().game();
    }

    // создание игрового поля
    TicTacToe(){
        table = new char[5][5];

        this.initTable();
    }

    //сама игра и весь движ
    void game(){
        while (true) {
            this.humanTurn();

            if (checkWin(DOT_X)) {
                System.out.println("Победа!");
                break;
            }

            if (this.isFullTable()){
                System.out.println("Ничья с ботом)000)00))00 (ботик)");
                break;
            }

            this.aiTurn();
            this.printTable();

            if (checkWin(DOT_O)){
                System.out.println("Вы проиграли, комп вин))0))0))");
                break;
            }

            if (this.isFullTable()){
                System.out.println("Ничья с ботом0))000 (ботик)");
                break;
            }

        }

        System.out.println("Игра закончена!");
        this.printTable();
    }

    //заполнение таблички символами "#"
    void initTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                this.table[line][column] = DOT_EMPTY;
            }
        }
    }

    //вывод игрового поля
    void printTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                System.out.print(this.table[line][column] + " ");
            }
            System.out.print("\n");
        }
    }

    //проверка на заполненность таблички, для того, чтобы узнать когда ничья
    boolean isFullTable(){
        for (int line = 0; line < 5; line++){
            for (int column = 0; column < 5; column++){
                if (this.table[line][column] == DOT_EMPTY) return false;
            }
        }

        return true;
    }

    //метод, содержащий цикл для чтения с клавы
    int getNum(){
        int trying;

        while (true){
            if (inp.hasNext()){
                trying = inp.nextInt();
                break;
            }
        }
        return trying;
    }

    //ход людишки
    void humanTurn(){
        int x, y;
        do{
            System.out.println("Введите число из диапазона между отчанием и надеждой (1..5)");
            x = getNum() - 1;
            y = getNum() - 1;
        }while(!isCellValidHuman(x, y));

        table[y][x] = DOT_X;
    }

    //ход компа
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

    //чекаем диапазончик челебоса
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

    //чекаем диапазончик компа
    boolean isCellValidAI(int x, int y){
        if (x < 0 || x > 4 || y < 0 || y > 4) return false;

        return this.table[y][x] == DOT_EMPTY;
    }

    //считаем наши X, O и пустые клетки
    void count(int line, int column){
        if (this.table[line][column] == DOT_X) countCellX++;

        if (this.table[line][column] == DOT_O) countCellO++;

        if (this.table[line][column] == DOT_EMPTY) countFreeCell++;
    }

    //инициализируем наши счетчики
    void initCount(){
        this.countCellX = 0;
        this.countCellO = 0;
        this.countFreeCell = 0;
    }

    //бот почти проиграл
    boolean isLosingForAI(){
        return (this.countFreeCell == 2 && this.countCellX == 3 || this.countFreeCell == 1 && this.countCellX == 3);
    }

    //бот почти выиграл
    boolean isWining(){
        return (this.countFreeCell == 2 && this.countCellO == 3 || this.countFreeCell == 1 && this.countCellO == 3);
    }

    //проверка на победку
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

    //рандомим ходьбу боту
    void randomWalk(){
        int x, y;
        do{
            x = rand.nextInt(5);
            y = rand.nextInt(5);
        }while (!isCellValidAI(x,y));

        this.table[y][x] = DOT_O;
    }

    //бот ставит O вместо "#"
    boolean putO(int line, int column){
        if (this.table[line][column] == DOT_EMPTY){
            this.table[line][column] = DOT_O;
            return true;
        }

        return false;
    }

    /*проверяем, если мы можем победить по ГЛАВНОЙ диагонали, то бот нам не даст этого сделать, при условии, если мы не
    начнем заполнять с центра, тк бот перекроет только 1 сторону, в другую нас ждет вин
   */
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

    /*проверяем, если мы можем победить по ПОБОЧНОЙ диагонали, то бот нам не даст этого сделать, при условии, если мы не
    начнем заполнять с центра, тк бот перекроет только 1 сторону, в другую нас ждет вин
   */
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

    /*проверяем, если мы можем победить по побочной диагонали от главной, то бот нам не даст этого сделать, при условии,
    если мы не начнем заполнять с центра, тк бот перекроет только 1 сторону, в другую нас ждет вин(остальные аналогично)*/
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

    /*проверяем, если мы можем победить по гор-ой линии, то бот нам не даст этого сделать, при условии, если мы не
    начнем заполнять с центра, тк бот перекроет только 1 сторону, в другую нас ждет вин
   */
    boolean checkLines() {
        for (int line = 0; line < 5; line++){
            this.initCount();
            for (int column = 0; column < 5; column++) {
                this.count(line, column);
            }

            if (this.isWining() || this.isLosingForAI()) {
                for (int column = 0; column < 4; column++) {
                    if (this.table[line][column] != this.table[line][column + 1])
                        if (this.putO(line,column)) return true;
                }
            }
        }
        return false;
    }

    /*проверяем, если мы можем победить по столбцу, то бот нам не даст этого сделать, при условии, если мы не
    начнем заполнять с центра, тк бот перекроет только 1 сторону, в другую нас ждет вин
   */
    boolean checkColumns() {
        for (int column = 0; column < 5; column++) {
            this.initCount();
            for (int line = 0; line < 5; line++) {
                this.count(line, column);
            }

            if (this.isWining() || this.isLosingForAI()) {
                for (int line = 0; line < 4; line++) {
                    if (this.table[line][column] != this.table[line + 1][column]){
                        if (this.putO(line, column)) return true;
                    }
                }
            }
        }
        return false;
    }
}
