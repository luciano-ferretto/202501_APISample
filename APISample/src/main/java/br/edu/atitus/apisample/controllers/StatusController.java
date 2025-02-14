package br.edu.atitus.apisample.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/status")
public class StatusController {
	
	@Value(value = "${server.port}")
	private String serverPort;
	
	@GetMapping()
	@ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "text/plain"))
	public String getStatus() {
		return "Server is running in port " + serverPort;
	}
	

}
