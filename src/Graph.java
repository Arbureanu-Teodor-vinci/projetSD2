import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  private Map<Cities, Set<Roads>> mapCitites;

  public Graph(File cities, File roads) throws FileNotFoundException {
    Scanner scCities = new Scanner(cities);
    Scanner scRoads = new Scanner(roads);

    this.mapCitites = new HashMap<>();
    while (scCities.hasNextLine()){
       String[] data = scCities.nextLine().split(",");
       int id = Integer.parseInt(data[0]);
       String name = data[1];
       double lat = Double.parseDouble(data[2]);
       double lon = Double.parseDouble(data[3]);

       this.mapCitites.put(new Cities(id,name,lat,lon),new HashSet<Roads>());
    }

    while (scRoads.hasNextLine()){
      String[] data = scRoads.nextLine().split(",");
      int id1 = Integer.parseInt(data[0]);
      int id2 = Integer.parseInt(data[1]);

      Roads road1 = new Roads(id1,id2);
      Roads raod2= new Roads(id2,id1);

      this.mapCitites.get(id1).add(road1);
      this.mapCitites.get(id2).add(raod2);

    }


  }

}
