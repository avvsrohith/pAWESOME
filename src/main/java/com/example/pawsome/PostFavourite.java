package com.example.pawsome;

public class PostFavourite {

    private String image_id;
    private String sub_id;

    public PostFavourite(String imageId,String subid) {
        this.image_id = imageId;
        this.sub_id=subid;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }
}
