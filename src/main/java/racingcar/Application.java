package racingcar;

import racingcar.adapter.in.InputTerminal;
import racingcar.adapter.in.RandomLibrary;
import racingcar.adapter.out.OutputTerminal;
import racingcar.application.service.RacingService;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        RacingService racingService = new RacingService(
                new InputTerminal(),
                new RandomLibrary(),
                new OutputTerminal()
        );

        racingService.run();
    }
}
