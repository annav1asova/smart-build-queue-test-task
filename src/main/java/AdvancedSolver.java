import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedSolver implements Solver {

  @Override
  public DayPlan solve(List<Train> trains) {
    final long[] times = trains.stream()
        .flatMap(
            train -> Stream.of(train.start(), train.start() + train.duration()))
        .mapToLong(t -> t)
        .distinct()
        .toArray();

    Arrays.sort(times);

    final List<Train> compactTrains = trains.stream()
        .map(train -> {
          final long newStart = Arrays.binarySearch(times, train.start());
          final long newEnd = Arrays.binarySearch(times, train.start() + train.duration());
          return new Train(
              train.id(),
              newStart,
              newEnd - newStart,
              train.reward()
          );
        }).collect(Collectors.toList());

    final List<List<Train>> timeToTrains = new ArrayList<>(2 * trains.size());
    for (int i = 0; i < 2 * trains.size(); ++i) {
      timeToTrains.add(new ArrayList<>());
    }
    compactTrains.forEach(
        train -> timeToTrains.get((int) (train.start() + train.duration() - 1)).add(train)
    );

    final long[] bestReward = new long[2 * trains.size()];
    Arrays.fill(bestReward, 0);

    final Train[] lastTrain = new Train[2 * trains.size()];
    Arrays.fill(lastTrain, null);

    for (int i = 0; i < 2 * trains.size(); ++i) {
      if (i > 0) {
        bestReward[i] = bestReward[i - 1];
        lastTrain[i] = lastTrain[i - 1];
      }

      timeToTrains.get(i).forEach(
          train -> {
            final int end = (int) (train.start() + train.duration() - 1);
            final long reward = train.reward();

            //init
            if (reward > bestReward[end]) {
              bestReward[end] = reward;
              lastTrain[end] = train;
            }

            //process this train
            final int start = (int) train.start();
            if (start - 1 >= 0) {
              final long canReward = bestReward[start - 1] + reward;
              if (canReward > bestReward[end]) {
                bestReward[end] = canReward;
                lastTrain[end] = train;
              }
            }
          }
      );
    }

    final List<Long> bestTrains = new ArrayList<>();
    Train last = lastTrain[bestReward.length - 1];
    while (last != null) {
      bestTrains.add(last.id());
      if (last.start() - 1 >= 0) {
        last = lastTrain[(int) (last.start() - 1)];
      } else {
        last = null;
      }
    }

    Collections.sort(bestTrains);
    return new DayPlan(bestReward[bestReward.length - 1], bestTrains);
  }
}
