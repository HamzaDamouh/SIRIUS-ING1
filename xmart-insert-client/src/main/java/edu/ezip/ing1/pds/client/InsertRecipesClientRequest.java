package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Recipe;
import edu.ezip.ing1.pds.business.dto.Recipes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertRecipesClientRequest extends ClientRequest<Recipe, String> {

    public InsertRecipesClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Recipe info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> recipeIdMap = mapper.readValue(body, Map.class);
        final String result  = recipeIdMap.get("recipe_id").toString();
        return result;
    }
}




