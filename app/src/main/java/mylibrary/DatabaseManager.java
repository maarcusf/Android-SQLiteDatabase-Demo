package mylibrary;

/**
 * Created by Raphael Bragança on 23/2/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        this.context = c;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String nome, String sobrenome, String idade, String altura, String peso ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NOME, nome);
        contentValues.put(dbHelper.SOBRENOME, sobrenome);
        contentValues.put(dbHelper.IDADE, idade+" anos");
        contentValues.put(dbHelper.ALTURA, altura+"m");
        contentValues.put(dbHelper.PESO, peso+"kg");

       // contentValues.put(dbHelper.NOME, nome);
        database.insert(dbHelper.TABLE_NAME, null, contentValues);
    }

    public void update(int id, String nome, String sobrenome, String idade, String altura, String peso) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.NOME, nome);
        contentValues.put(dbHelper.SOBRENOME, sobrenome);
        contentValues.put(dbHelper.IDADE, idade);
        contentValues.put(dbHelper.ALTURA, altura);
        contentValues.put(dbHelper.PESO, peso);
        database.update(dbHelper.TABLE_NAME, contentValues, dbHelper.ID + " = " + id, null);
    }

    public void delete(int id) {
        database.delete(dbHelper.TABLE_NAME,dbHelper.ID + " ='" + id + "'",null);
    }

    public Cursor fetch() {
        String[] columns = new String[]{dbHelper.ID, dbHelper.NOME, dbHelper.SOBRENOME,dbHelper.IDADE, dbHelper.ALTURA,
                dbHelper.PESO};
        Cursor cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
