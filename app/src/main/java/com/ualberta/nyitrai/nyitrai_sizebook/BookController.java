package com.ualberta.nyitrai.nyitrai_sizebook;

import java.util.ArrayList;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class BookController implements SController {
    SizeBook sb = null;

    public BookController(SizeBook sb) { this.sb = sb; }

    public ArrayList<Record> getRecords() { return sb.getRecords(); }
}
