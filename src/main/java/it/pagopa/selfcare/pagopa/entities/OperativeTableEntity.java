package it.pagopa.selfcare.pagopa.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "id")
@Document("tavoloOp")
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class OperativeTableEntity implements Persistable<String> {

    @Id
    private String id;

    private String taxCode;

    private String name;

    private String referent;

    private String email;

    private String telephone;

    @LastModifiedDate
    @FieldNameConstants.Include
    private Instant modifiedAt;
    @LastModifiedBy
    @FieldNameConstants.Include
    private String modifiedBy;

    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    private String createdBy;

    public OperativeTableEntity() {
        createdAt = Instant.now();
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public static class Fields {
        protected static String id = org.springframework.data.mongodb.core.aggregation.Fields.UNDERSCORE_ID;

        private Fields() {
        }
    }

}
