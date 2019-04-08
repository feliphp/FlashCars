package aplicacion.android.jotajotavm.flashcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardsSQLiteHelper extends SQLiteOpenHelper {
    //sentencia para crear la table
    String sqlCreate = "CREATE TABLE Cards(VIN INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, pregunta TEXT, respuesta TEXT, rating INTEGER)";

    public CardsSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cards");
        db.execSQL(sqlCreate);
    }
}
