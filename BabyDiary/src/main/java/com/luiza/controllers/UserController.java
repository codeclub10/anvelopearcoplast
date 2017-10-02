package com.luiza.controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luiza.models.User;
import com.luiza.models.UserDao;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
@RequestMapping("/user")
public class UserController {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  /**
   * /create  --> Create a new user and save it in the database.
   * 
   * @param email User's email
   * @param name User's name
   * @return A string describing if the user is succesfully created or not.
   */
  @RequestMapping("/create")
  @ResponseBody
  public String create(String email, String parola, String firstName,String lastName,String sex) {
    User user = null;
    try {
      user = new User(email, parola, firstName, lastName, sex);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error creating the user: " + ex.toString();
    }
    return "User succesfully created! (id = " + user.getId() + ")";
  }
  
  /**
   * /delete  --> Delete the user having the passed id.
   * 
   * @param id The id of the user to delete
   * @return A string describing if the user is succesfully deleted or not.
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }
    return "User succesfully deleted!";
  }
  
  /**
   * /get-by-email  --> Return the id for the user having the passed email.
   * 
   * @param email The email to search in the database.
   * @return The user id or a message error if the user is not found.
   */
  @RequestMapping("/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    try {
      User user = userDao.findByEmail(email);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId;
  }
  
  /**
   * /update  --> Update the email and the name for the user in the database 
   * having the passed id.
   * 
   * @param id The id for the user to update.
   * @param email The new email.
   * @param name The new name.
   * @return A string describing if the user is succesfully updated or not.
   */
  @RequestMapping("/update")
  @ResponseBody
  public String updateUser(long id, String email, String firstName, String lastName, String password, String sex) {
    try {
      User user = userDao.findOne(id);
      user.setEmail(email);
      user.setFirstName(firstName);
      user.setPassword(password);
      user.setLastName(lastName);
      user.setSex(sex);
      
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }
  
  @RequestMapping("/getUsers")
  @ResponseBody
  public List<User> getUsers() {
	 Iterable<User> users=null;
	 List<User> target = new ArrayList<>();
    try {
      users =  userDao.findAll();
      this.addAll(target, users.iterator());

    }catch (Exception e) {
		// TODO: handle exception
	}
    return target;

    
  }
  
  private <T> void addAll(Collection<T> collection, Iterator<T> iterator) {
	    while (iterator.hasNext()) {
	        collection.add(iterator.next());
	    }
	}

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  @Autowired
  private UserDao userDao;
  
} // class UserController