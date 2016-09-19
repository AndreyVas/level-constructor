import javafx.scene.paint.Color;

public class CanvasLineActions extends CanvasActions
{
	public static void drawLines(LevelCreator lc)
	{
		lc.gcLine.setStroke(Color.GRAY);
		lc.gcLine.setLineWidth(0.2);
		
		for(double y = lc.levelHeight - lc.heightOffset; y > 0 - lc.heightOffset; y -= DY)
		{
			lc.gcLine.strokeLine(0, y, lc.levelWidth, y);
		}
		
		for(double x = 0 - lc.widthOffset; x < lc.levelWidth - lc.widthOffset; x += DX)
		{
			lc.gcLine.strokeLine(x, 0, x, lc.levelHeight);
		}
	}
	
	public static void clearScreen(LevelCreator lc)
	{
		lc.gcLine.clearRect(0, 0, lc.canvasLine.getWidth(), lc.canvasLine.getHeight());
	}
	
	public static void reDrawLines(LevelCreator lc)
	{
		clearScreen(lc);
		drawLines(lc);
	}
}
