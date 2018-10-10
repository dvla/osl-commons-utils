package uk.gov.dvla.osl.commons.mongo.converters;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 *  LocalDateTime converter, converts from Date to LocalDateTime.
 */
public class LocalDateTimeConverter extends TypeConverter implements SimpleValueConverter {

    /** No argument constructor. */
    public LocalDateTimeConverter() {
        super(LocalDateTime.class);
    }

    @Override
    protected boolean isSupported(Class<?> cls, MappedField optionalExtraInfo) {
        return LocalDateTime.class.isAssignableFrom(cls);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        LocalDateTime ldt = (LocalDateTime)value;
        Instant instant = ldt.atZone(ZoneId.of("Z")).toInstant();
        return Date.from(instant);
    }

    @Override
    public Object decode(@SuppressWarnings("rawtypes") Class targetClass,
                         Object fromDBObject, MappedField optionalExtraInfo)
            throws MappingException {
        if (fromDBObject == null) {
            return null;
        }
        Date date = (Date)fromDBObject;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
