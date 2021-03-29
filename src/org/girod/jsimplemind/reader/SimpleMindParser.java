/*
Copyright (C) 2021 Herve Girod

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/jSimpleMind
 */
package org.girod.jsimplemind.reader;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.girod.jsimplemind.model.SimpleMindDiagram;
import org.girod.jsimplemind.model.Topic;
import org.girod.jsimplemind.xml.XMLNode;
import org.girod.jsimplemind.xml.XMLRoot;
import org.xml.sax.SAXException;

/**
 * The parser class.
 *
 * @version 0.3
 */
public class SimpleMindParser {
   private SimpleMindDiagram diagram = null;
   private NoteParser noteParser = null;
   private DiagramFactory factory = null;

   public SimpleMindParser() {
   }

   /**
    * Set the associated Diagram factory.
    *
    * @param factory the factory
    */
   public void setDiagramFactory(DiagramFactory factory) {
      this.factory = factory;
   }

   /**
    * Return the associated Diagram factory.
    *
    * @return the factory
    */
   public DiagramFactory getDiagramFactory() {
      return factory;
   }

   /**
    * Set the associated NoteParser to parse the content of Topic notes.
    *
    * @param noteParser the NoteParser
    */
   public void setNoteParser(NoteParser noteParser) {
      this.noteParser = noteParser;
   }

   /**
    * Return the associated NoteParser to parse the cotnetn of Topic notes.
    *
    * @return the NoteParser
    */
   public NoteParser getNoteParser() {
      return noteParser;
   }

   /**
    * ¨Parse a SimpleMind file.
    *
    * @param file the file
    * @return the diagram
    */
   public SimpleMindDiagram parse(File file) throws IOException, SAXException {
      URL url = file.toURI().toURL();
      return parse(url);
   }

   /**
    * ¨Parse a SimpleMind stream. See {@link ReaderUtilities#getSimpleMindStream(java.io.File)} or
    * {@link ReaderUtilities#getSimpleMindStream(java.net.URL)} to see how to get this stream from a SimpleMind file.
    *
    * @param stream the stream
    * @return the diagram
    */
   public SimpleMindDiagram parse(InputStream stream) throws IOException, SAXException {
      SAXParserFactory saxfactory = SAXParserFactory.newInstance();
      try {
         SAXParser parser = saxfactory.newSAXParser();
         XMLTreeHandler handler = new XMLTreeHandler();
         parser.parse(stream, handler);
         return walk(handler.getRoot());
      } catch (ParserConfigurationException ex) {
         ex.printStackTrace();
         return null;
      }
   }

   /**
    * ¨Parse a SimpleMind URL.
    *
    * @param url the URL
    * @return the diagram
    */
   public SimpleMindDiagram parse(URL url) throws IOException, SAXException {
      SAXParserFactory saxfactory = SAXParserFactory.newInstance();
      try {
         SAXParser parser = saxfactory.newSAXParser();
         XMLTreeHandler handler = new XMLTreeHandler();
         parser.parse(url.openStream(), handler);
         return walk(handler.getRoot());
      } catch (ParserConfigurationException ex) {
         ex.printStackTrace();
         return null;
      }
   }

   private Topic createTopic(int id) {
      Topic topic;
      if (factory != null) {
         topic = factory.createTopic(id);
         if (topic == null) {
            topic = new Topic(id);
         }
      } else {
         topic = new Topic(id);
      }
      return topic;
   }

   private void createDiagram() {
      if (factory != null) {
         diagram = factory.createDiagram();
      } else {
         diagram = new SimpleMindDiagram();
      }
   }

   private SimpleMindDiagram walk(XMLRoot root) {
      List<Topic> topics = new ArrayList<>();
      createDiagram();
      if (noteParser != null) {
         noteParser.resetState(diagram);
      }
      Map<Integer, Integer> parents = new HashMap<>();
      XMLTreeWalker walker = new XMLTreeWalker(root);
      Topic topic = null;
      boolean hasTextColor = false;
      boolean hasStrokeColor = false;
      boolean hasFillColor = false;
      while (walker.hasNext()) {
         XMLNode node = walker.nextNode();
         String name = node.getName();
         if (name.equals("topic")) {
            int id = node.getAttributeValueAsInt("id");
            topic = createTopic(id);
            hasTextColor = false;
            hasStrokeColor = false;
            hasFillColor = false;
            int parentid = node.getAttributeValueAsInt("parent", -1);
            if (parentid != -1) {
               parents.put(id, parentid);
            }
            if (node.hasAttribute("text")) {
               topic.setText(node.getAttributeValue("text").trim());
            }
            diagram.addTopic(topic);
            if (node.hasAttribute("x") && node.hasAttribute("y")) {
               int x = node.getAttributeValueAsInt("x");
               int y = node.getAttributeValueAsInt("y");
               topic.setPosition(x, y);
            }
            topics.add(topic);
         } else if (name.equals("fillcolor") && topic != null && !hasFillColor) {
            hasFillColor = true;
            int red = node.getAttributeValueAsInt("r", 0);
            int green = node.getAttributeValueAsInt("g", 0);
            int blue = node.getAttributeValueAsInt("b", 0);
            Color color = new Color(red, green, blue);
            topic.setFillColor(color);
         } else if (name.equals("textcolor") && topic != null && !hasTextColor) {
            hasTextColor = true;
            int red = node.getAttributeValueAsInt("r", 0);
            int green = node.getAttributeValueAsInt("g", 0);
            int blue = node.getAttributeValueAsInt("b", 0);
            Color color = new Color(red, green, blue);
            topic.setTextColor(color);
         } else if (name.equals("strokecolor") && topic != null && !hasStrokeColor) {
            hasStrokeColor = true;
            int red = node.getAttributeValueAsInt("r", 0);
            int green = node.getAttributeValueAsInt("g", 0);
            int blue = node.getAttributeValueAsInt("b", 0);
            Color color = new Color(red, green, blue);
            topic.setStrokeColor(color);
         } else if (name.equals("note") && topic != null) {
            String cdata = node.getCDATA();
            if (cdata != null) {
               cdata = cdata.trim();
               topic.setNote(cdata);
               if (noteParser != null) {
                  noteParser.parseNote(topic, cdata);
               }
            }
         } else if (name.equals("title") && topic == null) {
            String title = node.getAttributeValue("text");
            diagram.setTitle(title);
         }
      }
      Iterator<Topic> it = topics.iterator();
      while (it.hasNext()) {
         topic = it.next();
         int topicid = topic.getID();
         if (parents.containsKey(topicid)) {
            int parentid = parents.get(topicid);
            if (diagram.hasTopic(parentid)) {
               Topic childTopic = diagram.getTopic(topicid);
               Topic parenttopic = diagram.getTopic(parentid);
               childTopic.setParent(parenttopic);
               parenttopic.addChild(childTopic);
            }
         }
      }
      if (noteParser != null) {
         noteParser.resolve();
      }      
      return diagram;
   }
}
