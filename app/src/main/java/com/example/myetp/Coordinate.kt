package com.example.myetp

import com.example.myetp.Coordinate

class Coordinate(var y: Int, var x: Int) {
    companion object {
        @JvmStatic
        fun add(A: Coordinate, B: Coordinate): Coordinate {
            return Coordinate(A.y + B.y, A.x + B.x)
        }

        @JvmStatic
        fun sub(A: Coordinate, B: Coordinate): Coordinate {
            return Coordinate(A.y - B.y, A.x - B.x)
        }

        @JvmStatic
        fun rotateAntiClock(X: Coordinate): Coordinate {
            return Coordinate(-X.x, X.y)
        }

        @JvmStatic
        fun isEqual(A: Coordinate, B: Coordinate): Boolean {
            return A.y == B.y && A.x == B.x
        }
    }
}