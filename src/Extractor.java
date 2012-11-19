import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Extractor {
	
	public Episode[] getEpisodes(String url) throws IOException {
	    // lecture de l'url
		URL oracle = new URL(url);
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		
		StringBuilder episodes = new StringBuilder("");
		
		// contenu stocké dans episodes
		while ((inputLine = in.readLine()) != null) {		
			episodes.append(inputLine + "\n");
		}
		
		// recherche par regex du titre et du résumé depuis episodes
		Pattern p = Pattern.compile("<a.+itemprop=\"name\">([^<]+)</a>.+\n.+itemprop=\"description\">([^<]+)</div>\n");
        Matcher matcher = p.matcher(episodes.toString());

        Episode[] eps = new Episode[100];
        int i = 0;

        while (matcher.find()) {
            eps[i] = new Episode(matcher.group(1), matcher.group(2)); 
            i++;
        }
        
        return eps;
	}
}
