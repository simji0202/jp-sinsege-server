package kr.co.paywith.pw.data.repository.busCompany;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BusCompanyService {

	@Autowired
	BusCompanyRepository busCompanyRepository;

	@Autowired
	ModelMapper modelMapper;


	@Transactional
	public BusCompany createBusCompany(BusCompanyDto busCompanyDto) {

		BusCompany busCompany = modelMapper.map(busCompanyDto, BusCompany.class);

		BusCompany newBusCompany = this.busCompanyRepository.save(busCompany);

		return newBusCompany;
	}

}
