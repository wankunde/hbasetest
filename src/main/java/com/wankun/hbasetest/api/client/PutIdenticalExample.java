package com.wankun.hbasetest.api.client;

// cc PutIdenticalExample Example adding an identical column twice
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.wankun.hbasetest.api.util.HBaseHelper;

public class PutIdenticalExample {

  public static void main(String[] args) throws IOException {
    Configuration conf = HBaseConfiguration.create();

    HBaseHelper helper = HBaseHelper.getHelper(conf);
    helper.dropTable("testtable");
    helper.createTable("testtable", "colfam1");
    HTable table = new HTable(conf, "testtable");

    // vv PutIdenticalExample
    Put put = new Put(Bytes.toBytes("row1"));
    put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
      Bytes.toBytes("val2"));
    put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
      Bytes.toBytes("val1")); // co PutIdenticalExample-1-Add Add the same column with a different value. The last value is going to be used.
    table.put(put);

    Get get = new Get(Bytes.toBytes("row1"));
    Result result = table.get(get);
    System.out.println("Result: " + result + ", Value: " + Bytes.toString(
      result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1")))); // co PutIdenticalExample-2-Get Perform a get to verify that "val1" was actually stored.
    // ^^ PutIdenticalExample
  }
}
