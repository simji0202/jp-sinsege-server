package kr.co.paywith.pw.data.repository.requestsBus;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RequestBusService {

	@Autowired
	RequestBusRepository requestBusRepository;

	@Autowired
	ModelMapper modelMapper;


	@Transactional
	public RequestBus createRequestBus(RequestBusDto requestBusDto) {

		RequestBus requestBus = modelMapper.map(requestBusDto, RequestBus.class);

		RequestBus newRequestBus = this.requestBusRepository.save(requestBus);

		return newRequestBus;
	}

}
