package com.gomezrondon.gcpexamgenerator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.shell.result.DefaultResultHandler;

import java.util.List;
import java.util.Map;

import org.springframework.shell.MethodTarget;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ApplicationTests {

	@Autowired
	private Shell shell;

	@Test
	void test() {
		Map<String, MethodTarget> commands = shell.listCommands();
		System.out.println(commands);
	}

}
