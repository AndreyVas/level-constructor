import java.util.ArrayList;

import javafx.scene.image.Image;

public class Entity implements Environments
{
	protected double x;
	protected double y;
	
	protected double width;
	protected double height;
	
	protected String type;
	protected String xmlName;

	ArrayList<Entity> objects;
	
	Entity()
	{
		objects = new ArrayList<Entity>();
	}
	
	Entity(double x, double y)
	{
		this.x = x;
		this.y = y;
		
		objects = new ArrayList<Entity>();
		
	}
	
	Entity(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		objects = new ArrayList<Entity>();
	}
	
	Entity(Image image, double x, double y)
	{
		objects = new ArrayList<Entity>();
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setWidth(double w)
	{
		width = w;
	}
	
	public void setHeight(double h)
	{
		height = h;
	}
	
	public double getWidht()
	{
		return width;
	}
	
	public double getHeitht()
	{
		return height;
	}
	
	public void addObject(Entity e)
	{
		
	}
	
	public void addObjects(ArrayList<Entity> e)
	{
		
	}
	
	public double getXCenter()
	{
		return x + width/2;
	}
	
	public double getYCenter()
	{
		return y + height/2;
	}
	
	public String getType()
	{
		return type;
	}

	public ArrayList<Entity> getObject()
	{
		return objects;
	}
	
	public Image getImg()
	{
		return null;
	}
	
	public double getChildereWidhtOver()
	{
		return 0;
	}
	
	public double getChildrenWidhtUnder()
	{
		return 0;
	}
	
	public String getXMLName()
	{
		return xmlName;
	}
}
