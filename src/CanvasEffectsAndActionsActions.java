import javafx.scene.paint.Color;

public class CanvasEffectsAndActionsActions extends CanvasActions
{
	public static void drawPointer(LevelCreator lc, double x, double y)
	{
		lc.gcEffects.clearRect(0, 0, lc.sc.getWidth() , lc.sc.getHeight());
		lc.gcEffects.setFill(Color.RED);
		lc.gcEffects.fillOval(xCorrection(lc, x) - 2, yCorrection(lc, y) - 2, 4, 4);
	}
	
	public static void drawObstacle(LevelCreator lc, Entity e, double x, double y)
	{
		double tmpX;
		double tmpY;
		double tmpWidth;
		double tmpHeight;
	
		if(e.getX() > x)
		{
			tmpX = x;
			tmpWidth = e.getX() - x;
		}
		else
		{
			tmpX = e.getX();
			tmpWidth = x - e.getX();
		}
		if(e.getY() > y)
		{
			tmpY = y;
			tmpHeight = e.getY() - y;
		}
		else
		{
			tmpY = e.getY();
			tmpHeight = y - e.getY();
		}

		lc.gcEffects.setFill(Color.rgb(96, 255, 0, 0.5));
		
		Entity tmpEntity = new Obstacle(tmpX, tmpY, tmpWidth, tmpHeight);
		
		for(Entity o : lc.objects)
		{
			if(lc.intersection = CanvasActions.checkiIntersection(lc, tmpEntity, o))
			{
				lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
				break;
			}
		}

		lc.gcEffects.clearRect(0, 0, lc.canvasEffectsAndActions.getWidth(), lc.canvasEffectsAndActions.getHeight());
		lc.gcEffects.fillRect(tmpX, tmpY, tmpWidth, tmpHeight);
	}
	
	public static void drawStalactite(LevelCreator lc, double x, double y)
	{
		lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
		
		Entity tmpEntity = new Stalactite(x - DX, y - DY, STALACTITE_WIDTH_K * DX, STALACTITE_HEIGHT_K * DY);

		if((lc.parentEntity = CanvasActions.hangingUnder(lc, lc.objects, tmpEntity)) != null)
		{
			lc.gcEffects.setFill(Color.rgb(96, 255, 0, 0.5));
		}

		lc.gcEffects.clearRect(0, 0, lc.canvasEffectsAndActions.getWidth(), lc.canvasEffectsAndActions.getHeight());
		lc.gcEffects.fillRect(x - DX, y - DY, STALACTITE_WIDTH_K * DX, DY * STALACTITE_HEIGHT_K);
	}
	
	public static void drawDrilledMount(LevelCreator lc, double x, double y)
	{
		lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
		
		Entity tmpEntity = new DrilledMount(x - 2 * DX, y - 2 * DY, DRILLED_MOUNT_WIDTH_K * DX, DRILLED_MOUNT_HEIGHT_K * DY);

		if((lc.parentEntity = CanvasActions.standsOn(lc, lc.objects, tmpEntity)) != null || standOnGround(lc, tmpEntity) == true)
		{
			lc.gcEffects.setFill(Color.rgb(96, 255, 0, 0.5));
		}
		
		lc.gcEffects.clearRect(0, 0, lc.canvasEffectsAndActions.getWidth(), lc.canvasEffectsAndActions.getHeight());
		lc.gcEffects.fillRect(x - 2 * DX, y - 2 * DX, DRILLED_MOUNT_WIDTH_K * DX, DRILLED_MOUNT_HEIGHT_K * DX);
	}
	
	public static void drawDwarf(LevelCreator lc, double x, double y)
	{
		lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
		
		Entity tmpEntity = null;
		
		switch(lc.entityChoice)
		{
			case TLBR_CHOICE_DWARF_DRILLER:
				tmpEntity = new DwarfDriller(x - 2 * DX, y - 2 * DY, DWARF_WIDTH_K * DX, DWARF_HEIGHT_K * DY);
				break;
			
			case TLBR_CHOICE_DWARF_WORKER:
				tmpEntity = new DwarfWorker(x - 2 * DX, y - 2 * DY, DWARF_WIDTH_K * DX, DWARF_HEIGHT_K * DY);
				break;
				
			case TLBR_CHOICE_DWARF_WARRIOR:
				tmpEntity = new DwarfWarrior(x - 2 * DX, y - 2 * DY, DWARF_WIDTH_K * DX, DWARF_HEIGHT_K * DY);
				break;
		}

		if((lc.parentEntity = CanvasActions.standsOn(lc, lc.objects, tmpEntity)) != null || standOnGround(lc, tmpEntity) == true)
		{
			lc.gcEffects.setFill(Color.rgb(96, 255, 0, 0.5));
		}
		
		lc.gcEffects.clearRect(0, 0, lc.canvasEffectsAndActions.getWidth(), lc.canvasEffectsAndActions.getHeight());
		lc.gcEffects.fillRect(x - 2 * DX, y - 2 * DX, DWARF_WIDTH_K * DX, DWARF_HEIGHT_K * DX);
	}
	
	public static void clearEffectsCanvas(LevelCreator lc)
	{
		lc.gcEffects.clearRect(0, 0, lc.canvasEffectsAndActions.getWidth(), lc.canvasEffectsAndActions.getHeight());
	}
	
	public static boolean drawSelectedArea(LevelCreator lc, Entity e)
	{
		
		lc.gcEffects.setFill(Color.rgb(96, 255, 0, 0.5));
		boolean moveComplete = true;

		// check intersection with other objects with type OBSTACLE
		
		for(Entity o : lc.objects)
		{
			if(!o.equals(lc.tmpEntity)) // check main elements
			{
				if(lc.tmpEntity.getType() == MAIN) 
				{
					
					if(lc.intersection = CanvasActions.checkiIntersection(lc, new Entity(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht()), o)
							|| e.getChildereWidhtOver() > e.getWidht() || e.getChildrenWidhtUnder() > e.getWidht())
					{
						lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
						moveComplete = false;
						break;
					}
					
					// check intersection with sub elements
					
					if(o.getObject().size() != 0)
					{
						for(Entity subO : o.getObject())
						{
							if(lc.intersection = CanvasActions.checkiIntersection(lc, new Entity(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht()), subO))
							{
								lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
								moveComplete = false;
								break;
							}
						}
					}
				}
				else if(lc.tmpEntity.getType() == UNDER)
				{
					if((lc.parentEntity = CanvasActions.hangingUnder(lc, lc.objects,  new Entity(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht()))) == null)
					{
						lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
						moveComplete = false;
					}
				}
				else if(lc.tmpEntity.getType() == OVER)
				{
					if((lc.parentEntity = CanvasActions.standsOn(lc, lc.objects,  new Entity(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht()))) == null)
					{
						lc.gcEffects.setFill(Color.rgb(255, 0, 0, 0.5));
						moveComplete = false;
						break;
					}
				}
			}
			
			
		}

		lc.gcEffects.fillRect(e.getX() - lc.widthOffset, e.getY() - lc.heightOffset, e.getWidht(), e.getHeitht());
		
		return moveComplete;
	}

	public static void moveEntity(LevelCreator lc, Entity e, MyPoint oldPoint, MyPoint newPoint)
	{
		clearEffectsCanvas(lc);
		
		e.setX(e.getX() + (newPoint.x - oldPoint.x));
		e.setY(e.getY() + (newPoint.y - oldPoint.y));

		lc.moveComplete = drawSelectedArea(lc, e) & true;
		
		if(e.getObject() != null && e.getObject().size() != 0)
		{
			for(Entity subE : e.getObject())
			{
				subE.setX(subE.getX() + (newPoint.x - oldPoint.x));
				subE.setY(subE.getY() + (newPoint.y - oldPoint.y));
				
				lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
			}
		}
		
		oldPoint.x = newPoint.x;
		oldPoint.y = newPoint.y;
	}

	public static void resizeEntityTop(LevelCreator lc, Entity e, MyPoint oldPoint, MyPoint newPoint)
	{
		if(e.getHeitht() - (newPoint.y - oldPoint.y) > 0)
		{
			clearEffectsCanvas(lc);
			
			e.setY(e.getY() + (newPoint.y - oldPoint.y));
			e.setHeight(e.getHeitht() - (newPoint.y - oldPoint.y));
			
			//drawSelectedArea(lc, e);
			lc.moveComplete = drawSelectedArea(lc, e) & true;
			
			if(e.getObject() != null && e.getObject().size() != 0)
			{
				for(Entity subE : e.getObject())
				{
					if(subE.getY() < e.getY() + e.getHeitht())
					{
						subE.setY(subE.getY() + (newPoint.y - oldPoint.y));
						
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
					else
					{
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
				}
			}
			
			oldPoint.y = newPoint.y;
		}
		
	}
	
	public static void resizeEntityBottom(LevelCreator lc, Entity e, MyPoint oldPoint, MyPoint newPoint)
	{
		if(e.getHeitht() + (newPoint.y - oldPoint.y) > 0)
		{
			clearEffectsCanvas(lc);
			
			e.setHeight(e.getHeitht() + (newPoint.y - oldPoint.y));

			//drawSelectedArea(lc, e);
			lc.moveComplete = drawSelectedArea(lc, e) & true;
			
			if(e.getObject() != null && e.getObject().size() != 0)
			{
				for(Entity subE : e.getObject())
				{
					if(subE.getY() + (newPoint.y - oldPoint.y) >= e.getY() + e.getHeitht())
					{
						subE.setY(subE.getY() + (newPoint.y - oldPoint.y));
						
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
					else
					{
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
				}
			}
			
			oldPoint.y = newPoint.y;
		}
	}
	
	public static void resizeEntityLeft(LevelCreator lc, Entity e, MyPoint oldPoint, MyPoint newPoint)
	{
		if(e.getWidht() - (newPoint.x - oldPoint.x) > 0
				&& e.getWidht() - (newPoint.x - oldPoint.x) >= e.getChildereWidhtOver()
					&& e.getWidht() - (newPoint.x - oldPoint.x) >= e.getChildrenWidhtUnder())
		{
			clearEffectsCanvas(lc);
			
			e.setX(e.getX() + (newPoint.x - oldPoint.x));
			e.setWidth(e.getWidht() - (newPoint.x - oldPoint.x));

			//drawSelectedArea(lc, e);
			lc.moveComplete = drawSelectedArea(lc, e) & true;

			if(e.getObject() != null && e.getObject().size() != 0)
			{
				for(Entity subE : e.getObject())
				{
					if(subE.getX() < e.getX())
					{
						moveSubE(lc, e, subE, newPoint.x - oldPoint.x);
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
					else
					{
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
				}	
			}
			
			
			
			oldPoint.x = newPoint.x;
		}
	}
	
	public static void resizeEntityRight(LevelCreator lc, Entity e, MyPoint oldPoint, MyPoint newPoint)
	{
		if(e.getWidht() + (newPoint.x - oldPoint.x) > 0
				&& e.getWidht() + (newPoint.x - oldPoint.x) >= e.getChildereWidhtOver()
					&& e.getWidht() + (newPoint.x - oldPoint.x) >= e.getChildrenWidhtUnder())
		{
			clearEffectsCanvas(lc);
			
			e.setWidth(e.getWidht() + (newPoint.x - oldPoint.x));

			//drawSelectedArea(lc, e);
			lc.moveComplete = drawSelectedArea(lc, e) & true;
			
			if(e.getObject() != null && e.getObject().size() != 0)
			{
				for(Entity subE : e.getObject())
				{
					if(subE.getX() + subE.getWidht() > e.getX() + e.getWidht())
					{
						moveSubE(lc, e, subE, newPoint.x - oldPoint.x);
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
					else
					{
						//drawSelectedArea(lc, subE);
						lc.moveComplete = drawSelectedArea(lc, subE) & lc.moveComplete;
					}
				}	
			}
			
			oldPoint.x = newPoint.x;
		}
	}
	
	public static void moveSubE(LevelCreator lc, Entity parent, Entity child, double offset)
	{
		if((child.getX() + offset >= parent.getX()) && (child.getX() + child.getWidht() + offset <= parent.getX() + parent.getWidht()))
		{
			child.setX(child.getX() + offset);
			
			if(parent.getObject().size() > 0)
			{
				for(Entity e : parent.getObject())
				{
					if(!child.equals(e) && child.getType() == e.getType())
						if(Math.abs(e.getXCenter() - child.getXCenter()) < (e.getWidht() + child.getWidht()) / 2)
						{
							moveSubE(lc, parent, e, offset);
						}
				}
			}
		}
	}
}
