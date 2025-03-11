package cleancode.minesweeper.tobe.cell;

public interface Cell {
    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGN = "□";

     boolean isLandMine();

     boolean hasLandMineCount();

     String getSign();

    // flag, open, isChecked 는 다 공통일 것 같음
    void flag();

    void open();

    boolean isChecked();

    boolean isOpened();

}
