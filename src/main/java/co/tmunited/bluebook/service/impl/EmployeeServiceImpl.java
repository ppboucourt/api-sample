package co.tmunited.bluebook.service.impl;

import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.repository.EmployeeRememberCodeRepository;
import co.tmunited.bluebook.repository.EmployeeRepository;
import co.tmunited.bluebook.repository.TypeEmployeeRepository;
import co.tmunited.bluebook.repository.UserRepository;
import co.tmunited.bluebook.repository.search.EmployeeSearchRepository;
import co.tmunited.bluebook.service.EmployeeService;
import co.tmunited.bluebook.service.UserService;
import co.tmunited.bluebook.web.rest.util.BooleanWrapper;
import co.tmunited.bluebook.web.rest.util.LongWrapper;
import co.tmunited.bluebook.web.rest.util.SmsResponseWrapper;
import co.tmunited.bluebook.web.rest.util.VerifyCodeWrapper;
import com.titicservices.dialer.core.MessageSender;
import com.titicservices.dialer.core.domain.SmsMessage;
import com.titicservices.dialer.core.domain.SmsResponse;
import com.titicservices.dialer.core.exception.NotInitializedSenderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * Service Implementation for managing Employee.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Value("#{environment['spring.application.text']}")
    public String TWO_FACTOR_MESSAGE;
    public static final String ALPHABET = "0123456789";
    public static final String PLUS_ONE = "+1";

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private EmployeeSearchRepository employeeSearchRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserService userService;

    @Inject
    private EmployeeRememberCodeRepository employeeRememberCodeRepository;

    /**
     * Save a employee.
     *
     * @param employee the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Employee save(Employee employee) {
        log.debug("Request to save Employee : {}", employee);

        if (employee.getUser().getPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(employee.getUser().getPassword());
            employee.getUser().setPassword(encryptedPassword);

        } else if (employee.getUser().getId() != null) {
            User tmpUser = userRepository.findOne(employee.getUser().getId());
            employee.getUser().setPassword(tmpUser.getPassword());
        }

        employee.getUser().setFirstName(employee.getFirstName());
        employee.getUser().setLastName(employee.getLastName());
        employee.getUser().setEmployee(employee);
        employee.getUser().setLangKey("en");

        employee.getUser().getAuthorities().add(new Authority("ROLE_USER"));
        employee.setDelStatus(false);

        Employee result = employeeRepository.save(employee);
        employeeSearchRepository.save(result);

        return result;
    }


    @Transactional
    public Employee saveFromSms(Employee employee) {
        log.debug("Request to save Employee : {}", employee);

        if (employee.getUser().getId() != null) {
            User tmpUser = userRepository.findOne(employee.getUser().getId());
            employee.getUser().setPassword(tmpUser.getPassword());
        }


        employee.getUser().setFirstName(employee.getFirstName());
        employee.getUser().setLastName(employee.getLastName());
        employee.getUser().setEmployee(employee);
        employee.getUser().setLangKey("en");

        employee.getUser().getAuthorities().add(new Authority("ROLE_USER"));

        employee.setDelStatus(false);

        Employee result = employeeRepository.save(employee);
        employeeSearchRepository.save(result);

        return result;
    }

    /**
     * Get all the employees.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        log.debug("Request to get all Employees");
        List<Employee> result = employeeRepository.findAllByDelStatusIsFalse();

        return result;
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Employee findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        Employee employee = employeeRepository.findOne(id);
        employee.getTypeEmployee().getAuthorities().size();
        return employee;
    }

    /**
     * Delete the  employee by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        Employee employee = employeeRepository.findOne(id);
        employee.setDelStatus(true);
        employee.getUser().setDelStatus(true);
        employee.getUser().setActivated(false);
        Employee result = employeeRepository.save(employee);

        employeeSearchRepository.save(result);
    }

    /**
     * Search for the employee corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Employee> search(String query) {
        log.debug("Request to search Employees for query {}", query);
        List<Employee> result = StreamSupport
            .stream(employeeSearchRepository.search(wrapperQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Employee> findAllByTypeEmployee(Long typeEmployeeId) {
        log.debug("Request to get all Employees who are Therapists");
        List<Employee> result = employeeRepository.findAllByTypeEmployeeIdAndDelStatusIsFalse(typeEmployeeId);

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> findOneByUserLogin(String login) {
        log.debug("Request to get an Employees using user login");
        Optional<Employee> employee = employeeRepository.findOneByUserLogin(login);
        return employee;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean employeeIsValidPin(Long id, String pin) {
        log.debug("Request to test PIN  with id {}", id);
        log.debug("Request to test PIN  with pin {}", pin);
        Optional<Employee> employee = employeeRepository.findOneByIdAndDelStatusIsFalse(id);

        if (employee.isPresent() && employee.get().getPin().equals(pin)) {
            log.debug("PIN test OK");
            return Boolean.TRUE;
        }

        log.debug("PIN test FAIL");
        return Boolean.FALSE;
    }

    @Inject
    TypeEmployeeRepository typeEmployeeRepository;

    @Override
    public List<Employee> findAllPhysicianEmployees() {
        TypeEmployee typePhysician = typeEmployeeRepository.findPhysicianWithEagerRelationshipsDelStatusIsFalse();

        return employeeRepository.findAllByTypeEmployeeAndDelStatusIsFalse(typePhysician);
    }

    @Transactional()
    public LongWrapper isTwoFactorNeeded(String browserUuid) {

        try {
            Employee employee = userService.getEmployeeWithAuthorities();
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime nowMinus30 = now.minusDays(30);
            log.debug("Employee: " + employee.toString());


            EmployeeRememberCode employeeRememberCodeSet = employeeRememberCodeRepository.findByBrowserUuid(browserUuid);

//            log.info("EmployeeRememberCode: " + employeeRememberCodeSet.getBrowserUuid());
//            log.info("EmployeeRememberCode: " + employeeRememberCodeSet.getCodeRemember());
            long value = 0;


            if (employeeRememberCodeSet == null || employeeRememberCodeSet.getCodeRemember()==null || Boolean.FALSE.equals(employeeRememberCodeSet.getCodeRemember()) || nowMinus30.isAfter(employeeRememberCodeSet.getSendDate()) ) {
                sendSmsCode(browserUuid);
                value = 1;// Need Send an Validate
            }

            return new LongWrapper(value);

        } catch (Exception e) {
            e.printStackTrace();
            return new LongWrapper();
        }

    }

    @Transactional()
    public SmsResponseWrapper sendSmsCode(String browserUuid) {

        Employee employee = userService.getEmployeeWithAuthorities();

        String phone = employee.getMobile();

        if (!phone.contains(PLUS_ONE)) {
            phone = PLUS_ONE + phone;
        }

        String code = generateCode();
        SmsMessage smsMessage = new SmsMessage(phone, TWO_FACTOR_MESSAGE + ": " + code);
        try {

            SmsResponse smsResponse = MessageSender.getInstance().sendSms(smsMessage);

            SmsResponseWrapper smsResponseWrapper = new SmsResponseWrapper(smsResponse, "");

            log.debug("SID: " + smsResponseWrapper.getSmsResponse().getSId());

            EmployeeRememberCode employeeRememberCode = employeeRememberCodeRepository.findByBrowserUuid(browserUuid);

            if(employeeRememberCode == null){// If exits, update it, if not create new
                employeeRememberCode = new EmployeeRememberCode();
            }

            employeeRememberCode.setCodeRemember(false);
            employeeRememberCode.setCode(code);
            employeeRememberCode.setBrowserUuid(browserUuid);
            employeeRememberCode.setSid(smsResponse.getSId());
            employeeRememberCode.setSendDate(ZonedDateTime.now());
            employeeRememberCode.setEmployee(employee);
            employeeRememberCodeRepository.save(employeeRememberCode);

            return smsResponseWrapper;
        } catch (NotInitializedSenderException e) {
            log.debug("Error sending code via sms");
            e.printStackTrace();
        }

        return new SmsResponseWrapper();
    }


    @Transactional()
    public BooleanWrapper rememberMe(BooleanWrapper booleanWrapper) {

        Employee employee = userService.getEmployeeWithAuthorities();

        if (booleanWrapper.getValue().equals(Boolean.FALSE)) {
            employee.setCode(null);
            employee.setCodeDate(null);
            employee.setSid(null);
        }

        employee.setCodeRemember(booleanWrapper.getValue());

        employee = employeeRepository.save(employee);
        employeeSearchRepository.save(employee);

        return booleanWrapper;
    }



    public BooleanWrapper verifyCode(VerifyCodeWrapper verifyCodeWrapper){

        Employee employee = userService.getEmployeeWithAuthorities();

        boolean verified= false;
        EmployeeRememberCode employeeRememberCode =  employeeRememberCodeRepository.findByBrowserUuidAndCode(verifyCodeWrapper.getBrowserUuid(), verifyCodeWrapper.getCode());
        if(employeeRememberCode != null){
            verified=true;
        }

        if(verified && verifyCodeWrapper.getRememberMe()==true){
            employeeRememberCode.setCodeRemember(verifyCodeWrapper.getRememberMe());
            employeeRememberCode.setEmployee(employee);
            employeeRememberCodeRepository.save(employeeRememberCode);
        }

        return new BooleanWrapper(verified);
    }


    private String generateCode() {
        final String AB = ALPHABET;
        SecureRandom rnd = new SecureRandom();


        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));


        return sb.toString();
    }

    public int compareTwoDate(ZonedDateTime pass, ZonedDateTime current) {
        return current.compareTo(pass);
    }

}
