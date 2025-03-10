package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {
    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();

        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    public void initializeGame() {
        int rowSize = board.length;
        int colSize = board[0].length;

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < landMineCount; i++) { // 10 이 col 숫자가아니라 지뢰숫자
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            findCell(landMineRow, landMineCol).turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                int count = 0;
                if (isLandMineCell(row, col)) {
                    // 이미 셀의 기본속성을 0으로 바꿔서 따로 안해도 됨
                    continue;
                }
                count = countNearbyLandMines(row, col, count);
                findCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }

    private int countNearbyLandMines(int row, int col, int count) {
        int rowSize = board.length;
        int colSize = board[0].length;

        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    public int getRowSize() {
        return board.length;
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)// BOARD 라는 이중 string 배열에 stream 을 걸면 String[] 형태의 Stream 이 나옴 Stream<String[]>
                // 그냥 Map 을 하면 Stream<Stream<String>> 이 나오는데 flatMap 을 하면서 평탄화를 통해 이중배열을 배열로, 즉, Stream<String> 으로 만들어주는 것
                .flatMap(stringArr -> Arrays.stream(stringArr)) // flatMap 을 하면 Stream<String> 이 생기는데 이 stringArray 를 하나씩 돌면서 다시 Stream<String[]> 만들거다
                // 여기까지가 Stream<String>
                .allMatch(Cell::isChecked);
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public int getColSize() {
        return board[0].length;
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }

        if (isOpenedCell(row, col)) {
            return;
        }

        if (isLandMineCell(row, col)) {
            return;
        }

        // 여기까지 안열렸으면 아직 안열린 cell 이니까 열어!
        open(row, col); // 오픈

        if (doesCellHaveLandMineCount(row, col)) { // 숫자가 있으면!
            // 열고 숫자를 초기화 한 것임
//            BOARD[row][col] = Cell.ofNearbyLandMineCount(NEAR_BY_LAND_MINE_COUNTS[row][col]);
            return;
        }

        // 재귀임 // 오픈을 시작하고 멈추는건 숫자일때, 지뢰일때임
        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }
}
