package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;
import java.util.Random;

// 모든 지뢰찾기 게임 로직을 여기에 둘 것임
public class Minesweeper {
    // 중요한 문자열, 숫자야. 유지보수할때 잘 봐야해! 할 수 있는 것 = 매직넘버, 매직스트링
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    // 상수 컨벤션 = 대문자와 언더스코어로 이루어져 있게 해야함
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINES_COUNT = 10;

    // 입출력에 대한건 여기서!
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        consoleOutputHandler.showGameStartCommand();
        initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(BOARD);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();

                actOnCell(cellInput, userActionInput);
            } catch (GameException e) { // 의도적인 Exception
                consoleOutputHandler.printExceptionMessage(e);

            } catch (Exception e) { // 예상하지 못한 Exception
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
//                e.printStackTrace(); // 실무에서는 Antipattern 실무에서는 log 시스템에서 log 를 남기고 별도의 조치를 취함
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();

            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentFOrUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();

        return consoleInputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        boolean isAllCellChecked = isAllCellChecked();
        if (isAllCellChecked) { // 게임 이긴 것
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

//    private boolean isAllCellOpened() {
//        boolean isAllOpened = true;
//        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
//            for (int col = 0; col < BOARD_COL_SIZE; col++) {
//                if (BOARD[row][col].equals(CLOSED_CELL_SIGN)) { // 네모 박스가 하나라도 있는지 확인
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;
//    }

    private boolean isAllCellChecked() {
        return Arrays.stream(BOARD)// BOARD 라는 이중 string 배열에 stream 을 걸면 String[] 형태의 Stream 이 나옴 Stream<String[]>
                // 그냥 Map 을 하면 Stream<Stream<String>> 이 나오는데 flatMap 을 하면서 평탄화를 통해 이중배열을 배열로, 즉, Stream<String> 으로 만들어주는 것
                .flatMap(stringArr -> Arrays.stream(stringArr)) // flatMap 을 하면 Stream<String> 이 생기는데 이 stringArray 를 하나씩 돌면서 다시 Stream<String[]> 만들거다
                // 여기까지가 Stream<String>
                .allMatch(Cell::isChecked);
    }

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZE) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
//        int selectedColIndex;
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
//                return -1;
                throw new GameException("잘못된 입력입니다.");
        }
//        return selectedColIndex;
    }

    private void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINES_COUNT; i++) { // 10 이 col 숫자가아니라 지뢰숫자
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                int count = 0;
                if (isLandMineCell(row, col)) {
                    // 이미 셀의 기본속성을 0으로 바꿔서 따로 안해도 됨
                    continue;
                }
                count = countNearbyLandMines(row, col, count);
                BOARD[row][col].updateNearbyLandMineCount(count);
            }
        }
    }

    private int countNearbyLandMines(int row, int col, int count) {
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }

        if (BOARD[row][col].isOpened()) {
            return;
        }

        if (isLandMineCell(row, col)) {
            return;
        }

        // 여기까지 안열렸으면 아직 안열린 cell 이니까 열어!
        BOARD[row][col].open(); // 오픈

        if (BOARD[row][col].hasLandMineCount()) { // 숫자가 있으면!
            // 열고 숫자를 초기화 한 것임
//            BOARD[row][col] = Cell.ofNearbyLandMineCount(NEAR_BY_LAND_MINE_COUNTS[row][col]);
            return;
        }

        // 재귀임 // 오픈을 시작하고 멈추는건 숫자일때, 지뢰일때임
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }
}
