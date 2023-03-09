/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coesi.util;

/**
 *
 * @author Alberto
 */
public class GlobalUtils {

    /**
     * Removes non-uppercase characters and numbers from strings it receives as
     * parameter and concatenates them
     *
     * @param items
     * @return
     */
    public static String createName(String... items) {
        String name = "";
        for (String item : items) {
            name += item.replaceAll("[^A-Z,0-9]", "");
        }
        return name;
    }
}
