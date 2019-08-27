package com.doranco.async_task;

import android.os.AsyncTask;
import android.widget.TextView;

import com.doranco.utils.NetworkUtils;
import com.doranco.whowroteit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    @Override
    protected String doInBackground(String... strings) {
        // appel de la méthode getBookInfo pour transmettre le terme de recherche récupéré des paramètres
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            //obtenir le tableau d'éléments JSON à partir de la chaîne de résultat.
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // parcourir le tableau JSON
            // initialisation des variables
            int i = 0;
            String title = null;
            String authors = null;

            // parcourir tableau itemsArray
            while (i < itemsArray.length() && (title == null && authors == null)) {
                // récupère l'item courant
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // récupère title et author de l'item courant
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch(Exception e) {
                    e.printStackTrace();
                }

            // move to the next item
                i++;
            }


                // Si une réponse correspondante est trouvée, mettre à jour l'interface utilisateur avec cette réponse
                if (title != null && authors != null) {
                    mTitleText.get().setText(title);
                    mAuthorText.get().setText(authors);

                    // sinon si aucun élément contenant titre et auteur valides
                } else {
                    // définit un titre TextView "aucun résultat"
                    mTitleText.get().setText(R.string.no_results);
                    // effacer le TextView de auteur
                    mAuthorText.get().setText("");
                }

        } catch (Exception e) {
            mTitleText.get().setText(R.string.no_results);
            mAuthorText.get().setText("");
        }
    }

    public FetchBook() {
        super();
    }

    public FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }
}
