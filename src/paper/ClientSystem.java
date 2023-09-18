package paper;

import java.io.IOException;
import java.util.Scanner;
import paper.utils.LogSystem;

public class ClientSystem {
  static String order;
  static String flag;

  static LogSystem lg;

  static void makeNote(){
    if ("切换至小学".equals(order)) {
      flag = "小学";
      System.out.println("准备生成小学数学题目，请输入生成题目数量");
    } else if ("切换至初中".equals(order)) {
      flag = "初中";
      System.out.println("准备生成初中数学题目，请输入生成题目数量");
    } else if ("切换至高中".equals(order)) {
      flag = "高中";
      System.out.println("准备生成高中数学题目，请输入生成题目数量");
    }
  }

  public static void main(String[] args) throws IOException {
    lg = new LogSystem();
    flag = "";
    System.out.println("请输入用户名和密码");
    Scanner sc = new Scanner(System.in);
    while (true) {
      String name = sc.next();
      String key = sc.next();
      flag = lg.checkUser(name, key);
      if ("0".equals(flag)) {
        System.out.println("请输入正确的用户名、密码");
      } else {
        System.out.println("当前选择为" + flag + "出题");
        System.out.println(
            "准备生成" + flag + "数学题目，请输入生成题目数量（输入-1将退出当前用户，重新登录）：");
        while (true) {
          order = sc.next();
          if ("-1".equals(order)) {
            System.out.println("请输入用户名、密码");
            break;
          } else if (lg.isNumeric(order)) {
            int num = Integer.parseInt(order);
            if (num >= 10 && num <= 30) {
              lg.makeQuestion(num, flag, name);
              System.out.println("题目生成完毕!\n你可以继续出题、切换模式或输入-1重新登陆");
            } else {
              System.out.println(
                  "输入无效，输入“切换至”+”小学或初或和高中“切换模式,输入10-30出题，输入-1重新登陆");
            }
          } else if ("切换至小学".equals(order) || "切换至初中".equals(order)
              || "切换至高中".equals(order)) {
            makeNote();
          } else {
            System.out.println(
                "输入无效，输入“切换至”+”小学、初中和高中“切换模式,输入10-30出题，输入-1重新登陆");
          }
        }
      }
    }
  }
}