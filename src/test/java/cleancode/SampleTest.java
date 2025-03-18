package cleancode;

import cleancode.minesweeper.tobe.Minesweeper;
import cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import cleancode.minesweeper.tobe.minesweeper.board.GameStatus;
import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Advanced;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Beginner;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.Middle;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.VeryBeginner;
import cleancode.minesweeper.tobe.minesweeper.io.BoardIndexConverter;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class SampleTest {

    // ConsoleInputHandler.java
   @DisplayName("Cell의 종류에 따라 적절한 상태로 설정되어있는지 확인한다.")
   @Test
   void checkSnapshot() {
       // given
       Cell cell = new EmptyCell();

       CellSnapshot testCellSnapshot = null;
       if (cell instanceof EmptyCell) {
           testCellSnapshot = CellSnapshot.ofEmpty();
       }
       if (cell instanceof NumberCell) {
           testCellSnapshot = CellSnapshot.ofNumber(3);
       }
       if (cell instanceof LandMineCell) {
           testCellSnapshot = CellSnapshot.ofLandMine();
       }

       // when
       CellSnapshot cellSnapshot = cell.getSnapshot();

       // then
        assertThat(testCellSnapshot.getStatus()).isEqualTo(testCellSnapshot.getStatus());
   }

   // GameLevel
    @DisplayName("레벨에 맞는 보드의 가로 크기를 가졌는지 확인한다.")
    @Test
    void getRowSize() {
        // given
        GameConfig gameConfig = new GameConfig(
                new Advanced(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        int testRowSize = 0;
        if (gameConfig.getGameLevel() instanceof VeryBeginner) {
            testRowSize = 4;
        }
        if (gameConfig.getGameLevel() instanceof Beginner) {
            testRowSize = 8;
        }
        if (gameConfig.getGameLevel() instanceof Middle) {
            testRowSize = 14;
        }
        if (gameConfig.getGameLevel() instanceof Advanced) {
            testRowSize = 20;
        }


        // when
        int rowSize = gameConfig.getGameLevel().getRowSize();

        // then
        assertThat(testRowSize).isEqualTo(rowSize);
    }

    // Advanced
    @DisplayName("레벨에 맞는 보드의 세로 크기를 가졌는지 확인한다.")
    @Test
    void getColSize() {
        // given
        GameConfig gameConfig = new GameConfig(
                new Advanced(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );


        int testColSize = 0;
        if (gameConfig.getGameLevel() instanceof VeryBeginner) {
            testColSize = 5;
        }
        if (gameConfig.getGameLevel() instanceof Beginner) {
            testColSize = 10;
        }
        if (gameConfig.getGameLevel() instanceof Middle) {
            testColSize = 18;
        }
        if (gameConfig.getGameLevel() instanceof Advanced) {
            testColSize = 24;
        }

        // when
        int colSize = gameConfig.getGameLevel().getColSize();

        // then
        assertThat(testColSize).isEqualTo(colSize);
    }

    // Advanced
    @DisplayName("레벨에 맞는 보드의 지뢰 갯수를 확인한다.")
    @Test
    void getLandMineCount() {
        // given
        GameConfig gameConfig = new GameConfig(
                new Advanced(),
                new ConsoleInputHandler(),
                new ConsoleOutputHandler()
        );

        int testLandMineCount = 0;
        if (gameConfig.getGameLevel() instanceof VeryBeginner) {
            testLandMineCount = 2;
        }
        if (gameConfig.getGameLevel() instanceof Beginner) {
            testLandMineCount = 10;
        }
        if (gameConfig.getGameLevel() instanceof Middle) {
            testLandMineCount = 40;
        }
        if (gameConfig.getGameLevel() instanceof Advanced) {
            testLandMineCount = 99;
        }

        // when
        int landMineCount = gameConfig.getGameLevel().getLandMineCount();

        // then
        assertThat(testLandMineCount).isEqualTo(landMineCount);
    }

    @DisplayName("입력에 따른 행 변환이 의도에 맞게 되었는지 확인한다.")
    @Test
    void convertRowForm() {
        // given
        String userInput = "a5";
        int testRow = 4;
        BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

        // when
        int rowIndex = boardIndexConverter.getSelectedRowIndex(userInput);

        // then
        assertThat(testRow).isEqualTo(rowIndex);
    }

    @DisplayName("입력에 따른 열 변환이 의도에 맞게 되었는지 확인한다.")
    @Test
    void convertColForm() {
        // given
        String userInput = "a5";
        int testCol = 0;
        BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

        // when
        int colIndex = boardIndexConverter.getSelectedColIndex(userInput);

        // then
        assertThat(testCol).isEqualTo(colIndex);
    }

    @DisplayName("GameException 이 의도한 메시지를 보여주는지 확인한다.")
    @Test
    void checkGameException() {
        // given
        String testMessage = "test success";
        GameException gameException = new GameException(testMessage);

        // when
        String message = gameException.getMessage();

        // then
        assertThat(testMessage).isEqualTo(message);
    }
}
