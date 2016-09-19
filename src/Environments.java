
public interface Environments 
{
	double DX 							= 6;
	double DY 							= 6;
	
	double STALACTITE_WIDTH_K			= 4;
	double STALACTITE_HEIGHT_K			= 5;
	
	double DRILLED_MOUNT_WIDTH_K		= 7;
	double DRILLED_MOUNT_HEIGHT_K		= 10;
	
	double DWARF_WIDTH_K		= 8;
	double DWARF_HEIGHT_K		= 10;
	
	double START_SCREEN_WIDHT 			= 900;
	double START_SCREEN_HEIGHT 			= 600;
	
	int ENTITY_CHOICE_NOTHING			= 0;
	int TLBR_CHOICE_DWARF_DRILLER 		= 1;
	int TLBR_CHOICE_OBSTACLE 			= 2;
	int TLBR_CHOICE_STALACTITE			= 3;
	int TLBR_CHOICE_DRILLED_MOUNT 		= 4;
	int TLBR_CHOICE_MOVE_SCREEN			= 5;
	int TLBR_CHOICE_BASKED				= 6;
	int TLBR_CHOICE_DWARF_WARRIOR		= 7;
	int TLBR_CHOICE_DWARF_WORKER		= 8;
	
	String TLBR_BTN_NAME_DWARF_DRILLER	= "bDwarfDriller";
	String TLBR_BTN_NAME_OBSTACLE		= "bObsacle";
	String TLBR_BTN_NAME_STALACTITE		= "bStalactite";
	String TLBR_BTN_NAME_DRILLED_MOUNT	= "bDrilledMount";
	String TLBR_BTN_NAME_MOVE_SCREEN	= "bMoveScreen";
	String TLBR_BTN_NAME_BASKED			= "bBasked";
	String TLBR_BTN_NAME_DWARF_WARRIOR	= "bDwarfWarrior";
	String TLBR_BTN_NAME_DWARF_WORKER	= "bDwarfWorker";
	
	String ICON_DWARF_DRILLER 			= "dwarfDriller.png";
	String ICON_OBSTACLE 				= "obstacle.png";
	String ICON_STALACTITE				= "stalactite.png";
	String ICON_DRILLED_MOUNT			= "drilledMount.png";
	String ICON_MOVE_SCREEN				= "bMoveScreen.png";
	String ICON_BASKED					= "basket.png";
	String ICON_DWARF_WORKER 			= "dwarfWorker.png";
	String ICON_DWARF_WARRIOR 			= "dwarfWarrior.png";
	
	int LEFT							= 0;
	int RIGHT							= 1;
	int TOP								= 2;
	int BOTTOM							= 3;
	int INSIDE							= 4;
	int OUTSIDE							= 5;
	
	String UNDER						= "under";
	String OVER							= "over";
	String MAIN							= "main";
	
	String XML_NAME_WIDTH				= "width";
	String XML_NAME_HEIGHT				= "height";
	String XML_NAME_OBSTACLE			= "obstacle";
	String XML_NAME_DWARF_DRILLER		= "dwarfdriller";
	String XML_NAME_DWARF_WARRIOR		= "dwarfwarrior";
	String XML_NAME_DWARF_WORKER		= "dwarfworker";
	String XML_NAME_STALACTITE			= "stalactite";
	String XML_NAME_DRILLED_MOUNT		= "drilledmount";
	
}
