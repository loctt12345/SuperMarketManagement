/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.app.model;

import com.loctt.app.service.impl.UserService;
import java.io.Serializable;
import javax.persistence.Query;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author loc12345
 */
public class MemberIDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj)
    {
        Query q = session.createQuery("SELECT m.userID FROM User m ORDER BY m.userID DESC"); 
        q.setMaxResults(1);
        String number = String.format("%03d",  Integer.parseInt(
                q.getSingleResult().toString().substring(3)) + 1);
        return "CUS" + number;
    }
}
