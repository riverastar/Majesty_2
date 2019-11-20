package com.example.majesty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Lista extends AppCompatActivity {
    ImageView flista;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        cargarlista();

        //Codigo para habilitar la flecha de atras
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        flista = (ImageView)findViewById(R.id.img_flista);



    }
    public void cargarlista(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        if (BaseDeDatos!=null){
            Cursor c= BaseDeDatos.rawQuery("select * from directorio",null);
            int cantidad = c.getCount();
            int i = 0;

            String[] arreglo = new String[cantidad];
            if (c.moveToFirst()){
                do {
                    String linea = c.getString(1)+"\n"+c.getString(2);
                    arreglo[i] = linea;
                    i++;
                }while (c.moveToNext());
                BaseDeDatos.close();
            }
            ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arreglo);

            ListView lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(adapter);
        }

    }
}

