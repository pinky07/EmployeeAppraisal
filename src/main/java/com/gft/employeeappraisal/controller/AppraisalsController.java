package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.converter.evaluationformtemplate.EvaluationFormTemplateDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.*;
import com.gft.employeeappraisal.repository.*;
import com.gft.employeeappraisal.service.*;
import com.gft.swagger.employees.api.AppraisalApi;
import com.gft.swagger.employees.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppraisalsController implements AppraisalApi {

	private final Logger logger = LoggerFactory.getLogger(AppraisalsController.class);

	// Services
	private final AppraisalService appraisalService;
	private final EmployeeService employeeService;
	private final EmployeeEvaluationFormService employeeEvaluationFormService;
	private final EvaluationFormTemplateService evaluationFormTemplateService;
	private final SecurityService securityService;
	private final ScoreValueService scoreValueService;
	private final ScoreValueRepository scoreValueRepository;
	private final AppraisalXEvaluationFormTemplateService appraisalXEvaluationFormTemplateService;
	private final AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository;
	private final EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository;
	private final SectionRepository sectionRepository;
	private final ScoreTypeRepository scoreTypeRepository;
	// DTO converters
	private final AppraisalDTOConverter appraisalDTOConverter;
	private final EmployeeEvaluationFormDTOConverter employeeEvaluationFormDTOConverter;
	private final EvaluationFormTemplateDTOConverter evaluationFormTemplateDTOConverter;


	@Autowired
	public AppraisalsController(
			AppraisalService appraisalService,
			EmployeeService employeeService,
			EmployeeEvaluationFormService employeeEvaluationFormService,
			EvaluationFormTemplateService evaluationFormTemplateService,
			SecurityService securityService,
			AppraisalDTOConverter appraisalDTOConverter,
			EmployeeEvaluationFormDTOConverter employeeEvaluationFormDTOConverter,
			EvaluationFormTemplateDTOConverter evaluationFormTemplateDTOConverter,
			ScoreValueService scoreValueService,
			AppraisalXEvaluationFormTemplateService appraisalXEvaluationFormTemplateService,
			EvaluationFormTemplateXSectionXQuestionService evaluationFormTemplateXSectionXQuestionService,
			EmployeeEvaluationFormAnswerService employeeEvaluationFormAnswerService,
			ScoreValueRepository scoreValueRepository,
			AppraisalXEvaluationFormTemplateRepository appraisalXEvaluationFormTemplateRepository,
			EvaluationFormTemplateXSectionXQuestionRepository evaluationFormTemplateXSectionXQuestionRepository,
			EmployeeEvaluationFormAnswerRepository employeeEvaluationFormAnswerRepository,
			SectionRepository sectionRepository,
			ScoreTypeRepository scoreTypeRepository) {
		this.appraisalService = appraisalService;
		this.employeeService = employeeService;
		this.employeeEvaluationFormService = employeeEvaluationFormService;
		this.evaluationFormTemplateService = evaluationFormTemplateService;
		this.securityService = securityService;
		this.appraisalDTOConverter = appraisalDTOConverter;
		this.employeeEvaluationFormDTOConverter = employeeEvaluationFormDTOConverter;
		this.evaluationFormTemplateDTOConverter = evaluationFormTemplateDTOConverter;
		this.scoreValueService = scoreValueService;
		this.appraisalXEvaluationFormTemplateService = appraisalXEvaluationFormTemplateService;
		this.evaluationFormTemplateXSectionXQuestionRepository = evaluationFormTemplateXSectionXQuestionRepository;
		this.scoreValueRepository = scoreValueRepository;
		this.appraisalXEvaluationFormTemplateRepository = appraisalXEvaluationFormTemplateRepository;
		this.sectionRepository = sectionRepository;
		this.scoreTypeRepository = scoreTypeRepository;

	}

	@Override
	public ResponseEntity<EmployeeEvaluationFormDTO> employeesIdAppraisalsIdFormsPut(
			@PathVariable Integer employeeId,
			@PathVariable Integer appraisalId,
			@RequestBody EmployeeEvaluationFormDTO evaluationFormBody) {
		EmployeeEvaluationForm employeeEvaluationForm = new EmployeeEvaluationForm();
		HttpStatus httpStatus;
		EmployeeEvaluationFormDTO response = new EmployeeEvaluationFormDTO();

		// TODO: create and add a validator

		// TODO: find employee

		// TODO: find appraisal

		// TODO: find the employee evaluation form to update, by id. if it has a submit date, throw an error


		if (evaluationFormBody.getSubmitDate() == null) {
			//call here saveAndContinue()
		} else {// call here saveAndSubmit()
			employeeEvaluationForm = employeeEvaluationFormDTOConverter.convertBack(evaluationFormBody);
			// TODO add validation: the submit date cannot be prior to the create date and cannot be superior to
			// OffsetDateTime.now()
			OffsetDateTime submitday = OffsetDateTime.now();
			employeeEvaluationForm.setSubmitDate(submitday);
			employeeEvaluationFormService.saveAndFlush(employeeEvaluationForm);
		}
		return new ResponseEntity<EmployeeEvaluationFormDTO>(HttpStatus.OK);
	}


	//here need to set scorevalue and comment
	@Override
	public ResponseEntity<EvaluationFormTemplateDTO> employeesIdAppraisalsIdFormsIdPut(
			@PathVariable("employeeId") Integer employeeId,
			@PathVariable("appraisalId") Integer appraisalId,
			@PathVariable("formId") Integer formId,
			@Valid @RequestBody EvaluationFormTemplateDTO evaluationFormBody) {
		List<SectionDTO> sectionDTOS = evaluationFormBody.getSections();
		for (SectionDTO sectionDTO : sectionDTOS) {
			List<ScoreValueDTO> scoreValueDTOS = sectionDTO.getScoreType().getScoreValues();
			for (ScoreValueDTO scoreValueDTO : scoreValueDTOS) {
				ScoreValue scoreValue = new ScoreValue();
				scoreValue.setValue(scoreValueDTO.getValue());
				System.out.println("name=" + scoreValue.getValue());
				scoreValueRepository.save(scoreValue);
			}

		}
		// do some magic!
		return new ResponseEntity<EvaluationFormTemplateDTO>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<AppraisalDTO>> employeesIdAppraisalsGet(
			@PathVariable Integer employeeId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /employees/{}/appraisals", user.getEmail(), employeeId);
		return this.employeesIdAppraisalsGet(user, user.getId());
	}

	@Override
	public ResponseEntity<List<EmployeeEvaluationFormDTO>> employeesIdAppraisalsIdFormsGet(
			@PathVariable("employeeId") Integer employeeId,
			@PathVariable("appraisalId") Integer appraisalId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}/forms", user.getEmail(), employeeId, appraisalId);
		return this.employeesIdAppraisalsIdFormsGet(user, employeeId, appraisalId);
	}


	@Override
	public ResponseEntity<EvaluationFormTemplateDTO> employeesIdAppraisalsIdFormsIdGet(
			@PathVariable("employeeId") Integer employeeId,
			@PathVariable("appraisalId") Integer appraisalId,
			@PathVariable("formId") Integer formId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}/forms/{}", user.getEmail(), employeeId,
				appraisalId, formId);
		return this.employeesIdAppraisalsIdFormsIdGet(user, employeeId, appraisalId, formId);
	}


	@Override
	public ResponseEntity<AppraisalDTO> employeesIdAppraisalsIdGet(
			@PathVariable Integer employeeId,
			@PathVariable Integer appraisalId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}", user.getEmail(), employeeId, appraisalId);
		return employeesIdAppraisalsIdGet(user, employeeId, appraisalId);
	}


	@Override
	public ResponseEntity<List<AppraisalDTO>> meAppraisalsGet() {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /me/appraisals", user.getEmail());
		return this.employeesIdAppraisalsGet(user, user.getId());
	}


	@Override
	public ResponseEntity<List<EmployeeEvaluationFormDTO>> meAppraisalsIdFormsGet(
			@PathVariable("appraisalId") Integer appraisalId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /me/appraisals/{}/forms", user.getEmail(), appraisalId);
		return this.employeesIdAppraisalsIdFormsGet(user, user.getId(), appraisalId);
	}

	@Override
	public ResponseEntity<EvaluationFormTemplateDTO> meAppraisalsIdFormsIdGet(
			@PathVariable("appraisalId") Integer appraisalId,
			@PathVariable("formId") Integer formId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /me/appraisals/{}/forms/{}", user.getEmail(), appraisalId, formId);
		return this.employeesIdAppraisalsIdFormsIdGet(user, user.getId(), appraisalId, formId);
	}

	@Override
	public ResponseEntity<AppraisalDTO> meAppraisalsIdGet(
			@PathVariable Integer appraisalId) {
		// Get logged in user
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /me/appraisals/{}", user.getEmail(), appraisalId);
		return employeesIdAppraisalsIdGet(user, user.getId(), appraisalId);
	}


	private ResponseEntity<List<AppraisalDTO>> employeesIdAppraisalsGet(
			Employee loggedInUser,
			int employeeId) {

		// Get Employee
		Employee employee = employeeService.getById(employeeId);

		// Get Appraisals
		List<Appraisal> appraisalList = this.employeeEvaluationFormService
				.findSelfByEmployee(employee)
				.map(EmployeeEvaluationForm::getAppraisalXEvaluationFormTemplate)
				.map(AppraisalXEvaluationFormTemplate::getAppraisal)
				.collect(Collectors.toList());

		// Security check
		appraisalList.forEach(appraisal -> this.securityService.canReadAppraisal(loggedInUser, employee, appraisal));

		// Get DTOs
		List<AppraisalDTO> appraisalDTOList = appraisalList
				.stream()
				.map(appraisalDTOConverter::convert)
				.collect(Collectors.toList());

		return new ResponseEntity<>(appraisalDTOList, HttpStatus.OK);
	}


	private ResponseEntity<List<EmployeeEvaluationFormDTO>> employeesIdAppraisalsIdFormsGet(
			Employee loggedInUser,
			int employeeId,
			int appraisalId) {

		// Get Employee
		Employee employee = employeeService.getById(employeeId);

		// Get Appraisal
		Appraisal appraisal = appraisalService.getById(appraisalId);

		// Add all EmployeeEvaluationForms where the Employee is the FilledByEmployee
		List<EmployeeEvaluationForm> employeeEvaluationFormList = this.employeeEvaluationFormService
				.findByFilledByEmployeeAndAppraisal(employee, appraisal)
				.collect(Collectors.toList());

		// Security check
		// If the check fails, flow is interrupted
		for (EmployeeEvaluationForm employeeEvaluationForm : employeeEvaluationFormList) {
			this.securityService.canReadEmployeeEvaluationForm(loggedInUser, employeeEvaluationForm);
		}

		// Get DTOs
		List<EmployeeEvaluationFormDTO> result = employeeEvaluationFormList
				.stream()
				.map(employeeEvaluationFormDTOConverter::convert)
				.collect(Collectors.toList());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}


	private ResponseEntity<EvaluationFormTemplateDTO> employeesIdAppraisalsIdFormsIdGet(
			Employee loggedInUser,
			int employeeId,
			int appraisalId,
			int formId) {

		// Get Employee
		Employee employee = employeeService.getById(employeeId);

		// Get EvaluationFormTemplate
		EvaluationFormTemplate evaluationFormTemplate = evaluationFormTemplateService.getById(formId);

		// Get Appraisal
		Appraisal appraisal = appraisalService.getById(appraisalId);

		// Security check
		this.securityService.canReadEvaluationFormTemplate(loggedInUser, employee, evaluationFormTemplate, appraisal);

		// Get DTO
		EvaluationFormTemplateDTO evaluationFormTemplateDTO =
				evaluationFormTemplateDTOConverter.convert(evaluationFormTemplate);

		return new ResponseEntity<>(evaluationFormTemplateDTO, HttpStatus.OK);
	}


	private ResponseEntity<AppraisalDTO> employeesIdAppraisalsIdGet(
			Employee loggedInUser,
			int employeeId,
			int appraisalId) {

		// Get Employee
		Employee employee = employeeService.getById(employeeId);

		// Get Appraisal
		Appraisal appraisal = appraisalService.getById(appraisalId);

		// Security check
		this.securityService.canReadAppraisal(loggedInUser, employee, appraisal);

		// Check if the employee was indeed part of the appraisal
		if (!this.employeeEvaluationFormService
				.findSelfByEmployeeAndAppraisal(employee, appraisal)
				.isPresent()) {
			throw new NotFoundException(String.format(
					"Employee[%d] was not part of Appraisal[%d]",
					employeeId,
					appraisalId));
		}

		return new ResponseEntity<>(appraisalDTOConverter.convert(appraisal), HttpStatus.OK);
	}

}
