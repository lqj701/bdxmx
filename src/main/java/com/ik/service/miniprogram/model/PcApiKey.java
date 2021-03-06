package com.ik.service.miniprogram.model;

import java.io.Serializable;
import java.util.Date;
import org.mybatis.extend.generic.model.BaseModel;

/**
 * Model: PcApiKey
 * Table: pc_api_keys
 * Alias: pak
 *
 * This Model generated by MyBatis Generator Extend.
 */
public class PcApiKey extends BaseModel<Integer> implements Serializable {
    /**
     * 用户ID
     * user_id
     */
    private Integer userId;

    /**
     * 
     * access_token
     */
    private String accessToken;

    /**
     * 创建时间
     * created_at
     */
    private Date createdAt;

    /**
     * 修改时间
     * updated_at
     */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     * user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * user_id
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * access_token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 
     * access_token
     *
     * @param accessToken 
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 创建时间
     * created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     * created_at
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 修改时间
     * updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 修改时间
     * updated_at
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PcApiKey other = (PcApiKey) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}