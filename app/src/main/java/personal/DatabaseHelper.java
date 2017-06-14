package personal;

/**
 * Created by Raphael Bragança on 23/2/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Initialize Database Information
    private static final String DB_NAME = "Personal";
    // Initialize Database version
    private static final int DB_VERSION = 5;
    // Initialize Table Name
    static final String TABLE_NAME = "PESSOA";
    // Initialize Table from(Columns)
    static final String ID = "_id";
    static final String NOME = "nome";
    static final String SOBRENOME = "sobrenome";
    static final String IDADE = "idade";
    static final String ALTURA = "altura";
    static final String PESO = "peso";
   // static final String FOTO = "foto";


    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" " +
            "("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+NOME+" TEXT," +
            " "+SOBRENOME+" TEXT," +
            " "+IDADE+" TEXT," +
            " "+ALTURA+" TEXT," +
            " "+PESO+" TEXT);"; //+
           // " "+FOTO+" BLOB)";



    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
