
public class Episode {
	private String title;
	private String summary;
	private String stemmedSummary;
	
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
	
	public void setStemmedSummary(String s)
	{
	    this.stemmedSummary = s;
	}
}
