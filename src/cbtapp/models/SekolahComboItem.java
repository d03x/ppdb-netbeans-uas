/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbtapp.models;


public class SekolahComboItem {
    private int id;
    private String label;

    public SekolahComboItem(int id, String label)  {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return label;
    }
}
