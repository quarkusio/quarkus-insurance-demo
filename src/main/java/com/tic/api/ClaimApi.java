package com.tic.api;

import com.tic.service.Claim;
import com.tic.service.ClaimService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET; 
import jakarta.ws.rs.Path; 
import java.util.List;

/**
 * Claims Rest Api
 * @author Phillip Kruger(phillip.kruger@gmail.com)
 */
@Path("/api")
public class ClaimApi {
    
    @Inject
    ClaimService claimService;
    
    @GET 
    @Path("/claim")
    public List<Claim> getClaims() { 
        return claimService.getClaims();
    }
}
