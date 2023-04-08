package helper;

import helper.model.CityManager;
import helper.model.Tour;

import java.util.function.Consumer;

@FunctionalInterface
public interface TSPSolver {
    Tour solve(CityManager cityManager, Consumer<Tour> tourConsumer);
}
