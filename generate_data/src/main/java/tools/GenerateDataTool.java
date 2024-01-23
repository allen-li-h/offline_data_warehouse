package tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Author：Allen
 * @Project：bigdata_demo
 * @Name：GenerateDataTool
 * @Date：2024年01月18日 0018 11:46:58
 */
public class GenerateDataTool {

    private static final String[] firstNames = {"张", "王", "李", "赵", "刘", "陈", "杨", "黄", "周", "吴", "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗"};
    private static final String[] secondNames = {"伟", "芳", "", "娜", "秀", "敏", "静", "丽", "强", "磊", "军", "勇", "杰", "娟", "涛", "明", "超", "平", "刚", "桂", "博", "云", "帆", "滔", "晨"};
    private static final String[] thirdNames = {"伟", "芳", "娜", "英", "敏", "静", "丽", "强", "磊", "军", "勇", "杰", "娟", "涛", "明", "超", "平", "刚", "博", "云", "帆", "滔", "晨"};
    public static final String[] averaCodes = {"130132","130227","130321","130481","130503","130638","130683","130730","131102","140110","140214","150702","152531","210102","210311","210321","210726","220102","220402","220421","220524","220722","220781","222403","230205","230208","230321","230381","230402","230404","230621","230811","310117","310120","320102","320381","320382","320507","320813","321202","330122","330383","330522","330624","330782","340207","340210","340323","340405","340811","341203","341324","350825","350922","360428","360483","360724","360822","361023","361130","370117","370322","370323","370686","370826","370829","371423","371426","371603","410106","410184","410325","410327","410505","410804","411121","411328","411502","411702","420324","420381","421223","430611","430681","430724","431126","431382","440222","440605","440607","441621","450124","450222","450422","450423","450481","451223","451423","469005","500119","510321","510502","510504","510524","511525","511681","511702","511703","513428","513431","520304","520329","520425","520524","522622","522635","522731","530112","530481","530827","532528","532625","532928","533323","533422","540236","540328","540629","610115","610116","610481","610724","610802","610828","610881","620104","620525","621027","622923","622924","630224","630225","632301","632622","632626","640121","650105","650521","653023","653024","654002"};
    public static final String[] classcodes = {"U"}; // , "P", "E", "H", "X"

    public static String[] provinces = {"京", "沪", "粤", "津", "冀", "豫", "川", "辽", "鄂", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "黑", "湘", "吉", "桂", "蒙", "陕", "甘", "晋", "贵", "云", "琼", "青", "新", "藏", "宁", "渝"};
    public static Random random = new Random();


    /**
     * 随机生成姓名
     * @return 姓名
     */
    public static String generateRandomChineseName() {
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String secondName = secondNames[random.nextInt(secondNames.length)];
        String thirdName = thirdNames[random.nextInt(secondNames.length)];
        return firstName + secondName + thirdName;
    }


    /**
     * 随机生成省份证号
     * @return 身份证号
     */
    public static String generateRandomChineseID() {

        StringBuilder idBuilder = new StringBuilder();

        // 接下来的 8 位为生日，随机生成一个合理的生日
        String averaCode = averaCodes[random.nextInt(averaCodes.length - 2)];
        int year = 1950 + random.nextInt(50);
        int month = 1 + random.nextInt(12);
        int day = 0;
        if (month == 2) {
            day = 1 + random.nextInt(28); // 简化处理，假设每个月都是28天
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            day = 1 + random.nextInt(31);
        } else {
            day = 1 + random.nextInt(30);
        }

        idBuilder.append(averaCode);
        idBuilder.append(String.format("%02d%02d%02d", year, month, day));

        // 接下来的 3 位为顺序码，随机生成
        idBuilder.append(String.format("%03d", random.nextInt(1000)));

        // 最后一位为校验码，这里简化处理，随机生成一个数字
        idBuilder.append(random.nextInt(10));

        return idBuilder.toString();
    }


    /**
     * 随机生成归属机构代码
     */
    public static String generateComcode() {
        String averaCode = averaCodes[random.nextInt(averaCodes.length - 2)];
        return averaCode;
    }


    /**
     * 随机生成性别
     */
    public static String generateSex() {
        String sex = random.nextInt(2) == 1? "男":"女" ;
        return sex;
    }


    /**
     * 随机生成业务时间
     * @return 时间
     */
    public static String generateRandomData(String format, String day) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar instance = Calendar.getInstance();
        long endMillis = instance.getTimeInMillis();
        instance.set(Integer.parseInt(day.split("-")[0])
                , Integer.parseInt(day.split("-")[1])
                , Integer.parseInt(day.split("-")[2]), 0, 0, 0);
        long startMillis = instance.getTimeInMillis();

        long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
        return simpleDateFormat.format(new Date(randomMillis));
    }



    /**
     * 获取缓冲字符流
     * @param path 待读取的路经
     * @return 缓冲字符流
     */
    public static BufferedReader getInputStream(String path) {
        BufferedReader reader = null;

        if (!new File(path).exists()) {
            System.out.println("文件路径不存在：" + path);
            return null;
        }

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }


    /**
     * 随机生成车架号
     * @return
     */
    public static String generateRandomVIN() {
        // 车架号通常由17位字符组成，包括数字和大写字母
        StringBuilder sb = new StringBuilder();
        String characters = "0123456789ABCDEFGHJKLMNPRSTUVWXYZ"; // 车架号允许的字符集合

        for (int i = 0; i < 17; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }


    /**
     * 随机生成车牌号
     * @return
     */
    public static String generateRandomLicensePlate() {
        // 假设车牌号由一个大写字母和5个数字组成
        String province = provinces[random.nextInt(provinces.length)]; // 随机选择一个省份简称
        StringBuilder licenseNumber = new StringBuilder();
        for (int i = 0; i <= 5; i++) {
            if (i < 2) {
                licenseNumber.append((char) (random.nextInt(26) + 'A')); // 生成随机大写字母
            } else {
                licenseNumber.append(random.nextInt(10)); // 生成随机数字
            }
        }
        return province + licenseNumber.toString();
    }


    /**
     * 随机生成车辆品牌
     * @return
     */
    public static String getRandomChineseCarBrand() {
        String[] chineseCarBrands = {"丰田-110000", "本田-120000", "宝马-250000", "奔驰-270000", "福特-110000", "奥迪-200000", "大众-90000", "日产-90000", "雪佛兰-100000", "特斯拉-190000", "沃尔沃-240000", "斯巴鲁-280000"};
        Random random = new Random();
        int randomIndex = random.nextInt(chineseCarBrands.length);

        double rate = random.nextDouble() * 0.7 + 1;
        String[] split = chineseCarBrands[randomIndex].split("-");
        return split[0] + "-" +(int) (Integer.parseInt(split[1]) * rate);
    }


    /**
     * 生成险类代码
     * @return
     */
    public static String generateClasscode() {
        return classcodes[random.nextInt(classcodes.length)];
    }

}
