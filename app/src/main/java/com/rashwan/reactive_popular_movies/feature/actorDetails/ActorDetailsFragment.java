package com.rashwan.reactive_popular_movies.feature.actorDetails;

import android.animation.Animator;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.ExpandableTextView;
import com.rashwan.reactive_popular_movies.common.utilities.PaletteTransformation;
import com.rashwan.reactive_popular_movies.common.utilities.RoundedTransformation;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.ActorTaggedImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;
import com.rashwan.reactive_popular_movies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rashwan on 4/26/17.
 */

public class ActorDetailsFragment extends Fragment implements ActorDetailsView ,AppBarLayout.OnOffsetChangedListener{
    private static final String ARGUMENT_CAST_ITEM = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_CAST_ID";
    private static final String ARGUMENT_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final ButterKnife.Action<View> SHOW = (view, index) -> view.setVisibility(View.VISIBLE);

    @BindView(R.id.actor_details_image_backdrop) ImageView actorBackdropImage;
    @BindView(R.id.actor_details_image_poster) ImageView actorPosterImage;
    @BindView(R.id.actor_details_text_actor_name) TextView actorNameText;
    @BindView(R.id.actor_details_toolbar_actor_name) TextView actorNameToolbarText;
    @BindView(R.id.actor_details_text_actor_bio) ExpandableTextView actorBioText;
    @BindView(R.id.actor_details_toolbar) Toolbar toolbar;
    @BindView(R.id.actor_details_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.actor_details_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.actor_details_text_actor_birthday) TextView actorBirthdayText;
    @BindView(R.id.actor_details_text_actor_deathday) TextView actorDeathdayText;
    @BindView(R.id.actor_details_died_at_title) TextView actorDiedAtTitle;
    @BindView(R.id.actor_details_text_actor_age) TextView actorAgeText;
    @BindView(R.id.actor_details_text_actor_birthPlace) TextView actorBirthPlaceText;
    @BindView(R.id.actor_details_rv_images) RecyclerView actorProfileImagesRv;
    @BindView(R.id.actor_details_no_bio_text) TextView actorNoBioText;
    @BindColor(android.R.color.black) int blackColor;
    @BindView(R.id.actor_details_appbar_constraint_layout) ConstraintLayout appbarConstraintLayout;
    @BindViews({R.id.actor_details_divider_bio_profile_images,R.id.actor_details_images_title
    ,R.id.actor_details_rv_images}) List<View> profileImagesViews;
    @Inject ActorDetailsPresenter presenter;
    @Inject ActorProfileImagesAdapter adapter;
    private Unbinder unbinder;
    private Cast castItem;
    private String sharedElementName;

    public static ActorDetailsFragment newInstance(Cast castItem, String sharedElementName) {
        ActorDetailsFragment actorDetailsFragment = new ActorDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CAST_ITEM, castItem);
        bundle.putString(ARGUMENT_SHARED_ELEMENT_NAME,sharedElementName);
        actorDetailsFragment.setArguments(bundle);
        return actorDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication()).createActorDetailsComponent()
                .inject(this);
        castItem = getArguments().getParcelable(ARGUMENT_CAST_ITEM);
        sharedElementName = getArguments().getString(ARGUMENT_SHARED_ELEMENT_NAME);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);
        setupViews();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        ((PopularMoviesApplication)getActivity().getApplication()).releaseActorDetailsComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void setupViews(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actorPosterImage.setTransitionName(sharedElementName);
        }
        ((ActorDetailsActivity)getActivity()).setSupportActionBar(toolbar);
        ((ActorDetailsActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActorDetailsActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        actorNameToolbarText.setAlpha(0);
        actorNameToolbarText.setText(castItem.name());
        appBarLayout.addOnOffsetChangedListener(this);
        populateCastItemDetails();
        setupActorProfileImagesRv();
        presenter.getActorDetails(castItem.id());
        presenter.getActorTaggedImages(castItem.id());
        presenter.getActorProfileImages(castItem.id());

    }
    private void populateCastItemDetails(){
        actorNameText.setText(castItem.name());
        Picasso.with(getActivity())
                .load(castItem.getFullProfilePath(Movie.QUALITY_LOW))
                .transform(new RoundedTransformation(blackColor))
                .transform(new PaletteTransformation())
                .error(R.drawable.ic_account_circle)
                .into(actorPosterImage, new PaletteTransformation.Callback(actorPosterImage) {
                    @Override
                    public void onPalette(Palette palette) {
                        getActivity().supportStartPostponedEnterTransition();
                        if (palette != null) {
                            Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                            if (collapsingToolbar != null && darkVibrantSwatch != null) {
                                int darkVibrantColor = darkVibrantSwatch.getRgb();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_movie_details,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onNavigateUp();
                return true;
        }
        return false;
    }

    @Override
    public void showActorDetails(CastDetails castDetails) {
        actorBioText.setText(castDetails.biography().replace("\n", ""));
        actorBirthdayText.setText(castDetails.getFormattedDate(castDetails.birthday()));
        if (!castDetails.deathday().isEmpty()){
            actorDiedAtTitle.setVisibility(View.VISIBLE);
            actorDeathdayText.setVisibility(View.VISIBLE);
            actorDeathdayText.setText(castDetails.getFormattedDate(castDetails.deathday()));
        }
        actorAgeText.setText(castDetails.getAge());
        actorBirthPlaceText.setText(castDetails.placeOfBirth());
    }

    @Override
    public void showActorTaggedImage(List<ActorTaggedImage> taggedImages) {
        Picasso.with(getActivity()).load(taggedImages.get(0)
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

    @Override
    public void showActorProfileImages(List<ActorProfileImage> profileImages) {
        if (!profileImages.isEmpty()) {
            ButterKnife.apply(profileImagesViews, SHOW);
            adapter.addProfileImages(profileImages);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showActorWithNoBio() {
        actorNoBioText.setVisibility(View.VISIBLE);
    }

    private void setupActorProfileImagesRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        actorProfileImagesRv.setLayoutManager(linearLayoutManager);
        actorProfileImagesRv.setHasFixedSize(true);
        actorProfileImagesRv.setNestedScrollingEnabled(false);
        actorProfileImagesRv.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enterReveal() {
        if (isVisible()) {
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
}
