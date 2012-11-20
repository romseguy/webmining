import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    // Récupération des données
		Extractor e = new Extractor();
		ArrayList<Episode> eps = e.getEpisodes("http://germain-forestier.info/cours/bi/tp/episodes.html");
		
		// Traitement des données
		MyAnalyzer a = new MyAnalyzer();
		
		int nombre_episode = eps.size();

		for (int i = 0; i != eps.size(); i++) {
	        eps.get(i).setStemmedSummary(a.stem(eps.get(i).getSummary()));
		}
		
		// Calcul du vecteur de fréquence pour chaque épisode
		for (int i = 0; i != eps.size(); i++) {
			eps.get(i).setFreqVector(TextComparator.createSet(eps.get(i).getStemmedSummary()));
		}
		
		// Remplissage de la matrice de similarité
		double[][] mat_simil = new double[nombre_episode][nombre_episode];
		
		for (int i = 0; i != eps.size(); i++)
			for (int j = 0; j != eps.size(); j++)
				mat_simil[i][j] = TextComparator.cosine_similarity(eps.get(i).getFreqVector(), eps.get(j).getFreqVector());

		// Histogramme
		System.out.println("Numéro de l'épisode ?");
		Scanner userInput = new Scanner(System.in);
		int num_episode = userInput.nextInt();

		// Map<épisode, similarité>
		Map<Integer, Double> sim_episode = new HashMap<Integer, Double>();

		for (int j = 0; j != eps.size(); j++)
		{
			if (j == num_episode)
				continue;
			
			sim_episode.put(j, mat_simil[num_episode][j]);
		}
		
		// Tri
		Comparator<Double> valueComparator = new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		};
		MapValueComparator<Integer, Double> mapComparator = new MapValueComparator<Integer, Double>(sim_episode, valueComparator);
		Map<Integer, Double> sortedOnValuesMap = new TreeMap<Integer, Double>(mapComparator);
		sortedOnValuesMap.putAll(sim_episode);

		// Affichage
		for (Map.Entry<Integer, Double> entry : sortedOnValuesMap.entrySet()) {
		     System.out.println("Episode " + entry.getKey() + " : " + entry.getValue());
		}
	}
}