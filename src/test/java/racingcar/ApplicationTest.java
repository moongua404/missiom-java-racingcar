package racingcar;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output().trim()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Nested
    @DisplayName("Level 1")
    class Level1 {
        @Test
        @DisplayName("기본 기능 테스트")
        void basicTest() {
            Integer[] numbers = Stream.concat(
                    Stream.of(MOVING_FORWARD, STOP, STOP),
                    Collections.nCopies(12, MOVING_FORWARD).stream()
            ).toArray(Integer[]::new);
            assertRandomNumberInRangeTest(
                    () -> {
                        run("pobi,woni,jun", "5");
                        assertThat(output().trim()).contains(
                                "pobi : -----", "woni : ----", "jun : ----",
                                "최종 우승자 : pobi"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }

        @Test
        @DisplayName("랜덤 범위 테스트")
        void randomRangeTest() {
            Integer[] numbers = {0, 9, 1, 8, 2, 7, 3, 6, 4, 5};
            assertRandomNumberInRangeTest(
                    () -> {
                        run("loser,winer", "5");
                        assertThat(output().trim()).contains(
                                "loser : -", "winer : -----"
                        );
                        assertThat(output().trim()).doesNotContain(
                                "loser : --", "winer : ------"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)
            );
        }

        @Test
        @DisplayName("이름 길이 범위 테스트")
        void longNameTest() {
            assertSimpleTest(() -> {
                assertThatThrownBy(() -> runException("java,script", "1"))
                        .isInstanceOf(IllegalArgumentException.class);
            });
        }
    }

    @Nested
    @DisplayName("Level 2")
    class Level2 {
        @Test
        @DisplayName("단일 우승자 테스트")
        void singleWinnerTest() {
            Integer[] numbers = {MOVING_FORWARD, STOP, STOP};
            assertRandomNumberInRangeTest(
                    () -> {
                        run("pobi,woni,jun", "1");
                        assertThat(output().trim()).contains(
                                "최종 우승자 : pobi"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }

        @Test
        @DisplayName("공동 우승자(2명) 테스트")
        void doubleWinnerTest() {
            Integer[] numbers = {MOVING_FORWARD, MOVING_FORWARD, STOP};
            assertRandomNumberInRangeTest(
                    () -> {
                        run("pobi,woni,jun", "1");
                        assertThat(output().trim()).contains(
                                "최종 우승자 : pobi, woni"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }

        @Test
        @DisplayName("공동 우승자(3명) 테스트")
        void severalWinnerTest() {
            Integer[] numbers = {MOVING_FORWARD, MOVING_FORWARD, MOVING_FORWARD};
            assertRandomNumberInRangeTest(
                    () -> {
                        run("pobi,woni,jun", "1");
                        assertThat(output().trim()).contains(
                                "최종 우승자 : pobi, woni, jun"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }
    }

    @Nested
    @DisplayName("Level 3")
    class Level3 {
        @Test
        @DisplayName("많은 시도횟수 테스트")
        void veryBigTrialsTest() {
            Integer[] numbers = Collections.nCopies(200, MOVING_FORWARD).toArray(Integer[]::new);

            assertRandomNumberInRangeTest(
                    () -> {
                        run("hogun,park", "100");
                        assertThat(output().trim()).contains(
                                "hogun : ----------------------------------------------------------------------------------------------------");
                        assertThat(output().trim()).contains(
                                "park : ----------------------------------------------------------------------------------------------------");
                        assertThat(output().trim()).doesNotContain(
                                "park : -----------------------------------------------------------------------------------------------------");
                        assertThat(output().trim()).doesNotContain(
                                "park : -----------------------------------------------------------------------------------------------------");

                        assertThat(output().trim()).contains(
                                "최종 우승자 : hogun, park"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)
            );
        }

        @Test
        @DisplayName("공백 이름 테스트")
        void blankBetweenNameTest() {
            Integer[] numbers = {MOVING_FORWARD, STOP};
            assertRandomNumberInRangeTest(
                    () -> {
                        run("ja va,ji gi", "1");
                        assertThat(output().trim()).contains(
                                "최종 우승자 : ja va"
                        );
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }

        @Test
        @DisplayName("빈 입력 테스트 테스트")
        void emptyAnswerTest() {
            assertThatThrownBy(() -> run("", "1"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("빈 이름 테스트")
        void emptyNameTest() {
            Integer[] numbers = Stream.concat(
                    Stream.of(STOP, MOVING_FORWARD, STOP),
                    Collections.nCopies(12, MOVING_FORWARD).stream()
            ).toArray(Integer[]::new);
            assertRandomNumberInRangeTest(
                    () -> {
                        try {
                            run("gamja,,yy", "1");
                            assertThat(output().trim()).contains("최종 우승자 : yy");
                        } catch (Exception exception) {
                            assertThat(exception).isInstanceOf(IllegalArgumentException.class);
                        }
                    }, numbers[0], Arrays.copyOfRange(numbers, 1, numbers.length)

            );
        }

        @Test
        @DisplayName("음수 시도 횟수 테스트")
        void minusTrialTest() {
            assertThatThrownBy(() -> run("pobi,jun", "-1"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("이름 중복 테스트")
        void duplicatedPlayerTest() {
            assertThatThrownBy(() -> run("pobi,woni,pobi", "1"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}