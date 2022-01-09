package com.example.apifun;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apifun.databinding.FragmentFirstBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ImageView imageView,imageView2;
    private ArrayList<ArrayList<Wallpaper>> wallpapers = new ArrayList<ArrayList<Wallpaper>>();
    private WallapaperAdapter wallapaperAdapter;
    private RecyclerView recyclerView;

    RequestQueue requestQueue;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        SearchView search = binding.getRoot().findViewById(R.id.search);
        search.setQueryHint("Search here...");

        recyclerView = binding.getRoot().findViewById(R.id.wall_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        imageView = binding.getRoot().findViewById(R.id.image);
//        imageView2 = binding.getRoot().findViewById(R.id.image2);
        return binding.getRoot();
    }

    private void parseJson() {
        String url = "https://pixabay.com/api/?key=19481934-cd71ca9d79f0877fb408cd246&q=wallpaper&orientation=vertical";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("hits");
                            JSONObject hit = array.getJSONObject(1);
                            JSONObject hit2 = array.getJSONObject(2);
                            String imgURL = hit.getString("webformatURL");
                            String imgURL2 = hit2.getString("webformatURL");
//                            Picasso.get().load(imgURL).into(imageView);
//                            Picasso.get().load(imgURL2).into(imageView2);
                            //actual
                            for (int i=0; i<array.length()-1;i=i+2){
                                JSONObject wallpaperObject1 = array.getJSONObject(i);
                                String wallpaperUrl1 = wallpaperObject1.getString("largeImageURL");
                                JSONObject wallpaperObject2 = array.getJSONObject(i+1);
                                String wallpaperUrl2 = wallpaperObject2.getString("largeImageURL");

                                ArrayList<Wallpaper> wallpaperSet = new ArrayList<Wallpaper>();
                                wallpaperSet.add(new Wallpaper(wallpaperUrl1));
                                wallpaperSet.add(new Wallpaper(wallpaperUrl2));

                                wallpapers.add(wallpaperSet);
                            }
                            Log.i("aunu", "onResponse: "+wallpapers);
                            wallapaperAdapter = new WallapaperAdapter(getActivity(),wallpapers);
                            recyclerView.setAdapter(wallapaperAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });

        requestQueue = Volley.newRequestQueue(getActivity());
        parseJson();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}