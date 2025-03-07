package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.Cell;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler {
    public void showGameStartCommand() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void showBoard(GameBoard board) {
        String alphabets = generateColAlphabets(board);

        System.out.println("    " + alphabets);

        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1); // 2자리 수 이상일때 col 정렬 맞출 수 있도록 함
            for (int col = 0; col < board.getColSize(); col++) {
                System.out.print(board.getSign(row, col) + " "); // 여기는 getter 를 안쓰는게 이상해 // 내가 여기에 보드를 그릴테니 cell 내용을 줘!
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())  // 0 부터 colSize 까지 range 만듦
                .mapToObj(index -> (char)('a' + index)) // 'a' + 0 = 'a'
                .map(c -> c.toString())
                .toList(); // 알파벳 묶음이 될 것임
        String joiningAlphabets = String.join(" ", alphabets); // 알파벳들을 연결해줘
        return joiningAlphabets;
    }

    public void printGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    public void printGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    public void printCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    public void printCommentFOrUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    public void printExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    public void printSimpleMessage(String message) {
        System.out.println(message);
    }
}
