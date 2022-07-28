package com.garibyan.armen.tictactoe

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.garibyan.armen.tictactoe.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var binding: FragmentGameBinding? = null
    private val args by navArgs<GameFragmentArgs>()
    private val fieldAdapter by lazy { FieldAdapter() }
    private var turnOfCross = true
    private var clickCounter = 0
    private var winner = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)



        return binding!!.root
    }

    private fun init() {
        repeat(args.fieldSize * args.fieldSize) {
            fieldsList.add(Field(it, false, false, false, ViewType.EMPTY))
        }

        binding!!.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), args.fieldSize)
            adapter = fieldAdapter
            isNestedScrollingEnabled = false

            fieldAdapter.submitList(fieldsList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        fieldAdapter.onItemClickListener = { x ->
            fieldsList.forEach {
                if (it.id == x.id && !it.isClicked) {
                    if (turnOfCross) {
                        fieldsList[fieldsList.indexOf(it)] = setCross(it)
                        turnOfCross = false
                    } else {
                        fieldsList[fieldsList.indexOf(it)] = setZero(it)
                        turnOfCross = true
                    }
                }
            }
            fieldAdapter.submitList(fieldsList.toList())
            checkWinner()
        }
        fieldAdapter.submitList(fieldsList.toList())
    }

    private fun horizontalCkeck(): Int {
        val size = args.fieldSize
        val horizontal = fieldsList.chunked(size)
        var horizontalWinner = 0

        horizontal.forEach {
            it.forEach {
                horizontalWinner = when (true) {
                    (it.isCross && it.isClicked) -> 1
                    (it.isZero && it.isClicked) -> 2
                    else -> 0
                }
            }
        }
        return horizontalWinner
    }

    private fun verticalCheck(): Int {
        val size = args.fieldSize
        var iter = 0
        val listSize = fieldsList.size
        val verticalList = mutableListOf<List<Field>>()
        var verticalWinner = 0

        repeat(size) {
            verticalList.add(fieldsList.slice(iter until listSize step size))
            iter++
        }

        verticalList.forEach {
            it.forEach {
                verticalWinner = when (true) {
                    (it.isCross && it.isClicked) -> 1
                    (it.isZero && it.isClicked) -> 2
                    else -> 0
                }
            }
            d("winner", winner.toString())
        }
        return verticalWinner
    }

    private fun diagonalCheck(): Int {
        val size = args.fieldSize
        val listSize = fieldsList.size
        val diagonalList = mutableListOf<MutableList<Field>>()
        var diagonalWinner = 0

        diagonalList.add(fieldsList.slice(0 until listSize step size + 1).toMutableList())
        diagonalList.add(fieldsList.slice(size - 1 until listSize step size - 1).toMutableList())
        diagonalList[1].removeLast()

        diagonalList.forEach {
            it.forEach {
                diagonalWinner = when (true) {
                    (it.isCross && it.isClicked) -> 1
                    (it.isZero && it.isClicked) -> 2
                    else -> 0
                }
            }
        }
        return diagonalWinner
    }

    private fun checkWinner() {

        horizontalCkeck()
        verticalCheck()
        diagonalCheck()
        d("winner", winner.toString())
    }

    private fun setCross(field: Field): Field {
        clickCounter++
        return Field(field.id, true, false, true, ViewType.CROSS)
    }

    private fun setZero(field: Field): Field {
        clickCounter++
        return Field(field.id, false, true, true, ViewType.ZERO)
    }
}

//    [(0, 1, 2, 3, 4)]
//    [(5, 6, 7, 8, 9)]
//    [(10, 11, 12, 13, 14)]
//    [(15, 16, 17, 18, 19)]
//    [(20, 21, 22, 23, 24)]

//    [(0, 1, 2)]
//    [(3, 4, 5)]
//    [(6, 7, 8)]

//    [(0, 1, 2, 3)]
//    [(4, 5, 6, 7)]
//    [(8, 9, 10, 11)]
//    [(12, 13, 14, 15)]
