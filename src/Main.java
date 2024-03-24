import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File cities = new File("cities.txt");
		File roads = new File("roads.txt");
		Graph graph = new Graph(cities, roads);
		graph.calculerItineraireMinimisantNombreRoutes("Berlin", "Madrid");
		System.out.println("--------------------------");
		graph.calculerItineraireMinimisantKm("Berlin", "Madrid");
	}

}
