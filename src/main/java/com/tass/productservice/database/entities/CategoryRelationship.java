package com.tass.productservice.database.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "category_relationship")
@Data
public class CategoryRelationship {

    @EmbeddedId
    private PK pk;

    @Data
    @Embeddable
    public static class PK implements Serializable {
        @Column(name = "id")
        private long parentId;
        @Column(name = "link_id")
        private long childrenId;

        public PK(long parentId, long childrenId) {
            this.parentId = parentId;
            this.childrenId = childrenId;
        }

        public PK() {

        }
    }
}
