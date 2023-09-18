package paper.entity;

import java.util.Random;

public class HighSchool extends School {
  int flag;//根据flag的值判断最后需要补几个）

  public HighSchool() {
    flag = 0;
  }

  @Override
  String selectSign(int num) {        // 根据随机数的值生成随机的符号
    String sign = "";
    switch (num) {
      case 0:
        sign = "+";
        break;
      case 1:
        sign = "-";
        break;
      case 2:
        sign = "*";
        break;
      case 3:
        sign = "/";
        break;
      case 4:
        sign = "(";
        break;
      case 5:
        sign = ")";
        break;
      case 6:
        sign = "√";
        break;
      case 7:
        sign = "^2";
        break;
      case 8:
        sign = "sin";
        break;
      case 9:
        sign = "cos";
        break;
      case 10:
        sign = "tan";
        break;
      default:
    }
    return sign;
  }

  String preQuestion() {         // 预处理：先生成第1个操作数，makeQuestion方法用来生成第2-5个操作上
    Random rand = new Random();
    int temp = rand.nextInt(11);
    String string = "";
    if (temp == 4) {//第1个操作数前可能有（、sin、cos、tan
      string += "(";
      flag++;
    }
    temp = rand.nextInt(11);
    if (temp == 6 || temp == 8 || temp == 9 || temp == 10) {
      string += selectSign(temp);
    }

    temp = rand.nextInt(100) + 1;        // 生成第1个操作数
    string += temp;

    temp = rand.nextInt(8);
    if (temp == 5 && flag > 0) {
      flag--;
      string += ")";
    }
    if (temp == 7) {
      string += "^2";
    }
    return string;
  }

  @Override
  public String makeQuestion() {         // 生成第2-5个操作数
    Random rand = new Random();
    int temp;
    String string = this.preQuestion();

    int signnum = rand.nextInt(4) + 1;
    for (int i = 0; i < signnum; i++) {
      temp = rand.nextInt(4);
      string += selectSign(temp);
      temp = rand.nextInt(11);
      if (temp == 4) {
        flag++;
        string += "(";
      }
      temp = rand.nextInt(11);
      if (temp == 6 || temp == 8 || temp == 9 || temp == 10) {
        string += selectSign(temp);
      }

      string += rand.nextInt(100) + 1;     // 生成操作数

      temp = rand.nextInt(11);
      if (temp == 5 && flag > 0) {
        flag--;
        string += ")";
      }
      if (temp == 7) {
        string += "^2";
      }
    }

    for (int i = 0; i < flag; i++) {         // 根据flag的值补）
      string += ")";
    }
    string += "=";
    flag = 0;
    return string;
  }
}