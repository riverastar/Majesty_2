package com.example.majesty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class segundoActivity extends AppCompatActivity {

        private EditText et_cedula,et_nombre,et_edad,et_celular,et_posicion,et_equipo;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_segundo);
            et_cedula = (EditText)findViewById(R.id.txt_cedula);
            et_nombre = (EditText)findViewById(R.id.txt_nombre);
            et_edad = (EditText)findViewById(R.id.txt_edad);
            et_celular = (EditText)findViewById(R.id.txt_celular);
            et_posicion = (EditText)findViewById(R.id.txt_posicion);
            et_equipo = (EditText)findViewById(R.id.txt_equipo);
        }
        //metodo dar de alta a jugador
        public void Inscribir(View view){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null,1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
            String cedula = et_cedula.getText().toString();
            String nombre = et_nombre.getText().toString();
            String edad = et_edad.getText().toString();
            String celular = et_celular.getText().toString();
            String posicion = et_posicion.getText().toString();
            String equipo = et_equipo.getText().toString();

            if (!cedula.isEmpty() && !nombre.isEmpty() && !edad.isEmpty() && !celular.isEmpty() && !posicion.isEmpty() && !equipo.isEmpty()){
                ContentValues registro = new ContentValues();

                registro.put("cedula",cedula);
                registro.put("nombre",nombre);
                registro.put("edad",edad);
                registro.put("celular",celular);
                registro.put("posicion",posicion);
                registro.put("equipo",equipo);

                BaseDeDatos.insert("jugadores",null,registro);
                BaseDeDatos.close();
                et_cedula.setText("");
                et_nombre.setText("");
                et_edad.setText("");
                et_celular.setText("");
                et_posicion.setText("");
                et_equipo.setText("");
                Toast.makeText(this,"Inscripcion exitosa",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show();

            }
        }
}
