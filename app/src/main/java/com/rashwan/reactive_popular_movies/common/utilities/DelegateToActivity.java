package com.rashwan.reactive_popular_movies.common.utilities;

import android.widget.ImageView;

/**
 * Created by rashwan on 3/15/17.
 */

public interface DelegateToActivity<T> {
    void delegateItemClicked(T item, ImageView sharedView);

}
