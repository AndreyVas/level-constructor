import javafx.scene.image.Image;

public class Stalactite extends Entity
{
	Stalactite()
	{
		type = UNDER;
		xmlName = XML_NAME_STALACTITE;
	}
	
	Stalactite(double x, double y)
	{
		super(x, y);
		
		type = UNDER;
		xmlName = XML_NAME_STALACTITE;
	}
	
	Stalactite(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		
		type = UNDER;
		xmlName = XML_NAME_STALACTITE;
	}
	
	public Image getImg()
	{
		return new Image(Environments.ICON_STALACTITE);
	}
}
