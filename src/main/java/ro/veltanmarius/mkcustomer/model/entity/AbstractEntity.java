package ro.veltanmarius.mkcustomer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.Instant;

@MappedSuperclass
public class AbstractEntity {
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
    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Instant lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String updatedBy) {
        this.lastUpdatedBy = updatedBy;
    }
}