package hdfs.fileOperator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author: Allen
 * @Project: bigdata_demo
 * @Name: HdfsFileOperator
 * @Date: 2024年01月17日 0017 14:26:42
 */
public class HdfsFileOperator {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        FileSystem fs = null;

        try {
            fs = FileSystem.get(new URI("hdfs:ns"), configuration, "root");

            fs.mkdirs(new Path("/tmp2"));
            // 本地文件上传到hdfs
//            fs.copyFromLocalFile(false, new Path("D:\\study\\IdeaFile\\bigdata_demo\\hadoop_demo\\src\\main\\resources\\core-site.xml"), new Path("/tmp2/"));
            // 使用IO流将本地文件上传到hdfs

            fs.delete(new Path("/tmp2/core-site.xml"), false);

            FileInputStream fileInputStream = new FileInputStream("D:\\\\study\\\\IdeaFile\\\\bigdata_demo\\\\hadoop_demo\\\\src\\\\main\\\\resources\\\\core-site.xml");
            FSDataOutputStream fsDataOutputStream = fs.create(new Path("/tmp2/core-site.xml"));
            IOUtils.copyBytes(fileInputStream, fsDataOutputStream, configuration);
            IOUtils.closeStream(fileInputStream);
            IOUtils.closeStream(fsDataOutputStream);


//            RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fs.listFiles(new Path("/"), true);
//            while (locatedFileStatusRemoteIterator.hasNext()) {
//                System.out.println(locatedFileStatusRemoteIterator.next().getPath());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
