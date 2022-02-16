package com.example.apifun;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;
import com.example.apifun.databinding.FragmentFirstBinding;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<ArrayList<Wallpaper>> wallpapers = new ArrayList<ArrayList<Wallpaper>>();
    private WallapaperAdapter wallapaperAdapter;
    private RecyclerView recyclerView;
    private Boolean isScrolling = false;
    private int currentItems,scrolledOutItems,totalItems;
    private int pagecount = 1;
    private String url="";

    RequestQueue requestQueue;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        SearchView search = binding.getRoot().findViewById(R.id.search);
        search.setQueryHint("Search here...");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.replaceAll("\\s+", "+").toLowerCase();
                String url = "https://pixabay.com/api/?key=19481934-cd71ca9d79f0877fb408cd246&q="+query;
                wallpapers.clear();
                parseJson(url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView = binding.getRoot().findViewById(R.id.wall_recyclerView);
        return binding.getRoot();
    }

    private void  parseJson(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("hits");
                            //actual
                            for (int i=0; i<array.length()-1;i=i+2){
                                JSONObject wallpaperObject1 = array.getJSONObject(i);
                                String wallpaperUrl1 = wallpaperObject1.getString("largeImageURL");
                                String smallUrl1 = wallpaperObject1.getString("webformatURL");
                                String userImgUrl1 = wallpaperObject1.getString("userImageURL");
                                String user1 = wallpaperObject1.getString("user");
                                int likes1 = wallpaperObject1.getInt("likes");
                                JSONObject wallpaperObject2 = array.getJSONObject(i+1);
                                String wallpaperUrl2 = wallpaperObject2.getString("largeImageURL");
                                String smallUrl2 = wallpaperObject2.getString("webformatURL");
                                String userImgUrl2 = wallpaperObject2.getString("userImageURL");
                                String user2 = wallpaperObject2.getString("user");
                                int likes2 = wallpaperObject2.getInt("likes");
                                ArrayList<Wallpaper> wallpaperSet = new ArrayList<Wallpaper>();
                                wallpaperSet.add(new Wallpaper(wallpaperUrl1,smallUrl1,userImgUrl1,user1,likes1));
                                wallpaperSet.add(new Wallpaper(wallpaperUrl2,smallUrl2,userImgUrl2,user2,likes2));

                                wallpapers.add(wallpaperSet);
                            }
                            wallapaperAdapter = new WallapaperAdapter(getActivity(),wallpapers);
                            recyclerView.setAdapter(wallapaperAdapter);
                            wallapaperAdapter.setOnItemClickListener(new WallapaperAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick1(int position) {
                                    Wallpaper wallpaper = wallpapers.get(position).get(0);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("likes",wallpaper.getLikes());
                                    bundle.putString("url",wallpaper.getFullWallpaper_url());
                                    bundle.putString("userurl",wallpaper.getUserImg_url());
                                    bundle.putString("user",wallpaper.getUser());
                                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                                }

                                @Override
                                public void onItemClick2(int position) {
                                    Wallpaper wallpaper = wallpapers.get(position).get(1);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("likes",wallpaper.getLikes());
                                    bundle.putString("url",wallpaper.getFullWallpaper_url());
                                    bundle.putString("userurl",wallpaper.getUserImg_url());
                                    bundle.putString("user",wallpaper.getUser());
                                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                                }
                            });
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

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        requestQueue = Volley.newRequestQueue(getActivity());
        url = "https://pixabay.com/api/?key=19481934-cd71ca9d79f0877fb408cd246&q=wallpaper&orientation=vertical";
        parseJson(url);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();
                totalItems = manager.getItemCount();
                if(isScrolling && (currentItems+scrolledOutItems == totalItems)){
                    pagecount++;
                    url = "https://pixabay.com/api/?key=19481934-cd71ca9d79f0877fb408cd246&q=wallpaper&orientation=vertical&page="+pagecount;
                    parseJson(url);
                    wallapaperAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}