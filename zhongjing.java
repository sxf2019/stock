package jforex;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.RandomAccessFile;
import com.dukascopy.api.*;
import java.io.File;  
import java.io.FileInputStream;  
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.InetAddress;  
import java.io.IOException;  
import java.net.*;  


public class zhongjing implements IStrategy {
    
    @Configurable("交易品种") public Instrument selectedInstrument = Instrument.XAUUSD;
    @Configurable("K线周期") public Period selectedPeriod = Period.FIVE_MINS;
    @Configurable("K线属性") public OfferSide selectedOfferSide = OfferSide.ASK;
    @Configurable("K线过滤") public Filter selectedFilter = Filter.WEEKENDS;
    
    private IEngine engine;
    private IConsole console;
    private IHistory history;
    private IContext context;
    private IIndicators indicators;
    private IUserInterface userInterface;
    private IBar barPrevious;
    
    private long timeCurrent;
    private long timeCurrents;
    private long Initialization=1499529600000L;
    private long Initialization1=1499529600000L;
    private long Initialization15=1499529600000L;
    private long Initialization30=1499529600000L;
    private long Initialization60=1499529600000L;
    private long Initialization240=1499529600000L;
    private double begin=0,end=0,high=0,low=0;
    private double begin1=0,high1=0,low1=0;
    private double begin15=0,high15=0,low15=0;
    
    public void onStart(IContext context) throws JFException {
        
        //sendPost("http://47.74.3.143/api/Stock/InsertTick","type=&isnew=1&low=1264.59&high=1264.79&begin=1264.67&end=1264.59&date=1502225700000&nextlow=1264.59&nexthigh=1264.59&nextbegin=1264.59&nextend=1264.59");
        this.engine = context.getEngine();
        this.console = context.getConsole();
        this.history = context.getHistory();
        this.context = context;
        this.indicators = context.getIndicators();
        this.userInterface = context.getUserInterface();       
    }

    public void onAccount(IAccount account) throws JFException {
        
    }

    public void onMessage(IMessage message) throws JFException {
    }

    public void onStop() throws JFException {
    }

    public void onTick(Instrument instrument, ITick tick) throws JFException {
        if(!instrument.equals(selectedInstrument)) {
            return;
        }
        try{
            ITick lastTick = history.getLastTick(selectedInstrument);
            timeCurrent=lastTick.getTime();
            long timeCurrentBar = history.getBarStart(selectedPeriod, timeCurrent);//5分钟K线时间
            long timeCurrentBar1 = history.getBarStart(Period.ONE_MIN, timeCurrent);//1分钟K线时间
            long timeCurrentBar15 = history.getBarStart(Period.FIFTEEN_MINS, timeCurrent);//15分钟K线时间
            
            long timeCurrentBar30=history.getBarStart(Period.THIRTY_MINS, timeCurrent);//30分钟K线时间
            long timeCurrentBar60=history.getBarStart(Period.ONE_HOUR, timeCurrent);//60分钟K线时间
            long timeCurrentBar240=history.getBarStart(Period.FOUR_HOURS, timeCurrent);//240分钟K线时间
            
            IBar  barPreviou= history.getBars(selectedInstrument, selectedPeriod, selectedOfferSide, selectedFilter, 2, timeCurrentBar, 0).get(0);//5分钟K线
            IBar  barPreviou1= history.getBars(selectedInstrument, Period.ONE_MIN, selectedOfferSide, selectedFilter, 2, timeCurrentBar1, 0).get(0);//1分钟K线
            IBar  barPreviou15= history.getBars(selectedInstrument, Period.FIFTEEN_MINS, selectedOfferSide, selectedFilter, 2, timeCurrentBar15, 0).get(0);//15分钟K线
            IBar  barPreviou30= history.getBars(selectedInstrument, Period.THIRTY_MINS, selectedOfferSide, selectedFilter, 2, timeCurrentBar30, 0).get(0);//30分钟K线
            IBar  barPreviou60= history.getBars(selectedInstrument, Period.ONE_HOUR, selectedOfferSide, selectedFilter, 2, timeCurrentBar60, 0).get(0);//60分钟K线
            IBar  barPreviou240= history.getBars(selectedInstrument, Period.FOUR_HOURS, selectedOfferSide, selectedFilter, 2, timeCurrentBar240, 0).get(0);//240分钟K线
            
            
            
            end=lastTick.getAsk();//当前价格
            
            if(timeCurrent>=Initialization30){//30分钟K线
                    while(timeCurrent>Initialization30)
                    {
                        Initialization30+=1800000;
                    }
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=30&isnew=1&low="+barPreviou30.getLow()+"&high="+barPreviou30.getHigh()+"&begin="+barPreviou30.getOpen()+"&end="+barPreviou30.getClose()+"&date="+(Initialization30-1800000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+"");
            }
            if(timeCurrent>=Initialization60){//60分钟K线
                    while(timeCurrent>Initialization60)
                    {
                        Initialization60+=3600000;
                    }
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=60&isnew=1&low="+barPreviou60.getLow()+"&high="+barPreviou60.getHigh()+"&begin="+barPreviou60.getOpen()+"&end="+barPreviou60.getClose()+"&date="+(Initialization60-3600000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+"");
            }
            if(timeCurrent>=Initialization240){//240分钟K线
                    while(timeCurrent>Initialization240)
                    {
                        Initialization240+=14400000;
                    }
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=240&isnew=1&low="+barPreviou240.getLow()+"&high="+barPreviou240.getHigh()+"&begin="+barPreviou240.getOpen()+"&end="+barPreviou240.getClose()+"&date="+(Initialization240-14400000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+"");
            }
            
            if(timeCurrent>=Initialization1){//1分钟K线
                     begin1=lastTick.getAsk();
                     high1=lastTick.getAsk();
                     low1=lastTick.getAsk();
                    while(timeCurrent>Initialization1)
                    {
                        Initialization1+=60000;
                    }       
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=1&isnew=1&low="+barPreviou1.getLow()+"&high="+barPreviou1.getHigh()+"&begin="+barPreviou1.getOpen()+"&end="+barPreviou1.getClose()+"&date="+(Initialization1-60000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+"");
            }
            if(timeCurrent>=Initialization15)
            {//15分钟K线
                     begin15=lastTick.getAsk();
                     high15=lastTick.getAsk();
                     low15=lastTick.getAsk();
                     while(timeCurrent>Initialization15)
                    {
                        Initialization15+=900000;
                    }
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=15&isnew=1&low="+barPreviou15.getLow()+"&high="+barPreviou15.getHigh()+"&begin="+barPreviou15.getOpen()+"&end="+barPreviou15.getClose()+"&date="+(Initialization15-900000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+"");
            }
            if(timeCurrent>=Initialization){//5分钟K线
                     begin=lastTick.getAsk();
                     high=lastTick.getAsk();
                     low=lastTick.getAsk();
                    while(timeCurrent>Initialization)
                    {
                        Initialization+=300000;
                    }
                   context.getConsole().getWarn().println(sendPost("http://127.0.0.1/api/Stock/InsertTick","type=5&isnew=1&low="+barPreviou.getLow()+"&high="+barPreviou.getHigh()+"&begin="+barPreviou.getOpen()+"&end="+barPreviou.getClose()+"&date="+(Initialization-300000)+"&nextlow="+end+"&nexthigh="+end+"&nextbegin="+end+"&nextend="+end+""));
            }
            
                boolean time1=false;
                boolean time15=false;
                if(end>high){
                    high=end;
                }
                if(end<low){
                    low=end;
                }
                sendPost("http://127.0.0.1/api/Stock/InsertTick","type=5&isnew=0&low="+barPreviou.getLow()+"&high="+barPreviou.getHigh()+"&begin="+barPreviou.getOpen()+"&end="+barPreviou.getClose()+"&date="+timeCurrent+"&nextlow="+low+"&nexthigh="+high+"&nextbegin="+begin+"&nextend="+end+"");
                if(end>high1){
                    high1=end;
                    time1=true;
                }
                if(end<low1){
                   low1=end;
                   time1=true;
                }
                
                if(end>high15){
                    high15=end;
                    time15=true;
                }
                if(end<low15){
                    low15=end;
                    time15=true;
                }
                if(time1){
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=1&isnew=0&low="+barPreviou1.getLow()+"&high="+barPreviou1.getHigh()+"&begin="+barPreviou1.getOpen()+"&end="+barPreviou1.getClose()+"&date="+timeCurrent+"&nextlow="+low1+"&nexthigh="+high1+"&nextbegin="+begin1+"&nextend="+end+"");
                }
                if(time15){
                    sendPost("http://127.0.0.1/api/Stock/InsertTick","type=15&isnew=0&low="+barPreviou15.getLow()+"&high="+barPreviou15.getHigh()+"&begin="+barPreviou15.getOpen()+"&end="+barPreviou15.getClose()+"&date="+timeCurrent+"&nextlow="+low15+"&nexthigh="+high15+"&nextbegin="+begin15+"&nextend="+end+"");
                }                        
        }catch(JFException e){
            context.getConsole().getWarn().println(e);
        }
    }
    
       /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            result="发送 POST 请求出现异常！"+e;
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    private String getTimeSerial(long time,String Utc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(Utc));
        calendar.setTimeInMillis(time);
        String serial = calendar.get(Calendar.YEAR)+"-"
                + (calendar.get(Calendar.MONTH)+ 1)+"-"
                + calendar.get(Calendar.DAY_OF_MONTH) +" "
                + calendar.get(Calendar.HOUR_OF_DAY)+":"
                + calendar.get(Calendar.MINUTE)+":"
                +calendar.get(Calendar.SECOND)+" "
                 +calendar.get(Calendar.MILLISECOND);
        return serial;
    }
    public static void method2(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            out.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
        }
    }
    public void onBar(Instrument instrument, Period period, IBar askBar, IBar bidBar) throws JFException {
        
    }
}