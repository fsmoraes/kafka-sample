package com.example.kafkasample

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class SampleController {

    private val logger: Logger = LoggerFactory.getLogger(SampleController::class.java)

    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, String>? = null

    @PostMapping("producer")
    fun producer() {
        kafkaTemplate!!.send("kafka.sample", "hello world with Kafka")
    }

    @KafkaListener(topics = ["kafka.sample"], groupId = "group_id")
    fun consumer(message: String?) {
        logger.info("==== $message ====")
    }
}
