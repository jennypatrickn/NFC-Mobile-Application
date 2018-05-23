package com.njara.bounty;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NFCReader extends Activity implements NfcAdapter.CreateNdefMessageCallback {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    Tag tag;
    Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test","Tafitritra create");
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(nfcAdapter!=null) {
            if (!nfcAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(),
                        "Please enable your mobile NFC.", Toast.LENGTH_LONG).show();
            }
        }
        //processNfcIntent(getIntent());
        setContentView(R.layout.activity_nfcreader);
        writeBtnTag();
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
            if(nfcAdapter!=null)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray,
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
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }

    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Log.e("Test",action);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //Méthode qui va traiter le tag NFC
            processNfcIntent(intent);
        }
    }

    public void writeBtnTag(){
        writeBtn = (Button) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Save Data To NFC
                 */
                saveDataToTag("Anarana", "Fanampiny", "Laharana", "Isa", "Vola");
            }
        });
    }
    public void processNfcIntent(Intent intent) {
        //Infos sur le tag
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id = tag.getId();
        String[] technologies = tag.getTechList();
        String textEncoded ="}";
        int content = tag.describeContents();
        Ndef ndef = Ndef.get(tag);
        boolean isWritable = ndef.isWritable();
        boolean canMakeReadOnly = ndef.canMakeReadOnly();
        //Récupération des messages
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage[] msgs;
        //Boucle sur les enregistrements
        Log.d("Test","Before check");
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                for(int j = 0; j <msgs[i].getRecords().length;j++ ){
                    NdefRecord record = msgs[i].getRecords()[j];
                    byte[] idRec = record.getId();
                    short tnf = record.getTnf();
                    byte[] type = record.getType();
                    byte[] message = record.getPayload();
                    String textEncoding = ((message[0] & 128) == 0) ? "UTF-8" : "UTF-16";
                    int languageCodeLength = message[0] & 0063;
                    try {
                        // Get the Text
                        textEncoded += new String(message, languageCodeLength + 1, message.length - languageCodeLength - 1, textEncoding);
                        Log.e("Result", textEncoded);
                    } catch (Exception e) {
                        Log.e("UnsupportedEncoding", e.toString());
                    }
                    //Utiliser ?
                    //Laisser Android choisir l’appli par défaut si type URI ?
                    if (Arrays.equals(type, NdefRecord.RTD_URI)) {
                        Uri uri = record.toUri();
                        Toast.makeText(getApplicationContext(),
                                "URI: "+uri, Toast.LENGTH_LONG).show();
                    }
                }


            }
        } else {
            //Tag de type inconnu, tester une récupération du contenu hexadécimal ?
            byte[] empty = new byte[]{};
            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
                    empty, empty, empty);
            NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
            msgs = new NdefMessage[]{msg};
        }
        //Traiter les informations...
        textEncoded+="}";

        Toast.makeText(getApplicationContext(),
                "Message: "+textEncoded, Toast.LENGTH_LONG).show();
    }

    private NdefRecord createRecord(String text){
        try{
            String lang       = "en";
            byte[] textBytes  = text.getBytes();
            byte[] langBytes  = lang.getBytes("US-ASCII");
            int    langLength = langBytes.length;
            int    textLength = textBytes.length;
            byte[] payload    = new byte[1 + langLength + textLength];
            // set status byte (see NDEF spec for actual bits)
            payload[0] = (byte) langLength;
            // copy langbytes and textbytes into payload
            System.arraycopy(langBytes, 0, payload, 1,              langLength);
            System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
            NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);
            return recordNFC;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    public NdefMessage createNdefMessage(NdefRecord[] records)
    {
        NdefMessage msg = new NdefMessage(records);
        return msg;
    }

    public static boolean writeTag(final NdefMessage message, final Tag tag)
    {
        try
        {
            int size = message.toByteArray().length;
            Ndef ndef = Ndef.get(tag);
            if (ndef == null)
            {
                //Tags qui nécessite un formatage ?
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null)
                {
                    try
                    {
                        format.connect();
                        //Formatage et écriture du message:
                        format.format(message);
                        //exemple de verrouillage du tag en écriture :
                        //formatable.formatReadOnly(message);
                        format.close() ;
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                } else {
                    //format == null
                    return false;
                }
            } else {
                //ndef!=null
                ndef.connect();
                if (!ndef.isWritable())
                {
                    return false;
                }
                if (ndef.getMaxSize() < size)
                {
                    return false;
                }
                ndef.writeNdefMessage(message);
                ndef.close();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void saveDataToTag(String name, String firstName, String idClient, String point, String bondAchat){
        NdefRecord[] records = { createRecord("'Nom':"+name),
                createRecord(",'Prenom':"+firstName), createRecord(",'IdClient':"+idClient),
                createRecord(",'Point':"+point), createRecord(",'BonAchat':"+bondAchat)};
        NdefMessage ndefMessage = createNdefMessage(records);
        writeTag(ndefMessage,tag);
    }

}