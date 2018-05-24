package com.njara.bounty.views;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.njara.bounty.MainActivity;
import com.njara.bounty.R;
import com.njara.bounty.appnjara.Util.Utilitaire;
import com.njara.bounty.helpers.Constants;
import com.njara.bounty.models.Product;
import com.njara.bounty.services.BasketService;

/**
 * Created by njara on 22/05/2018.
 */
public class BoiteDialog {

    private Activity context = null;

    public BoiteDialog(Activity context) {
        super();
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public void showUpdateQt(final Product product, int nbrenstock) {

        LinearLayout inputLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dialog_update_qt, null);

        final TextView text = (TextView) inputLayout.findViewById(R.id.text_confirm);
        text.setText("Add quantity to basket");

        TextView valueTextView=(TextView)inputLayout.findViewById(R.id.valueTextView);
        valueTextView.setEnabled(false);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
        alertDialog.setView(inputLayout);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Confirm", null);
        alertDialog.setNegativeButton("Close", null);
        final AlertDialog d = alertDialog.create();
        d.show();
        final Button ok = d.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button no = d.getButton(AlertDialog.BUTTON_NEGATIVE);

        final SpinnerNumber spinnerNumber = new SpinnerNumber(inputLayout,1,20);

        spinnerNumber.setMaxValue(nbrenstock);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                 String resp = spinnerNumber.getValueTextView().getText().toString();
                 BasketService.addToBasket(product,new Integer(resp));
                d.dismiss();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                d.dismiss();
            }
        });

    }

    public  void showDialogCard(final String type){
        LinearLayout inputLayout = (LinearLayout) this.context.getLayoutInflater().inflate(R.layout.show_dialog_card, null);
        final TextView text=(TextView) inputLayout.findViewById(R.id.text_confirm);
        final TextView text2=(TextView) inputLayout.findViewById(R.id.traitement);
        text.setText("Wait for your card");
        final ProgressBar pro=(ProgressBar)inputLayout.findViewById(R.id.pro1);
        pro.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
        alertDialog.setView(inputLayout);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.setNegativeButton("Close", null);
        final AlertDialog d=alertDialog.create();
        d.show();
        final Button ok=d.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button non=d.getButton(AlertDialog.BUTTON_NEGATIVE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ok.setEnabled(false);
                non.setEnabled(false);
                pro.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);

                try {
                    MainActivity main=(MainActivity)context;
                    main.nfcReader.processNfcIntent(null);
                    if(type== Constants.GIFT){

                       double gift= BasketService.card.points*BasketService.fidelity.Gift;
                        BasketService.card.points=0;
                        main.nfcReader.saveDataToTag(BasketService.card);
                        Thread.sleep(3000);
                        non.setEnabled(true);
                        text2.setText("You have "+gift +" Ar");
                        BasketService.discount=gift;
                       TextView  discount=(TextView)context.findViewById(R.id.discount);
                       discount.setText(Utilitaire.format(gift));

                    }else{
                        BasketService.card.points=BasketService.card.points+BasketService.getPoints();
                        main.nfcReader.saveDataToTag(BasketService.card);

                        Thread.sleep(3000);
                        non.setEnabled(true);
                        text2.setText("You have got "+BasketService.card.points +"points");
                    }

                    AppCompatButton win=(AppCompatButton)context.findViewById(R.id.btn_winpoints);
                    win.setVisibility(View.GONE);
                    AppCompatButton reward=(AppCompatButton)context.findViewById(R.id.btn_reward);
                    reward.setVisibility(View.GONE);

                    AppCompatButton order=(AppCompatButton)context.findViewById(R.id.btn_order);
                    order.setVisibility(View.VISIBLE);





                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                d.dismiss();

            }
        });
    }


}
