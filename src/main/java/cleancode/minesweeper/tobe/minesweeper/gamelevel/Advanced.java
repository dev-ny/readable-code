package cleancode.minesweeper.tobe.minesweeper.gamelevel;

public class Advanced implements GameLevel { // GameLevel(추상화된 클래스) 의 Spec 을 만족시키는 구현체
    @Override
    public int getRowSize() {
        return 20;
    }

    @Override
    public int getColSize() {
        return 24;
    }

    @Override
    public int getLandMineCount() {
        return 99;
    }
}
