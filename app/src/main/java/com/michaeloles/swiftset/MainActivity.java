package com.michaeloles.swiftset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.michaeloles.swiftset.SortingGroups.Difficulty;
import com.michaeloles.swiftset.SortingGroups.Equipment;
import com.michaeloles.swiftset.SortingGroups.Joints;
import com.michaeloles.swiftset.SortingGroups.MuscleGroup;
import com.michaeloles.swiftset.SortingGroups.Plyometrics;
import com.michaeloles.swiftset.SortingGroups.PredefinedTemplates;
import com.michaeloles.swiftset.SortingGroups.PushPullLegs;
import com.michaeloles.swiftset.SortingGroups.Sport;
import com.michaeloles.swiftset.SortingGroups.Stability;
import com.michaeloles.swiftset.SortingGroups.Tempo;
import com.michaeloles.swiftset.SortingGroups.Unilateral;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<SortingGroup> currentOptions = new ArrayList<>();//all the current ways the exercises can still be sorted
    public static ArrayList<SortingGroup> removedOptions = new ArrayList<>();//all the sorting groups that have already been used or cant be used
    private static ArrayList<SortingCategory> chosenOptions = new ArrayList<>();//all the sorting groups that have been selected by the user
    public static ExerciseDb remainingDb; // Updated to hold the remaining exercises
    //On the first time opening the app create menu options, after that update based on user selections
    private static boolean refresh = true;
    private static boolean backToHome = true;//Checks what we should do when the back button is pressed
    final String FIRST_USE_PREF = "FirstUsePref";
    public static String currentDifficultyLevel = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(spChanged);
        this.create();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!currentDifficultyLevel.equals(getDifficultyLevel())){
            currentDifficultyLevel = getDifficultyLevel();
            refresh = true;
            this.create();
        }
    }

    private void create(){
        if(isFirstTimeUser()){
            PredefinedTemplates.addTemplates(getApplicationContext(),PredefinedTemplates.TemplateType.STARTER);
            showAppDemo();
        }

        //If intent has reset main boolean, remove the current progress on the main activity
        if(getIntent().hasExtra("reset_main")) {
            Bundle extras = getIntent().getExtras();
            refresh = (Boolean) extras.getSerializable("reset_main");
        }


        if(getIntent().hasExtra("set_preferences")){
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName());
            intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS,true);
        }

        backToHome = refresh;
        if(refresh) {
            removedOptions.clear();
            currentOptions.clear();
            chosenOptions.clear();
            remainingDb = new ExerciseDb(this);
            addMainMenuOptions();
            remainingDb.resetDatabase();
            SavedExercises.resetExerciseList(this);

            personalize(getDifficultyLevel(),getHiddenEquipment());
            refresh = false;
        }else{
            //The sorting category chosen by the user in CategorySelector.java.  Will be used to shrink the exercise pool
            Bundle extras = getIntent().getExtras();
            ArrayList<SortingCategory> chosenScList = (ArrayList<SortingCategory>) extras.getSerializable("chosen_sorting_category");
            String dbSortCategory = "";
            StringBuilder dbSortBy = new StringBuilder();
            assert chosenScList != null;
            for(int i=0;i<chosenScList.size();i++) {
                SortingCategory chosenSc = chosenScList.get(i);
                chosenOptions.add(chosenSc);

                //New groups that can be added because of the chose sorting category (Ex: Fly Movement Pattern can be added after Chest is chosen)
                ArrayList<SortingGroup> newOptions = chosenSc.getNewOptions();
                for (SortingGroup sg : newOptions) {
                    if (!removedOptions.contains(sg) && !currentOptions.contains(sg)) {
                        currentOptions.add(sg);
                    }
                }
                if(i==0) {
                    dbSortCategory = chosenSc.getDbColumnName();
                }
                dbSortBy.append(chosenSc.getSortBy()).append("/");
            }

            dbSearch(remainingDb, dbSortBy.toString(), dbSortCategory);
        }
        setViewAllText();
        updateSortingPath();
        addButtons(this);
    }

    //Shows the user a demo of how the app works the first time they open it
    private void showAppDemo() {        Intent intent = new Intent(this, OnboardingActivity.class);
        intent.putExtra("first_time_user",true);
        startActivity(intent);
    }

    //Checks if this is the first time the user has every opened the app by using shared preferences
    //Can be used to show tutorials and set default workouts and templates
    private boolean isFirstTimeUser() {

        SharedPreferences settings = getSharedPreferences(FIRST_USE_PREF, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //The app is being started for the first time
            //record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
            return true;
        }
        return false;
    }

    //Returns an arraylist of the equipment the user has selected to hide in the settings menu
    private ArrayList<String> getHiddenEquipment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> defaultSet = new HashSet<>();
        return new ArrayList<>(sharedPreferences.getStringSet("hidden_equipment",defaultSet));
    }

    //returns if a user has allowed advanced exercises in the settings menu
    private String getDifficultyLevel() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString("base_difficulty_level", "3");
    }

    /** Is called to personalize the available results based on the users preferences
    ** @param difficultyLevel the difficulty level selected by the user, remove exercises more difficult than this
    ** @param hiddenEquipment removes equipment the user has selected in settings to they don't want
    **/
    private static void personalize(String difficultyLevel,ArrayList<String> hiddenEquipment) {
        remainingDb.removeDifficultyAbove(difficultyLevel);
        remainingDb.EquipRemoveRows(hiddenEquipment);
    }

    @Override
    public void onBackPressed() {
        //There is nothing to reset, return user to the homescreen
        if(backToHome) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }else{
            //Just reset the data first instead of going back to the homescreen
            reset(this.findViewById(android.R.id.content));
        }
    }

    //Resets all of the progress from the user in selecting an exercise and returns to the main activity
    public void reset(View view){
        refresh = true;
        //Refresh Activity with as first time created to reset the database
        finish();
        startActivity(getIntent());
    }

    //Sets the text for the view all button with the number of exercises remaining in the pool displayed
    private void setViewAllText() {
        Button viewAll = (Button) findViewById(R.id.viewAll);
        String allExercises = "Exercises (" + remainingDb.numRows() + ")";
        viewAll.setText(allExercises);
    }

    //Updates the displayed sorting path with the new category exercises are being sorted by
    private void updateSortingPath() {
        //Add new buttons for each of the sorting groups available to the user
        LinearLayout sortingPath = (LinearLayout) findViewById(R.id.sortingPath);
        sortingPath.removeAllViews();

        for(int i=0; i<chosenOptions.size(); i++){
            String name = chosenOptions.get(i).getName();
            //Don't printout difficulty levels because they're already preselected by the user and it might get annoying
            if(!name.toLowerCase().contains("difficulty")){
                Button b = new Button(this);
                b.setTextSize(9);
                b.setHeight(10);
                b.setText(name);
                b.setTextColor(Color.BLUE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int numExercises = SavedExercises.addExercise(chosenOptions,v.getContext());
                        Toast.makeText(v.getContext(), "Saved! (" + numExercises + " exercises in current workout)", Toast.LENGTH_SHORT).show();
                    }
                });
                sortingPath.addView(b);
            }
        }
    }

    //Sorts through the remaining exercises and eliminates the ones that no longer fit
    private void dbSearch(ExerciseDb db, String dbSortBy, String dbSortCategory) {
        db.removeRows(dbSortBy, dbSortCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.demo_video) {
            Intent intent = new Intent(this,OnboardingActivity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.play_store_rating) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            return true;
        }

        if (id == R.id.privacy_policy) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://swiftsetapp.com/privacy.html")));
        }

        return super.onOptionsItemSelected(item);
    }

    //Creates the different sorting group classes that exercises can be sorted by
    public void addMainMenuOptions(){
        currentOptions.add(new Equipment());
        currentOptions.add(new MuscleGroup());
        currentOptions.add(new PushPullLegs());
        currentOptions.add(new Tempo());
        currentOptions.add(new Joints());
        currentOptions.add(new Unilateral());
        currentOptions.add(new Stability());
        currentOptions.add(new Difficulty());
        currentOptions.add(new Sport());
        currentOptions.add(new Plyometrics());
        for(SortingGroup s:currentOptions){
            s.isOriginal = true;
        }
    }

    public void viewExercises(View view) {
        Intent intent = new Intent(this, ExerciseSelector.class);
        startActivity(intent);
    }

    public void viewWorkouts(View view) {
        remainingDb.resetDatabase();
        personalize(getDifficultyLevel(),getHiddenEquipment());
        Intent intent = new Intent(this, WorkoutViewer.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remainingDb.close();
    }

    //returns a sortingGroup based on its name
    public static boolean removeSortingGroup(SortingGroup sg){
        for(SortingGroup s:currentOptions){
            if(s.getClass().equals(sg.getClass())){
                removedOptions.add(s);
                currentOptions.remove(s);
                return true;
            }
        }
        return false;
    }

    //returns a sortingGroup based on its name
    public static boolean removeSortingGroup(Class sg){
        for(SortingGroup s:currentOptions){
            if(s.getClass().equals(sg)){
                removedOptions.add(s);
                currentOptions.remove(s);
                return true;
            }
        }
        return false;
    }


    //Adds buttons for every sorting group to the main page
    public void addButtons(Context context){
        LinearLayout l = (LinearLayout) findViewById(R.id.sortingButtons);
        l.removeAllViews();

        final ArrayList<SortingGroup> names=new ArrayList<>();//Helps the onClick function find what group was selected

        //Add new buttons for each of the sorting groups available to the user
        for(int i=0; i<currentOptions.size(); i++){
            Button newButton = new Button(this);
            SortingGroup s = currentOptions.get(i);
            newButton.setText(s.getName());
            newButton.setId(i);
            newButton.setPadding(5,0,0,0);
            newButton.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            final TypedValue value = new TypedValue();
            context.getTheme ().resolveAttribute(R.attr.colorAccent, value, true);
            GradientDrawable gd = new GradientDrawable();
            if(s.isOriginal) {
                gd.setColor(Color.WHITE); // Changes this drawbale to use a single color instead of a gradient
            }else{
                gd.setColor(Color.rgb(230, 255, 230));//Light green signifies it has been added because of the users choices
            }
            gd.setStroke(1, value.data);
            newButton.setCompoundDrawablesWithIntrinsicBounds(s.getGroupIcon(), 0, 0, 0);
            newButton.setCompoundDrawablePadding(5);
            newButton.setBackground(gd);
            newButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

            names.add(s);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),CategorySelector.class);
                    intent.putExtra("chosen_sorting_group",names.get(v.getId()));//Sends the chosen sorting group to the CategorySelector class when clicked
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            });
            l.addView(newButton);
        }
    }

    //Called when preferences are changed
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
    SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("notifications_new_message")) {
                //on represents if the user has noticaitons turned on
                boolean on = sharedPreferences.getBoolean("notifications_new_message", true);
                if (!on) {//If notifications are not on add them to a list of people not to send noticiations to
                    FirebaseMessaging.getInstance().subscribeToTopic("Unsubscribed");
                } else {//If they re-enable notifications, remove them from the blocked list
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("Unsubscribed");
                }
            }
        }
    };

    //Getters and Setters
    public static ExerciseDb getRemainingDb() {
        return remainingDb;
    }
}
