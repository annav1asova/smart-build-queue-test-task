import java.util.ArrayList;
import java.util.List;

public class BruteForceSolver implements Solver {

  @Override
  public DayPlan solve(List<Train> trains) {
    //2^N
    long best = 0;
    List<Long> trainIds = new ArrayList<>();
    for (int mask = 0; mask < (1 << trains.size()); mask++) {
      List<Train> filtered = new ArrayList<>();
      long value = 0;
      List<Long> ids = new ArrayList<>();

      for (int j = 0; j < trains.size(); j++) {
        if ((mask & (1 << j)) > 0) {
          filtered.add(trains.get(j));
          value += trains.get(j).reward();
          ids.add(trains.get(j).id());
        }
      }

      boolean ok = true;
      for (Train x : filtered) {
        for (Train y : filtered) {
          if (x.id() == y.id()) continue;
          ok &= x.start() >= y.start() + y.duration() || y.start() >= x.start() + x.duration();
        }
      }
      if (ok && value > best) {
        best = value;
        trainIds = ids;
      }
    }
    return new DayPlan(best, trainIds);
  }
}
