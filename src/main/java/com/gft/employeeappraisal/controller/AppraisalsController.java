package com.gft.employeeappraisal.controller;

import com.gft.employeeappraisal.Validation.EmployeeEvaluationFormDateValidate;
import com.gft.employeeappraisal.converter.appraisal.AppraisalDTOConverter;
import com.gft.employeeappraisal.converter.employeeevaluationform.EmployeeEvaluationFormDTOConverter;
import com.gft.employeeappraisal.converter.employeeevaluationformanswer.EmployeeEvaluationFormAnswerDTOConverter;
import com.gft.employeeappraisal.converter.evaluationform.EvaluationFormDTOConverter;
import com.gft.employeeappraisal.converter.evaluationformtemplate.EvaluationFormTemplateDTOConverter;
import com.gft.employeeappraisal.exception.NotFoundException;
import com.gft.employeeappraisal.model.Appraisal;
import com.gft.employeeappraisal.model.Employee;
import com.gft.employeeappraisal.model.EmployeeEvaluationForm;
import com.gft.employeeappraisal.model.EvaluationFormTemplate;
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
	private final ValidationService validationService;

	// DTO converters
	private final AppraisalDTOConverter appraisalDTOConverter;
	private final EmployeeEvaluationFormDTOConverter employeeEvaluationFormDTOConverter;
	private final EvaluationFormDTOConverter evaluationFormDTOConverter;
	private final EvaluationFormTemplateDTOConverter evaluationFormTemplateDTOConverter;
	private final EmployeeEvaluationFormAnswerDTOConverter employeeEvaluationFormAnswerDTOConverter;

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
			EmployeeEvaluationFormAnswerDTOConverter employeeEvaluationFormAnswerDTOConverter,
            EvaluationFormDTOConverter evaluationFormDTOConverter,
			ValidationService validationService

	) {
		this.appraisalService = appraisalService;
		this.employeeService = employeeService;
		this.employeeEvaluationFormService = employeeEvaluationFormService;
		this.evaluationFormTemplateService = evaluationFormTemplateService;
		this.securityService = securityService;
		this.appraisalDTOConverter = appraisalDTOConverter;
		this.employeeEvaluationFormDTOConverter = employeeEvaluationFormDTOConverter;
		this.evaluationFormTemplateDTOConverter = evaluationFormTemplateDTOConverter;
		this.employeeEvaluationFormAnswerDTOConverter = employeeEvaluationFormAnswerDTOConverter;
		this.evaluationFormDTOConverter = evaluationFormDTOConverter;
		this.validationService=validationService;
	}




	@Override
	public ResponseEntity<EmployeeEvaluationFormDTO> employeesIdAppraisalsIdFormsPut(@PathVariable Integer employeeId,
			@PathVariable Integer appraisalId, @RequestBody EmployeeEvaluationFormDTO evaluationFormBody) {

		logger.debug("{} called endpoint: employees/{employeeid}/appraisals/{appraisalId}/forms", employeeId,appraisalId);
		EmployeeEvaluationForm employeeEvaluationForm = new EmployeeEvaluationForm();
		EmployeeEvaluationFormDTO response = new EmployeeEvaluationFormDTO();

		this.validationService.validate(response);

		Employee employee = this.employeeService.getById(employeeId);

		Appraisal appraisal = this.appraisalService.getById(appraisalId);


		if (evaluationFormBody.getSubmitDate().equals("")||evaluationFormBody.getSubmitDate() == null) {

			employeeEvaluationForm = employeeEvaluationFormDTOConverter.convertBack(evaluationFormBody);
			employeeEvaluationFormService.saveAndFlush(employeeEvaluationForm);
			//call here saveAndContinue(), user can continue any time,so not saving date
		}
		else {// call here saveAndSubmit(),user click saveAndSubmit,then system capture current datetime

			employeeEvaluationForm = employeeEvaluationFormDTOConverter.convertBack(evaluationFormBody);
			int dateValidate = evaluationFormBody.getCreateDate().compareTo(evaluationFormBody.getSubmitDate());
			boolean dateValidateIfequal = evaluationFormBody.getSubmitDate().equals(evaluationFormBody.getCreateDate());

			EmployeeEvaluationFormDateValidate.validateDate(dateValidate);
			if(dateValidate==0||dateValidateIfequal==true||dateValidate==1)
			{
				OffsetDateTime submitDay = OffsetDateTime.now();
				employeeEvaluationForm.setSubmitDate(submitDay);
				employeeEvaluationFormService.saveAndFlush(employeeEvaluationForm);
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@Override
	public ResponseEntity<EvaluationFormTemplateDTO> employeesIdAppraisalsIdFormsIdAnswersPut(@PathVariable("employeeId") Integer employeeId, @PathVariable("appraisalId") Integer appraisalId,
																							  @PathVariable("formId") Integer formId, @Valid @RequestBody EvaluationFormTemplateDTO employee) {
		EvaluationFormTemplateDTO response = new EvaluationFormTemplateDTO();

		this.validationService.validate(response);
		EvaluationFormTemplate template = new EvaluationFormTemplate();
		template = evaluationFormTemplateDTOConverter.convertBack(employee);
		List<SectionDTO> sectionDTOS = employee.getSections();
		this.evaluationFormTemplateService.saveAndFlush(template);
		return new ResponseEntity<>(HttpStatus.OK);
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
				.map(EmployeeEvaluationForm::getAppraisal)
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

	@Override
	public ResponseEntity<List<AnswerDTO>> employeesIdAppraisalsIdFormsIdAnswersGet(
			@PathVariable("employeeId") Integer employeeId,
			@PathVariable("appraisalId") Integer appraisalId,
			@PathVariable("formId") Integer formId) {
		Employee user = this.employeeService.getLoggedInUser();
		logger.debug("{} called endpoint: GET /employees/{}/appraisals/{}/forms/{}/answers", user.getEmail(), employeeId,
				appraisalId, formId);
		return this.employeesIdAppraisalsIdFormsIdAnswersGet(user, employeeId, appraisalId, formId);
	}

	private ResponseEntity<List<AnswerDTO>> employeesIdAppraisalsIdFormsIdAnswersGet(
			Employee loggedInUser,
			Integer employeeId,
			Integer appraisalId,
			Integer formId
	) {
		// Get Employee
		Employee employee = employeeService.getById(employeeId);

		// Get Appraisal
		Appraisal appraisal = appraisalService.getById(appraisalId);

		// Security check
		this.securityService.canReadAppraisal(loggedInUser, employee, appraisal);

		EmployeeEvaluationForm employeeEvaluationForm = employeeEvaluationFormService
				.findByEmployeeAndFilledByEmployeeAndAppraisal(loggedInUser, employee, appraisal)
				.orElseThrow(() -> new NotFoundException(String.format(
						"EmployeeEvaluationForm not found for Employee [%d] and Appraisal[%d]",
						employeeId,
						appraisalId)));

		List<AnswerDTO> answerDTOList = employeeEvaluationForm.getEmployeeEvaluationFormAnswerSet()
				.stream()
				.map(employeeEvaluationFormAnswerDTOConverter::convert)
				.collect(Collectors.toList());

		return new ResponseEntity<>(answerDTOList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EvaluationFormDTO> evaluationFormIdGet(@PathVariable("formId") Integer formId) {
        this.logger.debug("GET evaluationFormIdGet(formId:"+formId+")");
		EmployeeEvaluationForm evalForm = this.employeeEvaluationFormService.getById(formId.intValue());
        return new ResponseEntity<>(this.evaluationFormDTOConverter.convert(evalForm), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> evaluationFormIdPut(@PathVariable("formId") Integer formId, @Valid @RequestBody EvaluationFormDTO evaluationFormDTO) {
		this.logger.debug("PUT evaluationFormIdGet(formId:"+formId+")");
		EmployeeEvaluationForm employeeEvaluationForm = this.employeeEvaluationFormService.getById(formId);
		this.evaluationFormDTOConverter.convertBack(evaluationFormDTO, employeeEvaluationForm);
		this.employeeEvaluationFormService.saveAndFlush(employeeEvaluationForm);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
