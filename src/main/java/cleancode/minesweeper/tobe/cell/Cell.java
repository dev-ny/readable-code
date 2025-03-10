package cleancode.minesweeper.tobe.cell;

public abstract class Cell {
    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";

    // 보드를 기반으로 먼저 만들어 보겠다.
    // final 인 데이터는 최대한 final 로 만들어야 함
    protected boolean isFlagged;
    protected boolean isOpened;
    // Cell 이 가진 속성: 근처 지뢰 숫자, 지뢰 여부
    // Cell의 상태: 깃발 유무, 열렸다/닫혔다, 사용자가 확인함

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

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
}
