package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import Model.RoundImageHelper;

/**
 * Created by HenryChiang on 15-05-31.
 */
public class ListingProcessActivity extends Activity{

    final int MAX_NUM_TAGS = 5;
    int TAGS_COUNT = 0;
    private FlowLayout tagFlowLayout;
    private LinearLayout singleTagLayout;

    private EditText tagInput;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_process);
        tagFlowLayout = (FlowLayout) findViewById(R.id.section5_tags);

        //section 2
        ImageView camera = (ImageView)findViewById(R.id.section2_img_camera);
        ImageView folder = (ImageView)findViewById(R.id.section2_img_folder);
        Bitmap roundProImg= RoundImageHelper.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_section2_camera));
        camera.setImageBitmap(roundProImg);
        folder.setImageBitmap(roundProImg);


        //section 5
        tagInput = (EditText)findViewById(R.id.section5_edit);
        ImageView addTags = (ImageView)findViewById(R.id.section5_plus);
        addTags.setOnClickListener(addTagButtonListener);

        //using section 6 (Choose a category) plus button for testing
        ImageView testingBtn = (ImageView)findViewById(R.id.section6_plus);
        testingBtn.setOnClickListener(testingBtnListener);



    }

    public View.OnClickListener addTagButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(tagInput.getText().length()>0&&tagFlowLayout.getChildCount()<MAX_NUM_TAGS){
                singleTagLayout = (LinearLayout)View.inflate(getApplicationContext(),R.layout.single_tag, null);
                singleTagLayout.setId(TAGS_COUNT);

                //Still need to add padding or margin to each tag for spacing
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                tagFlowLayout.addView(singleTagLayout);

                //Button for removing Tag
                ImageView cancelTag = (ImageView) findViewById(TAGS_COUNT).findViewById(R.id.tag_cancel_btn);
                //cancelTag.setId(TAGS_COUNT);
                cancelTag.setOnClickListener(tagCancelButtonListener);

                //Tag Name
                TextView tagName = (TextView) findViewById(TAGS_COUNT).findViewById(R.id.tag_name);
                //tagName.setId(TAGS_COUNT);
                tagName.setText(tagInput.getText().toString().trim());
                //tagName.setTag(tagInput.getText().toString());

                tagInput.setText("");
                TAGS_COUNT++;
                Log.d("Child count","Add 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));

            }

        }
    };

    public View.OnClickListener tagCancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tagFlowLayout.removeView((View) v.getParent());
            Log.d("Child count", "Remove 1, total: " + String.valueOf(tagFlowLayout.getChildCount()));


        }

    };

    public ArrayList<String> getAllTagNames(FlowLayout fl){
        ArrayList<String> tagNamesArrayList = new ArrayList<>();


        for(int i=0;i<fl.getChildCount();i++){
            View child = fl.getChildAt(i);

            TextView getTagName = (TextView)(child.findViewById(R.id.tag_name));
            tagNamesArrayList.add(getTagName.getText().toString());
        }
        return tagNamesArrayList;

    }

    public View.OnClickListener testingBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<String> TagNames = getAllTagNames(tagFlowLayout);

            for(int i =0;i<TagNames.size();i++){
                Log.d("Tag Name", ": " + TagNames.get(i));
            }
        }


    };

}