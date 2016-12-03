package il.co.idocare.screens.navigationdrawer.mvcviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import il.co.idocare.Constants;
import il.co.idocare.R;
import il.co.idocare.controllers.listadapters.NavigationDrawerListAdapter;
import il.co.idocare.datamodels.functional.NavigationDrawerEntry;
import il.co.idocare.datamodels.functional.UserItem;
import il.co.idocare.mvcviews.AbstractViewMVC;

/**
 * This mvc view represents navigation drawer's UI
 */

public class NavigationDrawerViewMvcImpl extends
        AbstractViewMVC<NavigationDrawerViewMvcImpl.NavigationDrawerViewMvcListener> {

    public interface NavigationDrawerViewMvcListener {

        /**
         * Will be called when entry from nav drawer is being chosen
         */
        void onDrawerEntryChosen(String entryName);
    }


    private ImageView mImgUserPicture;
    private ImageView mImgReputationStar;
    private TextView mTxtUserReputation;
    private TextView mTxtUserNickname;

    private NavigationDrawerListAdapter mNavDrawerAdapter;

    public NavigationDrawerViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_navigation_drawer, container, false));


        mImgUserPicture = (ImageView) getRootView().findViewById(R.id.img_user_picture);
        mImgReputationStar = (ImageView) getRootView().findViewById(R.id.img_reputation_star);
        mTxtUserReputation = (TextView) getRootView().findViewById(R.id.txt_user_reputation);
        mTxtUserNickname = (TextView) getRootView().findViewById(R.id.txt_user_nickname);

        initDrawerListView();
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    public void bindUserData(UserItem user) {

        if (user != null && user.getId() > 0) {
            mImgUserPicture.setVisibility(View.VISIBLE);
            mImgReputationStar.setVisibility(View.VISIBLE);
            mTxtUserReputation.setVisibility(View.VISIBLE);
            mTxtUserNickname.setVisibility(View.VISIBLE);

            mTxtUserNickname.setText(user.getNickname());
            mTxtUserReputation.setText(String.valueOf(user.getReputation()));

            if (user.getPictureUrl() != null && user.getPictureUrl().length() > 0) {
                showUserPicture(user.getPictureUrl());
            } else {
                mImgUserPicture.setImageResource(R.drawable.ic_default_user_picture);
            }
        } else {
            mImgUserPicture.setVisibility(View.GONE);
            mImgReputationStar.setVisibility(View.GONE);
            mTxtUserReputation.setVisibility(View.GONE);
            mTxtUserNickname.setVisibility(View.GONE);
        }
    }


    private void showUserPicture(String pictureUrl) {
        String universalImageLoaderUri = pictureUrl;
        try {
            new URL(universalImageLoaderUri);
        } catch (MalformedURLException e) {
            // The exception means that the current Uri is not a valid URL - it is local
            // uri and we need to adjust it to the scheme recognized by UIL
            universalImageLoaderUri = "file://" + universalImageLoaderUri;
        }

        ImageLoader.getInstance().displayImage(
                universalImageLoaderUri,
                mImgUserPicture,
                Constants.DEFAULT_DISPLAY_IMAGE_OPTIONS);

    }


    private void initDrawerListView() {

        final ListView drawerList = (ListView) getRootView().findViewById(R.id.drawer_list);

        mNavDrawerAdapter = new NavigationDrawerListAdapter(getRootView().getContext(), 0);
        drawerList.setAdapter(mNavDrawerAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                drawerList.setItemChecked(position, true);

                String chosenEntry = getRootView().getResources().getString(
                        mNavDrawerAdapter.getItem(position).getTitleResId());

                for (NavigationDrawerViewMvcListener listener : getListeners()) {
                    listener.onDrawerEntryChosen(chosenEntry);
                }
            }
        });
    }

    /**
     * Refresh drawer's entries
     */
    public void refreshDrawer(boolean isUserLoggedIn) {

        List<NavigationDrawerEntry> entries = new ArrayList<>(8);
        entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_my, R.drawable.ic_drawer_assigned_to_me));
        entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_new_request, R.drawable.ic_drawer_add_new_request));
        entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_map, R.drawable.ic_drawer_location));
        entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_requests_list, R.drawable.ic_drawer_requests_list));
        entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_settings, R.drawable.ic_drawer_settings));

        // No need for both login/logout options at once
        if (isUserLoggedIn)
            entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_logout, R.drawable.ic_drawer_logout));
        else
            entries.add(new NavigationDrawerEntry(R.string.nav_drawer_entry_login, 0));

        mNavDrawerAdapter.clear();
        mNavDrawerAdapter.addAll(entries);
        mNavDrawerAdapter.notifyDataSetChanged();
    }

}