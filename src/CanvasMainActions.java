import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CanvasMainActions extends CanvasActions
{
	public static void createObstacle(LevelCreator lc, Entity e, double x, double y)
	{
		
		if(!lc.intersection)
		{
			if(e.getX() > x)
			{
				e.setWidth(e.getX() - x);
				e.setX(x);
			}
			else
			{
				e.setWidth(x - e.getX());
				e.setX(e.getX());
			}
			if(e.getY() > y)
			{
				e.setHeight(e.getY() - y);
				e.setY(y);
			}
			else
			{
				e.setHeight(y - e.getY());
				e.setY(e.getY());
			}
			
			if(e.getWidht() != 0 && e.getHeitht() != 0)
			{
				lc.gcMain.setFill(Color.BURLYWOOD);
				lc.gcMain.fillRect(e.getX(), e.getY(), e.getWidht(), e.getHeitht());

				Obstacle o = new Obstacle(e.getX() + lc.widthOffset, e.getY() + lc.heightOffset, e.getWidht(), e.getHeitht());
				lc.objects.add((lc.objects.size() > 0)?lc.objects.size() - 1:0, o);
				CanvasActions.checkLevelSize(lc, o);
			}	
		}
	}
	
	public static void createStalactite(LevelCreator lc, Image img, Entity e, double x, double y)
	{
		if(!lc.intersection && lc.parentEntity != null)
		{
			lc.gcMain.drawImage(img, x - DX, y - DY, DX * STALACTITE_WIDTH_K, DY * STALACTITE_HEIGHT_K);
			lc.parentEntity.addObject(new Stalactite((x - DX) + lc.widthOffset, (y - DY) + lc.heightOffset, DX * STALACTITE_WIDTH_K, DY * STALACTITE_HEIGHT_K));
		}
	}
	
	public static void createDrilledMount(LevelCreator lc, Image img, double x, double y)
	{
		Entity tmpEntity = new DrilledMount((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DRILLED_MOUNT_WIDTH_K, DY * DRILLED_MOUNT_HEIGHT_K);
		
		if(!lc.intersection && lc.parentEntity != null)
		{
			lc.gcMain.drawImage(img, x - 2 * DX, y - 2 * DY, DX * DRILLED_MOUNT_WIDTH_K, DY * DRILLED_MOUNT_HEIGHT_K);
			lc.parentEntity.addObject(tmpEntity);
		}	
		else if(standOnGround(lc, new DrilledMount((x - 2 * DX), (y - 2 * DY), DX * DRILLED_MOUNT_WIDTH_K, DY * DRILLED_MOUNT_HEIGHT_K)) == true)
		{
			lc.objects.add(0, tmpEntity);
			lc.gcMain.drawImage(img, x - 2 * DX, y - 2 * DY, DX * DRILLED_MOUNT_WIDTH_K, DY * DRILLED_MOUNT_HEIGHT_K);
		}
	}
	
	public static void createDwarf(LevelCreator lc, Image img, Entity e, double x, double y)
	{
		if(!lc.intersection && lc.parentEntity != null)
		{
			// if dwarf in obstacle
			
			switch(lc.entityChoice)
			{
				case TLBR_CHOICE_DWARF_DRILLER:
					lc.parentEntity.addObject(new DwarfDriller((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));
					break;
				
				case TLBR_CHOICE_DWARF_WORKER:
					lc.parentEntity.addObject(new DwarfWorker((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));
					break;
					
				case TLBR_CHOICE_DWARF_WARRIOR:
					lc.parentEntity.addObject(new DwarfWarrior((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));
					break;
			}
			
			lc.gcMain.drawImage(img, x - 2 * DX, y - 2 * DY, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K);
			
		}
		else if(standOnGround(lc, new DwarfDriller((x - 2 * DX), (y - 2 * DY), DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K)))
		{
			// if dwarf in a ground
			
			switch(lc.entityChoice)
			{
				case TLBR_CHOICE_DWARF_DRILLER:
					lc.objects.add(0, new DwarfDriller((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));

					break;
				
				case TLBR_CHOICE_DWARF_WORKER:
					lc.objects.add(0, new DwarfWorker((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));
					break;
					
				case TLBR_CHOICE_DWARF_WARRIOR:
					lc.objects.add(0, new DwarfWarrior((x - 2 * DX) + lc.widthOffset, (y - 2 * DY) + lc.heightOffset, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K));
					break;
			}
			
			lc.gcMain.drawImage(img, x - 2 * DX, y - 2 * DY, DX * DWARF_WIDTH_K, DY * DWARF_HEIGHT_K);
		}
	}
	
	public static void reDrawObject(LevelCreator lc, Entity e)
	{
		switch(e.getType())
		{
			case UNDER:
				lc.gcMain.drawImage(e.getImg(), e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
				break;
				
			case OVER:
				lc.gcMain.drawImage(e.getImg(), e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
				break;
			
			case MAIN:
				
				lc.gcMain.setFill(Color.BURLYWOOD);
				lc.gcMain.fillRect(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
				
				if(e.getObject() != null && e.getObject().size() != 0)
				{
					for(Entity o : e.getObject())
					{
						reDrawObject(lc, o);
					}
				}
				break;
		}
	}
	
	public static void reDrawAll(LevelCreator lc)
	{
		clearScreen(lc);
		
		lc.gcMain.setFill(Color.BURLYWOOD);
		
		for(Entity e : lc.objects)
		{
			if(e.getClass().toString().equals("class Obstacle"))
				lc.gcMain.fillRect(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
			else
				lc.gcMain.drawImage(e.getImg(), e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
			
			for(Entity sub : e.getObject())
			{		
				lc.gcMain.drawImage(sub.getImg(), sub.getX() - lc.widthOffset, sub.getY() - lc.heightOffset, sub.getWidht(), sub.getHeitht());
			}
		}
	}
	
	public static void clearScreen(LevelCreator lc)
	{
		lc.gcMain.clearRect(0, 0, lc.canvasMain.getWidth(), lc.canvasMain.getHeight());
	}
	
	public static boolean deleteObject(LevelCreator lc, Entity forDel)
	{
		// delete obstacle and his sub elements
		
		for(Entity e : lc.objects)
		{
			if(e.equals(forDel))
			{
				lc.gcMain.clearRect(forDel.getX() - lc.widthOffset, 
						forDel.getY() - lc.heightOffset, forDel.getWidht(), forDel.getHeitht());
				
				if(e.getObject().size() != 0)
				{
					for(Entity subE : e.getObject())
					{
						lc.gcMain.clearRect(subE.getX() - lc.widthOffset, 
								subE.getY() - lc.heightOffset, subE.getWidht(), subE.getHeitht());
					}
				}
				
				lc.objects.remove(forDel);
				
				return true;
			}
			
			// delete only sub elements
			
			if(e.getObject().size() != 0)
			{
				for(Entity subE : e.getObject())
				{
					if(subE.equals(forDel))
					{
						lc.gcMain.clearRect(forDel.getX() - lc.widthOffset, 
								forDel.getY() - lc.heightOffset, forDel.getWidht(), forDel.getHeitht());
						
						e.getObject().remove(forDel);
						
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public static void clearArea(LevelCreator lc, Entity e)
	{
		lc.gcMain.clearRect(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
		
	
		if(e.getObject() != null && e.getObject().size() != 0)
		{
			for(Entity o : e.getObject())
			{
				lc.gcMain.clearRect(o.getX() - lc.widthOffset, o.getY() - lc.heightOffset, o.getWidht(), o.getHeitht());
			}
		}
	}
	
	/*
	public static boolean checkIntersectionWithAllObjects(LevelCreator lc, Entity en)
	{
		boolean interact = false;
		
		for(Entity o : lc.objects)
		{
			if(!o.equals(en))
			{
				if(lc.tmpEntity.getType() == MAIN) 
				{
					if(lc.intersection = CanvasActions.checkiIntersection(lc, new Entity(en.getX() - lc.widthOffset, 
							en.getY() - lc.heightOffset, en.getWidht(), en.getHeitht()), o))
					{
						interact = true;
						break;
					}
					
					// check intersection for sub elements
					if(o.getObject() != null && o.getObject().size() != 0)
					{
						for(Entity subO : o.getObject())
						{
							if(lc.intersection = CanvasActions.checkiIntersection(lc, new Entity(en.getX() - lc.widthOffset, 
									en.getY() - lc.heightOffset, en.getWidht(), en.getHeitht()), subO))
							{
								interact = true;
								break;
							}
						}
					}
				}
				else if(lc.tmpEntity.getType() == UNDER)
				{
					if((lc.parentEntity = CanvasActions.hangingUnder(lc, lc.objects,  new Entity(en.getX() - lc.widthOffset, en.getY() - lc.heightOffset, en.getWidht(), en.getHeitht()))) == null)
					{
						interact = true;
						break;
					}
				}
				else if(lc.tmpEntity.getType() == OVER)
				{
					if((lc.parentEntity = CanvasActions.standsOn(lc, lc.objects,  new Entity(en.getX() - lc.widthOffset, en.getY() - lc.heightOffset, en.getWidht(), en.getHeitht()))) == null)
					{
						interact = true;
						break;
					}
				}	
			}
		}
		
		return interact;
	}
	*/
	public static void finishMoveEntity(LevelCreator lc, Entity en, Entity oldEntityPosition)
	{
		//if(!checkIntersectionWithAllObjects(lc, en))
		if(lc.moveComplete)
		{
			// moved

			if(lc.tmpEntity.getType() == MAIN || lc.parentEntity == null) 
			{
				reDrawObject(lc, en);
			}
			else
			{
				for(Entity e : lc.objects)
				{
					if(e.getObject() != null && e.getObject().size() != 0)
					{
						for(Entity subE : e.getObject())
						{
							if(subE.equals(en))
							{
								e.getObject().remove(subE);
								lc.parentEntity.addObject(en);
								reDrawObject(lc, en);
								break;
							}
						}
					}
				}
			}
		}
		else
		{
			// to old place
			
			en.setX(oldEntityPosition.getX());
			en.setY(oldEntityPosition.getY());
			
			if(en.getObject() != null && en.getObject().size() != 0)
			{
				en.getObject().clear();
				
				for(Entity e : oldEntityPosition.getObject())
				{
					en.addObject(e);
				}
			}
			
			reDrawObject(lc, en);
		}
		
		CanvasActions.checkLevelSize(lc, en);
	}

	public static void finishResizeEntity(LevelCreator lc, Entity en, Entity oldEntityPosition)
	{
		//if(!checkIntersectionWithAllObjects(lc, en))
		if(lc.moveComplete)
		{
			// fix new size

			if(lc.tmpEntity.getType() == MAIN || lc.parentEntity == null) 
			{
				reDrawObject(lc, en);
			}
		}
		else
		{
			// to return old size
			
			en.setX(oldEntityPosition.getX());
			en.setY(oldEntityPosition.getY());
			en.setWidth(oldEntityPosition.getWidht());
			en.setHeight(oldEntityPosition.getHeitht());
			
			if(en.getObject() != null && en.getObject().size() != 0)
			{
				en.getObject().clear();
				
				for(Entity e : oldEntityPosition.getObject())
				{
					en.addObject(e);
				}
			}
			
			reDrawObject(lc, en);
		}
		
		CanvasActions.checkLevelSize(lc, en);
	}
}
