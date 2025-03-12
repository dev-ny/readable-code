package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.List;

public class GameBoard {
    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();

        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public boolean isLandMineCellAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
                || cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMinePositions =  cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions); // 너가 가진 거에서 파라미터가 주어진 것을 뺀 나머지 position 을 줘
        initializeNumberCells(numberPositionCandidates);
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        for (CellPosition position: allPositions) {
            updateCellAt(position, new EmptyCell());
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMinePositions) {
        for (CellPosition position: landMinePositions) {
            updateCellAt(position, new LandMineCell());
        }
    }

    private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
        for (CellPosition candidatePosition : numberPositionCandidates) {
            int count = countNearbyLandMines(candidatePosition);
            if (count != 0) {
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMineCellAt) // 이거 지뢰Cell 이야?
                .count();

        return (int) count;

//        int count = 0;
//        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCellAt(row - 1, col - 1)) {
//            count++;
//        }
//        if (row - 1 >= 0 && isLandMineCellAt(row - 1, col)) {
//            count++;
//        }
//        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCellAt(row - 1, col + 1)) {
//            count++;
//        }
//        if (col - 1 >= 0 && isLandMineCellAt(row, col - 1)) {
//            count++;
//        }
//        if (col + 1 < colSize && isLandMineCellAt(row, col + 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCellAt(row + 1, col - 1)) {
//            count++;
//        }
//        if (row + 1 < rowSize && isLandMineCellAt(row + 1, col)) {
//            count++;
//        }
//        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCellAt(row + 1, col + 1)) {
//            count++;
//        }
//        return count;
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITION.stream()
                .filter(relativePosition -> cellPosition.canCalculatePositionBy(relativePosition)) // relativePosition 이 계산 가능한 Position 이야? 0 이상이야?
                .map(relativePosition -> cellPosition.calculatePositionBy(relativePosition)) // 그러면 relativePosition 으로 새로운 좌표 계산해!
                .filter(position -> position.isRowIndexLessThan(rowSize)) // 근데 이거 boardSize 이상이야?
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    public int getRowSize() {
        return board.length;
    }

    public boolean isAllCellChecked() {
//        return Arrays.stream(board)// BOARD 라는 이중 string 배열에 stream 을 걸면 String[] 형태의 Stream 이 나옴 Stream<String[]>
//                // 그냥 Map 을 하면 Stream<Stream<String>> 이 나오는데 flatMap 을 하면서 평탄화를 통해 이중배열을 배열로, 즉, Stream<String> 으로 만들어주는 것
//                .flatMap(stringArr -> Arrays.stream(stringArr)) // flatMap 을 하면 Stream<String> 이 생기는데 이 stringArray 를 하나씩 돌면서 다시 Stream<String[]> 만들거다
//                // 여기까지가 Stream<String>
//                .allMatch(Cell::isChecked);

        // cell 을 가공하던 책임이 cells 안으로 들어가면서 목록을 구성하는 책임이 Cells 안으로 들어감
        Cells cells = Cells.from(board);
        return cells.isAllChecked();

    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public int getColSize() {
        return board[0].length;
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (cellPosition.isRowIndexMoreThanOrEqual(getRowSize())
                || cellPosition.isColIndexMoreThanOrEqual(getColSize())) { // 얘도 바깥에서 BoardSize 보다 큰지는 확인했지만 재귀로 연산을 하기때문에 두어야 함.
            return;
        }

        if (isOpenedCell(cellPosition)) {
            return;
        }

        if (isLandMineCellAt(cellPosition)) {
            return;
        }

        // 여기까지 안열렸으면 아직 안열린 cell 이니까 열어!
        openAt(cellPosition); // 오픈

        if (doesCellHaveLandMineCount(cellPosition)) { // 숫자가 있으면!
            // 열고 숫자를 초기화 한 것임
//            BOARD[row][col] = Cell.ofNearbyLandMineCount(NEAR_BY_LAND_MINE_COUNTS[row][col]);
            return;
        }

        calculateSurroundedPositions(cellPosition, getRowSize(), getColSize())
                .forEach(this::openSurroundedCells);
//        ==
//        RelativePosition.SURROUNDED_POSITION.stream()
//                .filter(relativePosition -> cellPosition.canCalculatePositionBy(relativePosition)) // if 는 filter // cellPosition 이 canCalculate 한지
//                .map(relativePosition -> cellPosition.calculatePositionBy(relativePosition)) // relativePosition 이 주어졌을 때 calculatePositionBy 를 하면 새로운 cellPosition 들이 우루루 나옴
//                .filter(position -> position.isRowIndexLessThan(getRowSize())) // board RowSize 와 생성된 Cell 의 row position 체크
//                .filter(position -> position.isColIndexLessThan(getColSize())) // board ColSize 와 생성된 Cell 의 col psoition 체크
//                .forEach(this::openSurroundedCells); // 이것에 대해 openSurroundedCells 호출
//        ==
//        for (RelativePosition relativePosition : RelativePosition.SURROUNDED_POSITION) {
//            if (cellPosition.canCalculatePositionBy(relativePosition)) {
//                // 둘다 0 보다 컸을때 새로운 cellposition 을 만들 수 있음!
//                CellPosition nextCellPosition = cellPosition.calculatePositionBy(relativePosition);
//                openSurroundedCells(nextCellPosition);
//            }
//        }
    }


    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isOpened();
    }
}
