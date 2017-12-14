package com.gft.employeeappraisal.converter.scorevalue;

import com.gft.employeeappraisal.model.ScoreValue;
import com.gft.employeeappraisal.service.EmployeeEvaluationFormAnswerService;
import com.gft.swagger.employees.model.ScoreValueDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Defines a mapping structure to be used by {@link ScoreValueDTOConverter}.
 *
 * @author Rubén Jiménez
 */
@Component
public class ScoreValueDTOMapper extends CustomMapper<ScoreValue, ScoreValueDTO> {

private static EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService;
@Autowired
public ScoreValueDTOMapper(EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService){
    this.employeeEvaluationFormAnswerService =employeeEvaluationFormAnswerService;
}
    @Override
    public void mapAtoB(ScoreValue scoreValue, ScoreValueDTO scoreValueDTO, MappingContext context) {
        scoreValueDTO.setId(scoreValue.getId());
        scoreValueDTO.setValue(scoreValue.getValue());
        scoreValueDTO.setDescription(scoreValue.getDescription());
    }

    @Override
    public void mapBtoA(ScoreValueDTO scoreValueDTO, ScoreValue scoreValue, MappingContext context) {
        // TODO Implement this method!
        scoreValue.setValue(scoreValueDTO.getValue());
        scoreValue.setDescription(scoreValueDTO.getDescription());
        scoreValue.setId(scoreValueDTO.getId());
//        scoreValue.setEmployeeEvaluationFormAnswerSet(employeeEvaluationFormAnswerService.getById(scoreValueDTO.getId()).);
//        throw new NotImplementedException();
    }
}
