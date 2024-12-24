package com.example.plateful.Listeners;

import com.example.plateful.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListeners {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
