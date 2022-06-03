package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.api.CEPService;
import com.example.retrofit.model.CEP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private EditText textoCep;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoResultado = findViewById(R.id.textResultado);
        textoCep = findViewById(R.id.editTextCep);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/") //URL de requisição
                .addConverterFactory(GsonConverterFactory.create()) //Definindo o convesor que será utilizando (Gson)
                .build();

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarCepRetrofit();
            }
        });
    }

    private void recuperarCepRetrofit(){

        String idCep = textoCep.getText().toString(); // Recuperar CEP do editText
        CEPService cepService = retrofit.create(CEPService.class); //Passando como parametro a interface, que retorna o cepService, podendo acessar o método recuperarCEP
        Call<CEP> call = cepService.recuperarCEP(idCep); //Acessando o método recuperarCEP

        //Utilizando o objeto call para criar uma tarefa assíncrona dentro de uma Thread de forma automática
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                
                if (textoCep.length() != 8){
                    Toast.makeText(MainActivity.this, "CEP INVALIDO", Toast.LENGTH_SHORT).show();
                }
                
                else if(response.isSuccessful()){
                    CEP cep = response.body();  //Convertendo os dados no MODEL criado
                    textoResultado.setText("CEP: " + cep.getCep() + "\n" + "Logradouro: " + cep.getLogradouro() + "\n" +"Complemento: " + cep.getComplemento() +
                            "\n" + "Bairro: " + cep.getBairro() + "\n" + "Localidade: " + cep.getLocalidade() + "\n" + "UF: " + cep.getUf());
                }

            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                
            }
        });

    }
}