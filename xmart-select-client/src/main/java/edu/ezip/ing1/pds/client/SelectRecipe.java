package edu.ezip.ing1.pds.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Recipe;
import edu.ezip.ing1.pds.business.dto.Recipes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class SelectRecipe {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_ALL_Recipe";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();



    // for GUI
    public static String retrieveRecipes() throws IOException, InterruptedException, SQLException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllRecipesClientRequest clientRequest = new SelectAllRecipesClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);
        StringBuilder recipesInfoBuilder = new StringBuilder();
        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Recipes recipes = (Recipes) joinedClientRequest.getResult();
            recipesInfoBuilder.append(getRecipesInfo(recipes)).append("\n");
        }
        return recipesInfoBuilder.toString();
    }

    // For dispatch
    private static String getRecipesInfo(Recipes recipes) {
        StringBuilder stringBuilder = new StringBuilder();
        AsciiTable asciiTable = new AsciiTable();
        for (Recipe recipe : recipes.getRecipes()) {
            asciiTable.addRule();
            asciiTable.addRow(recipe.getName(), recipe.getIngredients(), recipe.getCalories(),recipe.getBreakfast());
        }
        asciiTable.addRule();
        stringBuilder.append("\n").append(asciiTable.render()).append("\n");
        return stringBuilder.toString();
    }
}


