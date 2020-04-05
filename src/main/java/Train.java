public class Train {
  private long id;
  private long start;
  private long duration;
  private long reward;

  public Train(long id, long start, long duration, long reward) {
    this.id = id;
    this.start = start;
    this.duration = duration;
    this.reward = reward;
  }

  public long id() {
    return id;
  }

  public long start() {
    return start;
  }

  public long duration() {
    return duration;
  }

  public long reward() {
    return reward;
  }
}
