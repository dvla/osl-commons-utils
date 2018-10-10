package uk.gov.dvla.osl.commons.mongo.converters;


import java.math.BigDecimal;
import java.util.UUID;

import com.github.fakemongo.Fongo;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BigDecimalConverterTest {

    private static final String TEST_DB = "testDB";
    private BasicDAO<BigDecimalTestEntity,UUID> testDao;

    @Before
    public void setUp() {

        Morphia morphia = new Morphia();
        morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);
        Fongo fongo = new Fongo("Mongo Server 1");
        testDao = new BasicDAO(BigDecimalTestEntity.class, fongo.getMongo(), morphia, TEST_DB);
    }

    @Test
    public void testBigDecimalConverter() throws Exception {

        for (int i = 0; i < 100; i++) {
            UUID id = UUID.randomUUID();
            BigDecimal price = generateRandomBigDecimalFromRange(new BigDecimal(-0.1)
                            .setScale(10, BigDecimal.ROUND_HALF_UP),
                    new BigDecimal(2147483647.2147483647).
                            setScale(10, BigDecimal.ROUND_HALF_UP));

            testDao.save(new BigDecimalTestEntity(id, price));

            BigDecimalTestEntity testEntity = testDao.get(id);
            assertThat(testEntity.getPrice(), is(price));
        }
    }

    private static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(10,BigDecimal.ROUND_HALF_UP);
    }
}