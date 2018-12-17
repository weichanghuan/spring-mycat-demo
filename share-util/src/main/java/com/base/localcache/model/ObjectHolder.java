package com.base.localcache.model;

/**
 * 对象持有类。
 */
public class ObjectHolder {
    /**
     * 对象版本
     */
    private String objectVersion;

    /**
     * 对象checksum,用于版本判断
     */
    private String objectChecksum;

    /**
     * 对象
     */
    private Object object;

    public String getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getObjectChecksum() {
        return objectChecksum;
    }

    public void setObjectChecksum(String objectChecksum) {
        this.objectChecksum = objectChecksum;
    }
}
