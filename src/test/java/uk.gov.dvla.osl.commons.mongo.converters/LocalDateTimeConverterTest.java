package uk.gov.dvla.osl.commons.mongo.converters;

import com.github.fakemongo.Fongo;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.Converters;
import org.mongodb.morphia.dao.BasicDAO;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * LocalDatetime converter test.
 */
public class LocalDateTimeConverterTest {

    private static final String TEST_DB = "mongotestDB";
    private BasicDAO<LocalDateTimeTestEntity,UUID> testDao;
    public UUID id = UUID.randomUUID();
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Before
    public void setUp() {
        Morphia morphia = new Morphia();
        Converters converters= morphia.getMapper().getConverters();
        converters.addConverter(LocalDateTimeConverter.class);
        morphia.mapPackage(LocalDateTimeTestEntity.class.getName());
        Fongo fongo = new Fongo("Mongo Server 1");
        final Datastore datastore = morphia.createDatastore(fongo.getMongo(), TEST_DB);
        datastore.ensureIndexes();
        testDao = new BasicDAO(LocalDateTimeTestEntity.class,datastore);
    }

    @Test
    public void testLocalDateTimeConverter() throws Exception {

        Date date =new SimpleDateFormat(DATE_FORMAT).parse("2001-03-01T00:00:00Z");
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime regDateTime =  LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        testDao.save(new LocalDateTimeTestEntity(id, regDateTime));
        LocalDateTimeTestEntity testEntity = testDao.get(id);
        assertThat(testEntity.getRegDate(), is(regDateTime));

    }

}
