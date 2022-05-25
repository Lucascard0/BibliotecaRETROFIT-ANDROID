package com.example.retrofit.api;

import com.example.retrofit.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    // Local/Endereço em que o JSON é buscado
    @GET("{cep}/json/")


    /*O método vai retornar uma chamada com o model CEP, 'poderia retornar uma lista caso necessário'

    * Utiliza-se o @Path para evidenciar para o retrofit que o parametro cep passado na string,
    corresponde ao mesmo valor cep passando no @GET
     */
    Call<CEP> recuperarCEP(@Path("cep") String cep); //Retorna um objeto CALL, padrão do Retrofit


}
