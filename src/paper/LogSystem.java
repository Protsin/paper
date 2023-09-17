package paper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogSystem {

  static ArrayList<User> user = new ArrayList<User>();

  static PrimarySchool primary;
  static MiddleSchool middle;
  static HighSchool high;

  LogSystem() throws IOException {
    primary = new PrimarySchool();
    middle = new MiddleSchool();
    high = new HighSchool();

    FileReader fileReader = new FileReader("user/user.txt");
    BufferedReader bufferReader = new BufferedReader(fileReader);
    String tempString;
    while ((tempString = bufferReader.readLine()) != null) {
      User temp = new User(tempString.substring(0, 3), tempString.substring(4, 7),
          tempString.substring(8));
      user.add(temp);
    }
    bufferReader.close();
  }

  String checkUser(String n, String k) {
    for (int i = 0; i < user.size(); i++) {
      if ((user.get(i).name.equals(n)) && (user.get(i).key.equals(k))) {
        return user.get(i).type;
      }
    }
    return "0";
  }

  boolean isNumeric(String string) {
    for (int i = string.length(); --i >= 0; ) {
      if (!Character.isDigit(string.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  void makeQuestion(int NUM, String flag, String address) throws IOException {
    if (flag.equals("小学")) {
      this.makePrimary(NUM, address);
    } else if (flag.equals("初中")) {
      this.makeMiddle(NUM, address);
    } else if (flag.equals("高中")) {
      this.makeHigh(NUM, address);
    }
  }

  String getAddress(String address) {
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

  void makePrimary(int NUM, String folderAddress) throws IOException {
    String txtAddress = this.getAddress(folderAddress);
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
  }

  void makeMiddle(int NUM, String folderAddress) throws IOException {
    String txtAddress = this.getAddress(folderAddress);
    for (int i = 0; i < NUM; i++) {
      String question = middle.makeQuestion();
      String tempString = "";
      tempString += (i + 1);
      tempString += "、";
      tempString += question;
      tempString += "\n";
      if (question.indexOf("√") == -1 && question.indexOf("^2") == -1) {
        i--;
      } else {
        if (!this.checkQuestion(question, folderAddress)) {
          System.out.println(tempString);
          this.writeQuestion(tempString, txtAddress);
        } else {
          i--;
        }
      }
    }
  }

  void makeHigh(int NUM, String folderAddress) throws IOException {
    String txtaddress = this.getAddress(folderAddress);
    for (int i = 0; i < NUM; i++) {
      String question = high.makeQuestion();
      String tempString = "";
      tempString += (i + 1);
      tempString += "、";
      tempString += question;
      int x = question.indexOf("sin") +
          question.indexOf("cos") + question.indexOf("tan");
      if (x == -3) {
        i--;
      } else {
        tempString += "\n";
        if (!this.checkQuestion(question, folderAddress)) {
          System.out.println(tempString);
          this.writeQuestion(tempString, txtaddress);
        } else {
          i--;
        }
      }
    }
  }

  boolean checkQuestion(String jugString, String ADDRESS) throws IOException {
    File file = new File(ADDRESS);
    String[] fileNames = file.list();
    List<String> result = new ArrayList<>();
    for (int i = 0; i < fileNames.length; i++) {
      BufferedReader bufferReader =
          new BufferedReader(new FileReader(ADDRESS + "/" + fileNames[i]));
      String tempString;
      while ((tempString = bufferReader.readLine()) != null) {
        int x = tempString.indexOf("、");
        result.add(tempString.substring(x + 1));
      }
      bufferReader.close();
    }
    if (result.contains(jugString)) {
      return true;
    }

    int signnum = (jugString.indexOf("+") > 0 ? 1 : 0) +
        (jugString.indexOf("-") > 0 ? 1 : 0) + (jugString.indexOf("*") > 0 ? 1 : 0)
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

  void writeQuestion(String writeString, String address) throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter(address, true));
    bw.write(writeString);
    bw.newLine();
    bw.close();
  }
}