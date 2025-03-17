package cleancode.minesweeper.tobe.minesweeper.board.position;

import java.util.Objects;

public class CellPosition {

    // final 을 붙여 불변성
    private final int rowIndex;
    private final int colIndex;


    private CellPosition(int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) {
            // 게임 중에 발생되지 않기를 바라는 예외라서 GameException 이 아니라 개발자가 직접 확인해 주세요! 하는 마음으로 IllegalArgumentException 을 던짐
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public static CellPosition of(int rowIndex, int colIndex) {
        return new CellPosition(rowIndex, colIndex);
    }

    /**
     * equals 와 hashCode 재정의를 통해서 동등성 보장!
     * rowIndex 와 colIndex 값이 같으면 같은 값!
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

    public boolean isRowIndexMoreThanOrEqual(int rowIndex) {
        return this.rowIndex >= rowIndex;
    }

    public boolean isColIndexMoreThanOrEqual(int colIndex) {
        return this.colIndex >= colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return this.rowIndex + relativePosition.getDeltaRow() >= 0
                && this.colIndex+ relativePosition.getDeltaCol() >= 0;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (this.canCalculatePositionBy(relativePosition)) {
            return CellPosition.of(
                    this.rowIndex + relativePosition.getDeltaRow(),
                    this.colIndex + relativePosition.getDeltaCol()
            );
        }
        throw new IllegalArgumentException("움직일 수 있는 좌표가 아닙니다. 좌표입니다.");
        // calculatePositionBy 만 단독으로 호출될때를 대비해서 if 문 적용.
    }

    public boolean isRowIndexLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColIndexLessThan(int colIndex) {
        return this.colIndex < colIndex;
    }
}
