import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  private Map<Cities, Set<Roads>> mapCities;

  public Graph(File cities, File roads) throws FileNotFoundException {
    Scanner scCities = new Scanner(cities);
    Scanner scRoads = new Scanner(roads);

    this.mapCities = new HashMap<>();
    while (scCities.hasNextLine()){
       String[] data = scCities.nextLine().split(",");
       int id = Integer.parseInt(data[0]);
       String name = data[1];
       double lat = Double.parseDouble(data[2]);
       double lon = Double.parseDouble(data[3]);

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
      System.out.println("No route found from " + depart + " to " + arrivee);
      return;
    }

    LinkedList<String> path = new LinkedList<>();
    String response = "Shortest path from " + depart + " to " + arrivee + ": ";
    for (String city = arrivee; city != null; city = parentMap.get(city)) {
      path.addFirst(city);
    }
    System.out.println(response);

for (String city : path) {
  if(path.indexOf(city) != path.size()-1){
    System.out.print(city + " -> "+ path.get(path.indexOf(city)+1) + " \n");
    }

    }

  }

  public void calculerItineraireMinimisantKm(String depart, String arrivee) {
    // TODO
  }


  private Cities findCityById(int id) {
    for (Cities city : mapCities.keySet()) {
      if (city.getId() == id) {
        return city;
      }
    }
    return null;
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
