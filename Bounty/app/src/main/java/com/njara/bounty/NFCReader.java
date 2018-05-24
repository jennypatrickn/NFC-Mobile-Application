package com.njara.bounty;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;

import com.njara.bounty.models.Card;
import com.njara.bounty.services.BasketService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NFCReader   {
    public NfcAdapter nfcAdapter;
    public PendingIntent pendingIntent;
    public Intent intent;
    public Tag tag;
    Button writeBtn;
    Button readBtn;
    private String name;
    private String firstname;
    private String clientId;
    private String point;
    private String bonAchat;
    private List<String> informations;
    public Activity activity;


    public NFCReader(){

    }

    public NFCReader(Activity activity){
        this.activity=activity;
    }




    /**
     * Read from NFC TAG
     * @param intent
     *
     */
    public void processNfcIntent(Intent intent) {
        if(intent==null){
            intent=this.intent;
        }
        //Infos sur le tag
        informations = new ArrayList<>();
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id = tag.getId();
        String[] technologies = tag.getTechList();
        String textEncoded ="{";
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
                        textEncoded = new String(message, languageCodeLength + 1, message.length - languageCodeLength - 1, textEncoding);
                        informations.add(textEncoded);
                    } catch (Exception e) {
                        Log.e("UnsupportedEncoding", e.toString());
                    }
                    //Utiliser ?
                    //Laisser Android choisir l’appli par défaut si type URI ?
                    if (Arrays.equals(type, NdefRecord.RTD_URI)) {
                        Uri uri = record.toUri();
                        //Toast.makeText(this.activity.getApplicationContext(),
                         //       "URI: "+uri, Toast.LENGTH_LONG).show();
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
        Card card =new Card(informations);
        BasketService.card=card;

    }

    /**
     * Create a NdefRecord from a text
     * @param text
     * @return
     */
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

    /**
     * Create an NdefMessage form a list of NdefRecord
     * @param records
     * @return
     */

    public NdefMessage createNdefMessage(NdefRecord[] records)
    {
        NdefMessage msg = new NdefMessage(records);
        return msg;
    }

    /**
     * Write the Message to the TAG NFC
     * @param message
     * @param tag
     * @return
     */
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


    /**
     * Save Data to NFC
     *
     */
    public void saveDataToTag(Card card){
        Log.e("Card", card.name);

        NdefRecord[] records = { createRecord(card.name),
                createRecord(card.surname), createRecord(card.id),
                createRecord(""+card.points), createRecord("2000")};
        NdefMessage ndefMessage = createNdefMessage(records);
        writeTag(ndefMessage,tag);
    }

}