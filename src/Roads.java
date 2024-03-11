import java.util.Objects;

public class Roads {

  private int idDepart;
  private int idDestination;

  public Roads(int idDepart, int idDestination) {
    this.idDepart = idDepart;
    this.idDestination = idDestination;
  }

  public int getIdDepart() {
    return idDepart;
  }

  public void setIdDepart(int idDepart) {
    this.idDepart = idDepart;
  }

  public int getIdDestination() {
    return idDestination;
  }

  public void setIdDestination(int idDestination) {
    this.idDestination = idDestination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Roads roads = (Roads) o;
    return idDepart == roads.idDepart && idDestination == roads.idDestination;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDepart, idDestination);
  }
}
