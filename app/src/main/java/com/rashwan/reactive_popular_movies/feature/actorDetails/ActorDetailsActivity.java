package com.rashwan.reactive_popular_movies.feature.actorDetails;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.RoundedTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.TransitionListenerAdapter;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 4/26/17.
 */

public class ActorDetailsActivity extends AppCompatActivity implements ActorDetailsView
        ,OnOffsetChangedListener{
    private static final String EXTRA_CAST_ITEM = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_CAST_ITEM";
    private static final String EXTRA_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final String TAG_ACTOR_DETAILS_FRAGMENT = "TAG_ACTOR_DETAILS_FRAGMENT";
    @BindView(R.id.actor_details_image_backdrop) ImageView actorBackdropImage;
    @BindView(R.id.actor_details_image_poster) ImageView actorPosterImage;
    @BindView(R.id.actor_details_text_actor_name) TextView actorNameText;
    @BindView(R.id.actor_details_toolbar_actor_name) TextView actorNameToolbarText;
    @BindView(R.id.actor_details_toolbar) Toolbar toolbar;
    @BindView(R.id.actor_details_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.actor_details_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.actor_details_appbar_constraint_layout) ConstraintLayout appbarConstraintLayout;
    @BindView(R.id.actor_details_sliding_tabs) TabLayout actorDetailsTabLayout;
    @BindView(R.id.actor_details_view_pager) ViewPager actorDetailsViewPager;
    @BindColor(android.R.color.black) int blackColor;
    @Inject ActorDetailsPresenter presenter;

    private String sharedElementName;
    private Cast castItem;
    private TransitionListenerAdapter transitionListener;

    public static Intent getActorDetailsIntent(Context context, Cast castItem, String sharedElementName){
        Intent intent = new Intent(context,ActorDetailsActivity.class);
        intent.putExtra(EXTRA_CAST_ITEM,castItem);
        intent.putExtra(EXTRA_SHARED_ELEMENT_NAME,sharedElementName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PopularMoviesApplication)getApplicationContext()).createActorDetailsComponent()
        .inject(this);
        Intent intent = getIntent();
        castItem = intent.getParcelableExtra(EXTRA_CAST_ITEM);
        sharedElementName = intent.getStringExtra(EXTRA_SHARED_ELEMENT_NAME);
        setContentView(R.layout.activity_actor_details);
        ButterKnife.bind(this);
        presenter.attachView(this);
        setupShareElementTransition();
        setupViews();

    }
    private void setupShareElementTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionListener = new TransitionListenerAdapter() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    setupViewPager();
                    getWindow().getSharedElementEnterTransition().removeListener(transitionListener);
                }
            };
            actorPosterImage.setTransitionName(sharedElementName);
            getWindow().getSharedElementEnterTransition().addListener(transitionListener);
        }
    }
    private void setupViewPager() {
        ActorDetailsPagerAdapter pagerAdapter = new ActorDetailsPagerAdapter(getSupportFragmentManager()
                ,castItem);
        actorDetailsViewPager.setAdapter(pagerAdapter);
        actorDetailsTabLayout.setupWithViewPager(actorDetailsViewPager);

    }

    private void setupViews() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actorNameToolbarText.setAlpha(0);
        actorNameToolbarText.setText(castItem.name());
        appBarLayout.addOnOffsetChangedListener(this);
        presenter.getActorTaggedImages(castItem.id());
        populateCastItemDetails();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onNavigateUp();
                return true;
        }
        return false;
    }

    @Override
    public boolean onNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getApplicationContext()).releaseActorDetailsComponent();
    }

    private void populateCastItemDetails(){
        actorNameText.setText(castItem.name());
        Picasso.with(this)
                .load(castItem.getFullProfilePath(Movie.QUALITY_LOW))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .transform(new RoundedTransformation(blackColor))
                .transform(new PaletteTransformation())
                .error(R.drawable.ic_account_circle)
                .into(actorPosterImage, new PaletteTransformation.Callback(actorPosterImage) {
                    @Override
                    public void onPalette(Palette palette) {
                        supportStartPostponedEnterTransition();
                        if (palette != null) {
                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                            if (collapsingToolbar != null && darkVibrantSwatch != null) {
                                int darkVibrantColor = darkVibrantSwatch.getRgb();
                                actorDetailsTabLayout.setBackgroundColor(darkVibrantColor);
                                collapsingToolbar.setContentScrimColor(darkVibrantColor);
                                collapsingToolbar.setStatusBarScrimColor(darkVibrantColor);
                                GradientDrawable gradient = new GradientDrawable
                                        (GradientDrawable.Orientation.TL_BR,new int[]{
                                                blackColor,
                                                darkVibrantColor
                                        });
                                gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                                appbarConstraintLayout.setBackground(gradient);
                            }
                        }
                    }});

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enterReveal() {
        if(!isDestroyed()) {
            actorBackdropImage.setVisibility(View.VISIBLE);
            final int finalRadius = Math.max(actorBackdropImage.getWidth(), actorBackdropImage.getHeight()) / 2;
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(actorBackdropImage
                    , actorBackdropImage.getWidth() / 2, actorBackdropImage.getHeight() / 2
                    , 0, finalRadius);
            circularReveal.start();
        }

    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingToolbar.getHeight() + verticalOffset > collapsingToolbar.getScrimVisibleHeightTrigger()) {
            actorNameToolbarText.animate().alpha(0).setDuration(250);
        } else {
            actorNameToolbarText.animate().alpha(1).setDuration(500);

        }
    }

    @Override
    public void showActorTaggedImage(List<ActorTaggedImage> taggedImages) {
        Picasso.with(this).load(taggedImages.get(0)
                .getFullImagePath(ActorTaggedImage.QUALITY_MEDIUM))
                .fit().centerCrop()
                .into(actorBackdropImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            enterReveal();
                        }else {
                            actorBackdropImage.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onError() {
                    }
                });
    }

}
