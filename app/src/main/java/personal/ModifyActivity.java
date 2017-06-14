package personal;

/**
 * Created by Raphael Bragança on 23/2/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.hazmirulafiq.androidsqlitedatabasedemo.R;

public class ModifyActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edSobrenome;
    private EditText edIdade;
    private EditText edAltura;
    private EditText edPeso;

    private int id;

    private boolean isItemDeleted = false;

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = 0;

        setContentView(R.layout.activity_modify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DatabaseManager(this);
        dbManager.open();


        edNome = (EditText) findViewById(R.id.editIdNome);
        edSobrenome = (EditText) findViewById(R.id.editIdSobrenome);
        edIdade = (EditText) findViewById(R.id.editIdIdade);
        edAltura = (EditText) findViewById(R.id.editIdAltura);
        edPeso = (EditText) findViewById(R.id.editIdIPeso);

        Bundle intentData = getIntent().getExtras();

        final String myID = intentData.getString("_id");
        final String nome = intentData.getString("Nome");
        final String sobrenome = intentData.getString("Sobrenome");
        final String idade = intentData.getString("Idade");
        final String altura = intentData.getString("Altura");
        final String peso = intentData.getString("Peso");


        edNome.setText(nome);
        edSobrenome.setText(sobrenome);
        edIdade.setText(idade);
        edAltura.setText(altura);
        edPeso.setText(peso);

        id = Integer.parseInt(myID);

        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        FloatingActionButton fabUpdate = (FloatingActionButton) findViewById(R.id.fabUpdate);
        fabDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbManager.delete(Integer.parseInt(myID));
                        setItemDeleted(true);
                        returnHome();
                    }
                }
        );
        fabUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newNome = edNome.getText().toString();
                        String newSobrenome = edSobrenome.getText().toString();
                        String newIdade = edIdade.getText().toString();
                        String newAltura = edAltura.getText().toString();
                        String newPeso = edPeso.getText().toString();

                        dbManager.update(Integer.parseInt(myID), newNome, newSobrenome, newIdade, newAltura, newPeso);
                        returnHome();
                    }
                }
        );
    }

    public void returnHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (isItemDeleted) {
            intent.putExtra("Aluno Removido!", true);
        }
        startActivity(intent);
    }

    public void setItemDeleted(boolean itemDeleted) {
        isItemDeleted = itemDeleted;
    }
}
