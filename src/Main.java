import java.io.IOException;
import java.util.ArrayList;

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

		for (int i = 0; i != eps.size(); i++) {
	        eps.get(i).setStemmedSummary(a.stem(eps.get(i).getSummary()));
	        System.out.println(a.stem(eps.get(i).getSummary()));
		}
	}
}
