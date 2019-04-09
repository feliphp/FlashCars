package aplicacion.android.jotajotavm.flashcards;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

public class ConfigActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnCreate;
    private Button btnDelete;
    private EditText textPregunta;
    private EditText textRespuesta;

    private CardsSQLiteHelper cardsHelper;
    private SQLiteDatabase db;

    private MyAdapter adapter;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        listView = (ListView) findViewById(R.id.listView);
        cards = new ArrayList<Card>();
        textPregunta = (EditText) findViewById(R.id.editTextPregunta);
        textRespuesta = (EditText) findViewById(R.id.editTextRespuesta);
        btnCreate = (Button) findViewById(R.id.buttonCreate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textPreguntaStr = textPregunta.getText().toString();
                String textRespuestaStr = textRespuesta.getText().toString();
                create(textPreguntaStr, textRespuestaStr);
                update();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
                update();
            }
        });

        //Abrimos y/o creamos BD
        cardsHelper = new CardsSQLiteHelper(this, "DBFlashCards1", null, 1);
        db = cardsHelper.getWritableDatabase();

        //adaptador
        adapter = new MyAdapter(this, cards, R.layout.itemdb);
        listView.setAdapter(adapter);

        update();
    }

    private void update() {
        cards.clear();
        cards.addAll(getAllCards());
        adapter.notifyDataSetChanged();
    }

    private List<Card> getAllCards() {
    //obtener los registros
        Cursor cursor = db.rawQuery("SELECT * FROM Cards", null);
        List<Card> list = new ArrayList<Card>();

        if(cursor.moveToFirst()){
            while (cursor.isAfterLast() == false){
                int VIN = cursor.getInt(cursor.getColumnIndex("VIN"));
                String pregunta = cursor.getString(cursor.getColumnIndex("pregunta"));
                String respuesta = cursor.getString(cursor.getColumnIndex("respuesta"));
                int rating = cursor.getInt(cursor.getColumnIndex("rating"));

                list.add(new Card(VIN, pregunta, respuesta, rating));
                cursor.moveToNext();
            }
        }
        return list;
    }

    private void create(String Pregunta, String Respuesta){
        if(db != null){
            //registro
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("pregunta",Pregunta);
            nuevoRegistro.put("respuesta",Respuesta);
            nuevoRegistro.put("rating", 0);
            Cursor cursor = db.rawQuery("SELECT * FROM Cards WHERE pregunta ='"+Pregunta+"'" , null);
            if(cursor.moveToFirst()){
                Toast.makeText(ConfigActivity.this, "Pregunta Repetida", Toast.LENGTH_SHORT).show();
            } else {
                db.insert("Cards", null, nuevoRegistro);
            }
        }
    }

    private void removeAll(){ db.delete("Cards","",null);}

    @Override
    protected void onDestroy(){
        db.close();
        super.onDestroy();
    }
}
