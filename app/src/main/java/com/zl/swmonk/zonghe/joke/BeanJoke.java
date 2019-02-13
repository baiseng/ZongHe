package com.zl.swmonk.zonghe.joke;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.zl.swmonk.zonghe.base.model.AppDatabase;

import java.io.Serializable;

@Table(database = AppDatabase.class)
public class BeanJoke extends BaseModel implements Serializable {

    /**
     * title : 感觉今天领导人确...
     * id : 5c0c8c416e36c2c7fa2475da
     * ct : 2018-12-09 11:30:09.657
     * text : type==1时需要，可为空
     * img:type==2或type==3时需要，可为空
     * type : 1或2或3
     */

    @Column
    private String title;
    @PrimaryKey
    @Column
    private String id;
    @Column
    private String ct;
    @Column
    private String text;
    @Column
    private String img;
    @Column()
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
