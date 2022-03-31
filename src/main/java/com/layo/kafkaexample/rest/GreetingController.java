package com.layo.kafkaexample.rest;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.layo.kafkaexample.Producer;
import com.layo.kafkaexample.model.Greeting;
import com.layo.kafkaexample.model.GreetingForm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class GreetingController {

	// inited from https://spring.io/guides/gs/handling-form-submission/

	int count = 0;
	@Autowired
	private final Producer producer = null;

	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		model.addAttribute("greeting", new Greeting(count++, new Faker().name().fullName()));
		model.addAttribute("greetingForm", new GreetingForm());
		// populateTopicsDropDown(model);
		// This must map to the template, e.g. greeting.html
		return "greeting";
	}

	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting, 
								@ModelAttribute GreetingForm greetingForm, 
								Model model) {
		model.addAttribute("greeting", new Greeting(count++, new Faker().name().fullName()));
		model.addAttribute("sentGreeting", greeting);
		model.addAttribute("greetingForm", greetingForm);
		// populateTopicsDropDown(model);
        sendMessage(greeting, greetingForm);
		// Maps to result.html
		//return "result";
		// not good having 2 pages, make greeting work for both
		return "greeting";
	}

	// public void populateTopicsDropDown(Model model) {
	// 	List<String> options = new ArrayList<String>();
	// 	options.add("GREETING_DATA");
	// 	options.add("transaction-1");
	// 	model.addAttribute("topics", options);
	// }

	private void sendMessage(Greeting greeting, GreetingForm form) {
        try {
            greeting.setTimeSent(LocalDateTime.now());

            String msg = new Gson().toJson(greeting);
            //ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendMessage("GREETING_DATA", "IN_KEY", LocalDate.now().toString());
			SendResult result;
			if (form.getTopic().equalsIgnoreCase("transaction-1")) {
				ListenableFuture<SendResult<String, Object>> listenableFuture = this.producer.sendJsonMessage(form.getTopic(), "IN_KEY", greeting);
				result = listenableFuture.get();
			} else {
				ListenableFuture<SendResult<String, String>> listenableFuture = this.producer.sendStringMessage(form.getTopic(), "IN_KEY", msg);
				result = listenableFuture.get();
			}
            log.info(String.format("Produced:\ntopic: %s\noffset: %d\npartition: %d\nvalue size: %d", result.getRecordMetadata().topic(),
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition(), result.getRecordMetadata().serializedValueSize()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
