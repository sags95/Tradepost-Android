package AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by sharathsind on 2015-08-29.
 */
public class TagsSearch extends AsyncTask<String,String,String> {
    String tag;
    Context c;

    TagsSearch(String a,Context context)
    {
super();
        tag=a;
        c=context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
