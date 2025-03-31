package com.anningtex.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class BookServer extends Service {
    public final String TAG = this.getClass().getSimpleName();
    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();

    //aidl 生成的
    public final IBookServer.Stub binder = new IBookServer.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks == null) {
                    return new ArrayList<>();
                }
                return mBooks;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in ");
                    book = new Book();
                }

                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                for (Book mBook : mBooks) {
                    Log.e(TAG, mBook.toString());
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.size());
            }
        }

        @Override
        public int getBookId() throws RemoteException {
            return 0;
        }

        @Override
        public void setBook() throws RemoteException {

        }


    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("Android开发艺术探索");
        book.setPrice(28);
        mBooks.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
