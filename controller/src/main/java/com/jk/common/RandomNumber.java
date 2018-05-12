package com.jk.common;

import java.util.Random;

public class RandomNumber {

    //根据指定长度生成纯数字的随机数
    public static String createData(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
        {
            sb.append(rand.nextInt(10));
        }
        String data=sb.toString();
        return data;
    }
    /**
     * 生成随机数当作getItemID 字母和数字组合
     * n ： 需要的长度
     * @return
     */
    public static String itemID( int n )
    {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }

    /**
     * 生产ItemName随机函数 随纯英文
     * length 长度
     * @return
     */
    public static String itemName( int length ){
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < length; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }

}
