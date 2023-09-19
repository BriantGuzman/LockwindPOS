package pay.point.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utility class for working with the Xml libraries in java
 */
class Documents {

	private static final ThreadLocal<DocumentBuilderFactory> DOCUMENT_BUILDER_FACTORY = new ThreadLocal<DocumentBuilderFactory>() {
		@Override
		public DocumentBuilderFactory initialValue() {
			return DocumentBuilderFactory.newInstance();
		}
	};

	private static final ThreadLocal<TransformerFactory> TRANSFORMER_FACTORY = new ThreadLocal<TransformerFactory>() {
		@Override
		public TransformerFactory initialValue() {
			return TransformerFactory.newInstance();
		}
	};
	
	private static final ThreadLocal<XMLInputFactory> xmlInputFactory = new ThreadLocal<XMLInputFactory>() {
		@Override
		public XMLInputFactory initialValue() {
			return XMLInputFactory.newInstance();
     	}
	};
	
	private static final ThreadLocal<XMLEventFactory> xmlEventFactory = new ThreadLocal<XMLEventFactory>() {
		@Override
		public XMLEventFactory initialValue() {
			return XMLEventFactory.newInstance();
     	}
	};
	
	private static final ThreadLocal<XMLOutputFactory> xmlOutputFactory = new ThreadLocal<XMLOutputFactory>() {
		@Override
		public XMLOutputFactory initialValue() {
			return XMLOutputFactory.newInstance();
     	}
	};

	/**
	 * Adds the element name with inner text to the specified element
	 */
	public static Element addElement(Element parent, String key, String value) {
		final Element child = parent.getOwnerDocument().createElement(key);
		child.setTextContent(value);
		parent.appendChild(child);
		return child;
	}

	/**
	 * Gets the first Element with a matching element name
	 */
	public static String selectFirst(Element parent, String key, String defaultValue) {
		
		final NodeList nodeList = parent.getElementsByTagName(key);
		for (int i = 0, f = nodeList.getLength(); i < f; i++) {
			final Node node = nodeList.item(i);
			if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
				return node.getTextContent();
			}
		}

		return defaultValue;
	}

	/**
	 * Prints document to a String and returns it
	 * 
	 * @param document to be printed
	 * @return String containing document
	 * @throws TransformerException
	 */
	public static String print(Document document, boolean pretty) throws TransformerException
	{
		final StringWriter writer = new StringWriter();
		final Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		if (pretty) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		}
		
		transformer.transform(new DOMSource(document), new StreamResult(writer));
		return writer.toString();
	}
	
	/**
	 * Prints document to a String and returns it
	 * 
	 * @param document to be printed
	 * @return String containing document
	 * @throws TransformerException
	 */
	public static void write(Document document, OutputStream outputStream) throws TransformerException
	{
		final Transformer transformer = TRANSFORMER_FACTORY.get().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.transform(new DOMSource(document), new StreamResult(outputStream));
	}
	
	/**
	 * Creates a new Document with the contents read from the String
	 */
	public static Document parseXml(String xmlString) throws ParserConfigurationException, SAXException, IOException 
	{
		final DocumentBuilder docBuilder =  DOCUMENT_BUILDER_FACTORY.get().newDocumentBuilder();
		return docBuilder.parse(new InputSource(new StringReader(xmlString)));
	}
	
	/**
	 * Creates a new Document with the contents read from the InputStream 
	 * without closing the stream
	 */
	public static Document parse(InputStream channel) throws ParserConfigurationException, SAXException, IOException, TransformerException, XMLStreamException 
    {
		final Document doc = Documents.create();
		final DOMResult domResult = new DOMResult(doc);
		final XMLEventWriter domWriter = xmlOutputFactory.get().createXMLEventWriter(domResult);
		final XMLEventReader reader = xmlInputFactory.get().createXMLEventReader(channel);
		
		int depth = 0;
		upper:
		while (reader.hasNext()) 
		{
			 final XMLEvent evt = reader.nextEvent();

			 switch (evt.getEventType()) 
			 {
			 	case XMLEvent.START_DOCUMENT:
			 		domWriter.add(xmlEventFactory.get().createStartDocument());
			 		break;
			 		
		        case XMLEvent.START_ELEMENT:
		        	domWriter.add(evt);
		            depth++;
		            break;

		        case XMLEvent.END_ELEMENT:
		        	domWriter.add(evt);
		            if (--depth == 0) 
		            {                       
		                domWriter.add(xmlEventFactory.get().createEndDocument());
		                reader.close();
		                break upper;
		            }
		            break;   
	            default:
	            	domWriter.add(evt);
	            	break;
			 }
		}

		return doc;
    }
	
	public static Document create() {
		try {
			final DocumentBuilder docBuilder = DOCUMENT_BUILDER_FACTORY.get().newDocumentBuilder();
			final Document xmldoc = docBuilder.newDocument();
			return xmldoc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Document create(String documentElement) {
		final Document xmldoc = Documents.create();
		final Element rootElement = xmldoc.createElement(documentElement);
		xmldoc.appendChild(rootElement);

		return xmldoc;
	}
	
	private Documents() { }
}