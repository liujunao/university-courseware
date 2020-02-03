import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.SecureRandom;

public class Ex_1 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("DES 加解密");    //创建Frame窗口
        JPanel jp = new JPanel();    //创建面板

        Dimension btnSize = new Dimension(80, 30);

        JTextArea txtfield1 = new JTextArea(5, 35);
        JButton button1 = new JButton("Encrypt");
        button1.setPreferredSize(btnSize);
        JButton btn1 = new JButton("File");
        btn1.setPreferredSize(btnSize);
        JLabel label1 = new JLabel("    Input:       ");

        JTextField textField = new JTextField(43);
        JLabel label = new JLabel("Key:       ");
        JButton btn = new JButton("File");
        btn.setPreferredSize(btnSize);

        JTextArea txtfield2 = new JTextArea(5, 35);
        JButton button2 = new JButton("Decrypt");
        button2.setPreferredSize(btnSize);
        JButton btn2 = new JButton("DeFile");
        btn2.setPreferredSize(btnSize);
        JLabel label2 = new JLabel("Encrypted: ");

        JTextArea txtfield3 = new JTextArea(5, 35);
        JButton button3 = new JButton("Exit");
        button3.setPreferredSize(btnSize);
        JButton btn3 = new JButton("File");
        btn3.setPreferredSize(btnSize);
        JLabel label3 = new JLabel("Decrypted: ");


        jp.add(label1);
        jp.add(txtfield1);
        jp.add(button1);
        jp.add(btn1);

        jp.add(label);
        jp.add(textField);
        jp.add(btn);

        jp.add(label2);
        jp.add(txtfield2);
        jp.add(button2);
        jp.add(btn2);

        jp.add(label3);
        jp.add(txtfield3);
        jp.add(button3);
        jp.add(btn3);

        frame.add(jp);
        frame.setBounds(300, 200, 650, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //加密
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = txtfield1.getText();
                KEY = textField.getText();
                String encrypt = "";
                try {
                    encrypt = encrypt(data);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                txtfield2.setText(encrypt);
                createAndWrite("encrypt",encrypt);
            }
        });

        //读取待加密文件
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser(txtfield1);
            }
        });

        //读取密钥文件
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                String filePath = fileChooser.getSelectedFile().toString();
                textField.setText(readFile(filePath));
            }
        });

        //解密
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = txtfield2.getText();
                KEY = textField.getText();
                String decrypt = "";
                try {
                    decrypt = decrypt(data);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                txtfield3.setText(decrypt);
            }
        });

        //解密并输出到文件
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = txtfield2.getText();
                KEY = textField.getText();
                String decrypt = "";
                try {
                    decrypt = decrypt(data);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                createAndWrite("decrypt",decrypt);
            }
        });

        //退出
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //显示解密内容
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser(txtfield3);
            }
        });
    }

    //读取文件并显示 JTextArea
    public static void fileChooser(JTextArea textArea){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        String filePath = fileChooser.getSelectedFile().toString();
        textArea.setText(readFile(filePath));
    }

    private static final String ENCODE = "UTF-8";
    public static String KEY = "";

    //根据键值进行加密
    public static String encrypt(String data) throws Exception {
        byte[] bytes = encrypt(data.getBytes(ENCODE), KEY.getBytes(ENCODE), "en");
        String string = new BASE64Encoder().encode(bytes);
        return string;
    }

    //根据键值进行解密
    public static String decrypt(String data) throws Exception {
        if (data == null) {
            return null;
        }
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(data);
        byte[] bytes1 = encrypt(bytes, KEY.getBytes(ENCODE), "de");

        return new String(bytes1, ENCODE);
    }

    private static byte[] encrypt(byte[] data, byte[] key, String type) throws Exception {
        //生成一个可信任的随机数源
        SecureRandom secureRandom = new SecureRandom();
        //从原始密钥数据创建 DESKeySpec 对象
        DESKeySpec desKeySpec = new DESKeySpec(key);
        //从创建有一个密钥工厂，然后用它把 DESKeySpec 转换成 SecretKey 对象
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        //从 Cipher 对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //从密钥初始化 Cipher 对象
        if (type.equalsIgnoreCase("de")) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
        }

        return cipher.doFinal(data);
    }

    //创建文件并进行写入
    public static void createAndWrite(String fileName, String data) {
        File file = new File("src/file/" + fileName + ".txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件内容
    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}