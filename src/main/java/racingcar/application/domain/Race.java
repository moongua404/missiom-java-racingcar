package racingcar.application.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import racingcar.application.domain.dto.CarDto;

public class Race {
    final List<Car> cars;
    final Supplier<Boolean> movementSupplier;

    public Race(String carNames, Supplier<Boolean> movementSupplier) {
        cars = splitName(carNames).map(Car::new).toList();
        this.movementSupplier = movementSupplier;
    }

    private Stream<String> splitName(String line) {
        Set<String> nameSet = new HashSet<>();

        return Arrays.stream(line.split(","))
                .peek(name -> {
                    validateNameLength(name);
                    if (!nameSet.add(name)) {
                        throw new IllegalArgumentException("Duplicate name found: " + name);
                    }
                });
    }

    private void validateNameLength(String name) {
        if (name.isEmpty() || name.length() > 5) {
            throw new IllegalArgumentException(String.format("Invalid Name Length - %s, %d", name, name.length()));
        }
    }

    public List<CarDto> getStatus() {
        return cars.stream().map((car) -> new CarDto(car.getName(), car.getDistance())).toList();
    }

    public void play() {
        cars.forEach(car -> {
            if (movementSupplier.get()) {
                car.addDistance(1);
            }
        });
    }

    public List<String> getWinner() {
        int max = cars.stream()
                .map(Car::getDistance)
                .max(Integer::compareTo)
                .orElseThrow(IllegalStateException::new);

        return cars.stream()
                .filter(car -> car.getDistance() == max)
                .map(Car::getName)
                .toList();
    }
}
