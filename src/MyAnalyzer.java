import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseTokenizer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

class MyAnalyzer extends Analyzer {
    final Set<String> stopWords;

    public MyAnalyzer() {
        String[] abc = { "when", "while", "who", "thi", "up", "have",
                "don", "ha", "hi", "him", "so", "out", "an", "that", "is",
                "in", "the", "he", "she", "her", "s", "i", "m", "t",
                "after", "from", "all", "can", "do", "which", "doesn", "go",
                "a", "to", "and", "of", "on", "it", "with", "be", "get", "for" };
        stopWords = new HashSet<String>(Arrays.asList(abc));
    }

    public final TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream ts = new LowerCaseTokenizer(Version.LUCENE_36, reader);
        ts = new StandardFilter(Version.LUCENE_36, ts);
        ts = new PorterStemFilter(ts);
        ts = new StopFilter(Version.LUCENE_36, ts, stopWords);
        
        return ts;
    }
    
    public String stem(String text) throws IOException {
        Reader reader = new StringReader(text);
        TokenStream ts = this.tokenStream(text, reader);
        boolean hasnext = ts.incrementToken();
        StringBuffer stemmedText = new StringBuffer();
        
        while (hasnext) {
            CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
            stemmedText.append(ta.toString() + " ");
            hasnext = ts.incrementToken();
        }
        
        return stemmedText.toString();
    }
}
