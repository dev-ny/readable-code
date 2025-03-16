package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Arrays;
import java.util.List;

// 일급 컬렉션 조건은 필드가 하나다! 컬렉션은 하나!
// 컬렉션에는 리스트, Set, Map 모두 올 수 있음
public class Cells {

    private final List<Cell> cells;

    private Cells(List<Cell> cells) {
        this.cells = cells;
    }

    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

    // Cells 가 cellsList 에 대한 가공의 책임을 가져가게 되고
    public static Cells from(Cell[][] cells) {
        List<Cell> cellsList =  Arrays.stream(cells)
                .flatMap(c -> Arrays.stream(c))
                .toList();

        return of(cellsList);
    }

    public boolean isAllChecked() {
        return cells.stream()
                .allMatch(Cell::isChecked);
    }
}
