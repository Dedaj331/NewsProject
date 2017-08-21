package hr.itot.newsproject.Helper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import hr.itot.newsproject.Utils.QueryUtils;

import java.util.List;

/**
 * Created by IgorTot on 20/7/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    public NewsLoader(Context context, String url){
        super(context);
        mUrl=url;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl==null){
            return null;
        }
        List<News> news= QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
