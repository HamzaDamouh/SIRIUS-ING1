package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Recipes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllRecipesClientRequest extends ClientRequest<Object, Recipes> {
    public SelectAllRecipesClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Recipes readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Recipes recipes = mapper.readValue(body, Recipes.class);
        return recipes;
    }
}
