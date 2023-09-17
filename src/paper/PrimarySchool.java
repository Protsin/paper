package paper;

import java.util.Random;

public class PrimarySchool extends School {

  @Override
  String selectSign(int num) {
    String sign = "";
    sign = "";
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
        sign = "";
        break;
      default:
    }
    return sign;
  }

  @Override
  String makeQuestion() {
    String string = "";
    Random rand = new Random();
    int flag = 0;
    int temp = rand.nextInt(6);
    if (temp == 4) {
      flag++;
      string += "(";
    }
    temp = rand.nextInt(100) + 1;
    string += temp;
    temp = rand.nextInt(6);
    if ((temp == 5) && (flag > 0)) {
      flag--;
      string += ")";
    }

    int signnum = rand.nextInt(4) + 1;
    for (int i = 0; i < signnum; i++) {
      temp = rand.nextInt(4);
      string += this.selectSign(temp);
      temp = rand.nextInt(6);
      if (temp == 4) {
        string += "(";
        flag++;
      }
      temp = rand.nextInt(100) + 1;
      string += temp;
      temp = rand.nextInt(6);
      if ((temp == 5) && (flag > 0)) {
        flag--;
        string += ")";
      }
    }

    for (int i = 0; i < flag; i++) {
      string += ")";
    }
    string += "=";
    return string;
  }
}