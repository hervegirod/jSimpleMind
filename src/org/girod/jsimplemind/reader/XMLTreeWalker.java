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

import org.girod.jsimplemind.xml.XMLNode;

/**
 *
 * @since 0.1
 */
class XMLTreeWalker {
   private final XMLNode rootNode;
   private XMLNode currentNode;
   private int level = 0;

   /**
    * Create a new XMLTreeWalker, in the default {@link #MODE_ROOT} mode.
    *
    * @param rootNode the root Node of this walker
    */
   XMLTreeWalker(XMLNode rootNode) {
      this.rootNode = rootNode;
      this.currentNode = rootNode;
   }

   public void setCurrentNode(XMLNode node) {
      currentNode = node;
      level = 0;
   }

   /**
    * Return the level in the XML subtree, the 0 value designing the root Node. The level will always be &gt= 0 in
    * the {@link #MODE_ROOT} mode, but it can be &lt 0 in the {@link #MODE_SLICE} mode, as the last Node
    * can be higher in the DOM hierarchy than the root node.
    *
    * @return the level
    */
   public int getLevel() {
      return level;
   }

   /**
    * Moves the TreeWalker to the first visible child of the current node, and returns the new node.
    *
    * @return the first child
    */
   public XMLNode firstChild() {
      XMLNode result = currentNode.getFirstChild();
      if (result != null) {
         level++;
         currentNode = result;
      }
      return result;
   }

   /**
    * Return the node at which the TreeWalker is currently positioned.
    *
    * @return the node
    */
   public XMLNode getCurrentNode() {
      return currentNode;
   }

   /**
    * The root node of the TreeWalker, as specified when it was created.
    */
   public XMLNode getRoot() {
      return rootNode;
   }

   /**
    * Moves the TreeWalker to the last visible child of the current node, and returns the new node.
    */
   public XMLNode lastChild() {
      XMLNode result = currentNode.getLastChild();
      if (result != null) {
         level++;
         currentNode = result;
      }
      return result;
   }

   /**
    * Moves the TreeWalker to the next visible node in document order relative to the current node, and returns the new node.
    */
   public XMLNode nextNode() {
      XMLNode result = getNextNode(currentNode);
      currentNode = result;
      return result;
   }

   public boolean hasNext() {
      if (currentNode == null) {
         return false;
      }

      // Go to the first child
      XMLNode n = currentNode.getFirstChild();
      if (n != null) {
         return true;
      }

      // if this is the rootNode and we are in MODE_ROOT, we don't allow to go further
      if (currentNode == rootNode) {
         return false;
      }

      // Go to the next sibling
      n = currentNode.getNextSibling();
      if (n != null) {
         return true;
      }

      // Go to the first sibling of one of the ancestors
      n = currentNode;
      while (((n = n.getParent()) != null) && (n != rootNode)) {
         level--;
         XMLNode t = n.getNextSibling();
         if (t != null) {
            return true;
         }
      }
      return false;
   }

   /**
    * Moves the TreeWalker to the next sibling of the current node, and returns the new node.
    */
   public XMLNode nextSibling() {
      // if this is the rootNode and we are in MODE_ROOT, we don't allow to go further
      if (currentNode == rootNode) {
         return null;
      }

      XMLNode result = currentNode.getNextSibling();
      if (result != null) {
         currentNode = result;
      }
      return result;
   }

   /**
    * Moves the TreeWalker to the previous sibling of the current node, and returns the new node.
    */
   public XMLNode previousSibling() {
      // if this is the rootNode, we don't allow to go further
      if (currentNode == rootNode) {
         return null;
      }

      XMLNode result = currentNode.getPreviousSibling();
      if (result != null) {
         currentNode = result;
      }
      return result;
   }

   /**
    * Moves to and returns the closest visible ancestor node of the current node.
    */
   public XMLNode parentNode() {
      // if this is the rootNode, we don't allow to go further
      if (currentNode == rootNode) {
         return null;
      }

      XMLNode result = currentNode.getParent();
      if (result != null) {
         currentNode = result;
         level--;
      }
      return result;
   }

   /**
    * Moves the TreeWalker to the previous visible node in document order relative to the current node, and returns the new node.
    */
   public XMLNode previousNode() {
      XMLNode result;
      if (currentNode == null) {
         return null;
      }

      // if this is the rootNode, we don't allow to go further
      if (currentNode == rootNode) {
         return null;
      }

      XMLNode n = currentNode.getPreviousSibling();

      // Go to the parent of a first child
      if (n == null) {
         result = currentNode.getParent();
         level--;
         return result;
      }

      // Go to the last child of child...
      XMLNode t;
      while ((t = n.getLastChild()) != null) {
         level++;
         n = t;
      }
      result = n;
      return result;
   }

   private XMLNode getNextNode(XMLNode node) {
      if (node == null) {
         return null;
      }

      // Go to the first child
      XMLNode n = node.getFirstChild();
      if (n != null) {
         level++;
         return n;
      }

      // if this is the rootNode and we are in MODE_ROOT, we don't allow to go further
      if (node == rootNode) {
         return null;
      }

      // Go to the next sibling
      n = node.getNextSibling();
      if (n != null) {
         return n;
      }

      // Go to the first sibling of one of the ancestors
      n = node;
      while (((n = n.getParent()) != null) && (n != rootNode)) {
         level--;
         XMLNode t = n.getNextSibling();
         if (t != null) {
            return t;
         }
      }
      return null;
   }
}
