package com.ysh.projectY.dao;

import com.ysh.projectY.entity.SchoolsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SchoolsInfoDao extends JpaRepository<SchoolsInfo, Integer> {
    public Optional<SchoolsInfo> findBySchoolName(String schoolName);

    @Query(value = "select * from schools_info order by id", nativeQuery = true)
    List<SchoolsInfo> findAllByPostalcode();

    Optional<SchoolsInfo> findByExternalImportNameContaining(String schoolName);

    int countByExternalImportNameContaining(String schoolName);
}
