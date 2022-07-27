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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    private fun init() {
        repeat(args.fieldSize * args.fieldSize) {
            fieldsList.add(Field(it, false, false, ViewType.EMPTY))
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

        val updatedList = mutableListOf<Field>()

        //tictactoes logika arc miweria, aq ubralob vcdilobdi viewtypis shecvlas, magram araferi ar icvleboda
        fieldAdapter.onItemClickListener = { x ->
            updatedList.addAll(fieldsList)
            updatedList.forEachIndexed { index, field ->
                if (field.id == x.id) {
                    updatedList[index] = field.copy(viewType = ViewType.CROSS)
                }
            }
            fieldAdapter.submitList(updatedList.toList())
            d("mylog", fieldsList.toString())
            d("mylog", updatedList.toString())
        }
    }
}