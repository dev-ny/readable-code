package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

// 모든 지뢰찾기 게임 로직을 여기에 둘 것임
public class Minesweeper implements GameInitializable, GameRunnable {
    // 중요한 문자열, 숫자야. 유지보수할때 잘 봐야해! 할 수 있는 것 = 매직넘버, 매직스트링
//    private static final int BOARD_ROW_SIZE = 8;
//    private static final int BOARD_COL_SIZE = 10;
    // 상수 컨벤션 = 대문자와 언더스코어로 이루어져 있게 해야함


    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    // 입출력에 대한건 여기서!
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outputHandler.showGameStartCommand();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

//                String cellInput = getCellInputFromUser();
                CellPosition cellInput = getCellInputFromUser();
                UserAction userAction = getUserActionInputFromUser();

                actOnCell(cellInput, userAction);
            } catch (GameException e) { // 의도적인 Exception
                outputHandler.showExceptionMessage(e);

            } catch (Exception e) { // 예상하지 못한 Exception
                outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
//                e.printStackTrace(); // 실무에서는 Antipattern 실무에서는 log 시스템에서 log 를 남기고 별도의 조치를 취함
            }
        }
    }

    private void actOnCell(CellPosition cellPosition, UserAction userAction) {

        if (doesUserChooseToPlantFlag(userAction)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();

            return;
        }

        if (doesUserChooseToOpenCell(userAction)) {
            if (gameBoard.isLandMineCellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(cellPosition);
            checkIfGameIsOver();
            return;
        }

        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(UserAction userAction) {
        return userAction == UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userAction) {
        return userAction == UserAction.FLAG;
    }

    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentFOrUserAction();
        return inputHandler.getUserActionFromUser() ;
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser(); // 이때, index 로서의 기능은 할 수 있게 함.
        // 보드 길이에 따른 Position 검증은 GameBoard 에!
        // 한군데서 하면 되는데 너무 잘게 쪼개는거 아닌가요? -> 그렇게 느낄 수 있겠지만 책임을 조금 분리!
        // 보드가 있는 곳에서 자연스럽게 검증을 해보자!
        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 좌표를 선택하셨습니다.");
        }
        return cellPosition;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) { // 게임 이긴 것
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

//    private boolean isAllCellOpened() {
//        boolean isAllOpened = true;
//        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
//            for (int col = 0; col < BOARD_COL_SIZE; col++) {
//                if (BOARD[row][col].equals(CLOSED_CELL_SIGN)) { // 네모 박스가 하나라도 있는지 확인
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;
//    }
}
