package br.edu.atitus.apisample.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.PointDTO;
import br.edu.atitus.apisample.entities.PointEntity;
import br.edu.atitus.apisample.services.PointService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "Erro de validação ou requisição inválida", content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Dados inválidos!!!"))),
		@ApiResponse(responseCode = "403", description = "Acesso não permitido")
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/ws/point")
public class PointController {
	private final PointService pointService;

	public PointController(PointService pointService) {
		super();
		this.pointService = pointService;
	}
	
	private PointEntity convertDTO2Entity(PointDTO dto) {
		var point = new PointEntity();
		BeanUtils.copyProperties(dto, point);
		return point;
	}
	

	@GetMapping()
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200")})
	protected ResponseEntity<List<PointEntity>> getMethod() throws Exception {
		var lista = pointService.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping()
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PointEntity.class))), })
	public ResponseEntity<PointEntity> signup(@RequestBody PointDTO dto) throws Exception {
		var point = convertDTO2Entity(dto);
		pointService.save(point);
		return ResponseEntity.status(HttpStatus.CREATED).body(point);
	}
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200")})
	public ResponseEntity<String> deleteMethod(@PathVariable UUID id) throws Exception {
		pointService.deleteById(id);
		return ResponseEntity.ok("Ponto removido");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", " ");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
	}
}


