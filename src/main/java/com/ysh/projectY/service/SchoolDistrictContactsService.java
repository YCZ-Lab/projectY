package com.ysh.projectY.service;

import com.ysh.projectY.dao.SchoolDistrictContactsDao;
import com.ysh.projectY.entity.SchoolDistrictContacts;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolDistrictContactsService {

    final SchoolDistrictContactsDao schoolDistrictContactsDao;

    public SchoolDistrictContactsService(SchoolDistrictContactsDao schoolDistrictContactsDao) {
        this.schoolDistrictContactsDao = schoolDistrictContactsDao;
    }

    public Optional<SchoolDistrictContacts> findById(int id) {
        return schoolDistrictContactsDao.findById(id);
    }

    public void delete(SchoolDistrictContacts schoolDistrictContacts) {
        schoolDistrictContactsDao.delete(schoolDistrictContacts);
    }

    public void deleteById(int id) {
        schoolDistrictContactsDao.deleteById(id);
    }

    public void saveAndFlush(SchoolDistrictContacts schoolDistrictContacts) {
        schoolDistrictContactsDao.saveAndFlush(schoolDistrictContacts);
    }
}
