package com.example.firstassignment;

public enum CellPosition {
    EMPTY("empty"),
    RED("X"),
    BLUE("O");

    private String cellSign;

    CellPosition(String cellSign){
        this.cellSign = cellSign;
    }

    public String getLabel(){
        return this.cellSign;
    }
}
