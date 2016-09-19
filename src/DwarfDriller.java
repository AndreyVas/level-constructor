import javafx.scene.image.Image;

public class DwarfDriller extends Entity
{
	DwarfDriller()
	{
		type = OVER;
		xmlName = XML_NAME_DWARF_DRILLER;
	}
	
	DwarfDriller(double x, double y)
	{
		super(x, y);
		type = OVER;
		xmlName = XML_NAME_DWARF_DRILLER;
	}
	
	DwarfDriller(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		type = OVER;
		xmlName = XML_NAME_DWARF_DRILLER;
	}
	
	public Image getImg()
	{
		return new Image(Environments.ICON_DWARF_DRILLER);
	}
}
