package com.e3mall.fast;

import com.zhl.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDfsTest {

    @Test
    public  void testUpdate() throws  Exception{
        //创建一个配置文件，文件名任意，内容就是tracker服务器地址
        //使用全局对象加载配置文件
        ClientGlobal.init("D:\\distributed\\e3parent\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient= new TrackerClient();
        //通过TrackerClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StrorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StrorageServer的引用，参数需要TrackerServer和StrorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //使用StorageClient上传文件
        String[] strings = storageClient.upload_file("D:\\img\\zzpic12256.jpg","jpg",null);
        for (String string: strings) {
            System.out.println(string);
        }
    }
    @Test
    public  void test2() throws  Exception{
        FastDFSClient fastDFSClient =  new FastDFSClient("D:\\distributed\\e3parent\\e3-manager-web\\src\\main\\resources\\conf\\client.conf")
;
    String string = fastDFSClient.uploadFile("D:\\img\\d.jpg","jpg",null);
        System.out.println(string);
    }
}
