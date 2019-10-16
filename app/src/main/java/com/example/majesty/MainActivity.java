package com.example.majesty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //poner el icono action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


    }
    //Metodo para mostrar y ocultar el menu
    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }
    //Metodo para asignar las funciones correspondientes a las opciones del menu
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.item1){
            Toast.makeText(this,"opcion1",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.item2){
            Toast.makeText(this,"opcion2",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.item3){
            Toast.makeText(this,"opcion3",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.item4){
            Toast.makeText(this,"opcion4",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void segundo(View view){
        Intent segundo = new Intent(this, segundoActivity.class);
        startActivity(segundo);
    }

}
