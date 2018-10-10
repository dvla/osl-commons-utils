package uk.gov.dvla.osl.commons.mongo.converters;

import java.math.BigDecimal;
import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class BigDecimalTestEntity {

    @Id
    private UUID id;
    private BigDecimal price;


    public BigDecimalTestEntity() {
    }

    public BigDecimalTestEntity(UUID id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
