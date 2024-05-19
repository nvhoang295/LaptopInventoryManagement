/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.model;

import com.haui_megatech.repository.UserRepository;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author vieth
 */
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static Integer counter;

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Date dateOfBirth;
    private Date whenCreated;
    private Date lastUpdated;
    private Integer logined;
    private Date lastLogined;

    public User(
            Integer id,
            String username,
            String password,
            String firstName,
            String lastName,
            String phoneNumber,
            String email,
            Gender gender,
            Date dateOfBirth,
            Date whenCreated,
            Date lastUpdated,
            Integer logined,
            Date lastLogined
    ) {
        this.id = ++counter;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.whenCreated = whenCreated;
        this.lastUpdated = lastUpdated;
        this.logined = logined;
        this.lastLogined = lastLogined;
    }

    public static void setCounter(Integer value) {
        counter = value;
    }
    
    public static Integer getCounter() {
        return counter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getLogined() {
        return logined;
    }

    public void setLogined(Integer logined) {
        this.logined = logined;
    }

    public Date getLastLogined() {
        return lastLogined;
    }

    public void setLastLogined(Date lastLogined) {
        this.lastLogined = lastLogined;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", whenCreated=" + whenCreated + ", lastUpdated=" + lastUpdated + ", logined=" + logined + '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private Integer id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private Gender gender;
        private Date dateOfBirth;
        private Date whenCreated;
        private Date lastUpdated;
        private Integer logined;
        private Date lastLogined;

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder whenCreated(Date whenCreated) {
            this.whenCreated = whenCreated;
            return this;
        }

        public UserBuilder lastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public UserBuilder logined(Integer logined) {
            this.logined = logined;
            return this;
        }

        public UserBuilder lastLogined(Date lastLogined) {
            this.lastLogined = lastLogined;
            return this;
        }

        public User build() {
            return new User(
                    this.id,
                    this.username,
                    this.password,
                    this.firstName,
                    this.lastName,
                    this.phoneNumber,
                    this.email,
                    this.gender,
                    this.dateOfBirth,
                    this.whenCreated,
                    this.lastUpdated,
                    this.logined,
                    this.lastLogined
            );
        }

    }

}
