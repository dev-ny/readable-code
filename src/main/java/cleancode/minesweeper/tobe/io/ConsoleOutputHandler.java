package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;
import cleancode.minesweeper.tobe.cell.CellSnapshot;
import cleancode.minesweeper.tobe.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {
    private static final String LAND_MINE_SIGN = "☼";
    protected static final String EMPTY_SIGN = "■";
    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGN = "□";

    @Override
    public void showGameStartCommand() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {
        String alphabets = generateColAlphabets(board);

        System.out.println("    " + alphabets);

        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1); // 2자리 수 이상일때 col 정렬 맞출 수 있도록 함
            for (int col = 0; col < board.getColSize(); col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                CellSnapshot snapShot = board.getSnapshot(cellPosition);
                String cellSign = decideCellSignFrom(snapShot);
                System.out.print(cellSign + " ");
//                System.out.print(board.getSign(cellPosition) + " "); // 여기는 getter 를 안쓰는게 이상해 // 내가 여기에 보드를 그릴테니 cell 내용을 줘!
            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignFrom(CellSnapshot snapShot) {
        CellSnapshotStatus status = snapShot.getStatus();
        if (status == CellSnapshotStatus.EMPTY) {
            return EMPTY_SIGN;
        }
        if (status == CellSnapshotStatus.FLAG) {
            return FLAG_SIGN;
        }
        if (status == CellSnapshotStatus.LAND_MINE) {
            return LAND_MINE_SIGN;
        }
        if (status == CellSnapshotStatus.NUMBER) {
            return String.valueOf(snapShot.getNearbyLandMineCount());
        }
        if (status == CellSnapshotStatus.UNCHECKED) {
            return UNCHECKED_SIGN;
        }
        throw new IllegalArgumentException("확인할 수 없는 셀입니다."); // 개발자가 확인해야 하는 Exception // 사용자가 확인해야하는 Exception = Game Exception
    }

    private static String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())  // 0 부터 colSize 까지 range 만듦
                .mapToObj(index -> (char)('a' + index)) // 'a' + 0 = 'a'
                .map(c -> c.toString())
                .toList(); // 알파벳 묶음이 될 것임
        String joiningAlphabets = String.join(" ", alphabets); // 알파벳들을 연결해줘
        return joiningAlphabets;
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showCommentFOrUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void showSimpleMessage(String message) {
        System.out.println(message);
    }
}
