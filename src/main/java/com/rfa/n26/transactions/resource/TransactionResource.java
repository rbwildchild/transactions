
package com.rfa.n26.transactions.resource;

import static com.rfa.n26.transactions.model.TransactionStorageStatus.*;

import com.rfa.n26.transactions.exception.TransactionHolderException;
import com.rfa.n26.transactions.holder.TransactionHolder;
import com.rfa.n26.transactions.model.Transaction;
import com.rfa.n26.transactions.model.TransactionStorageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;

@Service
@Validated
@Path("/transactions")
public class TransactionResource {

    static private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    @Autowired
    private TransactionHolder transactionHolder;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveTransaction(@NotNull Transaction transaction) {
        try {
            TransactionStorageStatus transactionStorageStatus = transactionHolder.saveTransaction(transaction);
            return Response.status(getResponseStatus(transactionStorageStatus)).entity("Success").build();
        } catch(TransactionHolderException the) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(the.getMessage()).build();
        }
    }

    private Response.Status getResponseStatus(TransactionStorageStatus transactionStorageStatus) {
        switch (transactionStorageStatus) {
            case DELAYED: return Response.Status.NO_CONTENT;
            case OK:
            default: return Response.Status.CREATED;
        }
    }

}
