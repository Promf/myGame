package com.mygdx.game;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.cars.Player;

public class DataBase implements Database {
    private static final String DATABASE_NAME = "game_data.db";
    private static final int DATABASE_VERSION = 11;
    private static final String TABLE_NAME = "playerInfo";
    private static final String TABLE2_NAME = "Levels";
    private static final String TABLE_SKINS_NAME = "Skins";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COINS = "coins";
    private static final String COLUMN_SKIN_PATH = "path";
    private static final String COLUMN_LEVEL = "level";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_COINS = 1;
    private static final int NUM_COLUMN_SKIN_PATH = 2;
    private static final int NUM_COLUMN_LEVEL = 3;


    private static final String COLUMN_LEVEL_ID = "id";
    private static final String COLUMN_LEVEL_NAME = "name";
    private static final String COLUMN_LEVEL_COST = "cost";
    private static final String COLUMN_LEVEL_GOAL = "goal";
    private static final String COLUMN_LEVEL_AVAILABLE = "bool";
    private static final String COLUMN_LEVEL_RECORD = "record";
    private static final String COLUMN_LEVEL_COUNT = "count";
    private static final String COLUMN_LEVEL_FON = "fon";

    private static final int NUM_COLUMN_LEVEL_ID = 0;
    private static final int NUM_COLUMN_LEVEL_NAME = 1;
    private static final int NUM_COLUMN_LEVEL_COST = 2;
    private static final int NUM_COLUMN_LEVEL_GOAL = 3;
    private static final int NUM_COLUMN_LEVEL_AVAILABLE = 4;
    private static final int NUM_COLUMN_LEVEL_RECORD = 5;
    private static final int NUM_COLUMN_LEVEL_COUNT = 6;
    private static final int NUM_COLUMN_LEVEL_FON = 7;

    private static final String COLUMN_SKINS_ID = "id";
    private static final String COLUMN_SKINS_NAME = "name";
    private static final String COLUMN_SKINS_COST = "coins";
    private static final String COLUMN_SKINS_TEXTURE = "path";
    private static final String COLUMN_SKINS_BOUGHT = "bool";
    private static final String COLUMN_SKINS_DESCRIPTION = "text";

    private static final int NUM_COLUMN_SKINS_ID = 0;
    private static final int NUM_COLUMN_SKINS_NAME = 1;
    private static final int NUM_COLUMN_SKINS_COST = 2;
    private static final int NUM_COLUMN_SKINS_TEXTURE = 3;
    private static final int NUM_COLUMN_SKINS_BOUGHT = 4;
    private static final int NUM_COLUMN_SKINS_DESCRIPTION = 5;



    private final SQLiteDatabase mDataBase;

    public DataBase(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(int id,int coins, String path) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_COINS, coins);
        cv.put(COLUMN_SKIN_PATH, path);
        cv.put(COLUMN_LEVEL, 1);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public long insert_skin(int id, String name, int cost, String path, int bool, String description ) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SKINS_ID, id);
        cv.put(COLUMN_SKINS_NAME, name);
        cv.put(COLUMN_SKINS_COST, cost);
        cv.put(COLUMN_SKINS_TEXTURE, path);
        cv.put(COLUMN_SKINS_BOUGHT, bool);
        cv.put(COLUMN_SKINS_DESCRIPTION, description);
        return mDataBase.insert(TABLE_SKINS_NAME, null, cv);
    }

    public long insert(int id, String name, int cost, int goal, int bool, int count, String fon) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_LEVEL_ID, id);
        cv.put(COLUMN_LEVEL_NAME, name);
        cv.put(COLUMN_LEVEL_COST, cost);
        cv.put(COLUMN_LEVEL_GOAL, goal);
        cv.put(COLUMN_LEVEL_AVAILABLE, bool);
        cv.put(COLUMN_LEVEL_COUNT, count);
        cv.put(COLUMN_LEVEL_FON, fon);
        return mDataBase.insert(TABLE2_NAME, null, cv);
    }

    public int update(Player player) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_COINS, player.getCoin());
        cv.put(COLUMN_LEVEL, player.getLevel());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(1)});
    }

    public int update(GameLevel gameLevel, int record) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_LEVEL_RECORD, record);
        return mDataBase.update(TABLE2_NAME, cv, COLUMN_LEVEL_ID + " = ?",new String[] { String.valueOf(gameLevel.getId())});
    }

    public int update_skins(CarSkin skin) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_SKINS_BOUGHT, 1);
        return mDataBase.update(TABLE_SKINS_NAME, cv, COLUMN_SKINS_NAME + " = ?",new String[] { String.valueOf(skin.getName())});
    }

    public int update(GameLevel gameLevel) {
        ContentValues cv=new ContentValues();
        int x;
        if (gameLevel.isAvailable()){x=1;}
        else x = 0;
        cv.put(COLUMN_LEVEL_AVAILABLE, x);
        return mDataBase.update(TABLE2_NAME, cv, COLUMN_LEVEL_ID + " = ?",new String[] { String.valueOf(gameLevel.getId())});
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
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        Player player = new Player((int) (Gdx.graphics.getWidth()*0.45), (int) (Gdx.graphics.getHeight()/3.5), 399, 499, Gdx.graphics.getWidth(), mCursor.getString(NUM_COLUMN_SKIN_PATH), mCursor.getInt(NUM_COLUMN_COINS));
        player.setLevel(mCursor.getInt(NUM_COLUMN_LEVEL));
        return player;
    }

    public CarSkin select_skin(int id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE_SKINS_NAME, null, COLUMN_SKINS_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        boolean isAvailable = mCursor.getInt(NUM_COLUMN_SKINS_BOUGHT)==1;
        return new CarSkin(mCursor.getString(NUM_COLUMN_SKINS_NAME), isAvailable, mCursor.getInt(NUM_COLUMN_SKINS_COST), mCursor.getString(NUM_COLUMN_SKINS_TEXTURE), mCursor.getString(NUM_COLUMN_SKINS_DESCRIPTION));
    }


    public GameLevel select_level(long id) {
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.query(TABLE2_NAME, null, COLUMN_LEVEL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        mCursor.moveToFirst();
        boolean av;
        av = mCursor.getInt(NUM_COLUMN_LEVEL_AVAILABLE) == 1;
        return new GameLevel(mCursor.getString(NUM_COLUMN_LEVEL_NAME), av, mCursor.getInt(NUM_COLUMN_LEVEL_GOAL), mCursor.getInt(NUM_COLUMN_LEVEL_COST), mCursor.getInt(NUM_COLUMN_LEVEL_RECORD), mCursor.getInt(NUM_COLUMN_LEVEL_ID), mCursor.getInt(NUM_COLUMN_LEVEL_COUNT), mCursor.getString(NUM_COLUMN_LEVEL_FON));
    }

/*
    public ArrayList<CarSkin> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_SKINS_NAME, null, null, null, null, null, null);

        ArrayList<CarSkin> arr = new ArrayList<CarSkin>();
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
    public int getCount(){
        @SuppressLint("Recycle") Cursor mCursor = mDataBase.rawQuery("select * from "+TABLE_SKINS_NAME, null);
        return mCursor.getColumnCount();
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COINS+ " INT, " +
                    COLUMN_SKIN_PATH + " TEXT," +
                    COLUMN_LEVEL +" INT);";
            String query1 = "CREATE TABLE " + TABLE2_NAME + " (" +
                    COLUMN_LEVEL_ID + " INT, " +
                    COLUMN_LEVEL_NAME + " TEXT," +
                    COLUMN_LEVEL_COST+ " INT, " +
                    COLUMN_LEVEL_GOAL + " INT, "+
                    COLUMN_LEVEL_AVAILABLE + " INT, " +
                    COLUMN_LEVEL_RECORD + " INT, " +
                    COLUMN_LEVEL_COUNT + " INT, " +
                    COLUMN_LEVEL_FON + " TEXT);";

            String query2 = "CREATE TABLE " + TABLE_SKINS_NAME + " (" +
                    COLUMN_SKINS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_SKINS_NAME + " TEXT," +
                    COLUMN_SKINS_COST+ " INT, " +
                    COLUMN_SKINS_TEXTURE + " TEXT,"+
                    COLUMN_SKINS_BOUGHT + " INT, " +
                    COLUMN_SKINS_DESCRIPTION + " TEXT);";

            db.execSQL(query);
            db.execSQL(query1);
            db.execSQL(query2);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKINS_NAME);
            onCreate(db);
        }
    }
}
