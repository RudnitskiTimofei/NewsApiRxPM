package by.it.andersen.newsapirxpm.datasource.databinding;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class PicassoAdapter {

    @BindingAdapter("picasso")
    public static void setImage(ImageView image, String resource) {
        Context context = image.getContext();

        if (resource == null || resource.isEmpty()) {
            Picasso.get().load(android.R.drawable.gallery_thumb).into(image);
        } else {
            Picasso.get().load(resource).into(image);
        }
    }
}
