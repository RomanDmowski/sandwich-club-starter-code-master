package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mPlaceOfOriginTextView;
    TextView mAlsoKnowAsListTextView;
    TextView mIngredientsListTextView;
    TextView mDescriptionTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mAlsoKnowAsListTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsListTextView = (TextView) findViewById(R.id.ingredients_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String _origin = sandwich.getPlaceOfOrigin();
        if (_origin.isEmpty()){
            mPlaceOfOriginTextView.setText(R.string.no_data_message );
        }
        else {
            mPlaceOfOriginTextView.setText(_origin );
        }
        mPlaceOfOriginTextView.append("\n");


        //alsoKnownAs
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if (alsoKnownList.size()>0){
            for (int i=0; i< alsoKnownList.size();i++){
                mAlsoKnowAsListTextView.append(alsoKnownList.get(i) + "\n");
            }
        }
        else  {
                mAlsoKnowAsListTextView.setText(R.string.no_data_message );
                mAlsoKnowAsListTextView.append("\n");
        }


        //ingredients

        List<String> ingrendientsList = sandwich.getIngredients();
        if (ingrendientsList.size()>0){
            for (int i=0; i< ingrendientsList.size();i++){
                mIngredientsListTextView.append(ingrendientsList.get(i) + "\n");
            }
        }
        else {
            mIngredientsListTextView.setText(R.string.no_data_message );
        }

        String _description =sandwich.getDescription();
        if(_description.isEmpty()){
            mDescriptionTextView.setText(R.string.no_data_message);
        }
        else {
            mDescriptionTextView.setText(_description);
        }

    }
}
