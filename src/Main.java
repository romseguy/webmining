import java.io.IOException;


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
	}

}