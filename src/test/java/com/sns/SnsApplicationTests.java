package com.sns;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
//@SpringBootTest
class SnsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void 비어있는지확인() {
		List<Integer> list = new ArrayList<>();
		if (ObjectUtils.isEmpty(list)) {
			log.info("list is empty");
		}
		
		String str = null;
		if (ObjectUtils.isEmpty(str)) {
			log.info("str is empty******");
		}
	}

}
