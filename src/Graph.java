import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Graph {

  private Map<Citiy, Set<Roads>> mapCities;

  private Map<Integer, Citiy> mapCitiesById;

  private Map<String, Citiy> mapCitiesByName;

  public Graph(File cities, File roads) throws FileNotFoundException {
    Scanner scCities = new Scanner(cities);
    Scanner scRoads = new Scanner(roads);

    this.mapCities = new HashMap<>();
    this.mapCitiesById = new HashMap<>();
    this.mapCitiesByName = new HashMap<>();

    while (scCities.hasNextLine()){
       String[] data = scCities.nextLine().split(",");
       int id = Integer.parseInt(data[0]);
       String name = data[1];
       double lat = Double.parseDouble(data[2]);
       double lon = Double.parseDouble(data[3]);

       this.mapCitiesById.put(id,new Citiy(id,name,lat,lon));
       this.mapCities.put(new Citiy(id,name,lat,lon),new HashSet<Roads>());
       this.mapCitiesByName.put(name,new Citiy(id,name,lat,lon));
    }

    while (scRoads.hasNextLine()){
      String[] data = scRoads.nextLine().split(",");
      int id1 = Integer.parseInt(data[0]);
      int id2 = Integer.parseInt(data[1]);

      Roads road1 = new Roads(id1,id2);
      Roads road2= new Roads(id2,id1);

      this.mapCities.get(findCityById(id1)).add(road1);
      this.mapCities.get(findCityById(id2)).add(road2);

    }
  }



  public void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee) {
    // TODO
    Map<Citiy, Citiy> destinationsCityMap = new HashMap<>();
    Set<Citiy> visitedCities = new HashSet<>();
    Queue<Citiy> queueToVisit = new LinkedList<>();

    Citiy departCity = findCityByName(depart);
    Citiy arriveeCity = findCityByName(arrivee);

    queueToVisit.add(departCity);
    visitedCities.add(departCity);

    while (!queueToVisit.isEmpty()) {
      Citiy currentCity = queueToVisit.poll();
      if (currentCity.equals(arriveeCity)) {
        break;
      }
      for (Roads road : mapCities.get(currentCity)) {
        Citiy aCityDestination = findCityById(road.getIdDestination());
        if (!visitedCities.contains(aCityDestination)) {
          queueToVisit.add(aCityDestination);
          visitedCities.add(aCityDestination);
          destinationsCityMap.put(aCityDestination, currentCity);
        }
      }

    }

    printPath(arriveeCity, destinationsCityMap, depart, arrivee);

  }




  public void calculerItineraireMinimisantKm(String depart, String arrivee) {
    Map<Citiy, Double> citiesShortestPathMap = new HashMap<>();
    Map<Citiy, Citiy> destinationsCityMap = new HashMap<>();
    TreeSet<Citiy> queue = new TreeSet<>(
        Comparator.comparingDouble(city -> citiesShortestPathMap.getOrDefault(city, Double.MAX_VALUE)));

    Citiy departCity = findCityByName(depart);
    Citiy arriveeCity = findCityByName(arrivee);

    citiesShortestPathMap.put(departCity, 0.0);
    queue.add(departCity);

    while (!queue.isEmpty()) {
      Citiy currentCity = queue.pollFirst();
      for (Roads road : mapCities.get(currentCity)) {
        Citiy next = findCityById(road.getIdDestination());
        double newDistance = citiesShortestPathMap.get(currentCity) + Util.distance(currentCity.getLongitude(), currentCity.getLatitude(), next.getLongitude(), next.getLatitude());
        if (newDistance < citiesShortestPathMap.getOrDefault(next, Double.MAX_VALUE)) {
          citiesShortestPathMap.put(next, newDistance);
          destinationsCityMap.put(next, currentCity);
          queue.add(next);
        }
      }
    }

    printPath(arriveeCity, destinationsCityMap, depart, arrivee);
  }


  private Citiy findCityById(int id) {
    return mapCitiesById.get(id);
  }

  private Citiy findCityByName(String name) {
    return mapCitiesByName.get(name);
  }

  //PRINT PATH
  private void printPath(Citiy arriveeCity, Map<Citiy, Citiy> destinationsCitiesMap, String depart, String arrivee) {

    if (!destinationsCitiesMap.containsKey(arriveeCity)) {
      System.out.println("Pas de routes de " + depart + " à " + arrivee);
      return;
    }

    LinkedList<Citiy> path = new LinkedList<>();
    for (Citiy city = arriveeCity; city != null; city = destinationsCitiesMap.get(city)) {
      path.addFirst(city);
    }

    StringBuilder roads = new StringBuilder();
    double summDistance = 0;
    int count = 0;
    for (int i = 0; i < path.size() - 1; i++) {
      double distance = Util.distance(path.get(i).getLongitude(), path.get(i).getLatitude(), path.get(i + 1).getLongitude(), path.get(i + 1).getLatitude());
        summDistance += distance;
        count+=1;
        roads.append(path.get(i).getNom()).append(" -> ").append(path.get(i + 1).getNom())
            .append("  (").append(Math.round(distance * 100.0) / 100.0).append(" km)").append(" \n");
    }
    String response = "Trajet de " + depart + " à " + arrivee + ": "+ count + " routes et " + summDistance + " km";
    System.out.println(response);
    System.out.println(roads);

  }
}
