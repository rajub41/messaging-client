package com.inmobi.messaging.consumer.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.inmobi.messaging.ClientConfig;
import com.inmobi.messaging.Message;
import com.inmobi.messaging.consumer.AbstractMessageConsumer;
import com.inmobi.messaging.consumer.MessageConsumer;
import com.inmobi.messaging.consumer.MessageConsumerFactory;

/**
 * Stdin consumer reads messages from stdin.
 */
public class StdInConsumer extends AbstractMessageConsumer {

  BufferedReader in;
  String topicName;

  protected void init(ClientConfig config) {
	  super.init(config);
	  topicName = getConfig().getString("stdin.topic");
	  start();
  }

  @Override
  public Message next() {
    try {
      String str = in.readLine();
      if (str != null) {
        return new Message(topicName, str.getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void commit() {
    // nothing to commit
  }

  @Override
  public void rollback() {
    // nothing to rollback
  }

  @Override
  public void close() {
    try {
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void start() {
    in = new BufferedReader(new InputStreamReader(System.in));
  }

  public static void main(String[] args) throws Exception {
    ClientConfig config = new ClientConfig();
    config.set("stdin.topic", "mytopic");
    config.set(MessageConsumerFactory.CONSUMER_CLASS_NAME_KEY,
      		  StdInConsumer.class.getName());
    MessageConsumer consumer = MessageConsumerFactory.create(config );
      
    try {
      while (true) {
        Message msg = consumer.next();
        System.out.println("Topic:" + msg.getTopic());
        System.out.println("Message:" + msg.getData());
      }
    }  finally {
      consumer.close();
    }
  }
}