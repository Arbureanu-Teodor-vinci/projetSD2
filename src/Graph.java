import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  private Map<Cities, Set<Roads>> mapCities;

  private Map<Integer,Cities> mapCitiesById;

  public Graph(File cities, File roads) throws FileNotFoundException {
    Scanner scCities = new Scanner(cities);
    Scanner scRoads = new Scanner(roads);

    this.mapCities = new HashMap<>();
    this.mapCitiesById = new HashMap<>();

    while (scCities.hasNextLine()){
       String[] data = scCities.nextLine().split(",");
       int id = Integer.parseInt(data[0]);
       String name = data[1];
       double lat = Double.parseDouble(data[2]);
       double lon = Double.parseDouble(data[3]);

       this.mapCitiesById.put(id,new Cities(id,name,lat,lon));
       this.mapCities.put(new Cities(id,name,lat,lon),new HashSet<Roads>());
    }

    while (scRoads.hasNextLine()){
      String[] data = scRoads.nextLine().split(",");
      int id1 = Integer.parseInt(data[0]);
      int id2 = Integer.parseInt(data[1]);

      Roads road1 = new Roads(id1,id2);
      Roads raod2= new Roads(id2,id1);

      this.mapCities.get(findCityById(id1)).add(road1);
      this.mapCities.get(findCityById(id2)).add(raod2);

    }
  }



  public void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee) {
    // TODO
    Map<String, String> parentMap = new HashMap<>();
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();

    queue.add(depart);
    visited.add(depart);

    while (!queue.isEmpty()) {
      String current = queue.poll();
      if (current.equals(arrivee)) {
        break;
      }
      Cities city = findCityByName(current);
      for (Roads road : mapCities.get(city)) {
        Cities next = findCityById(road.getIdDestination());
        if (!visited.contains(next.getNom())) {
          queue.add(next.getNom());
          visited.add(next.getNom());
          parentMap.put(next.getNom(), current);
        }
      }

    }
    if (!parentMap.containsKey(arrivee)) {
      System.out.println("Pas de route de " + depart + " à " + arrivee);
      return;
    }

    LinkedList<Cities> path = new LinkedList<>();
    for (String city = arrivee; city != null; city = parentMap.get(city)) {
      path.addFirst(findCityByName(city));
    }
    StringBuilder roads = new StringBuilder();
double sommeDistance = 0;
int count = 0;
for (Cities city : path) {
  if(path.indexOf(city) != path.size()-1){
    double dis = Util.distance(city.getLongitude(),city.getLatitude(),path.get(path.indexOf(city)+1).getLongitude(),path.get(path.indexOf(city)+1).getLatitude());
    sommeDistance += dis;
    count+=1;
    roads.append(city.getNom()).append(" -> ").append(path.get(path.indexOf(city) + 1).getNom())
        .append("  (").append(Math.round(dis * 100.0) / 100.0).append(" km)").append(" \n");
    }

    }
    String response = "Trajet de " + depart + " à " + arrivee + ": "+ count + " routes et " + sommeDistance + " km";
    System.out.println(response);
    System.out.println(roads);

  }




  public void calculerItineraireMinimisantKm(String depart, String arrivee) {
    Map<String, Double> shortestDistances = new HashMap<>();
    Map<String, String> previousCities = new HashMap<>();
    //A faire en TreeSet
    PriorityQueue<Cities> queue = new PriorityQueue<>(
        Comparator.comparingDouble(city -> shortestDistances.getOrDefault(city.getNom(), Double.MAX_VALUE)));

    shortestDistances.put(depart, 0.0);
    queue.add(findCityByName(depart));

    while (!queue.isEmpty()) {
      Cities current = queue.poll();
      for (Roads road : mapCities.get(current)) {
        Cities next = findCityById(road.getIdDestination());
        double newDistance = shortestDistances.get(current.getNom()) + Util.distance(current.getLongitude(), current.getLatitude(), next.getLongitude(), next.getLatitude());
        if (newDistance < shortestDistances.getOrDefault(next.getNom(), Double.MAX_VALUE)) {
          shortestDistances.put(next.getNom(), newDistance);
          previousCities.put(next.getNom(), current.getNom());
          queue.add(next);
        }
      }
    }

    if (!previousCities.containsKey(arrivee)) {
      System.out.println("Pas de routes de " + depart + " à " + arrivee);
      return;
    }

    LinkedList<String> path = new LinkedList<>();
    for (String city = arrivee; city != null; city = previousCities.get(city)) {
      path.addFirst(city);
    }


    StringBuilder roads = new StringBuilder();
    double totalDistance = 0;
    int count = 0;
    for (int i = 0; i < path.size() - 1; i++) {
      double distance = Util.distance(findCityByName(path.get(i)).getLongitude(), findCityByName(path.get(i)).getLatitude(), findCityByName(path.get(i + 1)).getLongitude(), findCityByName(path.get(i + 1)).getLatitude());
      totalDistance += distance;
      count += 1;
      roads.append(path.get(i)).append(" -> ").append(path.get(i + 1)).append("  (").append(Math.round(distance * 100.0) / 100.0).append(" km)").append(" \n");
    }

    String response = "Trajet de " + depart + " à " + arrivee + ": " + count + " routes et " + Math.round(totalDistance * 100.0) / 100.0 + " km";
    System.out.println(response);
    System.out.println(roads);
  }


  private Cities findCityById(int id) {
    return mapCitiesById.get(id);
  }

  private Cities findCityByName(String name) {
    for (Cities city : mapCities.keySet()) {
      if (city.getNom().equals(name)) {
        return city;
      }
    }
    return null;
  }
}
