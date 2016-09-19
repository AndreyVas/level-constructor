import java.util.ArrayList;

public class Obstacle extends Entity
{
	
	
	Obstacle()
	{
		super();
		
		type = MAIN;
		xmlName = XML_NAME_OBSTACLE;
	}
	
	Obstacle(double x, double y)
	{
		super(x, y);
		

		type = MAIN;
		xmlName = XML_NAME_OBSTACLE;
	}
	
	Obstacle(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		

		type = MAIN;
		xmlName = XML_NAME_OBSTACLE;
	}
	
	public void addObject(Entity e)
	{
		objects.add(e);
	}
	
	public void addObjects(ArrayList<Entity> al)
	{
		objects.addAll(al);
	}
	
	
	
	public double getChildereWidhtOver()
	{
		double width = 0;
		
		if(objects.size() > 0)
			for(Entity e : objects)
			{
				if(e.getType() == OVER)
					width += e.getWidht();
			}
		
		return width;
	}
	
	public double getChildrenWidhtUnder()
	{
		double width = 0;
		
		if(objects.size() > 0)
			for(Entity e : objects)
			{
				if(e.getType() == UNDER)
					width += e.getWidht();
			}
		
		return width;
	}
}
