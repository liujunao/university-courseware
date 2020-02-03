import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 文件处理器类，用来对文件内的内容计数
 * Created by cmy on 2018/3/12.
 */
public class Filehandler {
    public String fileName = "None";//记录文件名字
    public int countChar = 0;//字符计数
    public int countword = 0;//单词计数
    public int countline = 0;//行数计数
    public int countSpaceLine = 0;//空行计数
    public int countCodeLine = 0;//代码行计数
    public int countNoteLine = 0;//注释行计数

    public void FileHandle(String path, int isStop, String[] StopList) throws IOException {
        //建立输入字符流
        InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
        BufferedReader br = new BufferedReader(isr);
        //获取文件名以备输出
        String[] Strings = path.split("\\\\");
        fileName = Strings[Strings.length-1];

        int label = 0;//用于记录/**/这种类型的注释,label=1表示检测到/*，检测到*/则令label=1

        //逐行读取文件中的内容
        String s = null;
        while((s = br.readLine()) != null)
        {
            s+='\n';//由于readLine()不读取每行最后的换行符，如果不加这一行会出问题
            countChar += s.length();
            //trim()函数可以去除每一行第一个字符前的空格
            s = s.trim();
            //按照空格和逗号来分词
            String[] temp = s.split(" |,");
            countword += temp.length;
            //如果有停词表，在分词之后要检查每一个单词，是否和停词表中的单词有重复，如果有则countword--
            if(isStop == 1){
                for(String p : temp){
                    for(String e : StopList){
                        if(p.equalsIgnoreCase(e))//equalsIgnoreCase()为不区分大小写的比较
                            countword--;
                    }
                }
            }

            countline++;
            //检测空行
            if((s == "" || s.length() <= 1) && label == 0){
                countSpaceLine++;
            }
            //以"//"开头或者"//"之前只有一个字符的都为注释行，/**/也为注释
            else if(((s.length() > 2 && s.substring(1, 3).equals("//")) || s.substring(0, 2).equals("//")) && label == 0 )
                countNoteLine++;
            else if(((s.length() > 2 && s.substring(1, 3).equals("/*")) || s.substring(0, 2).equals("/*")) && label == 0){
                label = 1;
                countNoteLine++;
            }
            else if(label == 1)
                countNoteLine++;
            else if(label == 0)
                countCodeLine++;
            //正则表达式匹配“*/”
            if(s.matches(".*\\*/.*")){
                if(label == 1){
                    label = 0;
                    int i = s.length()-1;
                    //如果*/后面跟着代码，则算代码行
                    for(; i>=0; i--){
                        if(s.charAt(i) == '/' && s.charAt(i-1) == '*')
                            break;
                        if(i == 0)
                            break;
                    }
                    //就要在在这里修复计数
                    if(!(i == s.length()-1 || i == s.length()-2)){
                        countCodeLine++;
                        countNoteLine--;
                    }

                }
            }
        }
        isr.close();
    }
}
