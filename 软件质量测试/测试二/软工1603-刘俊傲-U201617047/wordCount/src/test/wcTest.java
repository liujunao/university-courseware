import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @DESCRIPTION :
 * @AUTHOR : yaren
 * @TIME : Created in 0:27 ${DATA}
 * @Modified By :
 */
public class wcTest {
    wc wcTest = new wc();
    Filehandler handler = new Filehandler();
    @Before
    public void setUp() throws Exception {
        String path = "src\\file\\stoptest.c";//统计的文件的路径
        int isStop = 0;
        String[] stopList = {"out","void"};//记录停用词
        handler.FileHandle(path, isStop, stopList);
    }

    @Test
    public void iteratorPath() {
        wcTest.iteratorPath("src\\",".c");
    }

    @Test
    public void output() throws Exception{
        wc.Output(new String[]{"-c"},handler,"");
        wc.Output(new String[]{"-w"},handler,"");
        wc.Output(new String[]{"-l"},handler,"");
        wc.Output(new String[]{"-a"},handler,"");
    }

    @Test
    public void main() throws IOException {
        wc.main(new String[]{"-w ","src/file/stoptest.c","-e","src\\file\\stoplist.txt"});
    }
}