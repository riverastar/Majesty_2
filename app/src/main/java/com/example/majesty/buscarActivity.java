package com.example.majesty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class buscarActivity extends AppCompatActivity {
        private EditText et_buscar;
        private Button bbuscar;
        private ImageView fotoconsulta;
        private TextView tvb_dir,tvb_des;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_buscar);

            //Codigo para habilitar la flecha de atras
            Bundle bundle = getIntent().getExtras();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            et_buscar = (EditText)findViewById(R.id.et_buscar);
            tvb_dir = (TextView)findViewById(R.id.tvb_dir);
            tvb_des = (TextView)findViewById(R.id.tvb_des);
            fotoconsulta = (ImageView)findViewById(R.id.img_fotoconsulta);

    }
    public void consultar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String dir = et_buscar.getText().toString();

        if (!dir.isEmpty()){
           Cursor file = BaseDeDatos.rawQuery("SELECT direccion, descripcion, foto FROM directorio WHERE direccion ='" + dir +"'", null);
            if (file.moveToFirst()){
                tvb_dir.setText(file.getString(0));
                tvb_des.setText(file.getString(1));
                byte [] img = file.getBlob(2);
                Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
                fotoconsulta.setImageBitmap(bmp);

                BaseDeDatos.close();
                et_buscar.setText("");
            }else {
                Toast.makeText(this,"Direcion no existe",Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else {
            Toast.makeText(this,"Debes introducir una direccion",Toast.LENGTH_LONG).show();
        }

    }
}
