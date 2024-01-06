package ro.veltanmarius.mkcustomer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.Instant;

/**
 * @author Marius Veltan
 */
@MappedSuperclass
@Data
public sealed class AbstractEntity permits CustomerEntity {
    @CreationTimestamp
    @Column(name="created_date")
    private Instant createdOn;
    @UpdateTimestamp
    @Column(name="last_updated_date")
    private Instant lastUpdatedOn;
    @CreatedBy
    @Column(name="created_by")
    private String createdBy;
    @LastModifiedBy
    @Column(name="last_updated_by")
    private String lastUpdatedBy;

}