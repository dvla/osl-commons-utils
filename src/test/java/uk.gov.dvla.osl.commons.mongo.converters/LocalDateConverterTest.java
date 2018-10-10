package uk.gov.dvla.osl.commons.mongo.converters;

import com.github.fakemongo.Fongo;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * LocalDate converter test.
 */
public class LocalDateConverterTest {

    private static final String TEST_DB = "testDB";
    private BasicDAO<LocalDateTestEntity,UUID> testDao;

    @Before
    public void setUp() {
        Morphia morphia = new Morphia();
        morphia.getMapper().getConverters().addConverter(LocalDateConverter.class);
        Fongo fongo = new Fongo("Mongo Server 1");
        testDao = new BasicDAO(LocalDateTestEntity.class, fongo.getMongo(), morphia, TEST_DB);
    }

    @Test
    public void testBigDecimalConverter() throws Exception {
        UUID id = UUID.randomUUID();
        LocalDate regDate =  LocalDate.parse("1991-06-17", ofPattern("yyyy-MM-dd"));


        testDao.save(new LocalDateTestEntity(id, regDate));

        LocalDateTestEntity testEntity = testDao.get(id);
        assertThat(testEntity.getRegDate(), is(regDate));


    }

}
