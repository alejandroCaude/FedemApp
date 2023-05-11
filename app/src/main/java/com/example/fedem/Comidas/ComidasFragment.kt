package com.example.fedem.Comidas

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
import com.example.fedem.Comidas.Adapter.ComidaAdapter
import com.example.fedem.Comidas.Adapter.OnClickListenerComida
import com.example.fedem.Comidas.ViewModel.ComidasViewModel
import com.example.fedem.Common.Entities.ComidaEntity
import com.example.fedem.databinding.FragmentComidasBinding


const val spacingInPixels = 30


class ComidasFragment : Fragment(), OnClickListenerComida {

    private lateinit var binding: FragmentComidasBinding
    private lateinit var mComidasViewModel: ComidasViewModel
    private lateinit var mComidaAdapter: ComidaAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mComidasViewModel = ViewModelProvider(requireActivity()).get(ComidasViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidasBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        mComidasViewModel = ViewModelProvider(this).get(ComidasViewModel::class.java)
        mComidasViewModel.getComidas().observe(viewLifecycleOwner) { comidas ->
            mComidaAdapter.setComidas(comidas)
        }
    }

    private fun setupRecyclerView() {
        mComidaAdapter = ComidaAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(requireActivity(), 2)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            addItemDecoration(GridSpacingItemDecoration(spacingInPixels))
            adapter = mComidaAdapter
        }
    }

    override fun onClick(comidaEntity: ComidaEntity) {
        val showPopUp = InfoCompletaComidasPopUpFragment()
        val bundle = Bundle()
        bundle.putLong("id", comidaEntity.id)
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