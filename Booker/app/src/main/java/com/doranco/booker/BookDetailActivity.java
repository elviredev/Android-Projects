package com.doranco.booker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.doranco.booker.BookAdapter.ID_BOOK_EXTRA;

public class BookDetailActivity extends AppCompatActivity {

    private Book currentBook;
    private ImageView mImageView;
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView isbTextView;
    private TextView ldTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mImageView = findViewById(R.id.book_item_image);
        titleTextView = findViewById(R.id.book_item_title);
        dateTextView = findViewById(R.id.book_item_pub_date);
        isbTextView = findViewById(R.id.book_item_isbn);
        ldTextView = findViewById(R.id.book_item_long_description);

        Intent intent = getIntent();
        String isbn = intent.getStringExtra(ID_BOOK_EXTRA);
        if(isbn != null){
            for(Book book : utils.feedBooks(this)){
                if(isbn.equals(book.getIsbn())){
                    currentBook = book;
                    break;
                }

            }
        }

        // Glide
        //création URI
        Uri imageUri = Uri.parse(currentBook.getThumbnailUrl()).buildUpon().build();
        //chargement de l'image dans le viewholder
        Glide.with(this).load(imageUri).into(mImageView);

        // format date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date publishedDate = formatter.parse(currentBook.getPublishedDate());
            String formattedDate = formatter.format(publishedDate);
            dateTextView.setText(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        titleTextView.setText(currentBook.getTitle());
        isbTextView.setText(currentBook.getIsbn());
        ldTextView.setText(currentBook.getLongDescription());
    }
}
