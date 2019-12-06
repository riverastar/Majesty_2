package com.example.majesty;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Lista extends AppCompatActivity {
 private ImageView flista;
 private ListView item_eli;
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
      item_eli = (ListView)findViewById(R.id.lista);

    }
    public void cargarlista(){
         AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
         SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        if (BaseDeDatos!=null){
           Cursor c= BaseDeDatos.rawQuery("select * from directorio",null);
           int cantidad = c.getCount();
           int i = 0;
          final String[] arreglo = new String[cantidad];
         if (c.moveToFirst()){
        do{
       String linea = c.getString(1);
       arreglo[i] = linea;
       i++;
      }while (c.moveToNext());
     BaseDeDatos.close();
    }
   final ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arreglo);
   final ListView lista = (ListView) findViewById(R.id.lista);
   lista.setAdapter(adapter);


       lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
           AlertDialog.Builder alerta = new AlertDialog.Builder(Lista.this);
           alerta.setMessage("¿Deseas eliminar esta direccion?")
                 .setCancelable(false)
                 .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 })
                   .setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                       }
                   });
           AlertDialog titulo = alerta.create();
           titulo.setTitle("Eliminar");
           titulo.show();
      }
   });
  }
 }

}

