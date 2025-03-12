package cleancode.minesweeper.tobe.cell;

public class EmptyCell implements Cell {
    private final CellState cellState = CellState.initialize();

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public CellSnapShot getSnapshot() {
        if (cellState.isOpened()) {
            return CellSnapShot.ofEmpty();
        }
        if (cellState.isFlagged()) {
            return CellSnapShot.ofFlag();
        }
        return CellSnapShot.ofUnChecked();
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
