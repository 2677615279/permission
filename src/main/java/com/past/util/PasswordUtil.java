package com.past.util;

import java.util.Random;

/**
 * 密码工具类
 */
public class PasswordUtil {

    //密码 字母字符可选范围  省去了容易混淆的字母 i l o I L O
    public final static String[] WORD = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    //密码 数字字符可选范围  省去了容易混淆的数字0 1
    public final static String[] NUM = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };


    /**
     * 随机生成密码 保证字母和数字交错生成
     * @return
     */
    public static String randomPassword(){

        //因为要拼接当前字符串作为随机密码，选择字符串自身可变的StringBuffer和StringBuilder，考虑线程安全，采用StringBuffer
        StringBuffer stringBuffer = new StringBuffer(); //StringBuffer支持并发操作、线程安全;StringBuilder相反，但在单线程中，StringBuilder效率高

        //Random有随机因子的概念，因子相同的话，每次随机的结果基本相同；因此通常每次都要指定一个因子避免这种请求，习惯性使用系统时间毫秒值
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false; //通过flag标识  先取数字(true)还是先取字母(false)

        //random.nextInt(n) 返回的随机数的范围   0（包括）和 n（不包括）之间均匀分布的 int 值。
        int length = random.nextInt(3) + 8; //默认密码长度为8或9或10

        for (int i = 0; i < length; i++) {
            if (flag){ //先取数字(true)还是先取字母(false)
                stringBuffer.append(NUM[random.nextInt(NUM.length)]);
            } else {
                stringBuffer.append(WORD[random.nextInt(WORD.length)]);
            }
            flag = !flag; //保证字母和数字交错生成
        }

        return stringBuffer.toString();
    }


}
