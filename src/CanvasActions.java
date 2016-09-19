import java.util.ArrayList;

public class CanvasActions implements Environments
{
	public static double xCorrection(LevelCreator lc, double sourceX)
	{
		double w = lc.widthOffset;
		
		if((sourceX + w) % DX < DX/2)
			return ((sourceX + w) - (sourceX + w) % DX) - w;
		else
			return ((sourceX + w) - (sourceX + w) % DX + DX) - w;
	}
	
	public static double yCorrection(LevelCreator lc, double sourceY)
	{
		double h = lc.levelHeight - lc.heightOffset;
		
		if((h - sourceY) % DY < DY/2)
			return h - ((h - sourceY) - (h - sourceY) % DY);
		else
			return h - (((h - sourceY) - (h - sourceY) % DY) + DY);
	}
	
	public static Entity standsOn(LevelCreator lc, ArrayList<Entity> al, Entity e)
	{
		for(Entity o : al)
		{
			if(!(lc.intersection = CanvasActions.checkiIntersection(lc, e, o)))
			{
				double x = o.getX() - lc.widthOffset;
				double y = o.getY() - lc.heightOffset;
				
				if(e.getY() + e.getHeitht() == y  && e.getX() >=x && e.getX() + e.getWidht() <= x + o.getWidht())
				{
					return o;
				}
			}
			else
			{
				return null;
			}	
		}
		
		return null;
	}
	
	public static boolean standOnGround(LevelCreator lc, Entity e)
	{
		if((e.getY() + e.getHeitht()) + lc.heightOffset == lc.levelHeight)
		{
			return true;
		}
		
		return false;
	}
	
	public static Entity hangingUnder(LevelCreator lc, ArrayList<Entity> al, Entity e)
	{
		for(Entity o : al)
		{
			if(!(lc.intersection = CanvasActions.checkiIntersection(lc, e, o)))
			{
				double x = o.getX() - lc.widthOffset;
				double y = o.getY() - lc.heightOffset;
				
				if(e.getY() == y + o.getHeitht() && e.getX() >= x && e.getX() + e.getWidht() <= x + o.getWidht())
				{
					return o;
				}
			}
			else
			{
				return null;
			}
		}
		
		return null;
	}
	
	public static boolean checkiIntersection(LevelCreator lc, Entity e1, Entity e2)
	{
		double cX1 = e1.getXCenter();
		double cX2 = e2.getXCenter() - lc.widthOffset;
		
		boolean resX;
		boolean resY;
		
		if(cX1 > cX2)
		{
			if(cX1 - cX2 >= e1.getWidht() / 2 + e2.getWidht() / 2)
				resX = false;
			else
				resX = true;
		}
		else
		{
			if(cX2 - cX1 >= e1.getWidht() / 2 + e2.getWidht() / 2)
				resX = false;
			else
				resX = true;
		}
		
		double cY1 = e1.getYCenter();
		double cY2 = e2.getYCenter() - lc.heightOffset;
		
		if(cY1 > cY2)
		{
			if(cY1 - cY2 >= e1.getHeitht() / 2 + e2.getHeitht() / 2)
				resY = false;
			else
				resY = true;
		}
		else
		{
			if(cY2 - cY1 >= e1.getHeitht() / 2 + e2.getHeitht() / 2)
				resY = false;
			else
				resY = true;
		}
	
		return resX && resY;
	}
	
	public static void setOffset(LevelCreator lc, double x, double y)
	{
		if(y > lc.movedScreenPoint.y)
		{
			if((lc.heightOffset -= (y - lc.movedScreenPoint.y)) < 0)
			{
				lc.heightOffset = 0;
			}
		}
		else
		{
			if((lc.heightOffset += (lc.movedScreenPoint.y - y)) >= lc.levelHeight - lc.screenHeight)
			{
				lc.heightOffset = lc.levelHeight - lc.screenHeight;
			}
		}

		if(x < lc.movedScreenPoint.x)
		{
			if((lc.widthOffset += (lc.movedScreenPoint.x - x)) >= lc.levelWidth - lc.screenWidth)
			{
				lc.widthOffset = lc.levelWidth - lc.screenWidth;
			}
		}
		else
		{
			if((lc.widthOffset -= (x - lc.movedScreenPoint.x)) <= 0)
			{
				lc.widthOffset = 0;
			}
		}

		lc.movedScreenPoint.x = x;
		lc.movedScreenPoint.y = y;
	}
	
	public static Entity isInObject(LevelCreator lc, double x, double y)
	{
		x += lc.widthOffset;
		y += lc.heightOffset;
		
		for(Entity e : lc.objects)
		{
			if((e.getX() < x && e.getX() + e.getWidht() > x) && (e.getY() < y && e.getY() + e.getHeitht() > y))
			{
				return e;
			}
			
			if(e.getObject().size() != 0)
				for(Entity subE : e.getObject())
				{
					if((subE.getX() < x && subE.getX() + subE.getWidht() > x) && (subE.getY() < y && subE.getY() + subE.getHeitht() > y))
					{
						return subE;
					}
				}
		}
		
		return null;
	}
	
	public static void checkLevelSize(LevelCreator lc, Entity e)
	{
		if(e.getY() < 100)
		{
			lc.levelHeight += 100;
			lc.heightOffset += 100;
			
			for(Entity en : lc.objects)
			{
				en.setY(en.getY() + 100);
				
				if(en.getObject().size() !=0 )
					for(Entity subEn : en.getObject())
					{
						subEn.setY(subEn.getY() + 100);
					}
			}
		}
		
		if((e.getX() + e.getWidht()) + 100 > lc.levelWidth)
		{
			lc.levelWidth += 100;
			lc.widthOffset += 100;	
		}
	}
	
	public static int moveEntityCursorView(LevelCreator lc, Entity e, double x, double y)
	{
		x += lc.widthOffset;
		y += lc.heightOffset;
		
		if((e.getX() < x && e.getX() + e.getWidht() > x) && (e.getY() < y && e.getY() + e.getHeitht() > y))
			return INSIDE;
		else if((e.getX() < x && e.getX() + e.getWidht() > x) && (e.getY() - DY * 2 < y && e.getY() > y))
			return TOP;
		else if((e.getX() < x && e.getX() + e.getWidht() > x) && (e.getY() + e.getHeitht() < y && e.getY() + e.getHeitht() + DY * 2 > y))
			return BOTTOM;
		else if((e.getX() > x && e.getX() - DX * 2 < x) && (e.getY() < y && e.getY() + e.getHeitht() > y))
			return LEFT;
		else if((e.getX() + e.getWidht() < x && e.getX() + e.getWidht() +  DX * 2 > x) && (e.getY() < y && e.getY() + e.getHeitht() > y))
			return RIGHT;
		
		return OUTSIDE;
	}

	
}
