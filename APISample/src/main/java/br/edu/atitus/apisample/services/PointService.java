package br.edu.atitus.apisample.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.PointEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.PointRepository;
import jakarta.transaction.Transactional;

@Service
public class PointService {

	private final PointRepository pointRepository;

	public PointService(PointRepository pointRepository) {
		super();
		this.pointRepository = pointRepository;
	}
	
	@Transactional
	public PointEntity save(PointEntity point) throws Exception {
		if (point == null)
			throw new Exception("Objeto nulo");
		validate(point);
		format(point);
		return pointRepository.save(point);
	}
	
	@Transactional
	public void deleteById(UUID id) throws Exception {
		var point = pointRepository.findById(id).orElseThrow(() -> new Exception("Ponto não encontrado com este id"));
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!point.getUser().getId().equals(user.getId()))
			throw new Exception("Este ponto pernte a outro usuário");
		pointRepository.deleteById(id);
	}
	
	public List<PointEntity> findAll() {
		return pointRepository.findAll();
	}
	
	private void validate(PointEntity point) throws Exception {
		if (point.getDescricao() == null || point.getDescricao().isEmpty())
			throw new Exception("Descrição informada inválida");
		if (point.getLatitude() < -90 || point.getLatitude() > 90)
			throw new Exception("Latitude informada inválida");
		if (point.getLongitude() < -180 || point.getLongitude() > 180)
			throw new Exception("Longitude informada inválida");
	}

	private void format(PointEntity point)  throws Exception {
		var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		point.setUser((UserEntity) user);
	}
}
