package com.ceshi.ha.bean;

public class Contact {
    private String name;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 通过构造方法赋值
     */
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * 重写Object类的toString方法 当直接输出Contact对象时会 调用此方法输出字符串格式的结果
     */
    @Override
    public String toString() {
        return "Contact [name=" + name + ",phone=" + phone + "]";
    }
}
