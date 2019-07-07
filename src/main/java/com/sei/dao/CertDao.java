package com.sei.dao;

import com.sei.bean.Cert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertDao {

    List<Cert> queryCert(@Param("queryText") String queryText) throws Exception;
}
