package rahul.com.chatapp.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rahul on 7/4/15.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHandler";
    public static final String SENDER_NAME = "sender_name";
    public static final String SENT_TIME = "sent_time";
    public static final String MESSAGE = "message";
    public static final String USER_IMAGE_URL = "user_image_url";

    private static final String DB_NAME = "chat";
    private static final String TABLE_NAME = "messages";

    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " + SENDER_NAME + " text," +
            SENT_TIME + " text, " + USER_IMAGE_URL + " text, " + MESSAGE + " text);";

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "create");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMessage(MessageItem item) {
        Log.i(TAG, "insert");
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SENDER_NAME, item.getUserName());
        contentValues.put(SENT_TIME, item.getTime());
        contentValues.put(USER_IMAGE_URL, item.getImageUrl());
        contentValues.put(MESSAGE, item.getMessage());
        Long result = db.insert(TABLE_NAME, null, contentValues);
        Log.i(TAG, "Result" + result);
        db.close();
    }

    public ArrayList<MessageItem> getMessageList() {
        Log.i(TAG, "getMessagesList");
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        Log.i(TAG, "count" + c.getCount());
        ArrayList<MessageItem> itemArrayList = null;
        if (c.moveToFirst()) {
            itemArrayList = new ArrayList<>();
            do {
                String name = c.getString(c.getColumnIndex(SENDER_NAME));
                String message = c.getString(c.getColumnIndex(MESSAGE));
                String time = c.getString(c.getColumnIndex(SENT_TIME));
                String imageUrl = c.getString(c.getColumnIndex(USER_IMAGE_URL));
                MessageItem item = new MessageItem();
                item.setUserName(name);
                item.setImageUrl(imageUrl);
                item.setMessage(message);
                item.setTime(time);
                itemArrayList.add(item);
            } while (c.moveToNext());
        }
        database.close();
        return itemArrayList;
    }


}