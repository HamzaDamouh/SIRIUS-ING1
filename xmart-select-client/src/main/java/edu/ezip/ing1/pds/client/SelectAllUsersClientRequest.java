package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Users;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllUsersClientRequest extends ClientRequest<Object, Users> {
    public SelectAllUsersClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Users readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Users users = mapper.readValue(body, Users.class);
        return users;
    }
}
