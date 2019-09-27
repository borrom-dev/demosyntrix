package com.angkorsuntrix.demosynctrix.entity.audit;

import com.angkorsuntrix.demosynctrix.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserAudit extends DateAudit {

    @CreatedBy
    @JsonProperty("create_by")
    private Long createBy;

    @LastModifiedBy
    @JsonProperty("update_by")
    private Long updateBy;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
