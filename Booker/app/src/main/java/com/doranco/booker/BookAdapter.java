package com.doranco.booker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public static final String ID_BOOK_EXTRA = "id.book.extra" ;
    private List<Book>mbooks;
    private Context mContext;
    private LayoutInflater inflater;

    public BookAdapter(List<Book> mbooks, Context mContext) {
        this.mbooks = mbooks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        // charge la vue de l'item
        View itemView = inflater.inflate(R.layout.book_item_layout, parent, false);
        return new BookViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mbooks.get(position);
        holder.title.setText(book.getTitle());
        holder.shortDescription.setText(book.getShortDescription());
        //TODO Format the date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date publishedDate = formatter.parse(book.getPublishedDate());
            String formattedDate = formatter.format(publishedDate);
            holder.publishedDate.setText(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Glide
        //création URI
        Uri imageUri = Uri.parse(book.getThumbnailUrl()).buildUpon().build();

        //chargement de l'image dans le viewholder
        Glide.with(mContext).load(imageUri).into(holder.thunbnail);
    }

    @Override
    public int getItemCount() {
        return mbooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView thunbnail;
        private TextView title;
        private TextView publishedDate;
        private TextView shortDescription;
        private BookAdapter mBookAdapter;

        public BookViewHolder(@NonNull View itemView, BookAdapter bookAdapter) {
            super(itemView);

            thunbnail = itemView.findViewById(R.id.book_item_image);
            title = itemView.findViewById(R.id.book_item_title);
            publishedDate = itemView.findViewById(R.id.book_item_pub_date);
            shortDescription = itemView.findViewById(R.id.book_item_short_description);
            this.mBookAdapter = bookAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, BookDetailActivity.class);
            intent.putExtra(ID_BOOK_EXTRA, mbooks.get(getAdapterPosition()).getIsbn());
            mContext.startActivity(intent);
        }
    }
}



















