import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

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
		System.out.println("Num�ro de l'�pisode � comparer ?");
		Scanner userInput = new Scanner(System.in);
		Map<Integer, Double> similEpisodeMap = TextComparator.getSimilarities(userInput.nextInt(), mat_simil);

		// Affichage
		for (Map.Entry<Integer, Double> entry : similEpisodeMap.entrySet()) {
		     int num_episode = entry.getKey() + 1;
		     System.out.println("Episode " + num_episode + " : " + entry.getValue() + " (" + Math.round(entry.getValue()*100) + "%)");
		}
		
		// Cr�ation du vecteur de fr�quence pour la saison enti�re
		Map<String, Double> seasonFreqVector = new HashMap<String, Double>();
		
		double max = 0.0;

		for (int i = 0; i != eps.size(); i++) {
			// on r�cup�re le vecteur de fr�quence de chaque �pisode
			Map<String, Double> freqVector = eps.get(i).getFreqVector();

			// pour chaque mot dans le vecteur
			for (Map.Entry<String, Double> entry : freqVector.entrySet()) {
				double value = entry.getValue();
				
				// on v�rifie si le mot n'est pas d�j� dans le vecteur saison
				if (seasonFreqVector.containsKey(entry.getKey()))
					// si c'est le cas la valeur � remplacer dans le vecteur saison sera la somme des 2
					value += seasonFreqVector.get(entry.getKey());
				
				if (value > max)
				    max = value;

				seasonFreqVector.put(entry.getKey(), value);
			}
		}
		
		// Cr�ation du fichier nuage de mots
		String file_name = "cloud.html";
		File cloud = new File(file_name);
		
		if (!cloud.exists())
		    cloud.createNewFile();
		
		FileWriter fstream = new FileWriter(file_name);
		BufferedWriter out = new BufferedWriter(fstream);
		double fontsize;
		
		// Contenu
		out.write("<html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"style.css\" /></head><body><h1>Nuage de mots de la saison complete</h1><ul>");
		
		// Taille 100% pour le nombre ayant le plus d'occurences
		for (Map.Entry<String, Double> entry : seasonFreqVector.entrySet()) {
		    fontsize = entry.getValue() / max * 100;
		    fontsize = (fontsize < 20)? 20 + fontsize : fontsize;
		    out.write("<li style=\"font-size: " + (int) fontsize + "%\">" + entry.getKey() + "</li>\n");
		}
		
		out.write("</ul></body></html>");
		out.close();
		
		System.out.println("Nuage de mots disponible pour la saison complete dans le fichier " + file_name);
	}
}