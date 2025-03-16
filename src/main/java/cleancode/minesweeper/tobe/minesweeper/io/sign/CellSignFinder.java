package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

import java.util.List;

public class CellSignFinder {

    public static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvider(),
            new FlagCellSignProvider(),
            new LandMineCellSignProvider(),
            new NumberCellSignProvider(),
            new UncheckedCellSignProvider()
    );

    public String findCellSignFrom(CellSnapshot snapshot) {
        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider -> provider.supports(snapshot)) // provider 의 supports 가 현재 snapShot 과 같은가? true 인거 하나 나옴
                .findFirst()
                .map(provider -> provider.provide(snapshot)) // 문양 제공해줘 = 문양 뽑아냄
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다.")); // 혹시 없는 경우는 예외 던짐
    }
}
