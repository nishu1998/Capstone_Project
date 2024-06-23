package com.mahakalstudio.cosmos

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahakalstudio.cosmos.databinding.FragmentEBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentE : Fragment() {

    private lateinit var binding: FragmentEBinding
    private lateinit var itemAdapter: ItemAdapter
    private val mangaList = mutableListOf<Manga>()
    private var page = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and adapter
        itemAdapter = ItemAdapter(mangaList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = itemAdapter

        // Set scroll listener
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    // Reached the end of the list
                    fetchManga("Sci-Fi", false, "all")
                }
            }
        })

        // Fetch initial manga data from API
        fetchManga("Sci-Fi", false, "all")
    }

    private fun fetchManga(genres: String, nsfw: Boolean, type: String) {
        isLoading = true
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait while data is fetching")
        progressDialog.show()

        // Function to fetch next page
        MangaApiObj.apiInterface.getData(page, genres, nsfw, type).enqueue(object : Callback<MangaResponseDataClass> {
            override fun onResponse(call: Call<MangaResponseDataClass>, response: Response<MangaResponseDataClass>) {
                progressDialog.dismiss()
                isLoading = false
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val fetchedManga = body.data
                        mangaList.addAll(fetchedManga)
                        itemAdapter.notifyDataSetChanged()
                        // Check if there are more results to fetch
                        if (fetchedManga.isNotEmpty()) {
                            // Increment page number for next request
                            page++
                        }
                    } else {
                        Log.d("FragmentE", "Response body is null")
                        Toast.makeText(requireContext(), "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("FragmentE", "API call failed: ${response.code()} - ${response.message()}")
                    Toast.makeText(requireContext(), "Failed to fetch manga", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MangaResponseDataClass>, t: Throwable) {
                progressDialog.dismiss()
                isLoading = false
                Log.e("FragmentE", "API call failed", t)
                Toast.makeText(requireContext(), "Failed to fetch manga: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
