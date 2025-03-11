package cleancode.minesweeper.tobe.cell;

public class CellState {
    private boolean isFlagged;
    private boolean isOpened;

    private CellState(boolean isFlagged, boolean isOpened) {
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    // 정적팩토리 메소드
    public static CellState initialize() {
        return new CellState(false, false);
    }

    // flag, open, isChecked 는 다 공통일 것 같음
    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened; // 닫혀있는데 깃발이 꽂혀있거나, 열었거나
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isFlagged() {
        return isFlagged;
    }
}
