package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

// cellsnapshot 을 넘겨줬을때 sign 을 return 하는 함수
public interface CellSignProvidable {

    boolean supports(CellSnapshot cellSnapshot);

    String provide(CellSnapshot cellSnapshot); // cellSnapshot 을 받는 이유는 numberCell 값을 받기 위해


}
