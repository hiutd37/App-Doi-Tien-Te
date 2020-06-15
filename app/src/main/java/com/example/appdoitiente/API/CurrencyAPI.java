package com.example.appdoitiente.API;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.appdoitiente.Currency;
import com.example.appdoitiente.MainActivity;
import com.example.appdoitiente.SingleTon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CurrencyAPI extends AsyncTask<String,String,String> {
    protected Activity context;
    protected SingleTon arrayList = null;

    public CurrencyAPI(){
        arrayList=SingleTon.getInstance();
    }
    public CurrencyAPI(Activity context) {
        this.context = context;
        arrayList=SingleTon.getInstance();
    }

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try{
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer=new StringBuffer();

            String line="";

            while ((line=reader.readLine())!=null){
                buffer.append(line);
            }

            String data = buffer.toString();
            //Log.i("sssdfsdfsdf",data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if(data==null) data="";
        try {
            Document document= (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(data)));

            Element elementRSS = (Element) document.getElementsByTagName("rss").item(0);
//            NodeList nodeListRSS=document.getElementsByTagName("rss");//Lấy element có tag name là rss
//            Element elementRSS = (Element) nodeListRSS.item(0);//Lấy phần từ đầu tiên
            NodeList nodeListItem = elementRSS.getElementsByTagName("item");//Lấy danh sách các item

            for (int i=0;i<nodeListItem.getLength();i++){
                String s = nodeListItem.item(i).getChildNodes().item(0).getTextContent();
                String[] c = s.split("/");

                Currency currency=setCurrency(c[1]);
                arrayList.currencies.add(currency);
            }

            callActivity();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void callActivity() {
            Intent intent = new Intent(this.context, MainActivity.class);
            this.context.startActivity(intent);
            this.context.finish();
    }

    private Currency setCurrency(String textContent) {
        if(textContent==null) return null;
        String code="";
        String country="";
//        int length = textContent.length()-2;
//        int limit = length - 3;
//
//        while (length>limit){
//            code+=textContent.charAt(length); //Get code currency
//            length--;
//        }
//
////        for (int i=0;i<length;i++){
////            country+=textContent.charAt(i);//Get name country
////        }
        String codeCountry[] = textContent.split("\\(");
        code=""+codeCountry[1].charAt(0)+codeCountry[1].charAt(1)+codeCountry[1].charAt(2);
        country = codeCountry[0];

        return new Currency(country,code);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
