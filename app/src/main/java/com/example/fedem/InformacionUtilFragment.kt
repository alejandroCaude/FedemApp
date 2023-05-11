package com.example.fedem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fedem.databinding.FragmentInformacionUtilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class InformacionUtilFragment : Fragment() {

    private lateinit var binding: FragmentInformacionUtilBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInformacionUtilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val SitiosInteres = "https://www.google.com/maps/search/sitios+de+interes/@39.4724988,-0.4063232,14z/data=!3m1!4b1"
        val Hoteles ="https://www.booking.com/searchresults.es.html?aid=397594&label=gog235jc-1DCAEoggI46AdIClgDaEaIAQGYAQq4ARfIAQzYAQPoAQH4AQKIAgGoAgO4AoXwsZ0GwAIB0gIkNjVlYzM4NDYtZTcxNy00Y2I1LWJjNmUtMGUyZDBkZGE2NTI52AIE4AIB&sid=34104d7bde7706790ca0681022712d6d&sb=1&sb_lp=1&src=index&src_elem=sb&error_url=https%3A%2F%2Fwww.booking.com%2Findex.es.html%3Faid%3D397594%26label%3Dgog235jc-1DCAEoggI46AdIClgDaEaIAQGYAQq4ARfIAQzYAQPoAQH4AQKIAgGoAgO4AoXwsZ0GwAIB0gIkNjVlYzM4NDYtZTcxNy00Y2I1LWJjNmUtMGUyZDBkZGE2NTI52AIE4AIB%26sid%3D34104d7bde7706790ca0681022712d6d%26sb_price_type%3Dtotal%26%26&ss=Valencia%2C+Comunidad+Valenciana%2C+Espa%C3%B1a&is_ski_area=&checkin_year=2023&checkin_month=1&checkin_monthday=19&checkout_year=2023&checkout_month=1&checkout_monthday=20&efdco=1&group_adults=2&group_children=0&no_rooms=1&b_h4u_keep_filters=&from_sf=1&ss_raw=valencia&ac_position=0&ac_langcode=es&ac_click_type=b&ac_meta=GhBmZjQ4Nzg4MjEzZDUwMDJhIAAoATICZXM6CHZhbGVuY2lhQABKAFAA&dest_id=-406131&dest_type=city&iata=VLC&place_id_lat=39.4698&place_id_lon=-0.376542&search_pageview_id=ff48788213d5002a&search_selected=true&search_pageview_id=ff48788213d5002a&ac_suggestion_list_length=5&ac_suggestion_theme_list_length=0"
        val ComoLlegar="https://www.google.com/maps/dir//46200+Paiporta,+Valencia/@39.4274415,-0.4535145,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0xd604e5c5d63bf9f:0x1082e832842f1655!2m2!1d-0.4184093!2d39.4274468"
        val openURL = Intent(Intent.ACTION_VIEW)

        binding.btnInteres.setOnClickListener{
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(R.string.dialog_fuera_title)
                    .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                        openURL.data = Uri.parse(SitiosInteres)
                        startActivity(openURL)
                    }
                    .setNegativeButton(R.string.dialog_delete_cancel,null)
                    .show()
            }

        }
        binding.btnHoteles.setOnClickListener{
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(R.string.dialog_fuera_title)
                    .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                        openURL.data = Uri.parse(Hoteles)
                        startActivity(openURL)
                    }
                    .setNegativeButton(R.string.dialog_delete_cancel,null)
                    .show()
            }

        }
        binding.btnCllegar.setOnClickListener{
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(R.string.dialog_fuera_title)
                    .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                        openURL.data = Uri.parse(ComoLlegar)
                        startActivity(openURL)
                    }
                    .setNegativeButton(R.string.dialog_delete_cancel,null)
                    .show()
            }

        }



    }


}