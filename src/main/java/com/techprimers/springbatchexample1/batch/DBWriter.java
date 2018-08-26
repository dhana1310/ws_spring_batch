package com.techprimers.springbatchexample1.batch;

import com.techprimers.springbatchexample1.entity.User;
import com.techprimers.springbatchexample1.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {

    	System.err.println("In Writer");
        userRepository.save(users);
    }
}
