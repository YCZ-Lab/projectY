package com.ysh.projectY;

import com.ysh.projectY.task.ImportBCSchoolsCOVID19;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectYTests {

    @Autowired
    ImportBCSchoolsCOVID19 importBCSchoolsCOVID19;

    //    @Test
//    @Transactional(readOnly = true)
    void contextLoads() {
        importBCSchoolsCOVID19.importBCSchoolsCOVID19Info();
    }

}
