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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;

public class NFCReader extends Activity implements NfcAdapter.CreateNdefMessageCallback {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(),
                    "Please enable your mobile NFC.", Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_nfcreader);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techListsArray = new String[][]{
                    {android.nfc.tech.Ndef.class.getName()},
                    {android.nfc.tech.NdefFormatable.class.getName()}};
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
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            //Méthode qui va traiter le tag NFC
            processNfcIntent(intent);
        }
    }

    public void processNfcIntent(Intent intent) {
        //Infos sur le tag
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id = tag.getId();
        String[] technologies = tag.getTechList();
        int content = tag.describeContents();
        Ndef ndef = Ndef.get(tag);
        boolean isWritable = ndef.isWritable();
        boolean canMakeReadOnly = ndef.canMakeReadOnly();
        //Récupération des messages
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage[] msgs;
        //Boucle sur les enregistrements
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                NdefRecord record = msgs[i].getRecords()[i];
                byte[] idRec = record.getId();
                short tnf = record.getTnf();
                byte[] type = record.getType();
                String message = record.getPayload().toString();
                //Utiliser ?
                //Laisser Android choisir l’appli par défaut si type URI ?
                if (Arrays.equals(type, NdefRecord.RTD_URI)) {
                    Uri uri = record.toUri();
                    Intent intentview = new Intent(Intent.ACTION_VIEW);
                    intentview.setData(uri);
                    startActivity(intentview);
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
    }

    public NdefMessage createNdefMessage(String text, String mimeType)
    {
        //Message de type MIME
        NdefMessage msg = new NdefMessage(NdefRecord.createMime(
                mimeType, text.getBytes())
                //Autre méthode qui fait la même chose...
                /**NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                 mimeType.getBytes(), new byte[0],
                 text.getBytes())**/
                //Message de type application :
                //par exemple text = "com.mbds.android.tagnfc"
                //permet de lancer l’application qui recevra le tag
                /**,NdefRecord.createApplicationRecord(text)**/
                //Message de type URI :
                //par exemple text = "http://www.mbds-fr.org"
                /**,NdefMessage(NdefRecord.createUri(
                 NdefRecord.createUri(Uri.encode(text))**/
        );
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

}