package com.study.security.auth.vo;

import com.study.security.api.vo.authority.PermissionInfo;

import java.util.List;

/**
 * @ClassName FrontUser
 * @Description 前端用户
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
public class FrontUser {
    public String id;
    public String username;
    public String name;
    private String description;
    private String image;
    private List<PermissionInfo> menus;
    private List<PermissionInfo> elements;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<PermissionInfo> getMenus() {
        return menus;
    }

    public void setMenus(List<PermissionInfo> menus) {
        this.menus = menus;
    }

    public List<PermissionInfo> getElements() {
        return elements;
    }

    public void setElements(List<PermissionInfo> elements) {
        this.elements = elements;
    }
}
