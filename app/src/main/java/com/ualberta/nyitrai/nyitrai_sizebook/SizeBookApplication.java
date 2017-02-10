package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Application;

/**
 * Created by nyitrai on 2/5/2017.
 */

/**
 * In between class for the SizeBook and the BookController.
 */
public class SizeBookApplication extends Application {
    transient private static SizeBook sizeBook = null;

    static SizeBook getSizeBook() {
        if (sizeBook == null) {
            sizeBook = new SizeBook();
        }
        return sizeBook;
    }

    transient private static BookController bookController = null;

    public static BookController getBookController() {
        if (bookController == null) {
            bookController = new BookController(getSizeBook());
        }
        return bookController;
    }
}
