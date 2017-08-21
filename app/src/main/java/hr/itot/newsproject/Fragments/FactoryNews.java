package hr.itot.newsproject.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import hr.itot.newsproject.DetailedActivity;
import hr.itot.newsproject.Helper.News;
import hr.itot.newsproject.Helper.NewsAdapter;
import hr.itot.newsproject.Helper.NewsLoader;
import hr.itot.newsproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FactoryNews extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {
    NewsAdapter adapter;
    ListView listNews;
    ProgressBar progressSpinner;
    LinearLayout no_internet_layput;


    private String REQUEST_URL;
    private final int NEWS_LOADER_ID = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listNews = (ListView) view.findViewById(R.id.newsList);
        progressSpinner = (ProgressBar) view.findViewById(R.id.progressSpinner);
        no_internet_layput = (LinearLayout) view.findViewById(R.id.no_internet_layout);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        REQUEST_URL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398";


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsAdapter(getContext(), new ArrayList<News>());
        listNews.setAdapter(adapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive = networkInfo != null && networkInfo.isConnectedOrConnecting();


        if (isNetworkActive) {

           /* NewsAsync newsAsync=new NewsAsync();
            newsAsync.execute(REQUEST_URL);*/
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressSpinner.setVisibility(GONE);
            no_internet_layput.setVisibility(VISIBLE);
            showDialog();

        }


        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);
                String title = currentNews.getTitle();
                String decribe = currentNews.getDescription();
                String imageUrl = currentNews.getUrlToImage();
                String uri = currentNews.getUrl();
                Intent intent = new Intent(getContext(), DetailedActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", decribe);
                intent.putExtra("urlToImage", imageUrl);
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                refresh();
                Log.d("test5","5minPassed");
                handler.postDelayed(this, 300000);//300000ms == 5min
            }
        }, 300000);

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(), REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        progressSpinner.setVisibility(GONE);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

   /* private class NewsAsync extends AsyncTask<String,Void,List<News>>{

        @Override
        protected List<News> doInBackground(String... params) {
            if (params.length < 1 || params[0]==null){
                return null;
            }
            List<News> newses= QueryUtils.fetchNewsData(params[0]);
            return newses;
        }
        @Override
        protected void onPostExecute(List<News> newses) {
            progressSpinner.setVisibility(GONE);

            adapter.clear();
            if (newses !=null && !newses.isEmpty()){
                adapter.addAll(newses);
            }
        }


    }*/

    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIconAttribute(android.R.attr.alertDialogIcon);
        builder.setTitle("Greška");
        builder.setMessage("Ups, došlo je do pogreške");
        builder.setPositiveButton("U redu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();


    }


    private void refresh() {
        adapter = new NewsAdapter(getContext(), new ArrayList<News>());
        listNews.setAdapter(adapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (isNetworkActive) {

            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

        } else {
            progressSpinner.setVisibility(GONE);
            no_internet_layput.setVisibility(VISIBLE);
            showDialog();

        }


        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = adapter.getItem(position);
                String title = currentNews.getTitle();
                String decribe = currentNews.getDescription();
                String imageUrl = currentNews.getUrlToImage();
                String uri = currentNews.getUrl();
                Intent intent = new Intent(getContext(), DetailedActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", decribe);
                intent.putExtra("urlToImage", imageUrl);
                intent.putExtra("uri", uri);
                startActivity(intent);
            }
        });

        Log.d("5minPassed", "5minPassed");
    }

}
