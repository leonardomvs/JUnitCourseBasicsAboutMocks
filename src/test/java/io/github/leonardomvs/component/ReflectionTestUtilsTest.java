package io.github.leonardomvs.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.leonardomvs.component.models.CollegeStudent;
import io.github.leonardomvs.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class ReflectionTestUtilsTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	CollegeStudent studentOne;
	
	@Autowired
	StudentGrades studentGrades;
	
	@BeforeEach
	public void studentBeforeEach() {
		
		studentOne.setFirstname("Erick");
		studentOne.setLastname("Roby");
		studentOne.setEmailAddress("erick.roby@luv2code_school.com");
		studentOne.setStudentGrades(studentGrades);
		
		ReflectionTestUtils.setField(studentOne, "id", 1);
		ReflectionTestUtils.setField(studentOne, "studentGrades", 
				new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75))));

	}
	
	@Test
	void getPrivateField() {
		assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
	}
	
	@Test
	void invokePrivateMethod() {
		assertEquals("Erick 1", 
					 ReflectionTestUtils.invokeMethod(studentOne, "getFirstNameAndId"), 
					 "Fail private method not call");
	}
	
}
