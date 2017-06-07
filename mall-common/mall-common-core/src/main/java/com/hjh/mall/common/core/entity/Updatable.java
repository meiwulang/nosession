package com.hjh.mall.common.core.entity;


public abstract class Updatable extends Markable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected Long update_version;
    
    protected String update_user;
    
    public Long getUpdate_version() {
        return update_version;
    }
    
    public void setUpdate_version(Long update_version) {
        this.update_version = update_version;
    }
    

    
    public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Updatable [update_version=").append(update_version).append(", update_user=")
                .append(update_user).append("]");
        return builder.toString();
    }
    
}
