package com.example.plateful;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plateful.Adapters.IngredientsAdapter;
import com.example.plateful.Adapters.InstructionsAdapter;
import com.example.plateful.Adapters.SimilarRecipeAdapter;
import com.example.plateful.Listeners.InstructionListener;
import com.example.plateful.Listeners.RecipeClickListener;
import com.example.plateful.Listeners.RecipeDetailsListener;
import com.example.plateful.Listeners.SimilarRecipesListener;
import com.example.plateful.Models.InstructionResponse;
import com.example.plateful.Models.RecipeDetailsResponse;
import com.example.plateful.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar,recycler_meal_instructions;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_details);
        findViews();
        id=Integer.parseInt(getIntent().getStringExtra("id"));
        manager =new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructions(instructionListener,id);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading Details........");
        dialog.show();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void findViews() {
        textView_meal_name=findViewById(R.id.textView_meal_name);
        textView_meal_source=findViewById(R.id.textView_meal_source);
        textView_meal_summary=findViewById(R.id.textView_meal_summary);
        imageView_meal_image=findViewById(R.id.imageViwe_meal_image);
        recycler_meal_ingredients=findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar=findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions=findViewById(R.id.recycler_meal_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter= new IngredientsAdapter(RecipeDetailsActivity.this,response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            //Toast.makeText(RecipeDetailsActivity.this, id, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

    private final InstructionListener instructionListener= new InstructionListener() {

        @Override
        public void didFetch(List<InstructionResponse> responses, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
            instructionsAdapter=new InstructionsAdapter(RecipeDetailsActivity.this, responses);
            recycler_meal_instructions.setAdapter(instructionsAdapter);

        }

        @Override
        public void didError(String message) {

        }
    };
}