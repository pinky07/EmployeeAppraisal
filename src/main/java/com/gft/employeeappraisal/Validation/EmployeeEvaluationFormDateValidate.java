package com.gft.employeeappraisal.Validation;

public class EmployeeEvaluationFormDateValidate
{
	public static void validateDate(int dates){
	if(dates>1)
	{
		throw new IllegalArgumentException("Submit date can not be prior to the Created date");
	}
	}

	public static void validateDate(boolean isSubmitDateEqualToCreateDate){
		if(!isSubmitDateEqualToCreateDate){
			throw new IllegalArgumentException("Submit date can not be prior to the Created date");
		}
	}
}
