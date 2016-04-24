package io.github.imtilab.dailymuslim;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Touhidul_MTI on 23-Apr-16.
 */
public class MyDialog extends DialogFragment implements View.OnClickListener{

    TextView tv_cs, tv_hs;
    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_popup_dialog, null);
        getDialog().setTitle("দৈনিক মুসলিম");
        getDialog().setCanceledOnTouchOutside(false);

        tv_cs = (TextView) view.findViewById(R.id.score_popup_dialog_tv1);
        tv_hs = (TextView) view.findViewById(R.id.score_popup_dialog_tv2);
        b1 = (Button)view.findViewById(R.id.score_popup_dialog_b1);

        setDisplay();
        return view;

    }
    public void setDisplay(){
        tv_cs.setText("আজকের স্কোরঃ "+ScorePassToDialog.todayScore+"%");
        tv_hs.setText("সর্বোচ্চ স্কোরঃ "+ScorePassToDialog.highScore+"%");
        b1.setOnClickListener(MyDialog.this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        getActivity().finish();//finish QuesAns activity on dialog dismiss
    }
}
