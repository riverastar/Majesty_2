package com.example.majesty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
  //declarar variables
  private EditText et_direcion, et_descripcion;
  private TextView txt_can_dir;
  private Button btn_guardar, btn_lista;
  private ImageView fotos;
  private ImageButton cargafoto;
  private Spinner opc_categoria;
  String [] opciones = {"Opciones","Casa","Edificio","Conjunto","Banco","Taller","Restaurante","Oficina","Local","Otro"};



  static final int REQUEST_IMAGE_CAPTURE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //poner el icono action bar
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    //Permisos para acceder a la camara
    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
     }
      //inicializar variables
      et_direcion = (EditText) findViewById(R.id.etxt_direccion);
      et_descripcion = (EditText) findViewById(R.id.etxt_descripcion);
      opc_categoria = (Spinner)findViewById(R.id.spinner_categoria);
      fotos = (ImageView) findViewById(R.id.iv_foto);
      txt_can_dir = (TextView) findViewById(R.id.txt_cantidad);
      cargafoto = (ImageButton) findViewById(R.id.cargarfoto);
      btn_guardar = (Button) findViewById(R.id.btn_guardar);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,opciones);
      opc_categoria.setAdapter(adapter);
      //metodo onclick para tomar foto
      cargafoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarintent();

            }

        });
      autoconsulta();
     }
     private void llamarintent() {
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
   }
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
         Bundle extras = data.getExtras();
         Bitmap imageBitmap = (Bitmap) extras.get("data");
         fotos.setImageBitmap(imageBitmap);
  }
 }

    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
    //Metodo para asignar las funciones correspondientes a las opciones del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {

        } else if (id == R.id.item2) {
            Intent buscar = new Intent(this, buscarActivity.class);
            startActivity(buscar);
        } else if (id == R.id.item3) {
            Intent lista = new Intent(this, Lista.class);
            startActivity(lista);

        } else if (id == R.id.item4) {
            Toast.makeText(this, "opcion4", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void segundo(View view) {
        Intent segundo = new Intent(this, buscarActivity.class);
        startActivity(segundo);
    }

    //metodo dar de alta a una direccion
    public void Guardar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String direcion = et_direcion.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String categoria = opc_categoria.getSelectedItem().toString();
        imegeViewToByte(fotos);


//validando que no se repita un registro
            if (!direcion.isEmpty() && !descripcion.isEmpty() && !categoria.contentEquals("Opciones")){
                Cursor validar = BaseDeDatos.rawQuery("select * from directorio where direccion='"+direcion+"'",null);
                validar.moveToFirst();

                do {
                    if (validar.getCount() == 0){
                        ContentValues registro = new ContentValues();
                        registro.put("direccion", direcion);
                        registro.put("descripcion", descripcion);
                        registro.put("categoria", categoria);
                        registro.put("foto", imegeViewToByte(fotos));
                        BaseDeDatos.insert("directorio", null, registro);
                        BaseDeDatos.close();
                        et_direcion.setText("");
                        et_descripcion.setText("");
                        fotos.setImageResource(R.drawable.previo);
                        categoria.contentEquals("Opciones");
                        makeText(this, "Direccion guardada"+validar.getCount(), LENGTH_SHORT).show();

                    } else {
                        makeText(this, "Ya existe esta direccion"+validar.getCount(), LENGTH_SHORT).show();
                    }
                }while (validar.moveToNext());
                BaseDeDatos.close();

        }else{
            Toast.makeText(this,"Debes llenar todos los campos",LENGTH_SHORT).show();
        }
    }

   private byte[] imegeViewToByte(ImageView image) {
    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] byteArray = stream.toByteArray();
    return byteArray;
  }
     private void autoconsulta(){
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
       SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
       Cursor cantidad = BaseDeDatos.rawQuery("select count(*) from directorio;", null);
       cantidad.moveToFirst();
      if (cantidad.moveToFirst()){
     do{
    txt_can_dir.setText("Cantidad de direcciones: "+cantidad.getString(0));
   }while (cantidad.moveToNext());
    BaseDeDatos.close();
  }
 }
}
