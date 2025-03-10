package cleancode.minesweeper.tobe.gamelevel;

public class Beginner implements GameLevel { // GameLevel(추상화된 클래스) 의 Spec 을 만족시키는 구현체
    @Override
    public int getRowSize() {
        return 8;
    }

    @Override
    public int getColSize() {
        return 10;
    }

    @Override
    public int getLandMineCount() {
        return 10;
    }
}
