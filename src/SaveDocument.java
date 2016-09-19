import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javafx.stage.FileChooser;

public class SaveDocument implements Environments
{
	SaveDocument()
	{
		
	}
	
	private static ArrayList<Entity> createLevelArray(double w, double h, Node n)
	{
		ArrayList<Entity> alE = new ArrayList<Entity>();
			
		if(n.hasChildNodes())
		{
			NodeList iter = n.getChildNodes();
		
			for(int i = 0; i < iter.getLength(); i++)
			{
				Entity e = null;  

				switch(iter.item(i).getNodeName())
				{
					case XML_NAME_OBSTACLE:
						e = new Obstacle(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
						
					
					case XML_NAME_DWARF_DRILLER:
						e = new DwarfDriller(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
						
					case XML_NAME_DWARF_WARRIOR:
						e = new DwarfWarrior(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
						
					case XML_NAME_DWARF_WORKER:
						e = new DwarfWorker(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
						
					case XML_NAME_DRILLED_MOUNT:
						e = new DrilledMount(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
						
					case XML_NAME_STALACTITE:
						e = new Stalactite(Double.valueOf(iter.item(i).getAttributes().getNamedItem("x").getNodeValue()) * DX,
								(h - Double.valueOf(iter.item(i).getAttributes().getNamedItem("y").getNodeValue())) * DY,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("w").getNodeValue()) * DX,
								Double.valueOf(iter.item(i).getAttributes().getNamedItem("h").getNodeValue()) * DY);
						break;
				}
				
				if(iter.item(i).hasChildNodes())
				{
					ArrayList<Entity> tmp;
					if((tmp = createLevelArray(w,h, iter.item(i))) != null)
						e.addObjects(tmp);
				}
				
				if(e != null) 
					alE.add(e);
			}
		}
		
		return alE.size() > 0 ? alE : null;
	}
	
	public static ArrayList<LevelContainer> loadXML() throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		FileChooser chooseFile = new FileChooser();
		File file = chooseFile.showOpenDialog(null);
		
		ArrayList<LevelContainer> lc = new ArrayList<LevelContainer>();

		if(file != null)
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		    
		    DocumentBuilder db = dbf.newDocumentBuilder(); 
		    Document doc = db.parse(file);

		    DOMSource source = new DOMSource(doc); 

		    DOMResult result = new DOMResult();
			
			TransformerFactory transFactory = TransformerFactory.newInstance(); 
		    Transformer transformer = transFactory.newTransformer(); 

		    transformer.transform(source, result); 
		    Node node = result.getNode().getFirstChild(); // get levels container
		    
		    NodeList level = node.getChildNodes();
		    
		    
		    for(int i = 0; i < level.getLength(); i++)
		    {
		    	if(level.item(i).getNodeName() == "level")
		    	{
		    		double levelWidth = Double.valueOf(level.item(i).getAttributes().getNamedItem("w").getNodeValue());
		    		double levelHeight = Double.valueOf(level.item(i).getAttributes().getNamedItem("h").getNodeValue());
		    		
		    		lc.add(new LevelContainer(levelWidth * DX, levelHeight * DY, createLevelArray(levelWidth, levelHeight, level.item(i))));
		    	}	
		    }
		}
		else
		{
			// no
		}
	
		return lc;
	}
	
	public static void createXML(ArrayList<LevelContainer> levelContainer) 
			throws ParserConfigurationException, TransformerException
	{
		FileChooser chooseFile = new FileChooser();
		File file = chooseFile.showSaveDialog(null);

		if(file != null)
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		    DocumentBuilder db = dbf.newDocumentBuilder(); 
		    Document doc = db.newDocument();
		    
		    Element root = doc.createElement("levels"); 
		    doc.appendChild(root); 
		    
		    
		    int i = 0;
		    for(LevelContainer lc : levelContainer)
		    {
		    	double width = lc.getLWidht();
		    	double height = lc.getLHeight();
		    	
		    	ArrayList<Entity> ar = new ArrayList<Entity>(lc.getObjects());
		    	
		    	
		    	
		    	/*-----------------------------------------------------------*/
			    
			    Element level = doc.createElement("level"); 
			    level.setAttribute("w", String.valueOf(Math.round(width / DX)));
			    level.setAttribute("h", String.valueOf(Math.round(height / DY)));
			    level.setAttribute("n", String.valueOf(i));
			    root.appendChild(level);
			    
			    /*-----------------------------------------------------------*/
			    
			    for(Entity e : ar)
			    {
			    	Element el = doc.createElement(e.getXMLName());
			    	
			    	el.setAttribute("x", String.valueOf(e.getX() / DX));
			    	el.setAttribute("y", String.valueOf((height - e.getY()) / DY));
			    	el.setAttribute("w", String.valueOf(e.getWidht() / DX));
			    	el.setAttribute("h", String.valueOf(e.getHeitht() / DY));

			    	if(e.getObject().size() > 0)
			    	{
			    		for(Entity subE : e.getObject())
			    		{
			    			Element subEl = doc.createElement(subE.getXMLName());
			    			
			    			subEl.setAttribute("x", String.valueOf(subE.getX() / DX));
			    			subEl.setAttribute("y", String.valueOf((height - subE.getY()) / DY));
			    			subEl.setAttribute("w", String.valueOf(subE.getWidht() / DX));
			    			subEl.setAttribute("h", String.valueOf(subE.getHeitht() / DY));
			    			
			    			el.appendChild(subEl);
			    		}
			    	}
			    	
			    	level.appendChild(el);
			    }
			    
			    i++;
		    }

		    /*-----------------------------------------------------------*/
		    
			try 
			{
				DOMSource source = new DOMSource(doc); 
			    StreamResult result;
			    
				result = new StreamResult(new FileOutputStream(file));
				
				TransformerFactory transFactory = TransformerFactory.newInstance(); 
			    Transformer transformer = transFactory.newTransformer(); 
			    transformer.transform(source, result); 
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else
			System.out.println("no");	
	}
}
