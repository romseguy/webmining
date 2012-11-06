import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Extractor e = new Extractor();
		e.getEpisodes("http://germain-forestier.info/cours/bi/tp/episodes.html");
		Episode[] eps = e.instanciateEpisodes();
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	}

}
