package com.mygdx.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.cars.Player;

public class DataBase implements Database {
    private static final String DATABASE_NAME = "game_base.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_NAME = "playerInfo";
    private static final String TABLE2_NAME = "Levels";
    private static final String TABLE3_NAME = "playerInfo";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COINS = "coins";
    private static final String COLUMN_SKIN_PATH = "path";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_COINS = 1;
    private static final int NUM_COLUMN_SKIN_PATH = 2;

    private static final String COLUMN_LEVEL_NAME = "name";
    private static final String COLUMN_LEVEL_COST = "cost";
    private static final String COLUMN_LEVEL_GOAL = "goal";
    private static final String COLUMN_LEVEL_AVAILABLE = "bool";

    private static final int NUM_COLUMN_LEVEL_NAME = 0;
    private static final int NUM_COLUMN_LEVEL_COST = 1;
    private static final int NUM_COLUMN_LEVEL_GOAL = 2;
    private static final int NUM_COLUMN_LEVEL_AVAILABLE = 3;


    private SQLiteDatabase mDataBase;

    public DataBase(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(int id,int coins, String path) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_COINS, coins);
        cv.put(COLUMN_SKIN_PATH, path);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public long insert(String name, int cost, int goal, int bool) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_LEVEL_NAME, name);
        cv.put(COLUMN_LEVEL_COST, cost);
        cv.put(COLUMN_LEVEL_GOAL, goal);
        cv.put(COLUMN_LEVEL_AVAILABLE, bool);
        return mDataBase.insert(TABLE2_NAME, null, cv);
    }

    public int update(Player player) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_COINS, player.getCoin());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(1)});
    }

    public int update(String path) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SKIN_PATH, path);
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(1)});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Player select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        return new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 399, 499, Gdx.graphics.getWidth(), mCursor.getString(NUM_COLUMN_SKIN_PATH), mCursor.getInt(NUM_COLUMN_COINS));
    }

    public GameLevel select(String name) {
        Cursor mCursor = mDataBase.query(TABLE2_NAME, null, COLUMN_LEVEL_NAME + " = ?", new String[]{name}, null, null, null);
        mCursor.moveToFirst();
        boolean av;
        av= mCursor.getInt(NUM_COLUMN_LEVEL_AVAILABLE) == 1;
        return new GameLevel(mCursor.getString(NUM_COLUMN_LEVEL_NAME), av, mCursor.getInt(NUM_COLUMN_LEVEL_GOAL), mCursor.getInt(NUM_COLUMN_LEVEL_COST));
    }


    /*public ArrayList<Matches> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Matches> arr = new ArrayList<Matches>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String TeamHome = mCursor.getString(NUM_COLUMN_TEAMHOME);
                String TeamGuest = mCursor.getString(NUM_COLUMN_TEAMGUAST);
                int GoalsHome = mCursor.getInt(NUM_COLUMN_GOALSHOME);
                int GoalsGuest=mCursor.getInt(NUM_COLUMN_GOALSGUEST);
                arr.add(new Matches(id, TeamHome, TeamGuest, GoalsHome,GoalsGuest));
            } while (mCursor.moveToNext());
        }
        return arr;
    }*/

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COINS+ " INT, " +
                    COLUMN_SKIN_PATH + " TEXT);";
            String query1 = "CREATE TABLE " + TABLE2_NAME + " (" +
                    COLUMN_LEVEL_NAME + " TEXT, " +
                    COLUMN_LEVEL_COST+ " INT, " +
                    COLUMN_LEVEL_GOAL + " INT, "+
                    COLUMN_LEVEL_AVAILABLE + " INT);";
            /*
            String query2 = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COINS+ " INT, " +
                    COLUMN_SKIN_PATH + " TEXT);";
            ;*/
            db.execSQL(query);
            db.execSQL(query1);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
