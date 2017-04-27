package com.rashwan.reactive_popular_movies.common.utilities;

import android.widget.ImageView;

/**
 * Created by rashwan on 4/27/17.
 */

public interface RvItemClickListener<T> {
    void onItemClicked(T item, ImageView sharedView);
}
