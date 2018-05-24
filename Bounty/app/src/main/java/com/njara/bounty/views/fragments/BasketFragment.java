package com.njara.bounty.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njara.bounty.R;
import com.njara.bounty.appnjara.Util.Utilitaire;
import com.njara.bounty.listeners.ButtonActionListener;
import com.njara.bounty.services.BasketService;
import com.njara.bounty.views.adapters.BasketAdapter;

/**
 * Created by nfidiman on 23/05/2018.
 */

public class BasketFragment  extends Fragment {

    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        this.recyclerView=(RecyclerView) view.findViewById(R.id.basketListView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
       recyclerView.setLayoutManager(mLayoutManager);


        BasketAdapter basketAdapter = new BasketAdapter(this.getActivity(), BasketService.baskets);
        recyclerView.setAdapter(basketAdapter);

        ButtonActionListener textViewListener=new ButtonActionListener(this.getActivity(),this.getActivity().getSupportFragmentManager());

        AppCompatButton winpoints=(AppCompatButton)view.findViewById(R.id.btn_winpoints);

        winpoints.setOnClickListener(textViewListener);

        TextView winPoint =(TextView) view.findViewById(R.id.win);
        winPoint.setText(""+BasketService.getPoints());

        TextView encaisse =(TextView) view.findViewById(R.id.montant_total_top);
        encaisse.setText(Utilitaire.format(BasketService.getAmountCash()));

        return view;
    }

}
