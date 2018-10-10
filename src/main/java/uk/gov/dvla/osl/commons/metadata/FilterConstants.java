package uk.gov.dvla.osl.commons.metadata;

public class FilterConstants {

    // We will pass-through all headers starting with this prefix via the ThreadLocalMetadata
    public static final String OSL_HEADER_PREFIX = "OSL-";

    // In addition, ThreadLocalMetadata items starting keyed starting with this prefix will be put in
    // EventStore metadata by EventStore elient
    public static final String OSL_EVENT_META_PREFIX = OSL_HEADER_PREFIX + "EVENT-META-";

    // This is the 'New' correlation id header that we are migrating to.
    // it is stored automatically via EventStore Client
    public static final String OSL_CORRELATION_ID_HEADER = "OSL-Correlation-Id";

    // This is the causationId header, that is stored automatically via EventStore Client
    public static final String OSL_CAUSATION_ID_HEADER = "OSL-Causation-Id";

}
