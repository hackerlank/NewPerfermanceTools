package cn.systoon.qc.test;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import cn.systoon.qc.dao.impl.TestPlanDAOImpl;
import cn.systoon.qc.domain.TestPlan;

public class TestPlanDAOImplTest {

	@Test
	public void testSave() {
		//, , 1, 1, 1, , , Mon Aug 22 11:45:06 CST 2016
		String jmxPlanName = "TestPlan_10Vuser_1471837506593.jmx";
		String testPlanDesc = null;
		Integer apiId = 1;
		Integer jmxPlanType = 1;
		Integer paramTypeInt = 1;
		String parameters = "{\"timestamp\":\"1469427450207\",\"id\":\"1\",\"signature\":\"ec93edebb7c28debcd76d41040219ed6\",\"appKey\":\"testApp\"}";
		String jmxPlan = "/Library/WebServer/Documents/script/TestPlan_10Vuser_1471837506593.jmx";
		Date createTime = new Date(System.currentTimeMillis());
		TestPlan testPlan = new TestPlan(jmxPlanName, testPlanDesc, apiId, jmxPlanType, paramTypeInt,parameters, jmxPlan, createTime);
		new TestPlanDAOImpl().save(testPlan);
	}

	@Test
	public void testUpdateTestPlan() {
		fail("Not yet implemented");
	}

}

