import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnit4.class)
class SolverTest {
  private Random random;
  private Solver brute;
  private Solver advanced;

  @BeforeEach
  public void init() {
    random = new Random();
    brute = new BruteForceSolver();
    advanced = new AdvancedSolver();
  }

  @Test
  public void testBruteForce1() {
    final List<Train> trains = Arrays.asList(
        new Train(0, 0, 5, 5),
        new Train(1, 2, 2, 3),
        new Train(2, 5, 3, 4),
        new Train(3, 4, 4, 5)
    );
    final DayPlan plan = brute.solve(trains);
    assertEquals(plan.getIncome(), 9);
    assertEquals(plan.getTrains(), Arrays.asList(0L, 2L));
  }

  @Test
  public void testBruteForce2() {
    final List<Train> trains = Arrays.asList(
        new Train(0, 0, 123, 5),
        new Train(1, 50, 206, 12),
        new Train(2, 250, 50, 6),
        new Train(3, 5, 255, 3)
    );
    final DayPlan plan = brute.solve(trains);
    assertEquals(9, plan.getIncome(), 12);
    assertEquals(Collections.singletonList(1L), plan.getTrains());
  }

  @Test
  public void testWithBruteForce5() {
    final List<Train> trains = generate(5);
    final DayPlan expected = brute.solve(trains);
    final DayPlan actual = advanced.solve(trains);
    assertEquals(expected.getIncome(), actual.getIncome());
    assertEquals(actual.getIncome(), calculateIncome(trains, actual.getTrains()));
  }

  @Test
  public void testWithBruteForce10() {
    final List<Train> trains = generate(10);
    final DayPlan expected = brute.solve(trains);
    final DayPlan actual = advanced.solve(trains);
    assertEquals(expected.getIncome(), actual.getIncome());
    assertEquals(actual.getIncome(), calculateIncome(trains, actual.getTrains()));
  }

  @Test
  public void testWithBruteForce15() {
    final List<Train> trains = generate(15);
    final DayPlan expected = brute.solve(trains);
    final DayPlan actual = advanced.solve(trains);
    assertEquals(expected.getIncome(), actual.getIncome());
    assertEquals(actual.getIncome(), calculateIncome(trains, actual.getTrains()));
  }

  @Test
  public void testWithBruteForce20() {
    final List<Train> trains = generate(20);
    final DayPlan expected = brute.solve(trains);
    final DayPlan actual = advanced.solve(trains);
    assertEquals(expected.getIncome(), actual.getIncome());
    assertEquals(actual.getIncome(), calculateIncome(trains, actual.getTrains()));
  }

  private List<Train> generate(int n) {
    final List<Train> trains = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      trains.add(
          new Train(
              i,
              random.nextInt(100),
              random.nextInt(100) + 1,
              random.nextInt(100)
          )
      );
    }
    return trains;
  }

  private long calculateIncome(List<Train> trains, List<Long> ans) {
    final Map<Long, Train> index = trains.stream()
        .collect(Collectors.toMap(Train::id, train -> train));
    return ans.stream().mapToLong(id -> {
      final Train train = index.getOrDefault(id, null);
      return train == null ? 0 : train.reward();
    }).sum();
  }
}