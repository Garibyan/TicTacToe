package com.garibyan.armen.tictactoe

data class Field(
    var id: Int,
    var isCross: Boolean,
    var isClicked: Boolean,
    var viewType: Int
)
