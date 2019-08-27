package com.doranco.whowroteit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.doranco.async_task.BookLoader;
import com.doranco.async_task.FetchBook;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private TextView mTitleText;
    private TextView mAuthorText;
    private EditText mBookInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);
        mBookInput = findViewById(R.id.bookInput);

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    // méthode utilisée avec l'asynctaskloader
    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();

        //  masque le clavier lorsque l'utilisateur appuie sur le bouton
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // lance la tâche en arrière-plan
        FetchBook fetchBook = new FetchBook(mTitleText, mAuthorText);
        fetchBook.execute(queryString);

        // effacer l'auteur
        mAuthorText.setText("");
        // changer text du TextView title pour afficher un msg de chargement
        mTitleText.setText(R.string.loading);

        // vérifier connection réseau
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // faire mises à jour des TextView si connection réseau existe, réseau est connecté et qu'une requête est disponible
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            // Lancer l'asynctaskloader
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        }

        // sinon mise à jour interface utilisateur avec un msg d'erreur si pas de terme à rechercher
        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }

    }

    // méthode utilisée avec l'asynctask
    public void searchBooks2(View view) {
        String queryString = mBookInput.getText().toString();

        //  masque le clavier lorsque l'utilisateur appuie sur le bouton
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // lance la tâche en arrière-plan
        FetchBook fetchBook = new FetchBook(mTitleText, mAuthorText);
        fetchBook.execute(queryString);

        // effacer l'auteur
        mAuthorText.setText("");
        // changer text du TextView title pour afficher un msg de chargement
        mTitleText.setText(R.string.loading);

        // vérifier connection réseau
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // faire mises à jour des TextView si connection réseau existe, réseau est connecté et qu'une requête est disponible
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            new FetchBook(mTitleText, mAuthorText).execute(queryString);
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        }

        // sinon mise à jour interface utilisateur avec un msg d'erreur si pas de terme à rechercher
        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            //obtenir le tableau d'éléments JSON à partir de la chaîne de résultat.
            JSONObject jsonObject = new JSONObject(data);
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
                mTitleText.setText(title);
                mAuthorText.setText(authors);

                // sinon si aucun élément contenant titre et auteur valides
            } else {
                // définit un titre TextView "aucun résultat"
                mTitleText.setText(R.string.no_results);
                // effacer le TextView de auteur
                mAuthorText.setText("");
            }

        } catch (Exception e) {
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
