package io.github.imtilab.dailymuslim;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Touhidul_MTI on 23-Apr-16.
 */
public class DeveloperDialog extends DialogFragment implements View.OnClickListener{

    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_developer, null);
        getDialog().setTitle("About Developer");
        getDialog().setCanceledOnTouchOutside(false);

        b1 = (Button)view.findViewById(R.id.about_developer_b1);
        b1.setOnClickListener(DeveloperDialog.this);

        return view;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
