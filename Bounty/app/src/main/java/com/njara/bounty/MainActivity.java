package com.njara.bounty;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.njara.bounty.helpers.UIHelper;
import com.njara.bounty.listeners.ButtonActionListener;
import com.njara.bounty.listeners.NavigationListener;
import com.njara.bounty.listeners.SearchListener;
import com.njara.bounty.models.Basket;
import com.njara.bounty.models.Card;
import com.njara.bounty.models.Fidelity;
import com.njara.bounty.services.BasketService;
import com.njara.bounty.services.IAccountService;
import com.njara.bounty.views.fragments.ProductFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  NfcAdapter.CreateNdefMessageCallback {

    private IAccountService accountService;
    private ButtonActionListener textViewListener;
    private NavigationListener navigationListener;
    private SearchListener searchListener;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public NFCReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcReader=new NFCReader(this);

        BasketService.card=new Card();
        BasketService.fidelity=new Fidelity();

        //NFC
        nfcReader.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcReader.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(nfcReader.nfcAdapter!=null) {
            if (!nfcReader.nfcAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(),
                        "Please enable your mobile NFC.", Toast.LENGTH_LONG).show();
            }
        }



       // accountService=new AccountService();
        textViewListener=new ButtonActionListener(this,getSupportFragmentManager());
        navigationListener=new NavigationListener(this,getSupportFragmentManager());
        searchListener=new SearchListener(this,getSupportFragmentManager());
        BasketService.baskets=new ArrayList<Basket>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationListener);

        View navView= navigationView.getHeaderView(0);
        Button headerLogin=(Button)navView.findViewById(R.id.btn_header_login);
        headerLogin.setOnClickListener(textViewListener);

        FloatingActionButton basketFlot = (FloatingActionButton) findViewById(R.id.basketflot);
        basketFlot.setOnClickListener(textViewListener);

        UIHelper.UpdateSearchViewUI(this,searchListener );


        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        String title = this.getResources().getString(R.string.title_product);
        fragmentTransaction.replace(R.id.content_main_frame, new ProductFragment(),title).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Test","Tafiditra");
        try {
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techListsArray = new String[][]{
                    {android.nfc.tech.Ndef.class.getName()},
                    {android.nfc.tech.NdefFormatable.class.getName()}};
            if(nfcReader.nfcAdapter!=null)
                nfcReader.nfcAdapter.enableForegroundDispatch(this, nfcReader.pendingIntent, intentFiltersArray,
                        techListsArray);
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),
                    "NFC not supported.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        nfcReader.nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        nfcReader.intent=intent;
        String action = intent.getAction();
        Log.e("Test",action);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //Méthode qui va traiter le tag NFC
           // nfcReader.processNfcIntent(intent);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title="";
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case android.support.v7.appcompat.R.id.search_button:
                title = this.getResources().getString(R.string.title_search);
              //  int fragment=  getSupportFragmentManager().beginTransaction().replace(R.id.content_main_frame, new SearchFragment(),title).commit();
                Log.e("Test", "SearchFragment");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
