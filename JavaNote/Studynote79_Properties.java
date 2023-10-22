import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

//传统方法读取文件数据
class Properties01 {
    public static void main(String[] args) throws IOException {
        //读取mysql.properties 文件，并得到ip, user 和 pwd
        //这里的文件路径是相对路径，相当于从jvm启动位置开始找，对于普通java项目jvm启动位置就是项目根路径
        BufferedReader br = new BufferedReader(new FileReader("src\\mysql.properties"));
        String line = "";
        while ((line = br.readLine()) != null) { //循环读取
            //如果多打了回车，会报数组越界异常
            String[] split = line.split("=");
            //如果我们要求指定的ip值
            if("ip".equals(split[0])) {
                System.out.println(split[0] + "值是: " + split[1]);
            }
        }

        br.close();
    }
}

/*
Properties类
继承关系
Dictionary
    ↑
Hashtable
    ↑
Properties

基本介绍
1）专门用于读写配置文件的集合类
配置文件的格式：
键=值
键=值
2）注意：键值对不需要有空格，值不需要用引号一起来。默认类型是String
3）Properties的常见方法
load(Reader reader)：加载配置文件的键值对到Properties对象
list(PrintStream out)：将数据显示到指定设备
getProperty(key)：根据键获取String值 -> 如果使用get(key)，调用的是其父类 Hashtable 的方法，会返回 Object，可以在Object对象后面+ ""转String
setProperty(key,value)：设置键值对到Properties对象
store(Writer writer, String comments)：将Properties中的键值对存储到配置文件，在idea中，保存信息到配置文件，如果含有中文，会存储为unicode码
http://tool.chinaz.com/tools/unicode.aspx unicode码查询工具
 */

class Properties02 {
    public static void main(String[] args) throws IOException {
        //使用Properties 类来读取mysql.properties 文件

        //1. 创建Properties 对象
        Properties properties = new Properties();
        //2. 加载指定配置文件
        //load加载的时候加载到properties对象，是继承了hashtable的，所以相同key的就替换value了
        properties.load(new FileReader("src\\mysql.properties"));
        //3. 把k-v显示控制台
        properties.list(System.out);
        //4. 根据key 获取对应的值
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        System.out.println("用户名=" + user);
        System.out.println("密码是=" + pwd);

        //乱码是因为properties文件编码和输出编码没统一

    }
}

class Properties03 {
    public static void main(String[] args) throws IOException {
        //使用Properties 类来创建 配置文件, 修改配置文件内容

        Properties properties = new Properties();
        //创建
        //1.如果该文件没有key 就是创建；如果该文件有key ,就是修改
        //2.传入的值不能为空
        //修改之前一定要将原来的配置文件的键值对加载到集合中，再对集合进行增删改，然后再覆盖写入原来的配置文件中
        /* Properties 父类是 Hashtable ， 底层就是Hashtable
           创建新键值对的核心方法源代码：
            public synchronized V put(K key, V value) {
                // Make sure the value is not null
                if (value == null) {
                    throw new NullPointerException();
                }

                // Makes sure the key is not already in the hashtable.
                Entry<?,?> tab[] = table;
                int hash = key.hashCode();
                int index = (hash & 0x7FFFFFFF) % tab.length;
                @SuppressWarnings("unchecked")
                Entry<K,V> entry = (Entry<K,V>)tab[index];
                for(; entry != null ; entry = entry.next) {
                    if ((entry.hash == hash) && entry.key.equals(key)) {
                        V old = entry.value;
                        entry.value = value;//如果key 存在，就替换
                        return old;
                    }
                }

                addEntry(hash, key, value, index);//如果是新k, 就addEntry
                return null;
            }

         */
        //下面的键值对会创建到内存中
        properties.setProperty("charset", "utf8");
        properties.setProperty("user", "汤姆");//注意保存时，是中文的 unicode码值
        properties.setProperty("pwd", "888888");

        //将k-v 存储文件中：传入store()方法的第一个参数是字节流或字符流均可，第二个参数是String类型的文件注释
        //保存时使用FileOutputStream（字节流）保存中文就是unicode码，使用FileWriter（字符流）保存的就仍然是中文
        properties.store(new FileWriter("src\\mysql2.properties"), null);
        System.out.println("保存配置文件成功~");

    }
}
