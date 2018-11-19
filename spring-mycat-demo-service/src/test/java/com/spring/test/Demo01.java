package com.spring.test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 测试1.6 javacompiler
 */
public class Demo01 {
    public static void main(String[] args) throws Exception {

        //将code写入文件中
        String code = "public class Hello{" +
                "public static void main(String[] args){" +
                "System.out.println(\"HelloWorld\");" +
                "}" +
                "}";
        File file = new File("/Users/wch/Hello.java");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);
        os.write(code.getBytes(), 0, code.length());
        os.flush();
        os.close();


        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //直接从文件中获取
        /**compiler.run(1,2,3,4)
         * @parameters: 1.in → "standard" input; use System.in（键盘输入） if null
        2.out → "standard" output; use System.out（输出到控制台） if null
        3.err → "standard" error; use System.err if null
        4.arguments → arguments to pass to the tool
         */
        //   int result = compiler.run(null, null, null, "/Users/wch/HelloWorld.java");
        //将Java代码以字符串的形式，先写入文件，然后再通过编译器读取出来，达到动态的效果。
        int result2 = compiler.run(null, null, null, "/Users/wch/Hello.java");
        //  System.out.println(result==0?"编译成功":"编译失败");
        System.out.println(result2 == 0 ? "编译成功" : "编译失败");

        //通过Runtime类动态运行编译好的类
        Runtime rt = Runtime.getRuntime();
        Process pro = rt.exec("java -cp /Users/wch Hello");//实际上已经执行了

        //让结果输出到控制台
        InputStream in = pro.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String temp = "";
        while ((temp = reader.readLine()) != null) {
            System.out.println(temp);
        }


        //通过类加载器来动态运行编译好的类
        URL[] urls = new URL[]{new URL("file:///" + "Users/wch/")};
        URLClassLoader loader = new URLClassLoader(urls);
        Class c = loader.loadClass("Hello");
        //调用加载类的main方法
        Method m = c.getMethod("main", String[].class);
        m.invoke(null, (Object) new String[]{"aa", "bb"});
        //注意上面的代码，如果不加（Object）转型的话，
        //则会编译成：m.invoke(null,"aa","bb"),就发生了参数个数不匹配的问题。
        //因此，必须要加上（Object）转型，避免这个问题。

    }
}
