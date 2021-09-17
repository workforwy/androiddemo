package com.ceshi.ha.bean;

import java.util.Arrays;

public class Container {
    private Contact[] contacts = new Contact[3];
    private int size;

    private Container() {
    }

    private static Container instance;

    static {
        instance = new Container();
    }

    public static Container getInstance() {
        return instance;
    }

    /**
     * 此方法负责将联系人添加到容器中
     */
    public void addContact(Contact contact) {
        // 判定是否已满，满了要扩容
        if (size == contacts.length) {
            contacts = Arrays.copyOf(contacts, 2 * contacts.length);
        }
        // 放数据
        contacts[size++] = contact;
        // 输出数组中所有数据
        System.out.println(Arrays.toString(contacts));
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public int size() {
        return size;
    }
}
