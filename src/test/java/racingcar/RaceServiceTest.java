package racingcar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RaceServiceTest {
    @ParameterizedTest
    @MethodSource("provideNamesAndExpected")
    void Name_List_Split(String input, List<String> names) {
        assertThat(
                new Race(input).getParticipants().foreach(item -> {
                    assertThat(item).containsExactlyElementsOf(names);
                });
        );
    }

    private static Stream<Arguments> provideNamesAndExpected() {
        return Stream.of(
                Arguments.of("hogun", List.of("hogun")),
                Arguments.of("pobi,woni,jun", List.of("pobi", "woni", "jun")),
                Arguments.of("hogun,@.@,^_^,   ", List.of("hogun", "@.@", "^_^", "   "))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"hogun,gamja,", "", "longName,hogun", "hogun,hogun"})
    void Name_List_Split_Exception(String input) {
        assertThatThrownBy(() -> new Race())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Take_Race_Turn() {
        //Given
        Race race = new Race("pobi,woni,jun");
    }

    @Test
    void Get_Race_Result() {

    }
}
