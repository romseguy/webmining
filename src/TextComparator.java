import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class TextComparator {

	public static Map<String, Double> createSet(String text) {
		Map<String, Double> match = new HashMap<String, Double>();
		StringTokenizer tk = new StringTokenizer(text, " ");
		while (tk.hasMoreElements()) {
			String s = tk.nextToken().toLowerCase();
			if (match.get(s) == null) {
				match.put(s, 1.0);
			} else {
				match.put(s, match.get(s) + 1);
			}
		}
		return match;
	}

	public static double cosine_similarity(Map<String, Double> v1, Map<String, Double> v2) {
		Set<String> both = new HashSet<String>();
		both.addAll(v1.keySet());
		both.retainAll(v2.keySet());
		double sclar = 0, norm1 = 0, norm2 = 0;
		for (String k : both)
			sclar += v1.get(k) * v2.get(k);
		for (String k : v1.keySet())
			norm1 += v1.get(k) * v1.get(k);
		for (String k : v2.keySet())
			norm2 += v2.get(k) * v2.get(k);
		return sclar / Math.sqrt(norm1 * norm2);
	}
	
	// donne la map (episode, similarité) par rapport à un episode donné
	public static Map<Integer, Double> getSimilarities(int num_episode, double[][] mat_simil) {
		Map<Integer, Double> sim_episode = new HashMap<Integer, Double>();

		// remplissage de la map
		for (int j = 0; j != mat_simil.length; j++)
		{
			if (j == num_episode)
				continue;
			
			sim_episode.put(j, mat_simil[num_episode][j]);
		}
		
		// tri ordre croissant par rapport à la similarité
		Comparator<Double> valueComparator = new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return o2.compareTo(o1);
			}
		};
		MapValueComparator<Integer, Double> mapComparator = new MapValueComparator<Integer, Double>(sim_episode, valueComparator);
		Map<Integer, Double> sortedOnValuesMap = new TreeMap<Integer, Double>(mapComparator);
		sortedOnValuesMap.putAll(sim_episode);
		
		return sortedOnValuesMap;
	}
}