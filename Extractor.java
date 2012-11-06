import java.util.regex.Pattern;


public class Extractor {
	StringBuilder episodes;
	
	public String getEpisodes() {
		URL oracle = new URL("http://germain-forestier.info/cours/bi/tp/episodes.html");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		
		episodes = new StringBuilder("");
		
		while ((inputLine = in.readLine()) != null) {		
			episodes.append(inputLine);
		}
		
		return output;
	}
	
	public Episode[] instanciateEpisodes() {
		Pattern p = Pattern.compile("<a.+itemprop=\"name\">([^<]+)</a>");
		Matcher matcher = Pattern.matcher(episodes.toString());
		
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
