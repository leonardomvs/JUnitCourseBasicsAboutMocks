package io.github.leonardomvs.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import io.github.leonardomvs.component.dao.ApplicationDao;
import io.github.leonardomvs.component.models.CollegeStudent;
import io.github.leonardomvs.component.models.StudentGrades;
import io.github.leonardomvs.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
class MockAnnotationTest {

	@Autowired
	ApplicationContext context;
	
	@Autowired
	CollegeStudent studentOne;
	
	@Autowired
	StudentGrades studentGrades;
	
	// @Mock
	@MockBean
	private ApplicationDao applicationDao;
	
	// @InjectMocks
	@Autowired
	private ApplicationService applicationService;
	
	@BeforeEach
	public void beforeEach() {
		studentOne.setFirstname("Erick");
		studentOne.setLastname("Roby");
		studentOne.setEmailAddress("erick.roby@luv2code_school.com");
		studentOne.setStudentGrades(studentGrades);
	}
	
	@DisplayName("When & Verify")
	@Test
	void assertEqualsTestAddGrades() {
		
		when(applicationDao.addGradeResultsForSingleClass(
				studentGrades.getMathGradeResults())).thenReturn(100.00);
		
		assertEquals(100, applicationService.addGradeResultsForSingleClass(
				studentOne.getStudentGrades().getMathGradeResults()));
		
		//CHECKS HOW MANY TIMES THE METHOD WAS CALLED
		verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
		
	}
	
	@DisplayName("Find Gpa")
	@Test
	void assertEqualsTestFindGpa() {
		when(applicationDao.findGradePointAverage(
				studentGrades.getMathGradeResults())).thenReturn(88.31);
		
		assertEquals(88.31, applicationService.findGradePointAverage(studentOne
				.getStudentGrades().getMathGradeResults()));
		
	}
	
	@DisplayName("Not Null")
	@Test
	void testAssertNotNull() {

		when(applicationDao.checkNull(
				studentGrades.getMathGradeResults())).thenReturn(true);
		
		assertNotNull(applicationService.checkNull(studentOne.getStudentGrades()
				.getMathGradeResults()), "Object should not be null");
		
	}
	
	@DisplayName("Throw runtime error")
	@Test
	void throwRuntimeError() {
		
		CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");
		
		doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);
		
		assertThrows(RuntimeException.class, () -> {
			applicationService.checkNull(nullStudent);
		});
		
		verify(applicationDao, times(1)).checkNull(nullStudent);
		
	}
	
	@DisplayName("Multiple Stubbing")
	@Test
	void stubbingConsecutiveCalls() {
		
		CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");
		
		when(applicationDao.checkNull(nullStudent))
			.thenThrow(new RuntimeException())
			.thenReturn("Do not throw exception second time");
		
		assertThrows(RuntimeException.class, () -> {
			applicationService.checkNull(nullStudent);
		});
		
		assertEquals("Do not throw exception second time",
				applicationService.checkNull(nullStudent));
		
		verify(applicationDao, times(2)).checkNull(nullStudent);
		
	}
	
}