package com.example.hbkjgoa.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class WebServiceUtil {
    //定义Web Service的命名空间
    static final String SERVICE_NS = "http://tempuri.org/";
    //定义Web Service提供服务的URL
    public  static String SERVICE_URL = "http://116.62.238.239:61645/webservice/mobiles.asmx";
    public static String SERVICE_URL2 ="http://116.62.238.239:61645/";

    public static String getURL(){
        return SERVICE_URL;
    }
    public static String getURL2(){
        return SERVICE_URL2;
    }
    public static  String UpdateGXJWD(String lat, String lng, int fwqid, String dd, String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);

        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);


        soapObject.addProperty("gcid",fwqid);
        soapObject.addProperty("jd",lat);
        soapObject.addProperty("wd",lng);


        envelope.bodyOut = soapObject;
        //System.out.println("jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            //System.out.println("jinrule ...............06");
            Log.d("yin","jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                //System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                System.out.println(result.toString());

                //SoapObject detail = (SoapObject)result.getProperty(0);
                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            Log.e("yin","IOException e");
            return null;
        }
        catch (XmlPullParserException e)
        {
            Log.e("yin","XmlPullParserException e");
            return null;
        }
        Log.e("yin","XmlPullParserException e222");
        return null;
    }


    public static  String InsertKQJL(String lat, String lng,String user,String sj, String method)
    {
        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("user",user);
        soapObject.addProperty("sj",sj);
        soapObject.addProperty("jd",lat);
        soapObject.addProperty("wd",lng);


        envelope.bodyOut = soapObject;
        //System.out.println("jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                System.out.println(result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            return null;
        }
        catch (XmlPullParserException e)
        {
            return null;
        }
        return null;
    }


    public static  String Insertpicture(String picture, String tpsm,int fwqid,String sj, String method)
    {
        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);


        soapObject.addProperty("imagebuffer",picture);
        soapObject.addProperty("imagesm",tpsm);
        soapObject.addProperty("fwqid",fwqid);


        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                System.out.println(result.toString());
                System.out.println(""+result.getProperty(0));
                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            return null;
        }
        catch (XmlPullParserException e)
        {
            return null;
        }
        return null;
    }
    public static  String AddBanLi(int fid, int wid,String Content,String mrspr, String tiaojian,String PiShiStr,int NodeID,String uname,String formdata)
    {
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, "AddBanLi");

        soapObject.addProperty("fid",fid);
        soapObject.addProperty("wid",wid);
        soapObject.addProperty("Content",Content);
        soapObject.addProperty("mrspr",mrspr);
        soapObject.addProperty("tiaojian",tiaojian);
        soapObject.addProperty("PiShiStr",PiShiStr);
        soapObject.addProperty("NodeID",NodeID);
        soapObject.addProperty("uname",uname);
        soapObject.addProperty("formdata",formdata);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + "AddBanLi", envelope);
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                System.out.println(result.toString());
                System.out.println(""+result.getProperty(0));
                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            return null;
        }
        catch (XmlPullParserException e)
        {
            return null;
        }
        return null;
    }


    //获取资料的HTML
    public static  String GetZLNR(int iZLID, String method)
    {
        // 调用的方法
        String methodName =method;
        // 创建HttpTransportSE传输对象
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        // 使用SOAP1.1协议创建Envelop对象，此对象用于向服务器端传入客户端输入的数据
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 实例化SoapObject对象，需要传入所调用Web Service的命名空间和Web Service方法名
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);


        //这边传入需要传入的参数
        soapObject.addProperty("id",iZLID);


        //将soapObject对象传递给服务器
        envelope.bodyOut = soapObject;
        // 设置与.Net提供的Web Service保持较好的兼容性
        System.out.println("jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            // 调用Web Service
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                // 获取服务器响应返回的SOAP消息
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                System.out.println(result.toString());

                //SoapObject detail = (SoapObject)result.getProperty(0);
                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            return null;
        }
        catch (XmlPullParserException e)
        {
            return null;
        }
        return null;
    }

    //做个都可以调用的方法(name1，name2,name3是添加的属性参数，设置为""时不添加到属性值中)
    public static  String everycanforStr(String name1,String name2,String name3,String name4,String value1,String value2,String value3,int value4,String method)
    {
        //Log.d("yin","webservice网址"+SERVICE_URL);
        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        //Log.d("yin","打印下SERVICE_URL："+SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }

        envelope.bodyOut = soapObject;
        //System.out.println("jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                //System.out.println(result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static  String fordzgg(String name1,String name2,String SERVICE_URLs,String value1,String value2,String method)
    {
        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URLs);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                //System.out.println(result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //参数过多的一个方法
    public static  String ZGTZSave(int ZGTZID,int iGCID,int sZGDW,String sJCBW,String sBH,String dJDRQ,String dZGRQ,String sZGNR,String sQFR,String userid,String sOperate)
    {

        String methodName ="ZGTZSave";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("iGCID",iGCID);
        soapObject.addProperty("sZGDW",sZGDW);
        soapObject.addProperty("sJCBW",sJCBW);
        soapObject.addProperty("sBH",sBH);
        soapObject.addProperty("dJDRQ",dJDRQ);
        soapObject.addProperty("dZGRQ",dZGRQ);
        soapObject.addProperty("sZGNR",sZGNR);
        soapObject.addProperty("sQFR",sQFR);
        soapObject.addProperty("userid",userid);

        soapObject.addProperty("id",ZGTZID);
        soapObject.addProperty("sOperate",sOperate);
        soapObject.addProperty("iGRPID",0);
        soapObject.addProperty("iJDJCID",0);
        soapObject.addProperty("cFSSZ","N");

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                //System.out.println(result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static  String AQZGTZSave(int ZGTZID,int iGCID,int sZGDW,String sJCBW,String sBH,String dJDRQ,String dZGRQ,String sZGNR,String sQFR,String userid,String sOperate)
    {

        String methodName ="AQZGTZSave";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("iGCID",iGCID);
        soapObject.addProperty("sZGDW",sZGDW);
        soapObject.addProperty("sJCBW",sJCBW);
        soapObject.addProperty("sBH",sBH);
        soapObject.addProperty("dJDRQ",dJDRQ);
        soapObject.addProperty("dZGRQ",dZGRQ);
        soapObject.addProperty("sZGNR",sZGNR);
        soapObject.addProperty("sQFR",sQFR);
        soapObject.addProperty("userid",userid);

        soapObject.addProperty("id",ZGTZID);
        soapObject.addProperty("sOperate",sOperate);
        soapObject.addProperty("iGRPID",0);
        soapObject.addProperty("iJDJCID",0);
        soapObject.addProperty("cFSSZ","N");

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                //System.out.println(result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //做个都可以调用的方法(name1，name2,name3是添加的属性参数，设置为""时不添加到属性值中)
    public static  String everycanforStr2(String name1,String name2,String name3,String name4,String name5,String name6,String value1,String value2,String value3,String value4,String value5,int value6,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }

        Log.d("yin",methodName+":"+soapObject);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }


    public static  String everycanforStr5(String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String value1,String value2,String value3,String value4,int value5,String value6,String value7,String value8,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7,value7);
        }
        if (!name8.equals("")) {
            soapObject.addProperty(name8,value8);
        }

        Log.d("yin",method+":"+soapObject);

        envelope.bodyOut = soapObject;
        Log.d("yin","传照片jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            Log.d("yin","传照片jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }

    public static  String everycanforStr_banli(String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String name9,String value1,String value2,String value3,String value4,String value5,String value6,String value7,String value8,String value9,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7,value7);
        }
        if (!name9.equals("")) {
            soapObject.addProperty(name9,value9);
        } if (!name9.equals("")) {
        soapObject.addProperty(name9,value9);
    }

        Log.d("yin",method+":"+soapObject);

        envelope.bodyOut = soapObject;
        Log.d("yin","传照片jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            Log.d("yin","传照片jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }
    public static  String everycanforStr4(String name1,String name2,String name3,String name4,String name5,String name6,String name7,String name8,String value1,String value2,String value3,String value4,String value5,String value6,String value7,String value8,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7,value7);
        }
        if (!name8.equals("")) {
            soapObject.addProperty(name8,value8);
        }

        Log.d("yin",method+":"+soapObject);

        envelope.bodyOut = soapObject;
        Log.d("yin","传照片jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            Log.d("yin","传照片jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }
    public static  String everycanforStr3(String name1,String name2,String name3,String name4,String name5,String name6,String name7,String value1,String value2,String value3,String value4,String value5,int value6,String value7,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7,value7);
        }

        Log.d("yin",method+":"+soapObject);

        envelope.bodyOut = soapObject;
        Log.d("yin","传照片jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            Log.d("yin","传照片jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }

    public static  String everycanforStr3_1(String name1,String name2,String name3,String name4,String name5,String name6,String name7,String value1,String value2,String value3,String value4,String value5,String value6,String value7,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7,value7);
        }

        Log.d("yin",method+":"+soapObject);

        envelope.bodyOut = soapObject;
        Log.d("yin","传照片jinrule ...............05");
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            Log.d("yin","传照片jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                Log.d("yin","返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;
                //Log.d("yin",result.toString());

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            //Log.d("yin","返回值非空IOException");
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            //Log.d("yin","返回值非空XmlPullParserException");
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }

    public static  String everycanforStr4(String name1,String name2,String name3,String name4,String name5,String name6,String value1,String value2,String value3,int value4,int value5,int value6,String method)
    {

        String methodName =method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1,value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2,value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3,value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4,value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5,value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6,value6);
        }
        Log.d("yin",methodName+"方法下："+soapObject);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null)
            {
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        //Log.d("yin","返回值非空最后");
        return null;
    }

    public static String HB_Add(int id, String txtJinDu, String txtWanCheng, String HBNR,
                                String userid) {

        String methodName = "TaskHB";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("id", id);
        soapObject.addProperty("txtJinDu", txtJinDu);
        soapObject.addProperty("txtWanCheng", txtWanCheng);
        soapObject.addProperty("HBNR", HBNR);
        soapObject.addProperty("userid", userid);

        Log.d("yin", methodName + "：" + soapObject);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        try {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0) + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        // Log.d("yin","返回值非空最后");
        return null;
    }



    public static String PS_Add(int id, String ZT, String PSNR,String userid) {

        String methodName = "TaskPS";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("id", id);
        soapObject.addProperty("ZT", ZT);
        soapObject.addProperty("PSNR", PSNR);
        soapObject.addProperty("userid", userid);

        Log.d("yin", methodName + "：" + soapObject);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        try {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0) + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        // Log.d("yin","返回值非空最后");
        return null;
    }

    public static String everycanforStrxmgl2(String name1, String name2, String name3, String name4, String name5,
                                             String name6, String name7,String name8, String value1, String value2, String value3, String value4, String value5,
                                             String value6, int value7, String value8,String method) {

        String methodName = method;
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        if (!name1.equals("")) {
            soapObject.addProperty(name1, value1);
        }
        if (!name2.equals("")) {
            soapObject.addProperty(name2, value2);
        }
        if (!name3.equals("")) {
            soapObject.addProperty(name3, value3);
        }
        if (!name4.equals("")) {
            soapObject.addProperty(name4, value4);
        }
        if (!name5.equals("")) {
            soapObject.addProperty(name5, value5);
        }
        if (!name6.equals("")) {
            soapObject.addProperty(name6, value6);
        }
        if (!name7.equals("")) {
            soapObject.addProperty(name7, value7);
        }
        if (!name8.equals("")) {
            soapObject.addProperty(name8, value8);
        }

        Log.d("WebServiceUtil", method + "：" + soapObject);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        try {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0) + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        // Log.d("yin","返回值非空最后");
        return null;
    }


    public static String HD_Add(String id,String title, String nr, String kssj,String jssj,String username) {

        String methodName = "AddAnPai";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
        soapObject.addProperty("id", id);
        soapObject.addProperty("title", title);
        soapObject.addProperty("nr", nr);
        soapObject.addProperty("kssj", kssj);
        soapObject.addProperty("jssj", jssj);
        soapObject.addProperty("username", username);
        Log.d("yin", methodName + "：" + soapObject);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        try {
            ht.call(SERVICE_NS + methodName, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0) + "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
        // Log.d("yin","返回值非空最后");
        return null;
    }

    public static  String GetKSJieGuoList(int pageNo,int iPageSize,String userid,String sjmx)
    {
        String methodName ="GetKSJieGuoList";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("pageNo",pageNo);
        soapObject.addProperty("iPageSize",iPageSize);
        soapObject.addProperty("userid",userid);
        soapObject.addProperty("sjmx",sjmx);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static  String GetShiJuanLB(String isSuiJi,String sfz)
    {
        String methodName ="GetShiJuanLB";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("isSuiJi",isSuiJi);
        soapObject.addProperty("sfz",sfz);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static  String TJShiJuan(int ShiJuanID,String ShiJuanMC,String userid,String messagedata,String DWMC,String SJHM,String TX,String SFZ)
    {
        String methodName ="TJShiJuan";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("ShiJuanID",ShiJuanID);
        soapObject.addProperty("ShiJuanMC",ShiJuanMC);
        soapObject.addProperty("userid",userid);
        soapObject.addProperty("messagedata",messagedata);
        soapObject.addProperty("DWMC",DWMC);
        soapObject.addProperty("SJHM",SJHM);
        soapObject.addProperty("TX",TX);
        soapObject.addProperty("SFZ",SFZ);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static  String GetShiJuanTIKu(int ShiJuanID)
    {
        String methodName ="GetShiJuanTIKu";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("ShiJuanID",ShiJuanID);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public static  String GetKSJieGuoDetail(int KaoShiID,String userid)
    {
        String methodName ="GetKSJieGuoDetail";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("KaoShiID",KaoShiID);
        soapObject.addProperty("userid",userid);

        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static  String GetKSTJList(int pageNo,int iPageSize)
    {
        String methodName ="GetKSTJList";
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);

        soapObject.addProperty("pageNo",pageNo);
        soapObject.addProperty("iPageSize",iPageSize);


        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.encodingStyle="UTF-8";
        try
        {
            System.out.println("jinrule ...............06");
            ht.call(SERVICE_NS + methodName, envelope);
            System.out.println("jinrule ...............07");
            if (envelope.getResponse() != null)
            {
                System.out.println("返回值非空。。。。。。。。。。。。。。。。");
                SoapObject result = (SoapObject) envelope.bodyIn;

                return result.getProperty(0)+"";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
