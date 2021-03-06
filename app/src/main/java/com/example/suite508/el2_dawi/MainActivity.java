package com.example.suite508.el2_dawi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lvMain;
    private Button btMain;
    private LVMainAdapter mLVMainAdapter;
    private int REQUEST_CODE = 1;
    private int pos = -1;

    private final View.OnClickListener btMainOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    private final AdapterView.OnItemClickListener lvMainOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Persona persona = mLVMainAdapter.getItem(position);
            pos = position;

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);

            intent.putExtra("nombre", persona.getNombre().toString());
            intent.putExtra("apellido", persona.getApellido().toString());
            intent.putExtra("telefono", persona.getTelefono().toString());
            intent.putExtra("direccion", persona.getDireccion().toString());
            intent.putExtra("edad", persona.getEdad().toString());

            startActivityForResult(intent, 69);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lvMain);
        btMain = (Button) findViewById(R.id.btMain);

        mLVMainAdapter = new LVMainAdapter(MainActivity.this);
        lvMain.setAdapter(mLVMainAdapter);

        btMain.setOnClickListener(btMainOnClickListener);
        lvMain.setOnItemClickListener(lvMainOnItemClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CODE) {

                Persona persona = new Persona();
                persona.setId(java.util.UUID.randomUUID().toString());
                persona.setNombre(data.getStringExtra("nombre"));
                persona.setApellido(data.getStringExtra("apellido"));
                persona.setTelefono(data.getStringExtra("telefono"));
                persona.setDireccion(data.getStringExtra("direccion"));
                persona.setEdad(data.getStringExtra("edad"));

                mLVMainAdapter.add(persona);
            }
            else
                if(requestCode == 69) {

                    Persona persona = mLVMainAdapter.getItem(pos);

                    mLVMainAdapter.remove(persona);

                    persona.setNombre(data.getStringExtra("nombre"));
                    persona.setApellido(data.getStringExtra("apellido"));
                    persona.setTelefono(data.getStringExtra("telefono"));
                    persona.setDireccion(data.getStringExtra("direccion"));
                    persona.setEdad(data.getStringExtra("edad"));

                    mLVMainAdapter.insert(persona, pos);
                    mLVMainAdapter.notifyDataSetChanged();
                }
        }

    }
}
