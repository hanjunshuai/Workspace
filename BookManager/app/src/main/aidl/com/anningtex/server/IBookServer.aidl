// IBookServer.aidl
package com.anningtex.server;

import com.anningtex.server.entity.Book;
// Declare any non-default types here with import statements

interface IBookServer {
   List<Book> getBooks();
//   void addBook(in Book book);
   int getBookId();
   void setBook();
}