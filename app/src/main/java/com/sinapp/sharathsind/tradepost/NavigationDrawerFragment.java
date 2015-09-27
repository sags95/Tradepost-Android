package com.sinapp.sharathsind.tradepost;

/**
 * Created by HenryChiang on 15-06-25.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.CustomTextView;
import Model.LimitedEditText;
import Model.NavigationDrawerAdapter;
import Model.NavigationDrawerCallbacks;
import Model.NavigationItem;
import Model.Variables;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements NavigationDrawerCallbacks {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private FrameLayout mFragementContainerViewRight;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private LinearLayout mHeaderLayout;

    //feedback
    private LinearLayout feedbackLayout;
    LayoutInflater li;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    int feedback_layout = R.layout.feedback_dialog;

    //Header
    CustomTextView mUsername, mEmail;
    CircleImageView avatarContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        li = getActivity().getLayoutInflater();
        mHeaderLayout = (LinearLayout)view.findViewById(R.id.navigationHeader);
        mHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                ArrayList<String> profileDetails = new ArrayList<String>();
                profileDetails.add(0,String.valueOf(Constants.userid));
                profileDetails.add(1,mUsername.getText().toString());
                profileDetails.add(2,mEmail.getText().toString());

                i.putStringArrayListExtra("profileDetails",profileDetails);
                i.putExtra("caller", "NavigationDrawer");
                if(avatarContainer.getDrawable()!=null) {
                    i.putExtra("profilePic", ((BitmapDrawable) avatarContainer.getDrawable()).getBitmap());
                }

                startActivity(i);
            }
        });
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);

        final List<NavigationItem> navigationItems = getMenu();
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);

        //feedback
        feedbackLayout = (LinearLayout)view.findViewById(R.id.navigationfooter);
        feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
        return view;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return mActionBarDrawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public List<NavigationItem> getMenu() {
        List<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem("Home", getResources().getDrawable(R.drawable.ic_home)));
        items.add(new NavigationItem("Offers", getResources().getDrawable(R.drawable.ic_offers)));
        items.add(new NavigationItem("My Item", getResources().getDrawable(R.drawable.ic_my_items)));
        items.add(new NavigationItem("My Favorites", getResources().getDrawable(R.drawable.ic_favorites)));
        items.add(new NavigationItem("Categories", getResources().getDrawable(R.drawable.ic_categories)));
        items.add(new NavigationItem("Settings", getResources().getDrawable(R.drawable.ic_settings)));


        return items;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     * @param toolbar      The Toolbar of the activity.
     */
    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar,FrameLayout fl) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mFragementContainerViewRight = fl;

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.ColorPrimary));

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.opendrawer, R.string.closedrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                if (drawerView != null && drawerView == mFragementContainerViewRight) {
                    super.onDrawerSlide(drawerView, 0);
                }
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {

                if(drawerView!=null && drawerView == mFragementContainerViewRight){
                    super.onDrawerSlide(drawerView, 0);
                }else{
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }

        };
        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

    }

    public void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavigationDrawerAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public void openDrawer() {mDrawerLayout.openDrawer(mFragmentContainerView);}
    public void closeDrawer() {mDrawerLayout.closeDrawer(mFragmentContainerView);}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUserData(String user, String email, Bitmap avatar) {
        avatarContainer = (CircleImageView) mFragmentContainerView.findViewById(R.id.imgAvatar);
        mUsername = (CustomTextView)mFragmentContainerView.findViewById(R.id.txtUsername);
        mEmail = (CustomTextView)mFragmentContainerView.findViewById(R.id.txtUserEmail);
        mUsername.setText(user);
        mEmail.setText(email);
        Uri url = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + Constants.userid + "/profile.png");
        Picasso.with(getActivity().getApplicationContext()).load(url).into(avatarContainer);

        if(Variables.username!=null&&Variables.email!=null)
        {
            mUsername.setText(Variables.username);
            mEmail.setText(Variables.email);

            if(Variables.profilepic!=null){
                int i = Constants.userid;
                avatarContainer.setImageBitmap(Variables.profilepic);

            }else {
                Uri url1 = Uri.parse("http://73.37.238.238:8084/TDserverWeb/images/" + Constants.userid + "/profile.png");
                Picasso.with(getActivity().getApplicationContext()).load(url1).into(avatarContainer);
                //Variables.setProfilepic(((BitmapDrawable)avatarContainer.getDrawable()).getBitmap());
            }
        }
    }

    public void customDialog() {
        final View dialogView = li.inflate(feedback_layout, null, false);

        //set up the limit for user input
        LimitedEditText feedbackEdit = (LimitedEditText)dialogView.findViewById(R.id.feedback_edit);
        feedbackEdit.setMaxLines(7);
        feedbackEdit.setMaxCharacters(250);

        final CustomTextView feedbackCharCount = (CustomTextView)dialogView.findViewById(R.id.feedback_char_count);
        feedbackEdit.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                feedbackCharCount.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        });


        builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyleFeedback);
        builder.setTitle("Send Us Feedback!");

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNegativeButton("Cancel", null);
        //set custom view.
        builder.setView(dialogView);

        //showing custom dialog.
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
