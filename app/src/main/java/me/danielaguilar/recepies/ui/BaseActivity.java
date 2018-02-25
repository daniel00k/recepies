package me.danielaguilar.recepies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by danielaguilar on 27-01-18.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "Fragment";

    protected void addFragmentTo(Class klass, int resourceId, Bundle bundle){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(resourceId);
        if(fragment == null){
            Log.d(TAG, "adding fragment"+klass.getName());
            fragment = Fragment.instantiate(getBaseContext(), klass.getName());
            fragment.setArguments(bundle);
            ft.add(resourceId, fragment);
        }else {
            Log.d(TAG, "replacing fragment"+klass.getName());
            fragment = Fragment.instantiate(getBaseContext(), klass.getName());
            fragment.setArguments(bundle);
            ft.replace(resourceId, fragment);
        }
        //ft.addToBackStack(fragment.getClass().getName());
        ft.commit();
    }



}
