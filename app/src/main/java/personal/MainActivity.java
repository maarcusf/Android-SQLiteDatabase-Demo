package personal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hazmirulafiq.androidsqlitedatabasedemo.R;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DatabaseHelper dbHelper;


    final String[] from = new String[]{dbHelper.ID, dbHelper.NOME, dbHelper.SOBRENOME, dbHelper.IDADE,dbHelper.ALTURA, dbHelper.PESO,
    };
    final int[] to = new int[]{R.id.id, R.id.listNome, R.id.listSobrenome, R.id.listIdade, R.id.listAltura, R.id.listPeso};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.myListView);

        adapter = new SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to, 0);
        listView.setAdapter(adapter);


        try {
            Intent intent = getIntent();
            Boolean ItemDeleted = intent.getExtras().getBoolean("ItemDeleted");
            ModifyActivity modifyActivity = new ModifyActivity();
            if (ItemDeleted) {
                Snackbar.make(listView, "ItemDeleted!", Snackbar.LENGTH_LONG).show();
                modifyActivity.setItemDeleted(false);
            }
        } catch (Exception e) {
            if (adapter.isEmpty()) {
                Snackbar.make(listView, "Clique no '+' para adicionar um Aluno.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(listView, "Pressione e segure para editar um Aluno.", Snackbar.LENGTH_LONG).show();
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long arg3) {


                AlertDialog.Builder editar = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.content_imc, null);
                TextView textView1 = (TextView) mView.findViewById(R.id.idImc);
                TextView textView2 = (TextView) mView.findViewById(R.id.idResultado);

                float imc = 0;

                TextView itemAltura = (TextView) view.findViewById(R.id.listAltura);
                TextView itemPeso = (TextView) view.findViewById(R.id.listPeso);


                float myAltura = Float.parseFloat(itemAltura.getText().toString());
                float myPeso = Float.parseFloat(itemPeso.getText().toString());

                //Fazer o cálculo do IMC
                imc = (myPeso)/(myAltura*myAltura);
                textView1.setText(String.valueOf(imc));

                if(imc<18.5)
                {
                    textView2.setText("Abaixo do Peso!");

                } else if((imc >= 18.6) && (imc < 24.9))
                {
                    textView2.setText("Peso Ideal!");
                } else if((imc >= 25) && (imc < 29.9))
                {
                    textView2.setText("Levemente Acima do Peso!");
                } else if((imc >= 30) && (imc < 34.9))
                {
                    textView2.setText("Obesidade Grau 1");
                }else if((imc >= 35) && (imc < 39.9))
                {
                    textView2.setText("Obesidade Grau 2 (Severa)!");
                } else {
                    textView2.setText("Obesidade Mórmida!");
                }

                editar.setView(mView);
                AlertDialog alert = editar.create();
                alert.show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView itemID = (TextView) view.findViewById(R.id.id);
                TextView itemNome = (TextView) view.findViewById(R.id.listNome);
                TextView itemSobrenome = (TextView) view.findViewById(R.id.listSobrenome);
                TextView itemIdade = (TextView) view.findViewById(R.id.listIdade);
                TextView itemAltura = (TextView) view.findViewById(R.id.listAltura);
                TextView itemPeso = (TextView) view.findViewById(R.id.listPeso);

                String myId = itemID.getText().toString();
                String myNome = itemNome.getText().toString();
                String mySobrenome = itemSobrenome.getText().toString();
                String myIdade = itemIdade.getText().toString();
                String myAltura = itemAltura.getText().toString();
                String myPeso = itemPeso.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                intent.putExtra("_id", myId);
                intent.putExtra("Nome", myNome);
                intent.putExtra("Sobrenome", mySobrenome);
                intent.putExtra("Idade", myIdade);
                intent.putExtra("Altura", myAltura);
                intent.putExtra("Peso", myPeso);


                startActivity(intent);

                return false;
            }
        });
    }



    public void onClickAddItem(View view) {
        Intent intent = new Intent(getApplicationContext(), AddItem.class);
        startActivity(intent);
    }

}
