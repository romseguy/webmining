import java.util.Map;


public class Episode {
	private String title;
	private String summary;
	private String stemmedSummary;
	private Map<String, Double> freqVector;
	
	public Episode(String title, String summary)
	{
		this.title = title;
		this.summary = summary;
	}
	
	public String getTitle()
	{
	    return this.title;
	}
	
	public String getSummary()
	{
	    return this.summary;
	}
	
	public String getStemmedSummary()
	{
	    return this.stemmedSummary;
	}
	
	public Map<String, Double> getFreqVector()
	{
		return this.freqVector;
	}
	
	public void setStemmedSummary(String s)
	{
	    this.stemmedSummary = s;
	}
	
	public void setFreqVector(Map<String, Double> v)
	{
		this.freqVector = v;
	}
}
