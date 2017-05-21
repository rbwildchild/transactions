
package com.rfa.n26.transactions.resource;

import com.rfa.n26.transactions.holder.TransactionHolder;
import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStorageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("/statistics")
public class StatsResource {

    @Autowired
    private TransactionHolder transactionHolder;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats() {
        return Response.ok(transactionHolder.getTransactionStats()).build();
    }

}
