import javafx.scene.image.Image;

public class DrilledMount extends Entity
{
	DrilledMount()
	{
		super();
		
		type = OVER;
		xmlName = XML_NAME_DRILLED_MOUNT;
	}
	
	DrilledMount(double x, double y)
	{
		super(x, y);
		
		type = OVER;
		xmlName = XML_NAME_DRILLED_MOUNT;
	}
	
	DrilledMount(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		
		type = OVER;
		xmlName = XML_NAME_DRILLED_MOUNT;
	}
	
	public Image getImg()
	{
		return new Image(Environments.ICON_DRILLED_MOUNT);
	}
}
