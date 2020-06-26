package com.example.appdoitiente.API;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.AdapterView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ConvertCurrencyAPI extends CurrencyAPI {
    CurrencyListener listener;
    Activity context;
    ProgressDialog dialog;
    public ConvertCurrencyAPI(CurrencyListener listener,Activity context) {
        super();
        this.context=context;
        this.listener=listener;
    }

    @Override
    protected void onPreExecute() {
        callDiaLogLoading();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String data){

        if(data==null) data="";
        try {
            Document document = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(data)));
            Element elementRSS = (Element) document.getElementsByTagName("rss").item(0);
            Element elementItem = (Element) elementRSS.getElementsByTagName("item").item(0);
            Element elementDes = (Element) elementItem.getElementsByTagName("description").item(0);

            String stringConvert[]=elementDes.getTextContent().split("<br/>");
            String currencyConvert[] = stringConvert[0].split("=");
            listener.setTyGia(tyGiaProcess(currencyConvert[1]));

            dialog.dismiss();

            Log.i("ádasdasd",stringConvert[0]);
            Log.i("ádasdasd",currencyConvert[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    private void callDiaLogLoading() {
        dialog=new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private Double tyGiaProcess(String s) {
        String tyGia="";
        for(int i=0;i<s.length()-3;i++){
            tyGia+=s.charAt(i);
        }
        return Double.parseDouble(tyGia);
    }

}
