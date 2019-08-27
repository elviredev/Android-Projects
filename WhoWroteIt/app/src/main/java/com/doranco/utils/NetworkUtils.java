package com.doranco.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    // Constante pour le LOG
    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    public static String getBookInfo (String queryString) {
        // connexion internet
        HttpURLConnection urlConnection = null;
        // lire données entrantes
        BufferedReader reader = null;
        // conserver la string
        String bookJSONString = null;

        try {
            // construction de l'URI
            Uri builtUri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, String.valueOf(10))
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            // conversion URI en objet URL
            URL requestUrl = new URL(builtUri.toString());

            // ouverture connection à l'URL + faire le GET
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // config réponse (en JSON) de la connection avec InputStream, BufferedReader et StringBuffer
            // Get the inputStream
            InputStream inputStream = urlConnection.getInputStream();
            // Créer un Buffered reader depuis cet input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // utiliser un StringBuilder pour contenir la réponse entrante
            StringBuilder builder = new StringBuilder();

            // Lire l'entrée ligne par ligne
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            // si réponse est vide, return null
            if(builder.length() == 0) {
                return null;
            }

            // convertir le stringBuilder en String et le stocker dans bookJSONString
            bookJSONString = builder.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // fermer la connection et le BufferedReader
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        // affiche la valeur de la variable bookJSONString dans le journal.
        if(bookJSONString == null){
            bookJSONString = "";
        }
        Log.d(LOG_TAG, bookJSONString);

        return bookJSONString;
    }

}
