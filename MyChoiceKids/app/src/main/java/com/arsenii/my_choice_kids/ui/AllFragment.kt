package com.arsenii.my_choice_kids.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.arsenii.my_choice_kids.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONException
import org.json.JSONObject
import kotlin.concurrent.thread

class AllFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var viewOfFragment: View
    private var organizations: MutableList<String> = mutableListOf()
    private var organizationsAddresses: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfFragment = inflater.inflate(R.layout.fragment_all, container, false)
        recyclerView = viewOfFragment.findViewById(R.id.recyclerView_all)

        Toast.makeText(context, "Секунду...", Toast.LENGTH_LONG).show()

        request("https://catalog.api.2gis.com/3.0/items?q=Кружки%20и%20секции%20для%20детей%20в%20Калининграде&page=1&key=ruezky7495")
        request("https://catalog.api.2gis.com/3.0/items?q=Кружки%20и%20секции%20для%20детей%20в%20Калининграде&page=2&key=ruezky7495")
        request("https://catalog.api.2gis.com/3.0/items?q=Кружки%20и%20секции%20для%20детей%20в%20Калининграде&page=3&key=ruezky7495")
        request("https://catalog.api.2gis.com/3.0/items?q=Кружки%20и%20секции%20для%20детей%20в%20Калининграде&page=4&key=ruezky7495")
        request("https://catalog.api.2gis.com/3.0/items?q=Кружки%20и%20секции%20для%20детей%20в%20Калининграде&page=5&key=ruezky7495")

        return viewOfFragment
    }

    private fun request(mURL: String) {
        var name: String
        var address: String
        val stringRequest = StringRequest(
            Request.Method.GET, mURL,
            { response ->
                val obj = JSONObject(response)
                if (obj!!.optJSONObject("meta").optInt("code") == 200) {
                    for (j in 0..9) {
                        name = obj!!.optJSONObject("result").optJSONArray("items").optJSONObject(j).optString("name")
                        address = obj!!.optJSONObject("result").optJSONArray("items").optJSONObject(j).optString("address_name")
                        organizations.add(name)
                        organizationsAddresses.add(address)
                    }

                } else {
                    Toast.makeText(context, "Ошибка, ${obj!!.optJSONObject("meta").optInt("code")}, $obj", Toast.LENGTH_LONG).show()
                }

                recyclerAdapter = RecyclerAdapter(organizations, organizationsAddresses)
                recyclerView.adapter = recyclerAdapter
            },
            { Toast.makeText(context, "Ошибка", Toast.LENGTH_SHORT).show()})

        Volley.newRequestQueue(context).add(stringRequest)
    }
}