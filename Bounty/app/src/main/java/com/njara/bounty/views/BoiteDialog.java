package com.njara.bounty.views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.njara.bounty.NFCReader;
import com.njara.bounty.R;
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

                //Todo implement card read
                Intent intent = new Intent(context, NFCReader.class);
                intent.putExtra("Nom","RAKOTO");
                intent.putExtra("Prenom","Charlie");
                intent.putExtra("IdClient","CL001");
                intent.putExtra("Point","20");
                intent.putExtra("BA","200000");
                context.startActivity(intent);

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
