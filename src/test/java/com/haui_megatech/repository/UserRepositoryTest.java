/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.haui_megatech.repository;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.Gender;
import com.haui_megatech.model.User;
import com.haui_megatech.repository.impl.UserRepositoryImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vieth
 */
public class UserRepositoryTest {

    private final UserRepository underTest;
    private final ApplicationContext context;

    public UserRepositoryTest() {
        underTest = new UserRepositoryImpl(new ApplicationContext());
        context = new ApplicationContext();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        context.initCounter();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class UserRepository.
     */
    @Test
    public void testSave() {
        User user = User.builder()
                .username("viethoang123")
                .password("123")
                .firstName("Hoàng")
                .lastName("Nguyễn Việt")
                .phoneNumber("0336118268")
                .email("hoangnv.swe@gmail.com")
                .build();
        Optional<User> savedInstance = underTest.save(user);
        System.out.println(savedInstance);
        assertEquals(savedInstance.get(), user);
    }

    /**
     * Test of saveAll method, of class UserRepository.
     */
//    @Test
//    public void testSaveAll() {
//        System.out.println("saveAll");
//        List<User> users = null;
//        UserRepository instance = new UserRepositoryImpl();
//        List<User> expResult = null;
//        List<User> result = instance.addAll(users);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findByUsername method, of class UserRepository.
     */
//    @Test
//    public void testFindByUsername() {
//        System.out.println("findByUsername");
//        String username = "";
//        UserRepository instance = new UserRepositoryImpl();
//        Optional<User> expResult = null;
//        Optional<User> result = instance.findByUsername(username);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getList method, of class UserRepository.
     */
//    @Test
//    public void testGetList() {
//        System.out.println("getList");
//        UserRepository instance = new UserRepositoryImpl();
//        List<User> expResult = null;
//        List<User> result = instance.getList();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of add method, of class UserRepository.
     */
//    @Test
//    public void testAdd() {
//        System.out.println("add");
//        User user = null;
//        UserRepository instance = new UserRepositoryImpl();
//        boolean expResult = false;
//        boolean result = instance.add(user);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of addAll method, of class UserRepository.
     */
//    @Test
//    public void testAddAll() {
//        System.out.println("addAll");
//        ArrayList<User> users = null;
//        UserRepository instance = new UserRepositoryImpl();
//        boolean expResult = false;
//        boolean result = instance.addAll(users);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of update method, of class UserRepository.
     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        int index = 0;
//        User user = null;
//        UserRepository instance = new UserRepositoryImpl();
//        boolean expResult = false;
//        boolean result = instance.update(index, user);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of deleteById method, of class UserRepository.
     */
//    @Test
//    public void testDeleteById() {
//        int preDelete = underTest.getAll().size();
//        System.out.println("deleteById");
//        int index = 10;
//        UserRepository instance = new UserRepositoryImpl();
//        boolean expResult = true;
//        boolean result = instance.deleteById(index);
//        int afterDelete = underTest.getAll().size();
//        
//        assertEquals(expResult, result);
//        assertEquals(preDelete, afterDelete + 1);
//    }

    /**
     * Test of getAll method, of class UserRepository.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        UserRepository instance = new UserRepositoryImpl(new ApplicationContext());
        ArrayList<User> result = instance.getAll();
        assertTrue(result != null);
    }


    
    

}
