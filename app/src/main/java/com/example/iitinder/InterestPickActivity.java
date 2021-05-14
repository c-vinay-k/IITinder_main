package com.example.iitinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestPickActivity extends AppCompatActivity {
    ArrayList<String> interestList;
    ArrayList<String>[] INTEREST_FINAL;
    ConstraintLayout[] interestGridLayout;
    String[] interestArray, subInterestArray;
    String[][] subMultiArray;
    boolean isSelected[];
    int length;
    GridView gridViewInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_pick);

        interestList = new ArrayList<>();
        interestGridLayout = new ConstraintLayout[]{findViewById(R.id.interestConstraint1), findViewById(R.id.interestConstraint2)};
        interestArray = new String[]{"Literature",
                "Music", "Binge Watching", "Sports", "Gaming", "Art", "Tech",
                "Anime", "Dance", "Movies", "Finance", "Fashion", "Travel", "Photography", "Food", "Writing",
                "Work/Career"};
        subMultiArray = new String[][]{
                {"Romantic Era", "Poetry", "Classical Novels", "Harry Potter", "Game of Thrones", "Fiction", "Comics",
                        "Graphic Novels", "Mystery & Thriller"},
                {"Metal", "HipHop", "Rock", "Pop", "Indie", "Classical Music", "Alternative R&B", "Bollywood Songs",
                        "K Pop", "Instrumental"},
                {"The Office", "The Big Bang Theory", "Breaking Bad", "How I Met Your Mother", "Money Heist",
                        "F.R.I.E.N.D.S.", "Scam 1992", "Sherlock", "Game of Thrones", "Fleabag"},
                {"Cricket", "Football", "Basketball", "Table Tennis", "Badminton", "Swimming", "Lawn Tennis",
                        "Volleyball", "Chess", "Athletics"},
                {"Call of Duty", "Valorant", "DOTA", "Counter Strike", "Need for Speed", "FIFA", "Fortnite", "Freefire",
                        "Among Us"},
                {"Painting", "Origami", "Sculpting", "Modern Art", "Graffiti", "Sketching", "Quilling"},
                {"Coding", "Electronics & Robotics", "Aeromodelling","Maths & Physics", "Energy", "BioX","Chemistry"},
                {"One Piece", "Naruto", "AOT", "Death Note", "Dragon Ball Z", "Manga", "Studio Ghibli",
                        "Cowboy Bepop", "Haikyuu", "Tokyo Ghoul"},
                {"Classical", "Bollywood", "Hip Hop", "Street Dance", "Folk", "Freestyle"},
                {"Hollywood", "Bollywood", "South Indian", "Animated"},
                {"Trading", "Quants", "Cryptocurrency", "Fin Frauds"},
                {"Casual", "Indian", "Western", "Trending", "Vintage", "Just Shorts & Hoodie"},
                {"Europe", "America", "Australia", "Dubai", "Asian Countries","Domestic"},
                {"Portraits", "Fashion", "Still Life", "Nature", "Wildlife", "Landscapes"},
                {"Vegan", "Non-Veg", "Regional","Street Food", "Pizza", "KFC", "Burgers", "Donuts", "Chinese"},
                {"Philosophy", "Fiction", "Suspense", "Adventure", "School Stories", "Sensual"},
                {"Still Exploring", "Serious Plans"}
        };
        length = interestArray.length;
        INTEREST_FINAL = new ArrayList[length];
        for (int i = 0; i<length; i++) INTEREST_FINAL[i] = new ArrayList<>();
        isSelected = new boolean[length];
        for(int i = 0; i<length; i++)
        {
            isSelected[i] = false;
        }
        Button button = findViewById(R.id.buttonNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    int id, mainPos;
    boolean[] subIsSelected;
    ArrayList<String> subList;
    Dialog subinterestDialog;
    ConstraintLayout viewContainer;
    CardView cardView;

    TextView selectedTextView;
    CardView selectedCard;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    public void onClickMainInterest(View view)
    {
        String idString = getResources().getResourceName(view.getId()).toString();

        try {
            id = Integer.parseInt((idString.substring(idString.length()-2)));
        }
        catch (NumberFormatException e)
        {
            id = Integer.parseInt(String.valueOf(idString.charAt(idString.length()-1)));
        }
        mainPos = id-1;
        viewContainer = findViewById(view.getContext().getResources().getIdentifier("interestConstraint"+Integer.toString(id), "id", getPackageName()));
        subinterestDialog = new Dialog(this);
        subinterestDialog.setContentView(R.layout.subinterest_picker);
        TextView textView = subinterestDialog.findViewById(R.id.subInterestHeading);
        GridView gridView = subinterestDialog.findViewById(R.id.subGrid);

//        recyclerView = subinterestDialog.findViewById(R.id.recyclerView);
//        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        textView.setText(textView.getText() + interestArray[mainPos]);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(subinterestDialog.getWindow().getAttributes());
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 380, metrics);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        subinterestDialog.show();
        subinterestDialog.getWindow().setAttributes(lp);
        Button OK = subinterestDialog.findViewById(R.id.subButtonOK);
        Button cancel = subinterestDialog.findViewById(R.id.subButtonCancel);
        ArrayList<InterestData> subInterestList = new ArrayList<>();
        for(int i = 0; i<subMultiArray[mainPos].length; i++)
        {
            subInterestList.add(new InterestData(subMultiArray[mainPos][i], 0, false));
        }
        int[] arr = {0, 0, 0};
        InterestGridAdapter interestGridAdapter = new InterestGridAdapter(InterestPickActivity.this, subMultiArray[mainPos], arr);
        gridView.setAdapter(interestGridAdapter);
        subIsSelected = new boolean[subMultiArray[mainPos].length];
        for(int i = 0; i<subMultiArray[mainPos].length; i++) subIsSelected[i] = false;
        subList = new ArrayList<>();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTextView = view.findViewById(R.id.mainText);
                selectedCard = view.findViewById(R.id.mainCardView);
                if(!subIsSelected[position])
                {
                    view.findViewById(R.id.mainCardView).setElevation(0);
                    view.findViewById(R.id.main_constraint_layout).setBackground(getDrawable(R.drawable.subinterest_btn_clicked));
                    Log.d("CLICKED", subMultiArray[mainPos][position]);
                    subList.add(subMultiArray[mainPos][position]);
                    subIsSelected[position] = true;

                }
                else{
                    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
                    view.findViewById(R.id.mainCardView).setElevation(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, metrics));
                    view.findViewById(R.id.main_constraint_layout).setBackground(getDrawable(R.drawable.subinterest_btn_unclicked));
                    view.setBackground((getDrawable(R.drawable.main_unclicked)));
                    if(!subList.isEmpty()) subList.remove(subMultiArray[mainPos][position]);
                    subIsSelected[position] = false;
                }
            }
        });
        if(!INTEREST_FINAL[mainPos].isEmpty())
        {
            INTEREST_FINAL[id-1].clear();
            subList.clear();
            for(int i = 0; i<subIsSelected.length; i++) subIsSelected[i]=false;
            isSelected[id] = false ;
            //subList.clear();

            /*for(int i = 0; i<INTEREST_FINAL[mainPos].size(); i++)
            {
                subIsSelected[i] = false;
                if(INTEREST_FINAL[mainPos].get(i)== selectedTextView.getText().toString())
                {

                    selectedCard.setBackground(getDrawable(R.drawable.subinterest_btn_clicked));
                    subList.add(subMultiArray[mainPos][i]);
                    subIsSelected[i] = true;
                }
            }*/
        }
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INTEREST_FINAL[id-1] = subList;
                subinterestDialog.dismiss();
                isSelected[id] = true;
                if(!(INTEREST_FINAL[id-1].isEmpty())) viewContainer.setBackground(getDrawable(R.drawable.main_clicked));
                else viewContainer.setBackground(getDrawable(R.drawable.main_unclicked));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subinterestDialog.dismiss();
                subList.clear();
                for(int i = 0; i<subIsSelected.length; i++) subIsSelected[i]=false;
                viewContainer.setBackground(getDrawable(R.drawable.main_unclicked));
                isSelected[id] = false ;
            }
        });

    }
}