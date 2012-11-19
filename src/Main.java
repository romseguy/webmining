import java.io.IOException;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    // Récupération des données
		Extractor e = new Extractor();
		Episode[] eps = e.getEpisodes("http://germain-forestier.info/cours/bi/tp/episodes.html");
		
		// Traitement des données
		MyAnalyzer a = new MyAnalyzer();

		for (int i = 0; i < eps.length; i++)
	        eps[i].setStemmedSummary(a.stem(eps[i].getSummary()));
	}
}
