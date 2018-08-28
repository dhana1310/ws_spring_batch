package com.dhana1310.springbatch.batch.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.dhana1310.springbatch.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BatchProcessor implements ItemProcessor<User, User> {

    private static final Map<String, String> DEPT_NAMES =
            new HashMap<>();

    public BatchProcessor() {
        DEPT_NAMES.put("001", "Technology");
        DEPT_NAMES.put("002", "Operations");
        DEPT_NAMES.put("003", "Accounts");
    }

    @Override
    public User process(User user) throws Exception {
        String deptCode = user.getDept();
        String dept = DEPT_NAMES.get(deptCode);
        user.setDept(dept);
        user.setTime(new Date());
        
    	user.setSalary(user.getSalary() + 5000);
        
    	log.info("In processor - "+ user);
        return user;
    }
}
