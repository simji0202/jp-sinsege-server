package kr.co.paywith.pw.data.repository.schedule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	@Autowired
	ModelMapper modelMapper;


	@Transactional
	public Schedule createSchedule(ScheduleDto scheduleDto) {

		Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);

		Schedule newSchedule = this.scheduleRepository.save(schedule);

		return newSchedule;
	}

}
