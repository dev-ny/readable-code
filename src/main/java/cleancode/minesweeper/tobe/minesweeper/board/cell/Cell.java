package cleancode.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {

     boolean isLandMine();

     boolean hasLandMineCount();

    // flag, open, isChecked 는 다 공통일 것 같음
    void flag();

    void open();

    boolean isChecked();

    boolean isOpened();

    CellSnapshot getSnapshot();
}
