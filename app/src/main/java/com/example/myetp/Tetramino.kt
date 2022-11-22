package com.example.myetp

import com.example.myetp.Coordinate.Companion.add
import com.example.myetp.Coordinate.Companion.rotateAntiClock
import com.example.myetp.Coordinate.Companion.sub
import java.util.*

enum class TetraminoType {
    SQUARE_SHAPED, T_SHAPED, L_SHAPED, LINE_SHAPED, Z_SHAPED, INV_L_SHAPED, INV_Z_SHAPED;

    companion object {
        private val VALUES = values()
        private val SIZE = VALUES.size
        private val RANDOM = Random()
        val randomTetramino: TetraminoType
            get() = VALUES[RANDOM.nextInt(SIZE)]
    }
}

class Tetramino {
    lateinit var blocks: Array<BasicBlock?>
    var type: TetraminoType? = null

    constructor(type: TetraminoType?, tetraId: Int) {
        val coordinates: Array<Coordinate>
        when (type) {
            TetraminoType.SQUARE_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(0, 10),
                    Coordinate(1, 10),
                    Coordinate(1, 11),
                    Coordinate(0, 11)
                )
                blocks = blocksGenerator(tetraId, 1, coordinates)
            }
            TetraminoType.INV_L_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(0, 10),
                    Coordinate(0, 11),
                    Coordinate(1, 10),
                    Coordinate(2, 10)
                )
                blocks = blocksGenerator(tetraId, 2, coordinates)
            }
            TetraminoType.L_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(0, 11),
                    Coordinate(0, 10),
                    Coordinate(1, 11),
                    Coordinate(2, 11)
                )
                blocks = blocksGenerator(tetraId, 3, coordinates)
            }
            TetraminoType.T_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(1, 10),
                    Coordinate(0, 10),
                    Coordinate(1, 11),
                    Coordinate(2, 10)
                )
                blocks = blocksGenerator(tetraId, 4, coordinates)
            }
            TetraminoType.Z_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(1, 11),
                    Coordinate(1, 10),
                    Coordinate(0, 10),
                    Coordinate(2, 11)
                )
                blocks = blocksGenerator(tetraId, 5, coordinates)
            }
            TetraminoType.INV_Z_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(1, 11),
                    Coordinate(0, 11),
                    Coordinate(1, 10),
                    Coordinate(2, 10)
                )
                blocks = blocksGenerator(tetraId, 6, coordinates)
            }
            TetraminoType.LINE_SHAPED -> {
                coordinates = arrayOf(
                    Coordinate(0, 10),
                    Coordinate(1, 10),
                    Coordinate(2, 10),
                    Coordinate(3, 10)
                )
                blocks = blocksGenerator(tetraId, 7, coordinates)
            }
            else -> {}
        }
    }

    private constructor(blocks: Array<BasicBlock?>) {
        this.blocks = blocks
    }

    private fun blocksGenerator(
        tetraId: Int,
        colour: Int,
        coordinates: Array<Coordinate>
    ): Array<BasicBlock?> {
        val blocks = arrayOfNulls<BasicBlock>(coordinates.size)
        for (itr in coordinates.indices) {
            blocks[itr] =
                BasicBlock(colour, tetraId, coordinates[itr], BasicBlockState.ON_TETRAMINO)
        }
        return blocks
    }

    fun copy(tetraId: Int): Tetramino {
        val newBlocks = arrayOfNulls<BasicBlock>(blocks.size)
        for (itr in blocks.indices) {
            newBlocks[itr] = blocks[itr]!!.copy()
            newBlocks[itr]!!.tetraId = tetraId
        }
        return Tetramino(newBlocks)
    }

    fun moveDown() {
        for (block in blocks) {
            block!!.coordinate.y++
        }
    }

    fun moveLeft() {
        for (block in blocks) {
            block!!.coordinate.x--
        }
    }

    fun moveRight() {
        for (block in blocks) {
            block!!.coordinate.x++
        }
    }

    fun performClockWiseRotation() {
        val referenceBlock = blocks[0]
        for (block in blocks) {
            val baseCoordinate = sub(block!!.coordinate, referenceBlock!!.coordinate)
            block.coordinate = add(rotateAntiClock(baseCoordinate), referenceBlock.coordinate)
        }
    }
}