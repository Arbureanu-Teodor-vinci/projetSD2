import java.util.Objects;

public class Cities {

  private int id;
  private String nom;
  private double longitude;
  private double latitude;

  public Cities(int id, String nom,double longitude,double latitude){
    this.id=id;
    this.nom=nom;
    this.longitude=longitude;
    this.latitude=latitude;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
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
    Cities cities = (Cities) o;
    return id == cities.id && Double.compare(longitude, cities.longitude) == 0
        && Double.compare(latitude, cities.latitude) == 0 && Objects.equals(nom,
        cities.nom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, longitude, latitude);
  }
}
