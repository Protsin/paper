package paper;

import java.util.Random;

public class MiddleSchool extends School {

  int flag;

  MiddleSchool() {
    flag = 0;
  }

  @Override
  String selectSign(int num) {
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
      default:
    }
    return sign;
  }

  String preQuestion() {
    String string = "";
    Random rand = new Random();
    int temp = rand.nextInt(8);
    if (temp == 4) {
      flag++;
      string += "(";
    }
    if (temp == 6) {
      string += "√";
    }

    temp = rand.nextInt(100) + 1;
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
  String makeQuestion() {
    String string = this.preQuestion();

    Random rand = new Random();
    int temp;
    int signnum = rand.nextInt(4) + 1;
    for (int i = 0; i < signnum; i++) {
      temp = rand.nextInt(4);
      string += selectSign(temp);
      temp = rand.nextInt(8);
      if (temp == 4) {
        flag++;
        string += "(";
      }
      if (temp == 6) {
        string += "√";
      }
      string += rand.nextInt(100) + 1;
      temp = rand.nextInt(8);
      if (temp == 5 && flag > 0) {
        flag--;
        string += ")";
      }
      if (temp == 7) {
        string += "^2";
      }
    }

    for (int i = 0; i < flag; i++) {
      string += ")";
    }

    flag = 0;
    string += "=";
    return string;
  }
}