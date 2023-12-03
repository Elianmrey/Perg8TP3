package org.example;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;



public class Main {
    public static void main(String[] args) throws Exception {

        URL urlObj = new URL("http://universities.hipolabs.com/search?country=Brazil");
        HttpURLConnection conexao = (HttpURLConnection) urlObj.openConnection();
        conexao.setRequestMethod("GET");
        int resposta = conexao.getResponseCode();

        if (resposta == HttpURLConnection.HTTP_OK) {
            BufferedReader receber = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

            String leitura;
            StringBuffer respons = new StringBuffer();

            while ((leitura = receber.readLine()) != null) {
                respons.append(leitura);
            }
            receber.close();


            Collection<Universidade> listaDeUniversidades = new ArrayList<>();
            JSONObject json;
            JSONArray arrayJson = new JSONArray(respons.toString());



            for (int c = 0; c < arrayJson.length(); c++) {
                json = arrayJson.getJSONObject(c);
                Universidade univ = new Universidade();
                univ.setNome(json.getString("name"));

                univ.setUrl(json.get("web_pages").toString().replace("[", "").replace("]", ""));
                listaDeUniversidades.add(univ);
            }
            for (Universidade uni : listaDeUniversidades)
                System.out.println("[Resposta da API] ---> " + uni);
        } else {
            System.out.println("API Indisponivel no momento");
        }
    }
}