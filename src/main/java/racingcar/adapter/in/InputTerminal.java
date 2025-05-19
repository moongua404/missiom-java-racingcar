package racingcar.adapter.in;

import camp.nextstep.edu.missionutils.Console;
import racingcar.application.port.inport.GetGamePropertyUseCase;

public class InputTerminal implements GetGamePropertyUseCase {
    public String getParticipantName() {
        return Console.readLine();
    }
    public int getTurnCount() {
        return Integer.parseInt(Console.readLine());
    }
}
