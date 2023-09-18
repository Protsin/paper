package paper.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import paper.entity.HighSchool;
import paper.entity.MiddleSchool;
import paper.entity.PrimarySchool;
import paper.entity.User;

public class LogSystem {
  static ArrayList<User> user = new ArrayList<User>();  // arraylist容器，用于存放user

  static PrimarySchool primary;
  static MiddleSchool middle;
  static HighSchool high;

  public LogSystem() {             // 登陆系统初始化，将user.txt里的用户信息读到u容器里面
    primary = new PrimarySchool();
    middle = new MiddleSchool();
    high = new HighSchool();

    try {
      FileReader fileReader = new FileReader("user/user.txt");
      BufferedReader bufferReader = new BufferedReader(fileReader);
      String tempString;
      while ((tempString = bufferReader.readLine()) != null) {
        User temp = new User(tempString.substring(0, 3), tempString.substring(4, 7),
            tempString.substring(8));
        user.add(temp);
      }
      bufferReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public String checkUser(String n, String k) {          // 检查输入的账号密码是否正确
    for (int i = 0; i < user.size(); i++) {
      if ((user.get(i).getName().equals(n)) && (user.get(i).getKey().equals(k))) {
        return user.get(i).getType();
      }
    }
    return "0";
  }

  public boolean isNumeric(String string) {      // 用于判断输入的字符串是不是一个数，但并不能判断出负数
    for (int i = string.length(); --i >= 0; ) {
      if (!Character.isDigit(string.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public void makeQuestion(int NUM, String flag, String address) {  // 检查需要出哪种类型的题目
    try {
      if ("小学".equals(flag)) {
        this.makePrimary(NUM, address);
      } else if ("初中".equals(flag)) {
        this.makeMiddle(NUM, address);
      } else if ("高中".equals(flag)) {
        this.makeHigh(NUM, address);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  String getAddress(String address) {                 // 用于生成当前时间的txt文件，并返回地址
    File directory = new File(address);
    if (!directory.exists()) {
      directory.mkdir();
    }
    address += "/";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    Date date = new Date(System.currentTimeMillis());
    address += formatter.format(date);
    address += ".txt";
    return address;
  }

  void makePrimary(int NUM, String folderAddress) {
    String txtAddress = this.getAddress(folderAddress);
    try {
      for (int i = 0; i < NUM; i++) {
        String tempString = "";
        tempString += (i + 1);
        tempString += "、";
        String question = primary.makeQuestion();
        tempString += question;
        tempString += "\n";
        if (!this.checkQuestion(question, folderAddress)) {
          System.out.println(tempString);
          this.writeQuestion(tempString, txtAddress);
        } else {
          i--;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void makeMiddle(int NUM, String folderAddress) {
    String txtAddress = this.getAddress(folderAddress);
    try {
      for (int i = 0; i < NUM; i++) {
        String question = middle.makeQuestion();
        String tempString = "";
        tempString += (i + 1);
        tempString += "、";
        tempString += question;
        tempString += "\n";
        if (question.indexOf("√") == -1 && question.indexOf("^2") == -1) {
          i--;                           //  判断是否符合规定，若初中题目不包含“√”"^2"，则作废再出一道
        } else {
          if (!this.checkQuestion(question, folderAddress)) {
            System.out.println(tempString);
            this.writeQuestion(tempString, txtAddress);
          } else {
            i--;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void makeHigh(int NUM, String folderAddress) {
    String txtaddress = this.getAddress(folderAddress);
    try {
      for (int i = 0; i < NUM; i++) {
        String question = high.makeQuestion();
        String tempString = "";
        tempString += (i + 1);
        tempString += "、";
        tempString += question;
        int x = question.indexOf("sin")
            + question.indexOf("cos") + question.indexOf("tan");
        if (x == -3) {
          i--;                        //  判断是否符合规定，若初中题目不包含“sin”"cos""tan"，则作废再出一道
        } else {
          tempString += "\n";
          if (!this.checkQuestion(question, folderAddress)) {    // 检查题目是否重复
            System.out.println(tempString);
            this.writeQuestion(tempString, txtaddress);
          } else {
            i--;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  boolean checkQuestion(String jugString, String ADDRESS) {
    File file = new File(ADDRESS);
    String[] fileNames = file.list();
    List<String> result = new ArrayList<>();
    try {
      for (int i = 0; i < fileNames.length; i++) {         // 在之前的题库中查重
        BufferedReader bufferReader =
            new BufferedReader(new FileReader(ADDRESS + "/" + fileNames[i]));
        String tempString;
        while ((tempString = bufferReader.readLine()) != null) {
          int x = tempString.indexOf("、");
          result.add(tempString.substring(x + 1));
        }
        bufferReader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (result.contains(jugString)) {
      return true;
    }

                                                             // 排除a+b=b+a，这种重复情况
    int signnum = (jugString.indexOf("+") > 0 ? 1 : 0)
        + (jugString.indexOf("-") > 0 ? 1 : 0) + (jugString.indexOf("*") > 0 ? 1 : 0)
        + (jugString.indexOf("/") > 0 ? 1 : 0);

    if (signnum == 1) {
      if (jugString.indexOf("+") > 0) {
        int x = jugString.indexOf("+");
        String s = jugString.substring(x + 1) + jugString.substring(0, x);
        return result.contains(s);
      } else if (jugString.indexOf("*") > 0) {
        int x = jugString.indexOf("*");
        String s = jugString.substring(x + 1) + jugString.substring(0, x);
        return result.contains(s);
      }
    }
    return false;
  }

  void writeQuestion(String writeString, String address) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(address, true));
      bw.write(writeString);                          // 将题目写入txt文件
      bw.newLine();
      bw.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}