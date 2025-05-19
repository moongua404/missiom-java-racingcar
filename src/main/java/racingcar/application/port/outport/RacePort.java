package racingcar.application.port.outport;

import java.util.List;
import racingcar.application.domain.enums.MessageConstants;

public interface RacePort {
    public void sendMessage(MessageConstants message);
    public void sendParticipantsData(String name, int distance);
    public void sendResult(MessageConstants message, List<String> result);
}
