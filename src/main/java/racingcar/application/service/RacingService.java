package racingcar.application.service;

import java.util.stream.IntStream;
import racingcar.application.domain.Race;
import racingcar.application.domain.enums.MessageConstants;
import racingcar.application.port.inport.GetGamePropertyUseCase;
import racingcar.application.port.inport.GetRandomUseCase;
import racingcar.application.port.outport.RacePort;

public class RacingService {
    private final GetGamePropertyUseCase getGamePropertyUseCase;
    private final GetRandomUseCase getRandomUseCase;
    private final RacePort racePort;

    private final int MIN_RANDOM_VALUE = 0;
    private final int MAX_RANDOM_VALUE = 9;
    private final int THRESHOLD_VALUE = 4;

    public RacingService(GetGamePropertyUseCase getGamePropertyUseCase,
                         GetRandomUseCase getRandomUseCase, RacePort racePort) {
        this.getGamePropertyUseCase = getGamePropertyUseCase;
        this.getRandomUseCase = getRandomUseCase;
        this.racePort = racePort;
    }

    public void run() {
        Race race = setRace();
        int round = setRound();
        racePort.sendNewLine();

        execute(race, round);
        racePort.sendResult(MessageConstants.RESULT_GUIDE, race.getWinner());
    }

    private Race setRace() {
        racePort.sendMessage(MessageConstants.RACE_CAR_NAME_QUESTION);
        return new Race(getGamePropertyUseCase.getParticipantName(),
                () -> getRandomUseCase.getRandomInRange(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE) >= THRESHOLD_VALUE);
    }

    private int setRound() {
        racePort.sendMessage(MessageConstants.RACE_ROUND_QUESTION);
        return getGamePropertyUseCase.getTurnCount();
    }

    private void execute(Race race, int round) {
        racePort.sendMessage(MessageConstants.EXECUTING_GUIDE);
        IntStream.range(0, round).forEach(i -> {
            race.play();
            race.getStatus().forEach(car -> racePort.sendParticipantsData(car.name(), car.distance()));
            racePort.sendNewLine();
        });
    }
}
