package org.greenwave.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.DataCategoryBusiness;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.model.DataCategory;
import org.greenwave.model.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {
	
	@Autowired
	DataMeasureBusiness dataMeasuresBusiness;
	
	@Autowired
	DataBusiness dataBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;
	
	@Autowired
	LocalizationBusiness localizationBusiness;
	
	@Autowired
	DataCategoryBusiness dataCategoryBusiness;
	
	public double[] percentageCalculator(double[] value){
		double[] percentage = new double[3];
		double sum = value[0]+value[1]+value[2];
		percentage[0]=(value[0]/sum)*100;
		percentage[1]=(value[1]/sum)*100;
		percentage[2]=(value[2]/sum)*100;
		return percentage;
	}
	
	
	//function tauxCalculator (for rate charts) : calculate the number of data in each states (OK, Acceptable, Dangerous)
	//Put these number in a tab use by percentageCalculator
	public double[] tauxCalculator(Date start, Date end, DataCategory waterType,String theData){
		Double acceptableRatio = null;
		Double dangerousRatio = null;
		if(waterType.getName().equals("SURFACE_WATER_DATA")){
			acceptableRatio = dataMeasuresBusiness.findRatioASW(theData);
			dangerousRatio = dataMeasuresBusiness.findRatioDSW(theData);
		}
		if(waterType.getName().equals("OTHER_SURFACE_WATER_DATA")){
			acceptableRatio = dataMeasuresBusiness.findRatioOASW(theData);
			dangerousRatio = dataMeasuresBusiness.findRatioODSW(theData);
		}
		int nbOK=dataBusiness.countOKData(theData, acceptableRatio, start, end, waterType);
		int nbDangerous=dataBusiness.countDangerousData(theData, dangerousRatio, start, end,waterType);
		int nbAcceptable=dataBusiness.countAcceptableData(theData, acceptableRatio, dangerousRatio, start, end,waterType);
		double[] repartition =new double[3];
		repartition[0]=(double)nbOK;
		repartition[1]=(double)nbAcceptable;
		repartition[2]=(double)nbDangerous;
		return repartition;
	}
	
	
	//Function avgCalculator : get the average value of the data for each month between start and end dates and put it in an array
	//There are 3 steps :
	// 1st : calculate average value for the first month so between start date and the last day of the month
	// 2nd : loop between the 1st day of next month and the last days of the month before the end one
	// 3rd : calculate average value for the last month so between the first day of month and the end date
	@SuppressWarnings("deprecation")
	public double[] avgCalculator(Date start, Date end, DataCategory waterType, String theData,String[]label, List<Localization> ids){
		System.out.println("AVG "+label.length);
		double[] avgValue=new double[label.length];
		List<Double> value= new ArrayList<Double>();
		double interVal = 0;
		if(label.length==1){
			value = dataBusiness.avgCalcul(theData, waterType, start, end,ids);
			for(int i=0;i<value.size();i++){
				interVal=interVal+value.get(i);
			}
			avgValue[0]=interVal/value.size();
		}
		else {
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		
		interVal = 0;
		value = dataBusiness.avgCalcul(theData, waterType, start, lastDayOfMonth,ids);
		for(int i=0;i<value.size();i++){
			interVal=interVal+value.get(i);
		}
		avgValue[0]=interVal/value.size();
		if(value.size()==0){
			avgValue[0]=0;
		}
		
		Date firstDayOfMonth; 	
		Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(start);
        finishCalendar.setTime(end);
		beginCalendar.add(Calendar.MONTH, 1);
		int k=1;
		finishCalendar.add(Calendar.MONTH, -1);
		finishCalendar.set(Calendar.DAY_OF_MONTH, finishCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		 while (beginCalendar.before(finishCalendar)) {
			 	c.setTime(beginCalendar.getTime());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			 	lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 	firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
			 	interVal = 0;
				value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
				for(int i=0;i<value.size();i++){
					interVal=interVal+value.get(i);
				}
				avgValue[k]=interVal/value.size();
				if(value.size()==0){
					avgValue[k]=0;
				}
				k++;
	            beginCalendar.add(Calendar.MONTH, 1);
	       }
		 c.setTime(end);
		 lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
		 interVal = 0;
		 value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
			for(int i=0;i<value.size();i++){
				interVal=interVal+value.get(i);
			}
			avgValue[k]=interVal/value.size();
			if(value.size()==0){
				avgValue[k]=0;
			}
			
		}
		return avgValue;
	}
	
	//function minCalculator : get the minimum value in the DB entries for a data type for each month
	//Steps are same as the avgCalculator function
	public double[] minCalculator(Date start, Date end, DataCategory waterType, String theData,String[]label, List<Localization> ids){
		System.out.println("MIN "+label.length);
		double[] minValue=new double[label.length];
		List<Double> value= new ArrayList<Double>();
		
		if(label.length==1){
			value=dataBusiness.avgCalcul(theData, waterType, start, end,ids);
			minValue[0]=value.get(value.indexOf(Collections.min(value)));
		}
		else {
		
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		value = dataBusiness.avgCalcul(theData, waterType, start, lastDayOfMonth,ids);		
		if(value.size()==0){
			minValue[0]=0;
		}
		else {
			minValue[0]=value.get(value.indexOf(Collections.min(value)));
		}
		Date firstDayOfMonth; 		
		Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(start);
        finishCalendar.setTime(end);
		beginCalendar.add(Calendar.MONTH, 1);
		int k=1;
		finishCalendar.add(Calendar.MONTH, -1);
		finishCalendar.set(Calendar.DAY_OF_MONTH, finishCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		 while (beginCalendar.before(finishCalendar)) {
			 	c.setTime(beginCalendar.getTime());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			 	lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 	firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
				value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
				if(value.size()==0){
					minValue[k]=0;
				}
				else {
					minValue[k]=value.get(value.indexOf(Collections.min(value)));
				}
				k++;
	            beginCalendar.add(Calendar.MONTH, 1);
	       }
		 c.setTime(end);
		 lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
		 value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
		 if(value.size()==0){
				minValue[k]=0;
			}
			else {
				minValue[k]=value.get(value.indexOf(Collections.min(value)));
			}
		}
		return minValue;
	}
	
	//function minCalculator : get the maximum value in the DB entries for a data type for each month
	//Steps are same as the avgCalculator function
	public double[] maxCalculator(Date start, Date end, DataCategory waterType, String theData,String[]label, List<Localization> ids){
		double[] maxValue=new double[label.length];
		List<Double> value= new ArrayList<Double>();
		System.out.println("MAX "+label.length);
		if(label.length==1){
			value=dataBusiness.avgCalcul(theData, waterType, start, end,ids);
			maxValue[0]=value.get(value.indexOf(Collections.max(value)));
		}
		else {
		
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		value = dataBusiness.avgCalcul(theData, waterType, start, lastDayOfMonth,ids);
		if(value.size()==0){
			maxValue[0]=0;
		}
		else {
			maxValue[0]=value.get(value.indexOf(Collections.max(value)));
		}
		Date firstDayOfMonth; 		
		Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(start);
        finishCalendar.setTime(end);
		beginCalendar.add(Calendar.MONTH, 1);
		int k=1;
		finishCalendar.add(Calendar.MONTH, -1);
		finishCalendar.set(Calendar.DAY_OF_MONTH, finishCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		 while (beginCalendar.before(finishCalendar)) {
			 	c.setTime(beginCalendar.getTime());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			 	lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 	firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
				value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
				if(value.size()==0){
					maxValue[k]=0;
				}
				else {
					maxValue[k]=value.get(value.indexOf(Collections.max(value)));
				}
				k++;
	            beginCalendar.add(Calendar.MONTH, 1);
	       }
		 c.setTime(end);
		 lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
		 value = dataBusiness.avgCalcul(theData, waterType, firstDayOfMonth, lastDayOfMonth,ids);
		 if(value.size()==0){
				maxValue[k]=0;
			}
			else {
				maxValue[k]=value.get(value.indexOf(Collections.max(value)));
			}
		}
		return maxValue;
	}
	
	//function labalForChartsGenerator : dynamicaly construct the array of label for x-axis in the charts by adding all combinaison of month and year between start and end date
	public String[] labelForChartsGenerator(Date start, Date end){
		//Initialization
		//Date object are depreciated so we use Calendar
		Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(start);
        finishCalendar.setTime(end);
        ArrayList<String> labels=new ArrayList<String>();
        DateFormat formater = new SimpleDateFormat("MMMM yyyy");//formater to convert date object to a string in a specific format
        labels.add(formater.format(beginCalendar.getTime()).toUpperCase());//we had the first month in the list
        beginCalendar.add(Calendar.MONTH, 1);//go to newt month
        finishCalendar.set(Calendar.DAY_OF_MONTH, finishCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));//set day of finishCalendar to the last day of month to avoid problem (if start day is greater than end we go to the next month before the loop ended)
        while (beginCalendar.before(finishCalendar)) {
            // add one month to date per loop and put it in the list
            String date = formater.format(beginCalendar.getTime()).toUpperCase();
            labels.add(date);
            beginCalendar.add(Calendar.MONTH, 1);
        }
        //convert list to array
        String[] labelTab = new String[labels.size()];
        labels.toArray(labelTab);
		return labelTab;
		
	}
	//monthNumberCalculator : return the number of month between start and end date even if in different year
	public int monthNumberCalculator(Date start, Date end){
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(start);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(end);
		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return diffMonth;
	}


	public Integer[] countDatasperMonth(Date startDate, Date endDate, DataCategory dataCat, String theData, String[] label,
			List<Localization> idsSelected) {
		// TODO Auto-generated method stub
		
		List<Integer> avgValue= new ArrayList<Integer>();
		double interVal = 0;
		if(label.length==1){

		}
		else {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		java.sql.Date lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		
		Date firstDayOfMonth; 	
		Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);
        finishCalendar.setTime(endDate);
		beginCalendar.add(Calendar.MONTH, 0);
		int k=1;
		finishCalendar.add(Calendar.MONTH, 0);
		finishCalendar.set(Calendar.DAY_OF_MONTH, finishCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		 while (beginCalendar.before(finishCalendar)) {
			 	c.setTime(beginCalendar.getTime());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			 	lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
			 	c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
			 	firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
			 
				avgValue.add(dataBusiness.countData(theData, firstDayOfMonth, lastDayOfMonth,dataCat,idsSelected));
				k++;
	            beginCalendar.add(Calendar.MONTH, 1);
	       }
		 c.setTime(endDate);
		 lastDayOfMonth = new java.sql.Date(c.getTimeInMillis());
		 c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		 firstDayOfMonth = new java.sql.Date(c.getTimeInMillis()); 
		}
		return avgValue.toArray(new Integer[avgValue.size()]);
	}

}
