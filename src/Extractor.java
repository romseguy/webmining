import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extractor {
	StringBuilder episodes;
	
	public void getEpisodes() throws IOException {
		URL oracle = new URL("http://germain-forestier.info/cours/bi/tp/episodes.html");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		
		episodes = new StringBuilder("");
		
		while ((inputLine = in.readLine()) != null) {		
			episodes.append(inputLine);
		}
	}
	
	public void instanciateEpisodes() {
		Pattern p = Pattern.compile("<a.+itemprop=\"name\">([^<]+)</a>");
		Matcher matcher = p.matcher(episodes.toString());
		
		while (matcher.find()) {
			System.out.println(matcher.group(1));
		}
	}
}
