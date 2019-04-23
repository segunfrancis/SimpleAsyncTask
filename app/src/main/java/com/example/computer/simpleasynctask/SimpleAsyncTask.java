package com.example.computer.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {
    /*
     * first AsyncTaskParameter is parameter for doInBackground()
     * second AsyncTaskParameter is parameter for onProgressUpdated()
     * third AsyncTaskParameter is parameter for onPostExecute()
     */

    private WeakReference<TextView> mTextView;

    /**
     * If you pass a TextView into the AsyncTask constructor and then store it in a member variable,
     * that reference to the TextView means the Activity cannot ever be garbage collected and thus
     * leaks memory, even if the Activity is destroyed and recreated as in a device configuration change.
     * This is called creating a leaky context, and Android Studio will warn you if you try it.
     * <p>
     * The weak reference prevents the memory leak by allowing the object held by that
     * reference to be garbage collected if necessary.
     */

    public SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int n = random.nextInt(11);
        int s = n * 200;

        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    // onPostExecute method is run on the main thread
    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
