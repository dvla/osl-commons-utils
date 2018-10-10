package uk.gov.dvla.osl.commons.mongo.converters;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 *  LocalDate converter.
 */
public class LocalDateConverter  extends TypeConverter implements SimpleValueConverter {

    public LocalDateConverter() {
        super(LocalDate.class);
    }

    @Override
    protected boolean isSupported(Class<?> cls, MappedField optionalExtraInfo) {
        return LocalDate.class.isAssignableFrom(cls);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    @Override
    public Object decode(@SuppressWarnings("rawtypes") Class targetClass,
                         Object fromDBObject, MappedField optionalExtraInfo)
            throws MappingException {
        if (fromDBObject == null) {
            return null;
        }
        return LocalDate.parse(fromDBObject.toString(), ofPattern("yyyy-MM-dd"));
    }
}
