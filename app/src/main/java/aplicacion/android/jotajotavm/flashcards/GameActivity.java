package aplicacion.android.jotajotavm.flashcards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private TextView tViewPregunta;
    private TextView resultado;
    private EditText textRespuesta;

    private Button btnContestar;

    private CardsSQLiteHelper cardsHelper;
    private SQLiteDatabase db;
    private List<Card> cards;
    private String pregunta;
    private String respuesta;
    private String btnComportamiento;
    private int rating;
    private int vin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnContestar = (Button) findViewById(R.id.buttonContestar);
        tViewPregunta = (TextView) findViewById(R.id.textViewPregunta);
        resultado= (TextView) findViewById(R.id.textViewResultado);
        textRespuesta = (EditText) findViewById(R.id.editTextRespuesta);
        btnComportamiento = "answer";
        //Abrimos y/o creamos BD
        cardsHelper = new CardsSQLiteHelper(this, "DBFlashCards1", null, 1);
        db = cardsHelper.getWritableDatabase();
        update();
        tViewPregunta.setText(pregunta.toString());
        Log.d("rating:" , String.valueOf(rating));
        btnContestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnComportamiento.equals("answer")) {
                    if (textRespuesta.getText().toString().toLowerCase().equals(respuesta.trim().toLowerCase())) {
                        Log.d("respuesta:", "correcta");
                        Toast.makeText(GameActivity.this, "Respuesta Correcta", Toast.LENGTH_SHORT).show();
                        resultado.setText("Correcto");
                        addRating(rating, vin, 2);
                    } else {
                        Log.d("respuesta:", "incorrecta");
                        Toast.makeText(GameActivity.this, "Respuesta In Correcta", Toast.LENGTH_SHORT).show();
                        resultado.setText("Inorrecto");
                        addRating(rating, vin, 1);
                    }

                    btnComportamiento = "next";
                    btnContestar.setText("Siguente");
                } else {
                    update();
                    tViewPregunta.setText(pregunta.toString());
                    resultado.setText("");
                    btnComportamiento = "answer";
                    btnContestar.setText("Contestar");
                }

            }
        });



    }

    private void update() {
        getRandomCard();
    }


    private void getRandomCard() {
        //obtener registro random
        Cursor cursorHayRatings = db.rawQuery("SELECT * FROM Cards WHERE rating > 0 ",null);
        if(cursorHayRatings.moveToFirst()){
            Cursor cursorNormal = db.rawQuery("SELECT * FROM Cards ORDER BY rating ASC LIMIT 0,1",null);
            if (cursorNormal.moveToFirst()) {
                vin = cursorNormal.getInt(cursorNormal.getColumnIndex("VIN"));
                pregunta = cursorNormal.getString(cursorNormal.getColumnIndex("pregunta"));
                respuesta = cursorNormal.getString(cursorNormal.getColumnIndex("respuesta"));
                rating = cursorNormal.getInt(cursorNormal.getColumnIndex("rating"));
            }
        } else {
            Cursor cursor = db.rawQuery("SELECT * FROM Cards WHERE VIN IN (SELECT VIN FROM Cards ORDER BY RANDOM() LIMIT 1)", null);
            if (cursor.moveToFirst()) {
                vin = cursor.getInt(cursor.getColumnIndex("VIN"));
                pregunta = cursor.getString(cursor.getColumnIndex("pregunta"));
                respuesta = cursor.getString(cursor.getColumnIndex("respuesta"));
                rating = cursor.getInt(cursor.getColumnIndex("rating"));
            }
        }
        Log.d("datos sss:" , pregunta);
    }

    private void addRating(int rating, int vin, int incremento){
        rating = rating + incremento;
        if(db != null){
            ContentValues Registro = new ContentValues();
            Registro.put("rating", rating);
            db.update("Cards", Registro, "VIN="+vin, null);
        }
    }


}
