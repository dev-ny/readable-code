package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColIndex(String cellInput, int collSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, collSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= rowSize) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int collSize) { // 'a' // 알파벳이 j 까지만 대응되어 있음
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL; // a가 들어가면 0, b가 들어가면 1
        if (colIndex < 0 || colIndex >= collSize) { // a = 97 인데 cellInput 이 96 이면 알파벳이 아니기 때문 // 현재 대문자는 생각하지 않음.
            throw new GameException("잘못된 입력입니다.");
        }
        return colIndex;
    }
}
