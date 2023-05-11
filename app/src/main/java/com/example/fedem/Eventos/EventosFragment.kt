package com.example.fedem.Eventos


import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fedem.Comidas.ComidasFragment
import com.example.fedem.Comidas.spacingInPixels
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.Eventos.Adapter.EventoAdapter
import com.example.fedem.Eventos.Adapter.OnClickListener
import com.example.fedem.Eventos.ViewModel.EventosViewModel
import com.example.fedem.databinding.FragmentEventosBinding


class EventosFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentEventosBinding
    private lateinit var mEventosViewModel: EventosViewModel
    private lateinit var mEventoAdapter: EventoAdapter
    private lateinit var mGridLayout: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            mEventosViewModel = ViewModelProvider(requireActivity()).get(EventosViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventosBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }
    private fun setupViewModel() {
            mEventosViewModel = ViewModelProvider(this).get(EventosViewModel::class.java)
            mEventosViewModel.getEventos().observe(viewLifecycleOwner) { eventos ->
                mEventoAdapter.setEventos(eventos)
            }


    }
    private fun setupRecyclerView() {
            mEventoAdapter = EventoAdapter(mutableListOf(), this)
            mGridLayout = LinearLayoutManager(requireActivity())
            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = mGridLayout

                this.adapter = mEventoAdapter
            }

    }

    override fun onClick(eventosEntity: EventoEntity) {
        val showPopUp = InfoCompletaEventosPopUpFragment()
        val bundle = Bundle()
        bundle.putLong("id", eventosEntity.id)
        showPopUp.arguments = bundle
        showPopUp.show(requireActivity().supportFragmentManager, "InfoCompletaEventosPopUpFragment")
    }

    class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.left = spacing / 2
            outRect.right = spacing / 2
            outRect.top = spacing / 2
            outRect.bottom = spacing / 2
        }
    }

}