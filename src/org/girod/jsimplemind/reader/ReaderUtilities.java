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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A Utility class for parsing SimpleMind diagrams.
 *
 * @since 0.1
 */
public class ReaderUtilities {
   private ReaderUtilities() {
   }

   /**
    * Return a stream for a SimpleMind file. This method will correctly handle smmx zip files or xml files. Note that
    * smmx files since recent SimpleMind versions are in fact zip files containing one xml file for the content of the
    * diagram.
    *
    * @param file the file
    * @return the stream
    * @throws IOException
    */
   public static InputStream getSimpleMindStream(File file) throws IOException {
      if (isArchive(file)) {
         ZipFile zipfile = new ZipFile(file);
         Enumeration<? extends ZipEntry> en = zipfile.entries();
         if (en.hasMoreElements()) {
            ZipEntry entry = en.nextElement();
            return zipfile.getInputStream(entry);
         } else {
            throw new IOException("Zip File is empty");
         }
      } else {
         InputStream stream = new FileInputStream(file);
         return stream;
      }
   }

   /**
    * Return a stream for a SimpleMind file. This method will correctly handle smmx zip files or xml files.
    *
    * @param url the url
    * @return the stream
    * @throws IOException
    */
   public static InputStream getSimpleMindStream(URL url) throws IOException {
      if (isArchive(url)) {
         File file = new File(url.getFile());
         ZipFile zipfile = new ZipFile(file);
         Enumeration<? extends ZipEntry> en = zipfile.entries();
         if (en.hasMoreElements()) {
            ZipEntry entry = en.nextElement();
            return zipfile.getInputStream(entry);
         } else {
            throw new IOException("Zip File is empty");
         }
      } else {
         return url.openStream();
      }
   }

   /**
    * Return true if an url is an archive.
    *
    * @param url the url
    * @return true if the url is an archive
    */
   public static boolean isArchive(URL url) {
      File file = new File(url.getFile());
      return isArchive(file);
   }

   /**
    * Return true if a File is an archive.
    *
    * @param f the File
    * @return true if the File is an archive
    */
   public static boolean isArchive(File f) {
      int fileSignature = 0;
      try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
         fileSignature = raf.readInt();
      } catch (IOException e) {
         return false;
      }
      return fileSignature == 0x504B0304 || fileSignature == 0x504B0506 || fileSignature == 0x504B0708;
   }
}
