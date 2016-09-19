import javafx.scene.image.Image;

public class DwarfWorker  extends Entity
{
	DwarfWorker()
	{
		type = OVER;
		xmlName = XML_NAME_DWARF_WORKER;
	}
	
	DwarfWorker(double x, double y)
	{
		super(x, y);
		type = OVER;
		xmlName = XML_NAME_DWARF_WORKER;
	}
	
	DwarfWorker(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		type = OVER;
		xmlName = XML_NAME_DWARF_WORKER;
	}
	
	public Image getImg()
	{
		return new Image(Environments.ICON_DWARF_WORKER);
	}
}
