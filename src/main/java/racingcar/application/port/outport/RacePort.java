package racingcar.application.port.outport;

import java.util.List;
import racingcar.application.domain.enums.MessageConstants;

public interface RacePort {
    void sendMessage(MessageConstants message);
    void sendParticipantsData(String name, int distance);
    void sendResult(MessageConstants message, List<String> result);
    void sendNewLine();
}
