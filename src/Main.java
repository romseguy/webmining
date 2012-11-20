import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    // R�cup�ration des donn�es
		Extractor e = new Extractor();
		ArrayList<Episode> eps = e.getEpisodes("http://germain-forestier.info/cours/bi/tp/episodes.html");
		
		// Traitement des donn�es
		MyAnalyzer a = new MyAnalyzer();
		
		int nombre_episode = eps.size();

		for (int i = 0; i != eps.size(); i++) {
	        eps.get(i).setStemmedSummary(a.stem(eps.get(i).getSummary()));
		}
		
		// Calcul du vecteur de fr�quence pour chaque �pisode
		for (int i = 0; i != eps.size(); i++) {
			eps.get(i).setFreqVector(TextComparator.createSet(eps.get(i).getStemmedSummary()));
		}
		
		// Remplissage de la matrice de similarit�
		double[][] mat_simil = new double[nombre_episode][nombre_episode];
		
		for (int i = 0; i != eps.size(); i++)
			for (int j = 0; j != eps.size(); j++)
				mat_simil[i][j] = TextComparator.cosine_similarity(eps.get(i).getFreqVector(), eps.get(j).getFreqVector());

		// Histogramme de similarit� des �pisodes par rapport au num�ro d'un �pisode donn�
		System.out.println("Num�ro de l'�pisode ?");
		Scanner userInput = new Scanner(System.in);
		Map<Integer, Double> similEpisodeMap = TextComparator.getSimilarities(userInput.nextInt(), mat_simil);

		// Affichage
		for (Map.Entry<Integer, Double> entry : similEpisodeMap.entrySet()) {
		     System.out.println("Episode " + entry.getKey() + " : " + entry.getValue());
		}
	}
}