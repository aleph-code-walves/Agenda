package br.com.dlweb.contato.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import br.com.dlweb.contato.R;

import br.com.dlweb.contato.Contatos.Contato;


public class DatabaseConnnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda";
    private static final String TABLE_CONTATO = "mae";
    private static final String TABLE_MEDICO = "medico";
    private static final String TABLE_BEBE = "bebe";

    private static final String CREATE_TABLE_CONTATO = "CREATE TABLE " + TABLE_CONTATO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "numero INTEGER, " +
            "numeroCasa INTEGER, " +
            "numeroTrabalho INTEGER, " +
            "data_nascimento DATE);";


    private static final String DROP_TABLE_CONTATO = "DROP TABLE IF EXISTS " + TABLE_CONTATO;

    public DatabaseConnnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTATO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CONTATO);
        onCreate(db);
    }

    /* Início CRUD Mãe */
    public long createContato(Contato m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("numero", m.getNumero());
        cv.put("numeroCasa", m.getNumeroCasa());
        cv.put("numeroTrabalho", m.getNumeroTrabalho());
        long id = db.insert(TABLE_CONTATO, null, cv);
        db.close();
        return id;
    }

    public long updateContato(Contato m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("numero", m.getNumero());
        cv.put("numeroCasa", m.getNumeroCasa());
        cv.put("numeroTrabalho", m.getNumeroTrabalho());

        long id = db.update(TABLE_CONTATO, cv, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public long deleteContato(Contato m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CONTATO, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public Contato getByIdContato(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "numero",  "numeroCasa","numeroTrabalho"};
        Cursor data = db.query(TABLE_CONTATO, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        data.moveToFirst();
        Contato m = new Contato();
        m.setId(data.getInt(0));
        m.setNome(data.getString(1));
        m.setNumero(data.getInt(2));
        m.setNumeroCasa(data.getString(3));
        m.setNumeroTrabalho(data.getString(4));
        data.close();
        db.close();
        return m;
    }

    public void getAllContato(Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "numero"};
        String orderBy = "nome";
        Cursor data = db.query(TABLE_CONTATO, columns, null, null, null, null, orderBy);
        int[] to = {R.id.textViewIdListMae, R.id.textViewNomeListMae, R.id.textViewCelularListMae};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.contato_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameContato(ArrayList<Integer> listMaeId, ArrayList<String> listMaeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome","numero"};
        Cursor data = db.query(TABLE_CONTATO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listMaeId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listMaeName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD Mãe */

}
