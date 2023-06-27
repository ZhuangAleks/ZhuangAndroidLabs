package algonquin.cst2335.torunse;

import java.io.Serializable;
import java.util.List;

public class CatImg implements Serializable {

    /**
     * tags : []
     * createdAt : 2022-05-24T05:13:36.430Z
     * updatedAt : 2022-10-11T07:52:32.725Z
     * validated : true
     * owner : null
     * file : 628c6980c303160017d5a36e.jpeg
     * mimetype : image/jpeg
     * size : 373329
     * _id : RaAraGK7NRcy70HS
     * url : /cat/RaAraGK7NRcy70HS
     */

    private String createdAt;
    private String updatedAt;
    private boolean validated;
    private String owner;
    private String file;
    private String mimetype;
    private int size;
    private String _id;
    private String url;
    private List<?> tags;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }
}
