package com.example.fedem.Actividades


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Actividades.Adapter.ActividadAdapter
import com.example.fedem.Actividades.Adapter.OnClickListenerActividades
import com.example.fedem.Actividades.ViewModel.ActividadesViewModel
import com.example.fedem.Eventos.EventosFragment
import com.example.fedem.R
import androidx.fragment.app.FragmentActivity
import com.example.fedem.Actividades.Adapter.ActividadAjenoAdapter
import com.example.fedem.Actividades.ViewModel.ActividadesAjenoViewModel
import com.example.fedem.Common.FedemApplication
import com.example.fedem.databinding.FragmentActividadesBinding


class ActividadesFragment : Fragment(), OnClickListenerActividades {

    private lateinit var binding: FragmentActividadesBinding
    private lateinit var mActividadesViewModel: ActividadesViewModel
    private lateinit var mActividadAdapter: ActividadAdapter
    private lateinit var mActividadesAjenoViewModel: ActividadesAjenoViewModel
    private lateinit var mActividadAjenoAdapter: ActividadAjenoAdapter
    private lateinit var mGridLayout: LinearLayoutManager
    private  var filtro=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mActividadesViewModel = ViewModelProvider(requireActivity()).get(ActividadesViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filtrar, menu)
        if(!filtro){
            menu.findItem(R.id.filtro).isVisible=true
            menu.findItem(R.id.quitarFiltro).isVisible=false
        }else{
            menu.findItem(R.id.filtro).isVisible=false
            menu.findItem(R.id.quitarFiltro).isVisible=true
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id_filtrar=item.itemId


        if(id_filtrar==R.id.filtro){
            setupRecyclerView2()
            setupViewModel2()
            filtro=true
            mActividadAjenoAdapter.notifyDataSetChanged()
        }
        if(id_filtrar==R.id.quitarFiltro){
            setupRecyclerView()
            setupViewModel()
            filtro=false
            mActividadAjenoAdapter.notifyDataSetChanged()
        }
        invalidateOptionsMenu(requireActivity())
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActividadesBinding.inflate(inflater, container, false)
        binding.swipeRefreshLayout.setOnRefreshListener {
          //Actualizar el recyclerView

            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }
    private fun setupViewModel() {
        mActividadesViewModel = ViewModelProvider(this).get(ActividadesViewModel::class.java)
        mActividadesViewModel.getActividades().observe(viewLifecycleOwner) { actividades ->
            mActividadAdapter.setActividades(actividades)
            }
    }
    private fun setupRecyclerView() {
        mActividadAdapter = ActividadAdapter(mutableListOf(), this)
            mGridLayout = LinearLayoutManager(requireActivity())
            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = mGridLayout
                this.adapter = mActividadAdapter
            }

    }
    private fun setupViewModel2() {
        mActividadesAjenoViewModel = ViewModelProvider(this).get(ActividadesAjenoViewModel::class.java)
        mActividadesAjenoViewModel.getActividades().observe(viewLifecycleOwner) { actividades ->
            mActividadAjenoAdapter.setActividades(actividades)
        }
    }
    private fun setupRecyclerView2() {
        mActividadAjenoAdapter = ActividadAjenoAdapter(mutableListOf(), this)
        mGridLayout = LinearLayoutManager(requireActivity())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            this.adapter = mActividadAjenoAdapter
        }

    }

    override fun onClick(actividadesEntity: ActividadEntity) {
        val showPopUp = InfoCompletaActividadesPopUpFragment()
        val bundle = Bundle()
        bundle.putLong("id", actividadesEntity.id)
        showPopUp.arguments = bundle
        showPopUp.show(requireActivity().supportFragmentManager, "InfoCompletaEventosPopUpFragment")
    }


}