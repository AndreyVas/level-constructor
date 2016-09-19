import javafx.scene.image.Image;

public class DwarfWarrior  extends Entity
{
	DwarfWarrior()
	{
		type = OVER;
		xmlName = XML_NAME_DWARF_WARRIOR;
	}
	
	DwarfWarrior(double x, double y)
	{
		super(x, y);
		type = OVER;
		xmlName = XML_NAME_DWARF_WARRIOR;
	}
	
	DwarfWarrior(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		type = OVER;
		xmlName = XML_NAME_DWARF_WARRIOR;
	}
	
	public Image getImg()
	{
		return new Image(Environments.ICON_DWARF_WARRIOR);
	}
}
