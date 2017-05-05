package com.rashwan.reactive_popular_movies.feature.actorDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.data.model.Cast;

/**
 * Created by rashwan on 4/26/17.
 */

public class ActorDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_CAST_ITEM = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_CAST_ITEM";
    private static final String EXTRA_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final String TAG_ACTOR_DETAILS_FRAGMENT = "TAG_ACTOR_DETAILS_FRAGMENT";

    public static Intent getActorDetailsIntent(Context context, Cast castItem, String sharedElementName){
        Intent intent = new Intent(context,ActorDetailsActivity.class);
        intent.putExtra(EXTRA_CAST_ITEM,castItem);
        intent.putExtra(EXTRA_SHARED_ELEMENT_NAME,sharedElementName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Cast castItem = intent.getParcelableExtra(EXTRA_CAST_ITEM);
        String sharedElementName = intent.getStringExtra(EXTRA_SHARED_ELEMENT_NAME);
        setContentView(R.layout.activity_actor_details);
        supportPostponeEnterTransition();
        FragmentManager fm = getSupportFragmentManager();
        ActorDetailsFragment actorDetailsFragment =
                (ActorDetailsFragment) fm.findFragmentByTag(TAG_ACTOR_DETAILS_FRAGMENT);
        if (savedInstanceState == null && actorDetailsFragment == null){
            actorDetailsFragment = ActorDetailsFragment.newInstance(castItem, sharedElementName);
            fm.beginTransaction().replace(R.id.actor_details_container,actorDetailsFragment
                    ,TAG_ACTOR_DETAILS_FRAGMENT).commit();
        }

    }
    @Override
    public boolean onNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }
}
