package com.example.apifun;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.artjimlop.altex.AltexImageDownloader;

import com.example.apifun.databinding.FragmentSecondBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

public class SecondFragment extends Fragment {

    private static final int PERMISSION_WRITE = 0;
    private FragmentSecondBinding binding;
    private CardView downloadButton;
    private ImageView imageView;
    private ImageView userImg;
    private TextView likes;
    private TextView user;
    private ProgressBar progressBar;
    private Space space;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        downloadButton = binding.getRoot().findViewById(R.id.download_button);
        imageView = binding.getRoot().findViewById(R.id.wallpaper);
        userImg = binding.getRoot().findViewById(R.id.user_img);
        progressBar = binding.getRoot().findViewById(R.id.progressBar);
        user = binding.getRoot().findViewById(R.id.user);
        likes = binding.getRoot().findViewById(R.id.likes);
        space = binding.getRoot().findViewById(R.id.sapce);

        checkPermission();
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadWallpaper();
            }
        });
        return binding.getRoot();

    }

    private void downloadWallpaper() {
        Toast.makeText(getActivity(),"Downloading...",Toast.LENGTH_SHORT).show();
//        new Downloading().execute(getArguments().getString("url"));
//        AltexImageDownloader.writeToDisk(getActivity(), getArguments().getString("url"),"");
        Uri imageUri = Uri.parse(getArguments().getString("url"));
        String fileName = imageUri.getLastPathSegment();
        String downloadSubpath = "ApiFun_" + fileName;

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"ApiFun");
        File destination = new File(dir, downloadSubpath);
        dir.mkdirs();

        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(imageUri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDescription(getArguments().getString("url"));
        request.allowScanningByMediaScanner();
        request.setDestinationUri(Uri.fromFile(destination));

        downloadManager.enqueue(request);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(getArguments().getString("url")).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                space.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();
            }
        });
        if (!TextUtils.isEmpty(getArguments().getString("userurl")))
        Picasso.get().load(getArguments().getString("userurl")).into(userImg);
        likes.setText(Integer.toString(getArguments().getInt("likes")));
        user.setText(getArguments().getString("user"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public class Downloading extends AsyncTask<String, Integer, String> {
//
//        @Override
//        public void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... url) {
//            File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
//            if (!mydir.exists()) {
//                mydir.mkdirs();
//            }
//
//            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//            Uri downloadUri = Uri.parse(url[0]);
//            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("mmddyyyyhhmmss");
//            String date = dateFormat.format(new Date());
//
//            request.setAllowedNetworkTypes(
//                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
//                    .setAllowedOverRoaming(false)
//                    .setTitle("Downloading")
//                    .setDestinationInExternalPublicDir("/apiforfun", date + ".jpg");
//
//            manager.enqueue(request);
//            return mydir.getAbsolutePath() + File.separator + date + ".jpg";
//        }
//
//        @Override
//        public void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
//        }
//    }
//
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            //do somethings
//        }
//    }

}