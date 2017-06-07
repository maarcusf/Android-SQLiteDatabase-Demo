package mylibrary;

/**
 * Created by Raphael Bragança on 23/2/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.hazmirulafiq.androidsqlitedatabasedemo.R;

import java.io.IOException;

public class AddItem extends AppCompatActivity {

    private EditText edNome;
    private EditText edSobrenome;
    private EditText edIdade;
    private EditText edAltura;
    private EditText edPeso;

    private ImageButton inputImage;


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_FILE = 2;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edNome = (EditText) findViewById(R.id.idNome);
        edSobrenome = (EditText) findViewById(R.id.idSobrenome);
        edIdade = (EditText) findViewById(R.id.idIdade);
        edAltura = (EditText) findViewById(R.id.idAltura);
        edPeso = (EditText) findViewById(R.id.idPeso);


        inputImage = (ImageButton) findViewById(R.id.inputImageButton);

        dbManager = new DatabaseManager(this);
        dbManager.open();
    }

    public void onClickDone(View view) {
        String nome = edNome.getText().toString();
        String sobrenome = edSobrenome.getText().toString();
        String idade = edIdade.getText().toString();
        String altura = edAltura.getText().toString();
        String peso = edPeso.getText().toString();



        if (nome.isEmpty() || sobrenome.isEmpty()|| idade.isEmpty() || altura.isEmpty() || peso.isEmpty()) {
            Snackbar.make(view, "Por favor, preencha todos os campos!", Snackbar.LENGTH_SHORT).show();
        } else {
            dbManager.insert(nome,sobrenome,idade, altura, peso);
            Intent intent = new Intent(AddItem.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void pickPhoto2(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQUEST_PICK_FILE:
                    onSelectFromGalleryResult(data);
                    break;
                case REQUEST_IMAGE_CAPTURE:

                    break;
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        inputImage.setImageBitmap(bitmap);
    }
}
