package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Recipes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("recipes")
    private  Set<Recipe> recipes = new LinkedHashSet<Recipe>();

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public final Recipes add (final Recipe recipe) {
        recipes.add(recipe);
        return this;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "Recipes=" + recipes +
                '}';
    }
}
