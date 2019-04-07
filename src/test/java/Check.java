import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Check extends Thread{
    public int num;
    Check(int i){
        num = i;
    }
    @Override
    public void run(){
        try{
            File file = new File("outis/part" + num + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                String[] temp = line.split(":");
                String ip = temp[0];
                int port = Integer.valueOf(temp[1]);
                try{
                    HttpGet httpGet = new HttpGet("http://google.com");
                    HttpHost httpHost = new HttpHost(ip,port);
                    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
                    httpClientBuilder.setProxy(httpHost);
                    HttpClient client = httpClientBuilder.build();
                    HttpResponse response = client.execute(httpGet);
                } catch (Exception e){
                    //System.out.println("검열대상");
                    continue;
                }
                WriteToFile.write(line,"newProxy");
                System.out.println(ip + " is Passed!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
