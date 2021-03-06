package com.inmobi.databus.readers;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.inmobi.messaging.consumer.util.HadoopUtil;

public class TestHadoopStreamReader extends TestAbstractDatabusWaitingReader{

  @BeforeTest
  public void setup() throws Exception {
    files = new String[] {HadoopUtil.files[1], HadoopUtil.files[3],
        HadoopUtil.files[5]};
    conf = new Configuration();
    fs = FileSystem.getLocal(conf);
    streamDir = new Path("/tmp/test/hadoop/" + this.getClass().getSimpleName(),
         testStream).makeQualified(fs);
    // initialize config
    HadoopUtil.setupHadoopCluster(conf, files, null, finalFiles, streamDir);
    inputFormatClass = SequenceFileInputFormat.class.getCanonicalName();
    encoded = false;
  }

  @AfterTest
  public void cleanup() throws IOException {
    fs.delete(streamDir.getParent(), true);
  }

  @Test
  public void testInitialize() throws Exception {
    super.testInitialize();
  }

  @Test
  public void testReadFromStart() throws Exception {
    super.testReadFromStart();
  }

  @Test
  public void testReadFromCheckpoint() throws Exception {
    super.testReadFromCheckpoint();
  }

  @Test
  public void testReadFromTimeStamp() throws Exception {
    super.testReadFromTimeStamp();
  }

  @Override
  Path getStreamsDir() {
    return streamDir;
  }

}
