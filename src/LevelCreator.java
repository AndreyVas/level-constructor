import java.util.ArrayList;
import javafx.scene.Cursor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelCreator extends Application implements Environments
{	
	Scene sc;
	
	Canvas canvasLine;
	Canvas canvasMain;
	Canvas canvasEffectsAndActions;
	
	GraphicsContext gcLine;
	GraphicsContext gcMain;
	GraphicsContext gcEffects;
	
	private StackPane canvasPane;
	private BorderPane mainPane;
	
	LevelCreator lc;
	
	/*---------------------------------------------------*/
	
	int entityChoice = ENTITY_CHOICE_NOTHING;
	
	boolean intersection;
	boolean changeObject;
	
	ArrayList<LevelContainer> levelContainer;
	ArrayList<Entity> objects;
	int currentLevel;
	Entity tmpEntity;
	Entity parentEntity;
	Entity entityMoveResize;
	MyPoint movedScreenPoint;
	int moveResizeAction;
	boolean moveComplete;
	
	/*---------------------------------------------------*/
	
	double screenWidth;
	double screenHeight;
	
	double levelWidth;
	double levelHeight;
	
	double widthOffset;
	double heightOffset;
	
	/*---------------------------------------------------*/
	
	MenuBar mb;				// main menu
	ToolBar leftToolBar;	// tool panel
	
	Menu levelMenu;
	Menu currentLevelM;
	
	/*---------------------------------------------------*/
	
	public static void main(String args[])
	{
		launch(args);
	}
	
	public void createLevelItemMenu()
	{
		levelMenu.getItems().clear();
		
		for(int i = 0; i < levelContainer.size(); i++)
		{
			MenuItem tmpItem = new MenuItem(1 + i + " level ");
		
			tmpItem.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent ae) 
				{
					levelContainer.get(currentLevel).setLevelContainer(levelWidth, levelHeight, objects);
				
					currentLevel = Character.getNumericValue(((MenuItem)ae.getTarget()).getText().charAt(0)) - 1;
					
					objects = new ArrayList<Entity>(levelContainer.get(currentLevel).getObjects());
					
					levelWidth = levelContainer.get(currentLevel).getLWidht();
					levelHeight = levelContainer.get(currentLevel).getLHeight();
					
					widthOffset = 0;
					heightOffset = levelHeight - screenHeight;

					CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
					CanvasMainActions.reDrawAll(lc);
					
					currentLevelM.setText("Current level : " + String.valueOf(currentLevel + 1));
				}
			});

			levelMenu.getItems().add(tmpItem);
		}
	}
	
	public void chooseCursor(double x, double y)
	{
		if(changeObject && tmpEntity != null)
		{
			// draw different pointers for move and resize object
			
			switch(moveResizeAction = CanvasActions.moveEntityCursorView(lc, tmpEntity, x, y))
			{
				case TOP:
					lc.sc.setCursor(Cursor.V_RESIZE);
					break;
				
				case BOTTOM:
					lc.sc.setCursor(Cursor.V_RESIZE);
					break;
					
				case LEFT:
					lc.sc.setCursor(Cursor.H_RESIZE);
					break;
					
				case RIGHT:
					lc.sc.setCursor(Cursor.H_RESIZE);
					break;
					
				case INSIDE:
					lc.sc.setCursor(Cursor.MOVE);
					break;
					
				case OUTSIDE:
					lc.sc.setCursor(Cursor.OPEN_HAND);
					break;
			}
			
		}
		else
		{
			// draw selection for object when pointer over it. And clean screen when pointer out of some object
			
			if((tmpEntity = CanvasActions.isInObject(lc, x, y)) != null)
			{
				lc.sc.setCursor(Cursor.HAND);
				CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
				CanvasEffectsAndActionsActions.drawSelectedArea(lc, tmpEntity);
			}
			else
			{
				lc.sc.setCursor(Cursor.OPEN_HAND);
				CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
			}
		}
	}
	
	public Entity copyEntity(Entity sourse)
	{
		Entity finals;

		switch(sourse.getClass().toString())
		{
			case "class Obstacle":
				
				finals = new Obstacle(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				
				if(sourse.getObject().size() != 0)
				{
					for(Entity e : sourse.getObject())
					{
						Entity subEnt = copyEntity(e);
						finals.addObject(subEnt);
					}
				}
				
				break;
				
			case "class Stalactite":
				
				finals = new Stalactite(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				break;
				
			case "class DwarfDriller":
				
				finals = new DwarfDriller(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				break;
				
			case "class DwarfWarrior":
				
				finals = new DwarfWarrior(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				break;
				
			case "class DwarfWorker":
	
				finals = new DwarfWorker(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				break;
				
			case "class DrilledMount":
				finals = new DrilledMount(sourse.getX(), sourse.getY(), sourse.getWidht(), sourse.getHeitht());
				break;
				
			default:
				finals = new Entity();
		}
		
		return finals;
	}
	
	public MenuBar createMainMenu()
	{
		mb = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		MenuItem open = new MenuItem("open");
		MenuItem save = new MenuItem("save");
		MenuItem newL = new MenuItem("new");
		
		fileMenu.getItems().addAll(open, save, newL);
		
		levelMenu = new Menu("levels");
		
		currentLevelM = new Menu("Current level : " + (currentLevel + 1));
		currentLevelM.setDisable(true);
		Menu numberOfLevels = new Menu("number of Levels 1");
		numberOfLevels.setDisable(true);

		mb.getMenus().add(fileMenu);
		mb.getMenus().add(levelMenu);
		mb.getMenus().add(currentLevelM);
		mb.getMenus().add(numberOfLevels);

		EventHandler<ActionEvent> mainMenuHandler = new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent ae) 
				{
					switch(((MenuItem)ae.getTarget()).getText())
					{
						case "open":
							
							try 
							{
								levelContainer = new ArrayList<LevelContainer>(SaveDocument.loadXML());

								/*-------------------------------------------------------------------*/
								
								objects = new ArrayList<Entity>(levelContainer.get(0).getObjects());
								
								levelWidth = levelContainer.get(0).getLWidht();
								levelHeight = levelContainer.get(0).getLHeight();
								
								widthOffset = 0;
								heightOffset = levelHeight - screenHeight;
		
								CanvasMainActions.reDrawAll(lc);
								
								/*-------------------------------------------------------------------*/
								
								numberOfLevels.setText("number of Levels" + String.valueOf(levelContainer.size()));
								createLevelItemMenu();
			
								/*-------------------------------------------------------------------*/
							} 
							catch (Exception e1) 
							{
								e1.printStackTrace();
							}
						
						break;
						
						case "save":
							
							try 
							{
								/*--------------------------save last level--------------------------*/
								
								if(levelContainer.size() == 0)
									levelContainer.add(new LevelContainer(levelWidth, levelHeight, objects));
								else
									levelContainer.get(currentLevel).setLevelContainer(levelWidth, levelHeight, objects);
								
								/*-------------------------------------------------------------------*/
								
								SaveDocument.createXML(levelContainer);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							} 
	
						break;
						
						case "new":
							
							/*-------------------------------------------------------------------*/
							
							if(levelContainer.size() == 0)
								levelContainer.add(new LevelContainer(levelWidth, levelHeight, objects));
							else
								levelContainer.get(currentLevel).setLevelContainer(levelWidth, levelHeight, objects);
							
							levelContainer.add(new LevelContainer(screenWidth, screenHeight, new ArrayList<Entity>()));
							
							currentLevel = levelContainer.size() - 1;
							currentLevelM.setText("Current level : " + String.valueOf(currentLevel + 1));
							numberOfLevels.setText("number of Levels" + String.valueOf(levelContainer.size()));
						
							objects = new ArrayList<Entity>(levelContainer.get(currentLevel).getObjects());
							
							levelWidth = levelContainer.get(currentLevel).getLWidht();
							levelHeight = levelContainer.get(currentLevel).getLHeight();
							
							widthOffset = 0;
							heightOffset = levelHeight - screenHeight;
	
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							CanvasMainActions.reDrawAll(lc);
							
							createLevelItemMenu();
						
							/*-------------------------------------------------------------------*/
							
						break;
						
						default:	
						break;
					}
				}
			};
			
		open.setOnAction(mainMenuHandler);
		save.setOnAction(mainMenuHandler);
		newL.setOnAction(mainMenuHandler);
		
		return mb;
	}
	
	public ToolBar createToolBar()
	{
		Button bDwarfDriller = new Button(TLBR_BTN_NAME_DWARF_DRILLER, new ImageView(ICON_DWARF_DRILLER));
		Button bDwarfWarrior = new Button(TLBR_BTN_NAME_DWARF_WARRIOR, new ImageView(ICON_DWARF_WARRIOR));
		Button bDwarfWorker = new Button(TLBR_BTN_NAME_DWARF_WORKER, new ImageView(ICON_DWARF_WORKER));
		Button bObsacle = new Button(TLBR_BTN_NAME_OBSTACLE, new ImageView(ICON_OBSTACLE));
		Button bStalactite = new Button(TLBR_BTN_NAME_STALACTITE, new ImageView(ICON_STALACTITE));
		Button bDrilledMount = new Button(TLBR_BTN_NAME_DRILLED_MOUNT, new ImageView(ICON_DRILLED_MOUNT));
		Button bMoveScreen = new Button(TLBR_BTN_NAME_MOVE_SCREEN, new ImageView(ICON_MOVE_SCREEN));
		Button bBasked = new Button(TLBR_BTN_NAME_BASKED, new ImageView(ICON_BASKED));
		
		bDwarfDriller.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bObsacle.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bStalactite.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bDrilledMount.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bMoveScreen.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bBasked.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bDwarfWarrior.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		bDwarfWorker.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		
		bDwarfDriller.setTooltip(new Tooltip("Dwarf Driller"));
		bObsacle.setTooltip(new Tooltip("Obsacle"));
		bStalactite.setTooltip(new Tooltip("Stalactite"));
		bDrilledMount.setTooltip(new Tooltip("Drilled Mount"));
		bMoveScreen.setTooltip(new Tooltip("Move Screen"));
		bBasked.setTooltip(new Tooltip("Basked"));
		bDwarfWarrior.setTooltip(new Tooltip("Dwarf Warrior"));
		bDwarfWorker.setTooltip(new Tooltip("Dwarf Worker"));
		
		leftToolBar = new ToolBar(bDwarfDriller, bDwarfWarrior, bDwarfWorker, bObsacle, bStalactite, bDrilledMount, bMoveScreen, bBasked);
		
		leftToolBar.setOrientation(Orientation.VERTICAL);
		
		EventHandler<ActionEvent> toolBarHandler = new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event) 
				{
					switch(((Button)event.getTarget()).getText())
					{
						case TLBR_BTN_NAME_DWARF_DRILLER:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_DWARF_DRILLER;
						break;
						
						case TLBR_BTN_NAME_DWARF_WARRIOR:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_DWARF_WARRIOR;
						break;
						
						case TLBR_BTN_NAME_DWARF_WORKER:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_DWARF_WORKER;
						break;
						
						case TLBR_BTN_NAME_OBSTACLE:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_OBSTACLE;
						break;
						
						case TLBR_BTN_NAME_STALACTITE:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_STALACTITE;
						break;
						
						case TLBR_BTN_NAME_DRILLED_MOUNT:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = TLBR_CHOICE_DRILLED_MOUNT;
						break;
						
						case TLBR_BTN_NAME_MOVE_SCREEN:
							entityChoice = TLBR_CHOICE_MOVE_SCREEN;
							//changeObject = false;
						break;
						
						case TLBR_BTN_NAME_BASKED:
							entityChoice = TLBR_CHOICE_BASKED;
						break;
						
						default:
							lc.sc.setCursor(Cursor.DEFAULT);
							entityChoice = ENTITY_CHOICE_NOTHING;
					}
					
					CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
				}
			};
			
		bDwarfDriller.setOnAction(toolBarHandler);
		bObsacle.setOnAction(toolBarHandler);
		bStalactite.setOnAction(toolBarHandler);
		bDrilledMount.setOnAction(toolBarHandler);
		bMoveScreen.setOnAction(toolBarHandler);
		bBasked.setOnAction(toolBarHandler);
		bDwarfWarrior.setOnAction(toolBarHandler);
		bDwarfWorker.setOnAction(toolBarHandler);
		
		return leftToolBar;
	}
	
	@Override
	public void start(Stage mainStage)
	{
		/*----------------initial variables initialization---------------*/
		
		lc = this;
		tmpEntity = null;
		objects = new ArrayList<Entity>();
		levelContainer = new ArrayList<LevelContainer>();
		changeObject = false;
		currentLevel = 0;
	
		mainStage.setTitle("Конструктор уровней");

		mainPane = new BorderPane();
		sc = new Scene(mainPane, START_SCREEN_WIDHT, START_SCREEN_HEIGHT);		
		mainStage.setScene(sc);

		canvasPane = new StackPane();

		/*-----------------------create main menu------------------------*/
		
		mb = createMainMenu();

		/*-----------------------create tool bar-------------------------*/
		
		leftToolBar = createToolBar();
		
		/*----------------add elements to main panel---------------------*/
		
		mainPane.setTop(mb);
		mainPane.setLeft(leftToolBar);
		mainPane.setCenter(canvasPane);
		
		/*---------------------------------------------------------------*/
		
		canvasLine = new Canvas(sc.getWidth(), sc.getHeight());
		gcLine = canvasLine.getGraphicsContext2D();
		
		canvasMain = new Canvas(sc.getWidth(), sc.getHeight());
		gcMain = canvasMain.getGraphicsContext2D();
		
		canvasEffectsAndActions = new Canvas(sc.getWidth(), sc.getHeight());
		gcEffects = canvasEffectsAndActions.getGraphicsContext2D();

		canvasPane.getChildren().addAll(canvasLine, canvasMain, canvasEffectsAndActions);

		/*---------------------------------------------------------------*/
		
		canvasEffectsAndActions.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent e) 
				{
					switch(entityChoice)
					{
						case TLBR_CHOICE_DWARF_DRILLER:
							
							break;
						
						case TLBR_CHOICE_OBSTACLE:
							tmpEntity = new Obstacle(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
							
						case TLBR_CHOICE_STALACTITE:
							
							break;
							
						case TLBR_CHOICE_DRILLED_MOUNT:
							break;
							
						case TLBR_CHOICE_MOVE_SCREEN:
							
							
							
							/*-------------------------------------------------------------------*/
	
							if(((tmpEntity != null  && moveResizeAction != OUTSIDE) || 
								(tmpEntity = CanvasActions.isInObject(lc, e.getX(), e.getY())) != null))
							{
								lc.sc.setCursor(Cursor.HAND);
								changeObject = true;
								CanvasEffectsAndActionsActions.drawSelectedArea(lc, tmpEntity);
							}
							else
							{
								lc.sc.setCursor(Cursor.OPEN_HAND);
								changeObject = false;
								CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							}
							
							/*-------------------------------------------------------------------*/
							
							chooseCursor(e.getX(), e.getY());
							
							/*-------------------------------------------------------------------*/
							
							if(lc.changeObject || (tmpEntity != null && moveResizeAction != OUTSIDE))
							{
								// move one object or change it size
								
								movedScreenPoint = new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							
								entityMoveResize = copyEntity(tmpEntity);
								//entityMoveResize = new Obstacle(tmpEntity.getX(), tmpEntity.getY(), tmpEntity.getWidht(), tmpEntity.getHeitht());
						
								CanvasMainActions.clearArea(lc, tmpEntity);
							}
							else
							{
								// move all screen 
								movedScreenPoint = new MyPoint(e.getX(), e.getY());
							}
							
							/*-------------------------------------------------------------------*/
							
							break;
							
						case TLBR_CHOICE_BASKED:
							movedScreenPoint = new MyPoint(e.getX(), e.getY());
							break;
					}
				}
			});
		
		canvasEffectsAndActions.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent e) 
				{
					switch(entityChoice)
					{
						case TLBR_CHOICE_DWARF_DRILLER:
							break;
						
						case TLBR_CHOICE_OBSTACLE:
							CanvasEffectsAndActionsActions.drawObstacle(lc, tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							
							break;
							
						case TLBR_CHOICE_STALACTITE:
							break;
							
						case TLBR_CHOICE_DRILLED_MOUNT:
							break;
							
						case TLBR_CHOICE_MOVE_SCREEN:
							
							if(lc.changeObject)
							{
								// move one object or change it size
								
								switch(moveResizeAction)
								{
									case TOP:
										CanvasEffectsAndActionsActions.resizeEntityTop(lc, tmpEntity, movedScreenPoint, 
												new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc,e.getY())));
										break;
									
									case BOTTOM:
										
										CanvasEffectsAndActionsActions.resizeEntityBottom(lc, tmpEntity, movedScreenPoint, 
												new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc,e.getY())));
										break;
										
									case LEFT:
										CanvasEffectsAndActionsActions.resizeEntityLeft(lc, tmpEntity, movedScreenPoint, 
												new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc,e.getY())));
										break;
										
									case RIGHT:
										CanvasEffectsAndActionsActions.resizeEntityRight(lc, tmpEntity, movedScreenPoint, 
												new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc,e.getY())));
										break;
										
									case INSIDE:
										CanvasEffectsAndActionsActions.moveEntity(lc, tmpEntity, movedScreenPoint, 
												new MyPoint(CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc,e.getY())));
										break;
										
									case OUTSIDE:
										
										break;
								}
							}
							else
							{
								// move all screen 
								
								lc.sc.setCursor(Cursor.CLOSED_HAND);
								
								CanvasActions.setOffset(lc, e.getX(), e.getY());
								CanvasLineActions.reDrawLines(lc);
								CanvasMainActions.reDrawAll(lc);
							}
							
							break;
							
						case TLBR_CHOICE_BASKED:
							lc.sc.setCursor(Cursor.CLOSED_HAND);
							
							CanvasActions.setOffset(lc, e.getX(), e.getY());
							CanvasLineActions.reDrawLines(lc);
							CanvasMainActions.reDrawAll(lc);
							break;
					}
				}
			});
		
		canvasEffectsAndActions.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent e) 
				{
					switch(entityChoice)
					{
						case TLBR_CHOICE_DWARF_DRILLER:
							break;
						
						case TLBR_CHOICE_OBSTACLE:
							CanvasMainActions.createObstacle(lc, tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							lc.intersection = false;
							break;
							
						case TLBR_CHOICE_STALACTITE:
							break;
							
						case TLBR_CHOICE_DRILLED_MOUNT:
							break;
							
						case TLBR_CHOICE_MOVE_SCREEN:
							
							if(lc.changeObject)
							{
								// move one object or change it size
								
								switch(moveResizeAction)
								{
									case TOP:
										
										CanvasMainActions.finishResizeEntity(lc, tmpEntity, entityMoveResize);
										
										lc.sc.setCursor(Cursor.OPEN_HAND);
										changeObject = false;
										CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
										break;
									
									case BOTTOM:
										CanvasMainActions.finishResizeEntity(lc, tmpEntity, entityMoveResize);
										
										lc.sc.setCursor(Cursor.OPEN_HAND);
										changeObject = false;
										CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
										break;
										
									case LEFT:
										CanvasMainActions.finishResizeEntity(lc, tmpEntity, entityMoveResize);
										
										lc.sc.setCursor(Cursor.OPEN_HAND);
										changeObject = false;
										CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
										break;
										
									case RIGHT:
										CanvasMainActions.finishResizeEntity(lc, tmpEntity, entityMoveResize);
										
										lc.sc.setCursor(Cursor.OPEN_HAND);
										changeObject = false;
										CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
										break;
										
									case INSIDE:
										
										CanvasMainActions.finishMoveEntity(lc, tmpEntity, entityMoveResize);
										
										lc.sc.setCursor(Cursor.OPEN_HAND);
										changeObject = false;
										CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
										
										break;
										
									case OUTSIDE:
										
										break;
								}								
							}
							else
							{
								// move all screen 
								
							}
							
							break;
					}
					
					tmpEntity = null;
					
					
				}
			});
	 
		canvasEffectsAndActions.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent e) 
				{
					switch(entityChoice)
					{
						case TLBR_CHOICE_DWARF_DRILLER:
							CanvasMainActions.createDwarf(lc, new Image(ICON_DWARF_DRILLER), tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							break;
							
						case TLBR_CHOICE_DWARF_WORKER:
							CanvasMainActions.createDwarf(lc, new Image(ICON_DWARF_WORKER), tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							break;
							
						case TLBR_CHOICE_DWARF_WARRIOR:
							CanvasMainActions.createDwarf(lc, new Image(ICON_DWARF_WARRIOR), tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							break;
							
						case TLBR_CHOICE_OBSTACLE:
							
							break;
							
						case TLBR_CHOICE_STALACTITE:
							CanvasMainActions.createStalactite(lc, new Image(ICON_STALACTITE), tmpEntity, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							break;
							
						case TLBR_CHOICE_DRILLED_MOUNT:
							CanvasMainActions.createDrilledMount(lc, new Image(ICON_DRILLED_MOUNT), CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							break;
							
						case TLBR_CHOICE_MOVE_SCREEN:
							
							if((tmpEntity = CanvasActions.isInObject(lc, e.getX(), e.getY())) != null)
							{
								lc.sc.setCursor(Cursor.HAND);
								changeObject = true;
								CanvasEffectsAndActionsActions.drawSelectedArea(lc, tmpEntity);
							}
							else
							{
								lc.sc.setCursor(Cursor.OPEN_HAND);
								changeObject = false;
								CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							}
							break;
							
						case TLBR_CHOICE_BASKED:
							if((tmpEntity = CanvasActions.isInObject(lc, e.getX(), e.getY())) != null)
								CanvasMainActions.deleteObject(lc, tmpEntity);
							break;
							
					}
				}
			});
	
		canvasEffectsAndActions.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() 
			{
				@Override
				public void handle(MouseEvent e) 
				{
					switch(entityChoice)
					{
						case TLBR_CHOICE_DWARF_DRILLER:
							CanvasEffectsAndActionsActions.drawDwarf(lc, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
						
						case TLBR_CHOICE_DWARF_WORKER:
							CanvasEffectsAndActionsActions.drawDwarf(lc, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
							
						case TLBR_CHOICE_DWARF_WARRIOR:
							CanvasEffectsAndActionsActions.drawDwarf(lc, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
							
						case TLBR_CHOICE_OBSTACLE:
							CanvasEffectsAndActionsActions.drawPointer(lc, e.getX(), e.getY());
							
							break;
							
						case TLBR_CHOICE_STALACTITE:
							CanvasEffectsAndActionsActions.drawStalactite(lc, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
							
						case TLBR_CHOICE_DRILLED_MOUNT:
							CanvasEffectsAndActionsActions.drawDrilledMount(lc, CanvasActions.xCorrection(lc, e.getX()), CanvasActions.yCorrection(lc, e.getY()));
							break;
							
						case TLBR_CHOICE_MOVE_SCREEN:
							chooseCursor(e.getX(), e.getY());
							break;
							
						case TLBR_CHOICE_BASKED:
							if((tmpEntity = CanvasActions.isInObject(lc, e.getX(), e.getY())) != null)
							{
								lc.sc.setCursor(Cursor.HAND);
								CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
								CanvasEffectsAndActionsActions.drawSelectedArea(lc, tmpEntity);
							}
							else
							{
								lc.sc.setCursor(Cursor.OPEN_HAND);
								CanvasEffectsAndActionsActions.clearEffectsCanvas(lc);
							}
							break;
					}
				}
			});
		
		/*---------------------------------------------------------------*/

		mainStage.show();

		screenWidth = levelWidth = sc.getWidth() - leftToolBar.getWidth();
		widthOffset = 0;
		
		screenHeight = levelHeight = sc.getHeight() - mb.getHeight();
		heightOffset = 0;
		
		canvasLine.setWidth(screenWidth);
		canvasLine.setHeight(screenHeight);
		
		canvasMain.setWidth(screenWidth);
		canvasMain.setHeight(screenHeight);
		
		canvasEffectsAndActions.setWidth(screenWidth);
		canvasEffectsAndActions.setHeight(screenHeight);
		
		mainStage.setMinHeight(mainStage.getHeight());
		mainStage.setMinWidth(mainStage.getWidth());
		
		mainStage.setMaxHeight(mainStage.getHeight());
		mainStage.setMaxWidth(mainStage.getWidth());
		
		levelWidth = levelWidth + 150;
		levelHeight = levelHeight + 150;
		widthOffset = 0;
		heightOffset = 150;
		
		/*---------------------------------------------------------------*/
		
		CanvasLineActions.drawLines(this);
	}
}
