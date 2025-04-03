package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

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

            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
            List<StudyCafePass> passCandidates = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
            outputHandler.showPassListForSelection(passCandidates);
            StudyCafePass selectedPass = inputHandler.getSelectPass(passCandidates);

            StudyCafeLockerPass lockerPass = selectLockerPass(selectedPass);

            outputHandler.showPassOrderSummary(selectedPass, lockerPass);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeLockerPass selectLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) {
            return null; // FIXED 일때만 사물함 사용하니까
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        StudyCafeLockerPass lockerPassCandidate = lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);

        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return lockerPassCandidate;
            }
        }

        return null;
    }
}
