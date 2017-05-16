package com.deviceinsight.services.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Main;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
/**
 * Created by tmichels on 9/3/15.
 */

@Configuration
public class KafkaCamelRoute {
    String topicName = "topic=test";
    String kafkaServer = "kafka:localhost:9092";
    String zooKeeperHost = "";
    String serializerClass = "serializerClass=kafka.serializer.StringEncoder";
    String autoOffsetOption = "autoOffsetReset=smallest";
    String groupId = "groupId=testingvinod";
//.append(zooKeeperHost)
    String toKafka = new StringBuilder().append(kafkaServer).append("?").append(
            topicName).append("&brokers=localhost:9092").toString();/*.append("&").append(
            serializerClass).toString();*/

    String fromKafka = new StringBuilder().append(toKafka).append("&").append(
            autoOffsetOption).append("&").append(groupId).toString();

    @Bean(name = "KafkaRouteProducer")
    public RouteBuilder kafkaRouteProducer() {
        return new RouteBuilder() {
            public void configure() {
               /* from("jetty:http://localhost:8182/mytestservice").process(
                        new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String message = exchange.getIn().getBody(String.class);
                                exchange.getIn().setBody(message, String.class);
                                exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY,
                                        0);
                                exchange.getIn().setHeader(KafkaConstants.KEY, "1");
                            }
                        }).to(toKafka);*/

         /*       from("direct:start")
                        .setHeader(Exchange.HTTP_METHOD,simple("GET"))
                        .to("https://www.next-cloud.de");
*/



                from("direct:start").process(
                        new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                //String message = exchange.getIn().getBody(String.class);
                                exchange.getIn().setBody("test", String.class);
                                exchange.getIn().setHeader(KafkaConstants.PARTITION_KEY,
                                        0);
                                exchange.getIn().setHeader(KafkaConstants.KEY, "1");
                            }
                        }).to(toKafka);

            }
        };
    }

/*    @Override
    public void configure() throws Exception {
                from("direct:kafkaRoute")
                        .to("kafka:localhost:9092?topic=test&groupId=testing&autoOffsetReset=earliest&consumersCount=1")
                        .bean(KafkaOutputBean.class);
            }
*/
   /* @Bean(name = "KafkaRouteConsumer")
    public RouteBuilder kafkaRouteConsumer() {
        return new RouteBuilder() {
            public void configure() {
                from(fromKafka).process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        if (exchange.getIn() != null) {
                            Message message = exchange.getIn();
                            String data = message.getBody(String.class);
                            System.out.println("Data =" + data.toString());
                        }
                    }
                });
            }
        };
    }*/

    public static class kafkaOutputBean {
        public void printKafkaBody(String body) {
            System.out.println("KafkaBody result >>>>> " + body);
        }
    }
}