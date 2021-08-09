package com.example.jpaonetoonedemo.controller;


import com.example.jpaonetoonedemo.model.Contact;
import com.example.jpaonetoonedemo.model.Student;
import com.example.jpaonetoonedemo.repository.ContactRepository;
import com.example.jpaonetoonedemo.repository.StudentRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/contacts")
    public List getAllContacts(){
        return contactRepository.findAll();
    }

    @GetMapping("/students/{studentId}/contacts")
    public Object getContactByStudentId(@PathVariable Long studentId) throws NotFoundException {
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }
        List contacts = contactRepository.findByStudentId(studentId);
        if(contacts.size() > 0) {
            return contacts.get(0);
        } else{
            throw new NotFoundException("Contact not found!");
        }
    }

    @PostMapping("/students/{studentId}/contacts")
    public String addContact(@PathVariable Long studentId, @RequestBody Contact contact) throws NotFoundException {
        Optional st = studentRepository.findById(studentId);
        if(st.isPresent()){
            Student student = (Student) st.get();
            contact.setStudent(student);
            return (String) contactRepository.save(contact);
        } else{
            throw new NotFoundException("Student not found with id "+studentId);
        }
    }

    @PutMapping("/contacts/{contactId}")
    public Contact updateContact(@PathVariable Long contactId,@RequestBody Contact contactUpdated) throws NotFoundException {
        Optional ct = contactRepository.findById(contactId);
        if(ct.isPresent()) {
            Contact contact = (Contact) ct.get();
            contact.setCity(contactUpdated.getCity());
            contact.setPhone(contactUpdated.getPhone());
            return (Contact) contactRepository.save(contact);
        } else {
            throw new NotFoundException("Contact not found!");
        }
    }

    @DeleteMapping("/contacts/{contactId}")
    public String deleteContact(@PathVariable Long contactId) throws NotFoundException {
        Optional ct = contactRepository.findById(contactId);
        if(ct.isPresent()) {
            Contact contact = (Contact) ct.get();
            contactRepository.delete(contact);
            return "Delete Successfully";
        }
        else {
            throw new NotFoundException("Contact not found!");
        }
    }
}
