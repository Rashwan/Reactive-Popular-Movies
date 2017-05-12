package com.rashwan.reactive_popular_movies.feature.actorDetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rashwan.reactive_popular_movies.data.model.Cast;
import com.rashwan.reactive_popular_movies.feature.actorDetails.actorInfo.ActorInfoFragment;

/**
 * Created by rashwan on 5/12/17.
 */

public class ActorDetailsPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;
    private final String tabTitles[] = new String[] { "Info", "Movies"};
    private Cast castItem;
    public ActorDetailsPagerAdapter(FragmentManager fm, Cast castItem) {
        super(fm);
        this.castItem = castItem;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ActorInfoFragment.newInstance(castItem);
        }
        return ActorInfoFragment.newInstance(castItem);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
