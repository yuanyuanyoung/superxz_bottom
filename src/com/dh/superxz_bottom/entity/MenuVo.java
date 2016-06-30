package com.dh.superxz_bottom.entity;

import java.util.List;

/**
 * @author 超级小志
 *
 */
public class MenuVo extends BaseBean {

    // subtitle (string, optional): 小标题,
    // menuId (string): 菜单ID,
    // menuName (string): 菜单名称,
    // menuLevel (string): 菜单级别,
    // menuUrl (string, optional): 菜单地址,
    // parentMenuId (string, optional): 父菜单ID,
    // sortNo (integer, optional): 菜单顺序,
    // menuType (string, optional): 菜单类型,
    // display (string, optional): 菜单是否显示,
    // chilMenus (array[MenuVO], optional): 子菜单列表,
    // pictureLocation (string, optional): 图片位置,
    // createDate (date-time, optional): 菜单创建日期,
    // className (string, optional): 访问类名,
    // methodName (string, optional): 访问方法名,
    // source (string, optional): 访问来源，1：IOS；2：andriod；3：触屏
   

    private String subtitle;// 小标题
    private String menuId;// 菜单ID
    private String menuName;// 菜单名称
    private String menuLevel;// 菜单级别
    private String menuUrl;// 菜单地址
    private String parentMenuId;// 父菜单ID
    private String sortNo;// 菜单顺序
    private String menuType;// 菜单类型
    private String display;// 菜单是否显示
    private List<MenuVo> chilMenus;// 子菜单列表
    private String pictureLocation;// 图片位置
    private String createDate;// 菜单创建日期
    private String className;// 访问类名
    private String methodName;// 访问方法名
    private String source;// 访问来源，1：IOS；2：andriod；3：触屏
    
    
    public MenuVo(){}
    
    public MenuVo(String name){
        menuName = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<MenuVo> getChilMenus() {
        return chilMenus;
    }

    public void setChilMenus(List<MenuVo> chilMenus) {
        this.chilMenus = chilMenus;
    }

    public String getPictureLocation() {
        return pictureLocation;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}