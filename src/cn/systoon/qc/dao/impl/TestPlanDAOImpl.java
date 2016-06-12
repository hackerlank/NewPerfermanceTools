package cn.systoon.qc.dao.impl;

import cn.systoon.qc.dao.DAO;
import cn.systoon.qc.dao.TestPlanDAO;
import cn.systoon.qc.domain.TestPlan;

public class TestPlanDAOImpl extends DAO<TestPlan> implements TestPlanDAO {

	@Override
	public void save(TestPlan testPlan) {
		String sql = "INSERT INTO testplan(testplan_name,descript,api_id,testplan_type,parameters,testplan_path,create_time)"
				+ " VALUES (?,?,?,?,?,?,?)";
		update(sql, testPlan.getTestPlanName(), testPlan.getDescript(), testPlan.getApiId(), testPlan.getTestPlanType(),
				testPlan.getParameters(), testPlan.getTestPlanPath(), testPlan.getCreateTime());

	}

	@Override
	public void update(TestPlan testPlan) {
		// TODO Auto-generated method stub

	}

}
