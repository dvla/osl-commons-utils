package uk.gov.dvla.osl.commons.mongo;


import com.codahale.metrics.health.HealthCheck;
import com.mongodb.DB;
import com.mongodb.MongoException;

public class MongoHealthCheck extends HealthCheck {

    private final DB db;

    public MongoHealthCheck(final DB db) {
        this.db = db;
    }

    @Override
    protected Result check() throws Exception {
        try {
            db.command("isMaster");
            return Result.healthy();
        }
        catch( MongoException me ) {
            return Result.unhealthy("cannot access database");
        }
    }
}