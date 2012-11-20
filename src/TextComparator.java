import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class TextComparator {

	public static void main(String[] args) {
		String text1 = "Julie loves me more than Linda loves me";
		String text2 = "Jane likes me more than Julie loves me";
		System.out.println(cosine_similarity(createSet(text1), createSet(text2)));
	}

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
}