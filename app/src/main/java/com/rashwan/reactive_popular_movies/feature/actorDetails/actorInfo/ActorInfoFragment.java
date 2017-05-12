package com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rashwan.reactive_popular_movies.PopularMoviesApplication;
import com.rashwan.reactive_popular_movies.R;
import com.rashwan.reactive_popular_movies.common.utilities.ExpandableTextView;
import com.rashwan.reactive_popular_movies.data.model.ActorProfileImage;
import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.data.model.CastDetails;

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

public class ActorInfoFragment extends Fragment implements ActorInfoView {
    private static final String ARGUMENT_CAST_ITEM = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_CAST_ID";
    private static final String ARGUMENT_SHARED_ELEMENT_NAME = "com.rashwan.reactive_popular_movies.feature.actorDetails.EXTRA_SHARED_ELEMENT_NAME";
    private static final ButterKnife.Action<View> SHOW = (view, index) -> view.setVisibility(View.VISIBLE);


    @BindView(R.id.actor_details_text_actor_bio) ExpandableTextView actorBioText;

    @BindView(R.id.actor_details_text_actor_birthday) TextView actorBirthdayText;
    @BindView(R.id.actor_details_text_actor_deathday) TextView actorDeathdayText;
    @BindView(R.id.actor_details_died_at_title) TextView actorDiedAtTitle;
    @BindView(R.id.actor_details_text_actor_age) TextView actorAgeText;
    @BindView(R.id.actor_details_text_actor_birthPlace) TextView actorBirthPlaceText;
    @BindView(R.id.actor_details_rv_images) RecyclerView actorProfileImagesRv;
    @BindView(R.id.actor_details_no_bio_text) TextView actorNoBioText;
    @BindColor(android.R.color.black) int blackColor;
    @BindViews({R.id.actor_details_divider_bio_profile_images,R.id.actor_details_images_title
    ,R.id.actor_details_rv_images}) List<View> profileImagesViews;
    @Inject ActorInfoPresenter presenter;@Inject
    ActorProfileImagesAdapter adapter;
    private Unbinder unbinder;
    private Cast castItem;

    public static ActorInfoFragment newInstance(Cast castItem) {
        ActorInfoFragment actorInfoFragment = new ActorInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CAST_ITEM, castItem);
        actorInfoFragment.setArguments(bundle);
        return actorInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PopularMoviesApplication)getActivity().getApplication()).createActorInfoComponent()
                .inject(this);
        castItem = getArguments().getParcelable(ARGUMENT_CAST_ITEM);
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
        ((PopularMoviesApplication)getActivity().getApplication()).releaseActorInfoComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void setupViews(){
        setupActorProfileImagesRv();
        presenter.getActorDetails(castItem.id());
        presenter.getActorProfileImages(castItem.id());
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.activity_movie_details,menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                getActivity().onNavigateUp();
//                return true;
//        }
//        return false;
//    }

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

}
