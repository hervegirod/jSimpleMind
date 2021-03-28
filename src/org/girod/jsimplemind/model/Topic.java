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
package org.girod.jsimplemind.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Topic.
 *
 * @version 0.2
 */
public class Topic {
   private int id;
   private String text = "";
   private String note = "";
   private Topic parent = null;
   private Color fillColor = Color.WHITE;
   private Color textColor = Color.BLACK;
   private Color strokeColor = Color.BLACK;
   private int x = 0;
   private int y = 0;
   private Map<String, Object> properties = new HashMap<>();
   private List<Topic> childrenList = new ArrayList<>();
   private Map<Integer, Topic> children = new HashMap<>();

   public Topic(int id) {
      this.id = id;
   }

   @Override
   public int hashCode() {
      int hash = 3;
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Topic other = (Topic) obj;
      if (this.id != other.id) {
         return false;
      }
      if (this.x != other.x) {
         return false;
      }
      if (this.y != other.y) {
         return false;
      }
      if (!Objects.equals(this.text, other.text)) {
         return false;
      }
      if (!Objects.equals(this.note, other.note)) {
         return false;
      }
      if (!Objects.equals(this.fillColor, other.fillColor)) {
         return false;
      }
      return true;
   }

   /**
    * Return the Topic id.
    *
    * @return the id
    */
   public int getID() {
      return id;
   }

   /**
    * Set the Topic position.
    *
    * @param x the x position
    * @param y the y position
    */
   public void setPosition(int x, int y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Return the Topic x position.
    *
    * @return the x position
    */
   public int getX() {
      return x;
   }

   /**
    * Return the Topic y position.
    *
    * @return the y position
    */
   public int getY() {
      return y;
   }

   /**
    * Set the Topic text.
    *
    * @param text the text
    */
   public void setText(String text) {
      this.text = text;
   }

   /**
    * Return the Topic text.
    *
    * @return the text
    */
   public String getText() {
      return text;
   }

   /**
    * Set the Topic fill color
    *
    * @param fillColor the fill color
    */
   public void setFillColor(Color fillColor) {
      this.fillColor = fillColor;
   }

   /**
    * Return the Topic fill color
    *
    * @return the fill color
    */
   public Color getFillColor() {
      return fillColor;
   }

   /**
    * Set the Topic text color
    *
    * @param textColor the text color
    */
   public void setTextColor(Color textColor) {
      this.textColor = textColor;
   }

   /**
    * Return the Topic text color
    *
    * @return the text color
    */
   public Color getTextColor() {
      return textColor;
   }

   /**
    * Set the Topic stroke color
    *
    * @param strokeColor the stroke color
    */
   public void setStrokeColor(Color strokeColor) {
      this.strokeColor = strokeColor;
   }

   /**
    * Return the Topic stroke color
    *
    * @return the stroke color
    */
   public Color getStrokeColor() {
      return strokeColor;
   }

   /**
    * Add a property for the Topic. properties can be set using the cotnetn of the Topic <code>note</code> by
    * {@link org.girod.jsimplemind.reader.NoteParser}s.
    *
    * @param key the property key
    * @param property the property value
    */
   public void addProperty(String key, Object property) {
      properties.put(key, property);
   }

   /**
    * Return true if there is a property for a specified key.
    *
    * @param key the key
    * @return true if there is a property for the specified key
    */
   public boolean hasProperty(String key) {
      return properties.containsKey(key);
   }

   /**
    * Return the property for a specified key.
    *
    * @param key the key
    * @return the property for the specified key
    */
   public Object getProperty(String key) {
      return properties.get(key);
   }

   /**
    * Return the properties for the Topic.
    *
    * @return the properties
    */
   public Map<String, Object> getProperties() {
      return properties;
   }

   /**
    * Set the Topic node. By default the not is empty.
    *
    * @param note the note
    */
   public void setNote(String note) {
      this.note = note;
   }

   /**
    * Return the Topic note.
    *
    * @return the note
    */
   public String getNote() {
      return note;
   }

   /**
    * Add a child Topic.
    *
    * @param topic the Topic
    */
   public void addChild(Topic topic) {
      childrenList.add(topic);
      children.put(topic.getID(), topic);
   }

   /**
    * Return the ordered list of children topics.
    *
    * @return the list of children topics
    */
   public List<Topic> getChildrenList() {
      return childrenList;
   }

   /**
    * Return the Map of children topics
    *
    * @return the map
    */
   public Map<Integer, Topic> getChildren() {
      return children;
   }

   /**
    * Set the Topic parent.
    *
    * @param parent the parent Topic
    */
   public void setParent(Topic parent) {
      this.parent = parent;
   }

   /**
    * Return the Topic parent.
    *
    * @return the parent Topic
    */
   public Topic getParent() {
      return parent;
   }

   /**
    * Return true if the Topic has a parent.
    *
    * @return true if the Topic has a parent
    */
   public boolean hasParent() {
      return parent != null;
   }
}
