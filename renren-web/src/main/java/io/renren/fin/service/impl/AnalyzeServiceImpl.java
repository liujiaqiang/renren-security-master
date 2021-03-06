package io.renren.fin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.fin.dao.AnalyzeDao;
import io.renren.fin.entity.AnalyzeEntity;
import io.renren.fin.service.AnalyzeService;



@Service("analyzeService")
public class AnalyzeServiceImpl implements AnalyzeService {
	@Autowired
	private AnalyzeDao<AnalyzeEntity> analyzeDao;

	@Override
	public List<AnalyzeEntity> analyzeList(Map<String, Object> map) {
		List<AnalyzeEntity> lists =analyzeDao.analyzeList(map);
		float totalAmount=0;
		for(int i=0;i<lists.size();i++){
			totalAmount +=lists.get(i).getTypeAmount();
		}
		for(int i=0;i<lists.size();i++){
			float temp=(float)Math.round(lists.get(i).getTypeAmount()/totalAmount*10000)/100;
			lists.get(i).setPercent(temp);
		}
		return lists;
	}

	@Override
	public int myQueryTotal(Map<String, Object> map) {
		
		return analyzeDao.myQueryTotal(map);
	}

	@Override
	public AnalyzeEntity getProfit(Map<String, Object> map) {
		
		Float totalIn = analyzeDao.getTotalIn(map);
		Float totalOut = analyzeDao.getTotalOut(map);
		
		if (totalIn==null) {
			totalIn=0F;
		}
		if (totalOut==null) {
			totalOut=0F;
		}
		
		Float profit=totalIn-totalOut;
		
		AnalyzeEntity analyze=new AnalyzeEntity();
		analyze.setTotalIn(totalIn);
		analyze.setTotalOut(totalOut);
		analyze.setProfit(profit);
		
		return analyze;
	}
	
	
}
