package cn.systoon.qc.dao.impl;

import java.util.List;

import cn.systoon.qc.dao.DAO;
import cn.systoon.qc.dao.TestPlanDAO;
import cn.systoon.qc.domain.TestPlan;

public class TestPlanDAOImpl extends DAO<TestPlan> implements TestPlanDAO {

	@Override
	public void save(TestPlan testPlan) {
		String sql = "INSERT INTO testplan(testplan_name,descript,api_id,jmxPlanType,paramType,parameters,jmxPlan,create_time)"
				+ " VALUES (?,?,?,?,?,?,?,?)";
		update(sql, testPlan.getTestPlanName(), testPlan.getDescript(), testPlan.getApiId(), testPlan.getJmxPlanType(),
				testPlan.getParamType(),testPlan.getParameters(), testPlan.getJmxPlan(), testPlan.getCreateTime());

	}

	@Override
	public void update(TestPlan testPlan) {
		// TODO Auto-generated method stub

	}
	
	public List<TestPlan> getListWithApiId(String apiId) {
		 String sql = "SELECT id,testplan_name testPlanName from testplan where api_id = ?";
		return getForList(sql, apiId);
	}
	
	public TestPlan getDescWithPlanId(String id) {
		String sql = "SELECT descript from testplan where id = ?";
		return get(sql, id);
	}

}
