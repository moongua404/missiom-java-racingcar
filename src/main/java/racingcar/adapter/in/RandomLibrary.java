package racingcar.adapter.in;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.application.port.inport.GetRandomUseCase;

public class RandomLibrary implements GetRandomUseCase {
    public int getRandomInRange(int min, int max) {
        return Randoms.pickNumberInRange(min, max);
    }
}
