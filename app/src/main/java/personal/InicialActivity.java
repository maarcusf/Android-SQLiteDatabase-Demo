package personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.hazmirulafiq.androidsqlitedatabasedemo.R;

public class InicialActivity  extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicial);
    }

    //Se clicar no botão voltar
    public void btIniciar(View v){
        Intent it = new Intent(InicialActivity.this, MainActivity.class);
        startActivity(it);

    }

}
