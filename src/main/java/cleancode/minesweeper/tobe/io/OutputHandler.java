package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;

import java.util.List;
import java.util.stream.IntStream;

public interface OutputHandler {

    void showGameStartCommand();

    void showBoard(GameBoard board);

    void printGameWinningComment();

    void printGameLosingComment();

    void printCommentForSelectingCell();

    void printCommentFOrUserAction();

    void printExceptionMessage(GameException e);

    void printSimpleMessage(String message);
}
