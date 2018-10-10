package uk.gov.dvla.osl.commons.mongo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MongoConfiguration {


    protected List<Server> seeds = new ArrayList<>();
    protected Credentials credentials;
    @NotNull
    protected String database;
    protected String writeConcern = "ACKNOWLEDGED";
    protected boolean enabled = true;

    public List<Server> getSeeds() {
        return seeds;
    }

    public void setSeeds( List<Server> seeds ) {
        this.seeds = seeds;
    }

    public String getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern( String writeConcern ) {
        this.writeConcern = writeConcern;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled( boolean enabled ) {
        this.enabled = enabled;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials( Credentials credentials ) {
        this.credentials = credentials;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase( String database ) {
        this.database = database;
    }

    public static class Server {
        @NotNull
        protected String host;
        @NotNull
        protected Integer port;
        public String getHost() {
            return host;
        }
        public void setHost( String host ) {
            this.host = host;
        }
        public Integer getPort() {
            return port;
        }
        public void setPort( Integer port ) {
            this.port = port;
        }
    }

    public static class Credentials {
        @NotNull
        protected String userName;
        @NotNull
        protected String password;
        public String getUserName() {
            return userName;
        }
        public void setUserName( String userName ) {
            this.userName = userName;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword( String password ) {
            this.password = password;
        }
    }


}