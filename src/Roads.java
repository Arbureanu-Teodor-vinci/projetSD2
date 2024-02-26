import java.util.Objects;

public class Roads {

  private double longitude;
  private double latitude;

  public Roads(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
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
    return Double.compare(longitude, roads.longitude) == 0
        && Double.compare(latitude, roads.latitude) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(longitude, latitude);
  }
}