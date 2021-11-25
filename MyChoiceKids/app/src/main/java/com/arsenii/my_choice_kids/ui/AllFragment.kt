package com.arsenii.my_choice_kids.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arsenii.my_choice_kids.R

class AllFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    private val organizations = mutableListOf<String>("Планета спорта", "Детская худ. школа", "Музыкальная школа", "Плавание", "Театр", "Уроки математики")
    private val organizationsTypes = mutableListOf<String>("Спорт", "Рисование", "Музыка", "Спорт", "Театр", "Обучение")
    private val organizationsAddresses = mutableListOf<String>("Чекистов 43", "Советский проспект 12", "Проспект мира 81", "Проспект мира 15", "Проспект мира 10", "Киевская 1")

    private lateinit var viewOfFragment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfFragment = inflater.inflate(R.layout.fragment_all, container, false)

        recyclerView = viewOfFragment.findViewById(R.id.recyclerView_all)
        recyclerAdapter = RecyclerAdapter(organizations, organizationsTypes, organizationsAddresses)

        recyclerView.adapter = recyclerAdapter

        return viewOfFragment
    }

    fun get_organizations(): Array<String> {
        return arrayOf("5","10","20","12","15")
    }

}