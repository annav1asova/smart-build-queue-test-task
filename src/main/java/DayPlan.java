import java.util.List;

public class DayPlan {
  // планируемый заработок
  private long income;
  // номера поездов
  private List <Long> trains;

  public DayPlan(long income, List<Long> trains) {
    this.income = income;
    this.trains = trains;
  }

  public long getIncome() {
    return income;
  }

  public List<Long> getTrains() {
    return trains;
  }
}
