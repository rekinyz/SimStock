package com.msggillardon.foundation.util.tomcat;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.catalina.Container;
import org.apache.catalina.core.StandardContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Class for adding folders to the application path of TOMCAT web apps outside of the web apps.<br>
 * <br>
 * <br>
 * <br>
 * A C H T U N G: the repositories/folders added in here are added AFTER the webapp classpath!!!!!!!!! <br>
 * <br>
 * <br>
 * <br>
 */
public class WebappExtender extends org.apache.catalina.loader.WebappLoader {
	private Logger LOG = Logger.getLogger(WebappExtender.class.getName());
	private static final String FOLDER = "conf";

	// zum debuggen hilft es, wenn der Tomcat vor dem Deployment gestartet wird, dann Debug vebinden und deployen.
	@Override
	public void setContainer(final Container container) {

		if (container instanceof StandardContext) {
			StandardContext ctx = (StandardContext) container;
			try {
				String catHome = System.getProperty("catalina.home");
				String catBase = System.getProperty("catalina.base");
				LOG.info("catalina.home: " + catHome);
				LOG.info("catalina.base: " + catBase);
				LOG.info("loading context.xml: " + ctx.getConfigFile());
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(ctx.getConfigFile().toURI().toString());
				File baseFolder = new File(catBase, FOLDER);
				File homeFolder = new File(catHome, FOLDER);
				NodeList list = doc.getElementsByTagName("folder");
				if (list.getLength() > 0 && !baseFolder.exists()) {
					LOG.info("catalina.base " + catBase + "/" + FOLDER + " folder does not exist, looking into catalina.home " + catHome + "/"
						+ FOLDER + " folder!");
					if (list.getLength() > 0 && !homeFolder.exists())
						throw new IOException("Neither catalina.base " + catBase + "/" + FOLDER + " folder nor catalina.home " + catHome + "/" + FOLDER
							+ " folder does not exist! Please create!");
				}
				for (int i = 0; i < list.getLength(); i++) {
					String subFolderName = list.item(i).getTextContent().trim();
					if (subFolderName.isEmpty()) {
						LOG.warning("context.xml <folder> tag must not be empty");
						continue;
					}
					File subFolder = new File(baseFolder, subFolderName);
					if (!subFolder.isDirectory() || !subFolder.canRead()) {
						LOG.warning("The configured folder in catalina.base does not exist or is not a directory or cannot be read: "
							+ baseFolder.getAbsolutePath() + "/" + subFolderName);
						subFolder = new File(homeFolder, subFolderName);
						if (!subFolder.isDirectory() || !subFolder.canRead()) {
							LOG.warning("The configured folder in catalina.home does not exist or is not a directory or cannot be read: "
								+ homeFolder.getAbsolutePath() + "/" + subFolderName);
							continue;
						}
					}
					addRepository(subFolder.toURI().toString());
					// hier jars hinzu, falls mal notwendig
					LOG.info("added folder to web app " + ctx.getDocBase() + ": " + subFolder.toURI().toString());
				}
			} catch (Exception ex) {
				LOG.log(Level.SEVERE, null, ex);
			}
		}
		super.setContainer(container);
	}
}

// falls auch JARS hinzugefuegt werden sollen:
//					for (File file : subFolder.listFiles()) {
//						if (file.isDirectory()) {
//							continue;
//						}
//						if (file.getName().endsWith(".jar")) {
//							LOG.info("add library: " + file.toURI().toString() + " to conext " + ctx);
//							addRepository(file.toURI().toString());
//						}
//					}
