import java.util.ArrayList;

public class LevelContainer 
{
	private double levelWidth;
	private double levelHeight;
	
	private ArrayList<Entity> objects;
	
	LevelContainer()
	{
		levelWidth = 0;
		levelHeight = 0;
		objects = new ArrayList<Entity>();
	}
	
	LevelContainer(double lWidth, double lHeight, ArrayList<Entity>obj)
	{
		levelWidth = lWidth;
		levelHeight = lHeight;
		System.out.println(obj);
		if(obj != null)
			objects = new ArrayList<Entity>(obj);
		else
			objects = new ArrayList<Entity>();
	}
	
	public double getLWidht()
	{
		return levelWidth;
	}
	
	public double getLHeight()
	{
		return levelHeight;
	}
	
	public void setWidht(double w)
	{
		levelWidth = w;
	}
	
	public void setHeight(double h)
	{
		levelHeight = h;
	}
	
	public ArrayList<Entity> getObjects()
	{
		return objects;
	}
	
	public void setLevelContainer(double w, double h, ArrayList<Entity>obj)
	{
		levelWidth = w;
		levelHeight = h;
		objects = new ArrayList<Entity>(obj);
	}
}
