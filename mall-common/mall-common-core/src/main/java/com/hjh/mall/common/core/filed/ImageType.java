package com.hjh.mall.common.core.filed;

public enum ImageType {
	
	    BMP("BMP", "image/bmp", "bmp"),
	    
	    JPG("JPG", "image/jpeg", "jpg"),
	    
	    GIF("GIF", "image/gif", "gif"),
	    
	    PNG("PNG", "image/png", "png"),	    
	    ;	    
	    private String name;
	    
	    private String mime;
	    
	    private String suffix;
	    
	    private ImageType(String name, String mime, String suffix) {
	        this.name = name;
	        this.mime = mime;
	        this.suffix = suffix;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public String getMime() {
	        return mime;
	    }
	    
	    public String getSuffix() {
	        return suffix;
	    }
	    
	    @Override
	    public String toString() {
	        return new StringBuilder().append(name).append('[').append(mime).append(']').append('.').append(suffix)
	                .toString();
	    }

}
