package Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

/**
 * Created by HenryChiang on 15-06-27.
 */
public class OffersViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Fragment tab1;
    Fragment tab2;
    private View.OnClickListener mItemClick;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public OffersViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, Fragment tab1, Fragment tab2, View.OnClickListener mItemClick) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.tab1=tab1;
        this.tab2=tab2;
        this.mItemClick=mItemClick;

    }
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}