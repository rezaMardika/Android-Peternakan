package com.example.root.ternaknesia;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by reza on 04/10/17.
 */

public interface ItemClickListenerKandang {

    @SuppressWarnings("StatementWithEmptyBody")
    boolean onNavigationItemSelected(MenuItem item);

    void onClick(View view, int position);

}
