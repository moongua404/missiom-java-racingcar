package racingcar.application.domain.enums;

public enum MessageConstants {
    RACE_CAR_NAME_QUESTION("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)"),
    RACE_ROUND_QUESTION("시도할 회수는 몇회인가요?"),
    EXECUTING_GUIDE("실행 결과"),
    RESULT_GUIDE("최종 우승자 : %s");

    private final String message;
    MessageConstants(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
