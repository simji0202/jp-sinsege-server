package kr.co.paywith.pw.data.repository.mbs.file;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;


	 /**
	  * 정보 등록
	  */
	 @Transactional
	 public File create(File file) {

		  // 데이터베이스 값 갱신
		  File newFile = this.fileRepository.save(file);

		  return newFile;
	 }


	 /**
	  * 정보 갱신
	  */
	 @Transactional
	 public File update(FileUpdateDto fileUpdateDto, File existFile) {

		  // 입력값 대입
		  this.modelMapper.map(fileUpdateDto, existFile);

		  // 데이터베이스 값 갱신
		  this.fileRepository.save(existFile);

		  return existFile;
	 }

}
