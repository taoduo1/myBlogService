package com.example.shop_file.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duo.tao
 * @Description:
 * @date 2022-08-28 9:54
 */
public class FileUtil {


    public static void main(String[] args) {
        List<Test> ss = new ArrayList<>();
        ss.add(new Test("456", 2));
        ss.add(new Test("123", 1));
        ss.add(new Test("789", 3));

        for (int i = 0; i < 10; i++) {
            System.out.println("==================");
            ss.stream().parallel().map(o->{
                System.out.println(o.getName());
                return o.getName();
            }).collect(Collectors.toList());
            System.out.println("==================");
        }


       /* ss.sort(Comparator.comparing(Test::getValue,(x,y)->{
            return x.compareTo(y);
        }).reversed());
        //ss.sort(Comparator.comparing((a)->a.value));
        *//*ss.sort(Comparator.comparing((Test a) -> a.value, (x, y) -> {
            if(x == null && y != null){
                return 1;
            }
            if(x !=null && y == null){
                return -1;
            }
            if(x == null){
                return 0;
            }
            return x.compareTo(y);
        }).reversed());*//*
        ss.forEach(o -> System.out.println(o.name + o.value));
*/
        //System.out.println(ss.stream().map(Test::getValue).reduce(Integer::sum).get());
        //System.out.println(ss.stream().mapToInt(Test::getValue).max().getAsInt());
        //System.out.println(ss.stream().collect(summarizingInt(Test::getValue)));

    }

    static class Test {
        String name;
        Integer value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Test(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }
}
