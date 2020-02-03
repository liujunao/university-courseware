package sample;

/**
 * wc.exe 文件计数程序
 * 引用的其他类：Filehandler
 * Created by cmy on 2018/3/12.
 */

import sun.security.util.Length;
import java.util.ArrayList;
import java.util.List;
import  java.io.*;
import java.awt.*;
import java.util.regex.Pattern;


public class wc {
    //用于记录递归获取的文件路
    File or;
    File[] files;
    List<String> filePath = new ArrayList<String>();

    //递归获取文件夹内符合要求的文件的路径，dir是文件夹路径，fileClass是文件类别
    public void iteratorPath(String dir, String fileClass) {
        or = new File(dir);
        files = or.listFiles();
        if (files != null) {
            //遍历文件夹内所有文件
            for (File file : files) {
                if (file.isFile()) {
                    String aPath = file.getPath();
                    //用i记录文件名中“.”的位置，以获取后缀名
                    int i = aPath.length()-1;
                    for(; i>=0; i--){
                        if(aPath.charAt(i) == '.')
                            break;
                        if(i == 0){
                            i=1;
                            break;
                        }
                    }
                    //判断后缀名是否等于fileClass
                    if(aPath.substring(i, aPath.length()).equals(fileClass)) {
                        filePath.add(file.getPath());
                    }
                }
                else if (file.isDirectory()) {
                    iteratorPath(file.getAbsolutePath(),fileClass);
                }
            }
        }
    }

    //用于向控制台和文件缓冲区buffer输出
    public static String Output(String[] args, Filehandler handler, String buffer) throws IOException {
        int flagc = 0;//用来记录哪些东西要输出
        int flagw = 0;
        int flagl = 0;
        int flaga = 0;
        for(String p : args){
            if(p.equals("-c"))
                flagc = 1;
            else if(p.equals("-w"))
                flagw = 1;
            else if(p.equals("-l"))
                flagl = 1;
            else if(p.equals("-a"))
                flaga = 1;
        }
        //按照顺序输出
        if(flagc == 1){
            System.out.println(handler.fileName + ", " + "字符数: "+handler.countChar);
            buffer += handler.fileName + ", " + "字符数: "+ handler.countChar + "\r\n";
        }
        if(flagw == 1) {
            System.out.println(handler.fileName + ", " + "单词数: "+handler.countword);
            buffer += handler.fileName + ", " + "单词数: "+ handler.countword + "\r\n";
        }
        if(flagl == 1){
            System.out.println(handler.fileName + ", " + "行数: "+handler.countline);
            buffer += handler.fileName + ", " + "行数: "+ handler.countline + "\r\n";
        }
        if(flaga == 1){
            System.out.println(handler.fileName + ", " + "代码行/空行/注释行: "+handler.countCodeLine + "/" + handler.countSpaceLine + "/" + handler.countNoteLine);
            buffer += handler.fileName + ", " + "代码行/空行/注释行: "+handler.countCodeLine + "/" + handler.countSpaceLine + "/" + handler.countNoteLine + "\r\n";
        }
        return buffer;
    }

    public static void main(String[] args) throws IOException {
        //记录时间
        long start = System.currentTimeMillis();

        String path = null;//统计的文件的路径
        String stopListPath = null;//停用表的路径
        String outputPath = ".\\output.txt";//输出文件的路径
        String[] stopList = null;//记录停用词
        String r = "-\\w";//正则表达式，用于匹配类似“-w“这样的参数
        String outputBuffer = "";//文件输出缓冲区，用于-o参数的控制，有-o参数则把buffer的内容写入文件，没有则不写入
        int lable = -1;//标志工作模式：-1为出错、0为默认统计模式、1为递归统计所有文件、2为用图形界面选择文件
        int isOutput = 0;//标志是否输出到文件
        int isStop = 0;//标志是否有停用词
        //遍历参数数组args[]，用正则表达式匹配出参数，再进一步判断是什么参数，并记录参数后面的路径
//        for(int i = 0;i<args.length;i++){
        for(int i = 1;i<args.length;i++){
            if(!(Pattern.matches(r, args[i]))){
                if(args[i-1].equals("-e")){
                    stopListPath = args[i];
                    isStop = 1;
                }
                else if(args[i-1].equals("-o")) {
                    outputPath = args[i];
                    isOutput = 1;
                }
                else
                    path = args[i];
            }
        }
        //有停用表，则打开停用表文件，读取到stopList中
        if(isStop == 1){
            InputStreamReader stopListStream = new InputStreamReader(new FileInputStream(stopListPath));
            BufferedReader stopListBuffer = new BufferedReader(stopListStream);
            String temp = null;
            while((temp = stopListBuffer.readLine()) != null) {
                //按空格分割
                stopList = temp.split(" ");
            }
        }
        //工作模式判断
        if(args[0].equals("-s"))
            lable = 1;
        else if(args[0].equals("-x"))
            lable = 2;
        else
            lable = 0;

        //path = "F:\\codes\\java\\try\\src\\test.txt";
        //递归扫描所有文件模式
        if(lable == 1){
            //将路径分开，以获取路径最后的文件类别
            //System.out.println(path);
            String[] Strings = path.split("\\\\");
            String fileClass = Strings[Strings.length-1];
            //dir是文件夹路径，fileClass是文件类别，即文件后缀名
            String dir = path.substring(0, path.length() - fileClass.length());
            int i = fileClass.length()-1;
            for(; i>=0; i--){
                if(fileClass.charAt(i) == '.')
                    break;
                if(i == 0){
                    i=1;
                    break;
                }
            }
            fileClass = fileClass.substring(i);
            //调用递归函数
            wc and = new wc();
            and.iteratorPath(dir, fileClass);
            //对返回的每一个path所指的文件进行计数并输出
            for (String list : and.filePath) {
//                System.out.println("File name: " + list);
//                outputBuffer += "File name: " + list + "\r\n";
                Filehandler handler = new Filehandler();
                handler.FileHandle(list, isStop, stopList);
                outputBuffer = Output(args, handler, outputBuffer);
            }
        }
        //普通模式
        else if(lable == 0){
            Filehandler handler = new Filehandler();
            handler.FileHandle(path, isStop, stopList);
            outputBuffer = Output(args, handler, outputBuffer);
        }
        //图形界面选择模式
        else if(lable == 2){
            //建立图形框架，调用FileDialog打开文件
            Frame f = new Frame("FileDialog Test");
            FileDialog d1 = new FileDialog(f, "Open File", FileDialog.LOAD);
            FileDialog d2 = new FileDialog(f, "Save File", FileDialog.SAVE);
            d1.setVisible(true);
//            System.out.println(d1.getDirectory() + d1.getFile());
            //获取文件路径，然后按照普通模式处理
            path = d1.getDirectory() + d1.getFile();
            Filehandler handler = new Filehandler();
            handler.FileHandle(path, isStop, stopList);
            outputBuffer = Output(args, handler, outputBuffer);
            //由于FileDialog会一直阻塞等待，不会主动结束程序，所以用System.exit()手动结束
            long end = System.currentTimeMillis();
            float costTime = (float)(end - start)/1000;
            System.out.println("cost: " + costTime + "s");
            System.exit(0);
        }
        //报错提示
        else if(lable == -1){
            System.out.println("Wrong!!");
        }
        //输出程序运行时间
        long end = System.currentTimeMillis();
        float costTime = (float)(end - start)/1000;
        System.out.println("cost: " + costTime + "s");
        outputBuffer += "cost: " + costTime + "s";
        //如果要输出到文件中，将outputBuffer中的内容写入文件
        if(isOutput == 1){
            File writename = new File(outputPath);
            writename.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(outputBuffer);
            out.flush();
            out.close();
        }
    }
}