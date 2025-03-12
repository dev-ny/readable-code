package cleancode.studycafe.asis;

import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.asis.io.InputHandler;
import cleancode.studycafe.asis.io.OutputHandler;
import cleancode.studycafe.asis.io.StudyCafeFileHandler;
import cleancode.studycafe.asis.model.StudyCafeLockerPass;
import cleancode.studycafe.asis.model.StudyCafePass;
import cleancode.studycafe.asis.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();



            if (isPassTypeHourly(studyCafePassType)) {
                StudyCafePass selectedPass = getPassTypeInputFromUser(StudyCafePassType.HOURLY);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }

            if (isPassTypeWeekly(studyCafePassType)) {
                StudyCafePass selectedPass = getPassTypeInputFromUser(StudyCafePassType.WEEKLY);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }

            if (isPassTypeFixed(studyCafePassType)) {
                StudyCafePass selectedPass = getPassTypeInputFromUser(StudyCafePassType.FIXED);
                StudyCafeLockerPass lockerPass = getSelectedLockerPass(selectedPass); // 고정석 가져오기

                boolean lockerSelection = doesUserChooseToUseLocker(lockerPass);

                showAllInfo(selectedPass, lockerPass, lockerSelection);
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private boolean doesUserChooseToUseLocker(StudyCafeLockerPass lockerPass) {
        boolean lockerSelection = false;
        if (lockerPass != null) {
            lockerSelection = getUserUseTheLocker(lockerPass);
        }
        return lockerSelection;
    }

    private void showAllInfo(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass, boolean lockerSelection) {
        if (lockerSelection) {
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } else {
            outputHandler.showPassOrderSummary(selectedPass, null);
        }
    }

    private boolean getUserUseTheLocker(StudyCafeLockerPass lockerPass) {
        boolean lockerSelection;
        outputHandler.askLockerPass(lockerPass);
        lockerSelection = inputHandler.getLockerSelection();
        return lockerSelection;
    }

    private StudyCafeLockerPass getSelectedLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler. readLockerPasses(); // FIXED,4,10000
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
                .filter(option -> option.isSelectedLockerPassType(selectedPass))
                .findFirst()
                .orElse(null);
        return lockerPass;
    }

    private static boolean isPassTypeFixed(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.FIXED;
    }

    private static boolean isPassTypeWeekly(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.WEEKLY;
    }

    private static boolean isPassTypeHourly(StudyCafePassType studyCafePassType) {
        return studyCafePassType == StudyCafePassType.HOURLY;
    }

    private void showPassListForSelection(List<StudyCafePass> hourlyPasses) {
        outputHandler.showPassListForSelection(hourlyPasses);
    }

    private StudyCafePass getPassTypeInputFromUser(StudyCafePassType passType) {
        List<StudyCafePass> passTypeList = getPassTypeList(passType);
        showPassListForSelection(passTypeList);
        StudyCafePass inputPassType = inputHandler.getSelectPass(passTypeList);
        return inputPassType;
    }

    private static List<StudyCafePass> getPassTypeList(StudyCafePassType passType) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == passType)
                .toList();
    }

}
