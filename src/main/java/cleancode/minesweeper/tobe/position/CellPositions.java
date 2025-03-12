package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    private CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> positions) {
        return new CellPositions(positions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return of(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) { // 얘는 landMineCount 인지 모름
        // shuffle 할때 그냥 컬렉션(positions) 가져다 쓰면 순서가 뒤죽박죽 될 수 있음.
        // 순서가 주ㅇ요할때는 새롭게 컬렉션을 만들어라
       List<CellPosition> cellPositions = new ArrayList<>(positions);
        Collections.shuffle(cellPositions);
        return cellPositions.subList(0, count); // 앞쪽에서 count 갯수만큼 자름
    }

    public List<CellPosition> subtract(List<CellPosition> positionsListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(positions); // 전체 positions 리스트로 새로 생성
        CellPositions positionsToSubtract = CellPositions.of(positionsListToSubtract); // 뺄 positions 객체로 새로 생성2

        return cellPositions.stream()
                .filter(position -> positionsToSubtract.doesNotContain(position))
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !positions.contains(position);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions); // 외부에서 참조할 수 없도록 positions 을 새롭게 만들어서 리턴해줌
    }
}
