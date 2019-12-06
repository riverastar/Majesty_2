package com.example.majesty;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;

public class buscarActivity extends AppCompatActivity {
  private EditText et_buscar;
  private Button bbuscar;
  private ImageView fotoconsulta;
  private  EditText tvb_dir,tvb_des,tvb_cat;
  private ScrollView consulta_inv;
  private PhotoViewAttacher photoViewAttacher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buscar);
     //Codigo para habilitar la flecha de atras
      Bundle bundle = getIntent().getExtras();
      ActionBar actionBar = getSupportActionBar();
      actionBar.setDisplayHomeAsUpEnabled(true);

      et_buscar = (EditText)findViewById(R.id.et_buscar);
      tvb_dir = (EditText) findViewById(R.id.tvb_dir);
      tvb_des = (EditText) findViewById(R.id.tvb_des);
      tvb_cat = (EditText) findViewById(R.id.tvb_cat);
      consulta_inv = (ScrollView) findViewById(R.id.slv_busqueda);
      fotoconsulta = (ImageView)findViewById(R.id.img_fotoconsulta);
      }
      public void consultar(View view) {
       AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
       SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

       String dir = et_buscar.getText().toString();

       if (!dir.isEmpty()){
           Cursor file = BaseDeDatos.rawQuery("SELECT direccion, descripcion, categoria, foto FROM directorio WHERE direccion ='" + dir +"'", null);
            if (file.moveToFirst()){
                tvb_dir.setText(file.getString(0));
                tvb_des.setText("Descripcion: "+file.getString(1));
                tvb_cat.setText(file.getString(2));
                byte [] img = file.getBlob(3);
                Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
                fotoconsulta.setImageBitmap(bmp);
                consulta_inv.setVisibility(view.getVisibility());
                photoViewAttacher = new PhotoViewAttacher(fotoconsulta);
                BaseDeDatos.close();
                et_buscar.setText("");
           }else{
                Toast.makeText(this,"Direccion no existe",Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
          }
   }else {
   Toast.makeText(this,"Debes introducir una direccion",Toast.LENGTH_LONG).show();
  }
 }
    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        final SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        final String eli = tvb_dir.getText().toString();
        AlertDialog.Builder alerta = new AlertDialog.Builder(buscarActivity.this);
        alerta.setMessage("¿Estas seguro que deseas eliminarla?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int uno = BaseDeDatos.delete("directorio","direccion='"+eli+"'",null);
                        BaseDeDatos.close();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Eliminar direccion");
        titulo.show();
   }
   public void Modificar(View view){
      AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
      SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

      String direcion = tvb_dir.getText().toString();
      String descripcion = tvb_des.getText().toString();
      String categoria = tvb_cat.getText().toString();

      if (!direcion.isEmpty() && !descripcion.isEmpty() && !categoria.isEmpty()) {
          ContentValues registro = new ContentValues();
          registro.put("direccion", direcion);
          registro.put("descripcion", descripcion);
          registro.put("categoria", categoria);
          int cantidad = BaseDeDatos.update("directorio", registro, "direccion='" + direcion + "'", null);
          BaseDeDatos.close();
          if (cantidad == 1){
              Toast.makeText(this,"Direccion modificada correctamente",Toast.LENGTH_LONG).show();
      }else {
              Toast.makeText(this,"La direccion no existe",Toast.LENGTH_LONG).show();
      }
      }else{
              Toast.makeText(this,"Debes llenar los campos",Toast.LENGTH_LONG).show();
       }
   }

}
